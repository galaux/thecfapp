(ns thecfapp.domain.community-test
  (:require
   [camel-snake-kebab.core :as csk]
   [clojure.data.json :as json]
   [clojure.spec.gen.alpha :as gen]
   [clojure.test :refer [deftest is]]
   [datomic.client.api :as d]
   [integrant.core :as ig]
   [thecfapp.db.core :as db]
   [thecfapp.domain.community :as community]
   [thecfapp.domain.score :as score]
   [thecfapp.domain.user :as user]
   [thecfapp.domain.wod :as wod]
   [thecfapp.domain.workout :as workout]
   [thecfapp.graphql :as gql]
   [thecfapp.test-util :as tutil])
  (:import
   java.util.UUID))

(def user-a (-> (gen/generate user/user-gen) (assoc :db/id "user-a")))
(def user-b (-> (gen/generate user/user-gen) (assoc :db/id "user-b")))
(def community-a (-> (gen/generate community/community-gen) (assoc :db/id "community-a")))
(def community-b (-> (gen/generate community/community-gen) (assoc :db/id "community-b")))
(def community-c (-> (gen/generate community/community-gen) (assoc :db/id "community-c")))

(deftest find-communities
  (let [{:keys [:thecfapp.db/conn :thecfapp/handler] :as system} (tutil/setup)
        gql-query (tutil/test-query :FindCommunities
                                    [:Community :CommunityAddress]
                                    {:name "HAHA"})
        communities [(assoc community-a :community/name "HaHa Comm A")
                     (assoc community-b :community/name "Comm B haHA")] ;; note case
        user-a (assoc user-a :community.privs/member communities)
        user-b (assoc user-b :community.privs/member communities)
        user-c (gen/generate user/user-gen)
        expected-communities (map (fn [community]
                                    {:id (.toString (:community/id community))
                                     :name (:community/name community)
                                     :address {:street (:community.address/street community)
                                               :zipCode (:community.address/zip-code community)
                                               :city (:community.address/city community)
                                               :country (:community.address/country community)}
                                     :memberCount 2})
                                  communities)]
    (tutil/domain-add conn (concat communities [user-a user-b user-c]))
    (let [response (handler gql-query)
          parsed-response (-> response
                              :body
                              (json/read-str :key-fn keyword))
          communities (-> parsed-response
                          :data
                          :findCommunities)]
      (is (= 200 (:status response)))
      (is (empty? (:errors parsed-response)))
      (is (= 2 (count communities)))
      (is (= expected-communities communities))
      (is (uuid? (-> communities first :id UUID/fromString))))
    (ig/halt! system)))

(deftest get-community
  (let [{:keys [:thecfapp.db/conn :thecfapp/handler] :as system} (tutil/setup)
        communities (gen/sample community/community-gen)
        searched-community (-> communities first (assoc :db/id "searched-community"))
        workout-a (-> (gen/generate workout/workout-gen) (assoc :db/id "workout-a"))
        wods (->> (gen/sample wod/wod-gen 3)
                  (map-indexed (fn [i wod]
                                 (assoc wod
                                        :db/id (str "wod-" i)
                                        :wod/community searched-community
                                        :wod/workout workout-a))))
        scores-map (->> wods
                        (map (fn [wod]
                               [(:wod/id wod)
                                (assoc (gen/generate score/score-gen)
                                       :score/wod wod
                                       :score/user (gen/generate user/user-gen))]))
                        (into {}))
        gql-query (tutil/test-query :CommunityDetail
                                    [:Community
                                     :CommunityAddress
                                     :Wod
                                     :Workout
                                     :Score
                                     :ScoreWithBelongsToLoggedUser]
                                    {:communityId (.toString (:community/id searched-community))})]
    (tutil/domain-add conn (concat communities wods (vals scores-map)))
    (let [response (handler gql-query)
          parsed-response (-> response
                              :body
                              (json/read-str :key-fn keyword))
          expected-workout {:id (.toString (:workout/id workout-a))
                            :name (:workout/name workout-a)
                            :description (:workout/description workout-a)
                            :scoringType (csk/->SCREAMING_SNAKE_CASE_STRING (:workout/scoring-type workout-a))}
          expected-wods (map (fn [wod]
                               {:id (.toString (:wod/id wod))
                                :date (.format gql/date-formatter (:wod/date wod))
                                :workout expected-workout
                                :scores [(let [score (get scores-map (:wod/id wod))]
                                           {:id (.toString (:score/id score))
                                            :value (:score/value score)
                                            :user {:id (.toString (:user/id (:score/user score)))}})]})
                             wods)
          expected-community {:id (.toString (:community/id searched-community))
                              :name (:community/name searched-community)
                              :address {:street (:community.address/street searched-community)
                                        :zipCode (:community.address/zip-code searched-community)
                                        :city (:community.address/city searched-community)
                                        :country (:community.address/country searched-community)}
                              :wods expected-wods
                              :memberCount 0}]
      (is (empty? (:errors parsed-response)))
      (is (= 200 (:status response)))
      (is (= expected-community (-> parsed-response :data :community))))
    (ig/halt! system)))

