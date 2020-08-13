(ns thecfapp.domain.wod-test
  (:require
   [camel-snake-kebab.core :as csk]
   [clojure.data.json :as json]
   [clojure.spec.gen.alpha :as gen]
   [clojure.test :refer [deftest is]]
   [integrant.core :as ig]
   [thecfapp.db.core :as db]
   [thecfapp.domain.community :as community]
   [thecfapp.domain.score :as score]
   [thecfapp.domain.user :as user]
   [thecfapp.domain.wod :as wod]
   [thecfapp.domain.workout :as workout]
   [thecfapp.graphql :as gql]
   [thecfapp.test-util :as tutil]))

(def user-a (-> (gen/generate user/user-gen) (assoc :db/id "user-a")))
(def community-a (gen/generate community/community-gen))
(def wod-a (-> (gen/generate wod/wod-gen) (assoc :db/id "wod-a")))
(def workout-a (gen/generate workout/workout-gen))
(def score-a (-> (gen/generate score/score-gen) (assoc :db/id "score-a")))
(def score-b (-> (gen/generate score/score-gen) (assoc :db/id "score-b")))

(deftest get-wodj
  (let [{:keys [:thecfapp.db/conn :thecfapp/handler] :as system} (tutil/setup)
        tx-data [user-a
                 (assoc wod-a
                        :wod/workout workout-a
                        :wod/community community-a)
                 score-a
                 score-b
                 [:db/add "score-a" :score/wod "wod-a"]
                 [:db/add "score-a" :score/user "user-a"]
                 [:db/add "score-b" :score/wod "wod-a"]
                 [:db/add "score-b" :score/user "user-a"]]
        fragments [:Score
                   :ScoreWithBelongsToLoggedUser
                   :User
                   :Wod
                   :Workout]
        variables {:wodId (.toString (:wod/id wod-a))}
        gql-query (tutil/test-query :WodWithScores fragments variables)]
    (tutil/domain-add conn tx-data)
    (let [response (handler gql-query)
          parsed-response (-> response
                              :body
                              (json/read-str :key-fn keyword))
          expected-user {:id (.toString (:user/id user-a))
                         :name (:user/screen-name user-a)}
          expected-wod {:id (.toString (:wod/id wod-a))
                        :date (.format gql/date-formatter (:wod/date wod-a))
                        :workout {:id (.toString (:workout/id workout-a))
                                  :name (:workout/name workout-a)
                                  :description (:workout/description workout-a)
                                  :scoringType (-> workout-a
                                                   :workout/scoring-type
                                                   csk/->SCREAMING_SNAKE_CASE_STRING)}
                        :scores [{:id (.toString (:score/id score-a))
                                  :value (:score/value score-a)
                                  :user expected-user}
                                 {:id (.toString (:score/id score-b))
                                  :value (:score/value score-b)
                                  :user expected-user}]}]
      (is (= 200 (:status response)))
      (is (empty? (:errors parsed-response)))
      (is (= expected-wod (-> parsed-response :data :wod))))
    (ig/halt! system)))

(deftest user-wods
  (let [{:keys [:thecfapp.db/conn :thecfapp/handler] :as system} (tutil/setup)
        user-a (assoc user-a :db/id "user-a")
        user-scores (map (fn [score wod]
                           (assoc score
                                  :score/user user-a
                                  :score/wod (assoc wod
                                                    :wod/community (gen/generate community/community-gen)
                                                    :wod/workout (gen/generate workout/workout-gen))))
                         (gen/sample score/score-gen 3)
                         (gen/sample wod/wod-gen 3))
        other-wods (gen/sample wod/wod-gen 3)
        fragments [:Score
                   :ScoreWithBelongsToLoggedUser
                   :Wod
                   :Workout]
        variables {:userId (.toString (:user/id user-a))}
        gql-query (tutil/test-query :UserWods fragments variables)]
    (tutil/domain-add conn (concat other-wods user-scores))
    (let [response (handler gql-query)
          parsed-response (-> response
                              :body
                              (json/read-str :key-fn keyword))
          expected-wods (map (fn [db-score]
                              (let [db-wod (:score/wod db-score)
                                    db-workout (-> db-wod :wod/workout)
                                    db-community (-> db-wod :wod/community)]
                                {:id (.toString (:wod/id db-wod))
                                 :date (.format gql/date-formatter (:wod/date db-wod))
                                 :scores [{:id (.toString (:score/id db-score))
                                           :value (:score/value db-score)
                                           :user {:id (.toString (:user/id (:score/user db-score)))}}]
                                 :workout {:id (.toString (:workout/id db-workout))
                                           :name (:workout/name db-workout)
                                           :description (:workout/description db-workout)
                                           :scoringType (csk/->SCREAMING_SNAKE_CASE_STRING (:workout/scoring-type db-workout))}
                                 :community {:id (.toString (:community/id db-community))
                                             :name (:community/name db-community)}}))
                             user-scores)]
      (is (empty? (:errors parsed-response)))
      (is (= 200 (:status response)))
      (is (= expected-wods (-> parsed-response :data :userWods))))
    (ig/halt! system)))
