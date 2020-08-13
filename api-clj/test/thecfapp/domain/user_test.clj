(ns thecfapp.domain.user-test
  (:require
   [clojure.data.json :as json]
   [clojure.spec.gen.alpha :as gen]
   [clojure.test :refer [deftest is]]
   [integrant.core :as ig]
   [thecfapp.domain.community :as community]
   [thecfapp.domain.user :as user]
   [thecfapp.test-util :as tutil]))

(def user-a (gen/generate user/user-gen))
(def user-b (gen/generate user/user-gen))
(def expected-user-a {:id (.toString (:user/id user-a))
                      :name (:user/screen-name user-a)
                      :email (:user/email user-a)
                      :communities []})
(def community-a (gen/generate community/community-gen))

;; (comment add-users
;;   (let [graphql-q
;;         "mutation AddUser($screenName: String!, $email: String!) {
;;            createUser(
;;              input: {
;;                screenName: $screenName,
;;                email: $email
;;              }
;;            ) {
;;              id email screenName
;;              communities { id name }
;;            }
;;          }"
;;         variables {:screenName (:user/screen-name user-a)
;;                    :email (:user/email user-a)}
;;         response ((app) (-> (request :post "/api/graphql")
;;                             (body (json/write-str {:query graphql-q
;;                                                    :variables variables}))
;;                             (content-type "application/graphql")))
;;         parsed-response (-> response
;;                             :body
;;                             (json/read-str :key-fn keyword))]
;;     (is (= 200 (:status response)))
;;     (is (empty? (:errors parsed-response)))
;;     (let [created-user (-> parsed-response :data :createUser)]
;;       (is (= (dissoc expected-user-a :id)
;;              (dissoc created-user :id)))
;;       (is (uuid? (UUID/fromString (:id created-user)))))))

(deftest list-users-not-used-by-mobile
  (let [{:keys [:thecfapp.db/conn :thecfapp/handler] :as system} (tutil/setup)]
     (tutil/domain-add conn [user-a])
     (let [graphql-q
           "query ListUsers {
              users {
                id name email
                communities { id name }
              }
            }"
           gql-query (tutil/test-query graphql-q [] nil)
           response (handler gql-query)
           parsed-response (-> response
                               :body
                               (json/read-str :key-fn keyword))]
      (is (= 200 (:status response)))
      (is (empty? (:errors parsed-response)))
      (is (= [expected-user-a] (-> parsed-response :data :users))))
    (ig/halt! system)))

(deftest get-user
  (let [{:keys [:thecfapp.db/conn :thecfapp/handler] :as system} (tutil/setup)
        gql-query (tutil/test-query :GetUser
                                    [:User]
                                    {:userId (.toString (:user/id user-a))})]
    (tutil/domain-add conn [user-a])
    (let [response (handler gql-query)
          parsed-response (-> response
                              :body
                              (json/read-str :key-fn keyword))
          expected-user-a {:id (.toString (:user/id user-a))
                           :name (:user/screen-name user-a)}]
      (is (= 200 (:status response)))
      (is (empty? (:errors parsed-response)))
      (is (= expected-user-a
             (-> parsed-response :data :user))))
    (ig/halt! system)))

;; (deftest test-macro
;;   (tutil/with-system [{:keys [:thecfapp.db/client
;;                               :thecfapp.db/conn
;;                               :thecfapp/handler] :as system} (tutil/setup)]
;;     (println "|| " system)
;;     (is (= 1
;;           1))))

;; (comment leave-community
;;   (tutil/domain-add db/conn
;;                     [(assoc community-a :db/id "community-a")
;;                      (assoc user-a
;;                        :db/id "user-a"
;;                        :community.privs/member "community-a")])
;;   (let [user-id (:user/id user-a)
;;         community-id (:community/id community-a)
;;         privs-before (d/q '[:find (pull ?user [*]) (pull ?community [*])
;;                             :where [?user :community.privs/member ?community]]
;;                        (d/db db/conn))
;;         graphql-q
;;         "mutation LeaveCommunity($userId: ID!, $communityId: ID!) {
;;            leaveCommunity(userId: $userId, communityId: $communityId) {
;;              id name
;;            }
;;          }"
;;         variables {:userId (.toString user-id)
;;                    :communityId (.toString community-id)}
;;         response ((app) (-> (request :post "/api/graphql")
;;                             (body (json/write-str {:query graphql-q
;;                                                    :variables variables}))
;;                             (content-type "application/graphql")))
;;         parsed-response (-> response
;;                             :body
;;                             (json/read-str :key-fn keyword))
;;         privs-after (d/q '[:find (pull ?user [*]) (pull ?community [*])
;;                            :where [?user :community.privs/member ?community]]
;;                       (d/db db/conn))]
;;     (is (not (empty? privs-before)))
;;     (is (empty? privs-after))))