(deftest user-communities
  (let [{:keys [:thecfapp.db/conn :thecfapp/handler] :as system} (tutil/setup)
        user-communities (gen/sample community/community-gen 3)
        other-communities (gen/sample community/community-gen 3)
        tx-data (conj other-communities
                      (assoc user-a
                             :community.privs/member user-communities))
        fragments [:Community :CommunityAddress]
        variables {:userId (.toString (:user/id user-a))}
        gql-query (tutil/test-query :UserCommunities fragments variables)]
    (tutil/domain-add conn tx-data)
    (let [response (handler gql-query)
          parsed-response (-> response
                              :body
                              (json/read-str :key-fn keyword))
          expected-communities (map (fn [community]
                                      {:id (.toString (:community/id community))
                                       :name (:community/name community)
                                       :address {:street (:community.address/street community)
                                                 :zipCode (:community.address/zip-code community)
                                                 :city (:community.address/city community)
                                                 :country (:community.address/country community)}
                                       :memberCount 1})
                                    user-communities)]
      (is (= 200 (:status response)))
      (is (empty? (:errors parsed-response)))
      (is (= expected-communities (-> parsed-response :data :userCommunities))))
    (ig/halt! system)))

(deftest join-community
  (let [{:keys [:thecfapp.db/conn :thecfapp/handler] :as system} (tutil/setup)
        variables {:userId (.toString (:user/id user-a))
                   :communityId (.toString (:community/id community-a))}
        gql-query (tutil/test-query :JoinCommunity [:Community] variables)]
    (tutil/domain-add conn [user-a community-a])
    (let [response (handler gql-query)
          parsed-response (-> response
                              :body
                              (json/read-str :key-fn keyword))
          expected-community {:id (.toString (:community/id community-a))
                              :name (:community/name community-a)}]
      (is (empty? (:errors parsed-response)))
      (is (= 200 (:status response)))
      (is (= expected-community (-> parsed-response :data :addUserToCommunity)))
      (let [privs (d/q '[:find ?priv-name
                         :in $ ?user-id ?community-id
                         :where
                         [?user :user/id ?user-id]
                         [?community :community/id ?community-id]
                         [?user ?priv ?community]
                         [?priv :db/ident ?priv-name]]
                       (d/db conn)
                       (:user/id user-a)
                       (:community/id community-a))]
        (is (= [[:community.privs/member]] privs))))
    (ig/halt! system)))

(deftest leave-community
  (let [{:keys [:thecfapp.db/conn :thecfapp/handler] :as system} (tutil/setup)
        variables {:userId (.toString (:user/id user-a))
                   :communityId (.toString (:community/id community-a))}
        gql-query (tutil/test-query :LeaveCommunity [] variables)
        tx-data [(assoc community-a :db/id "community-a")
                 (assoc user-a
                        :db/id "user-a"
                        :community.privs/member "community-a")]]
    (tutil/domain-add conn tx-data)
    (let [privs-before (d/q '[:find (pull ?user [*]) (pull ?community [*])
                              :where [?user :community.privs/member ?community]]
                            (d/db conn))
          response (handler gql-query)
          parsed-response (-> response
                              :body
                              (json/read-str :key-fn keyword))
          privs-after (d/q '[:find (pull ?user [*]) (pull ?community [*])
                             :where [?user :community.privs/member ?community]]
                           (d/db conn))]
      (is (empty? (:errors parsed-response)))
      (is (= 200 (:status response)))
      (is (not (empty? privs-before)))
      (is (empty? privs-after)))
    (ig/halt! system)))

;; (deftest create-community
;;   (let [{:keys [:thecfapp.db/conn :thecfapp/handler] :as system} (tutil/setup)
;;         variables {}
;;         gql-query (tutil/test-query :CreateCommunity [] variables)
;; TODO finish this test (from here on, the code is just a copy/paste the previous test)
;;         tx-data [(assoc community-a :db/id "community-a")
;;                  (assoc user-a
;;                         :db/id "user-a"
;;                         :community.privs/member "community-a")]]
;;     (tutil/domain-add conn tx-data)
;;     (let [privs-before (d/q '[:find (pull ?user [*]) (pull ?community [*])
;;                               :where [?user :community.privs/member ?community]]
;;                             (d/db conn))
;;           response (handler gql-query)
;;           parsed-response (-> response
;;                               :body
;;                               (json/read-str :key-fn keyword))
;;           privs-after (d/q '[:find (pull ?user [*]) (pull ?community [*])
;;                              :where [?user :community.privs/member ?community]]
;;                            (d/db conn))]
;;       (is (empty? (:errors parsed-response)))
;;       (is (= 200 (:status response)))
;;       (is (not (empty? privs-before)))
;;       (is (empty? privs-after)))
;;     (ig/halt! system)))
