(ns cfapp.api.graphql.resolvers.user-test
  (:require
   [clojure.data.json :as json]
   [clojure.spec.gen.alpha :as gen]
   [clojure.test :refer [deftest is]]
   [integrant.core :as ig]
   [cfapp.api.db.domain.community :as community]
   [cfapp.api.db.domain.user :as user]
   [cfapp.api.test-util :as tutil]))

(def user-a (gen/generate user/user-gen))
(def user-b (gen/generate user/user-gen))
(def expected-user-a
  {:id          (.toString (:user/id user-a))
   :name        (:user/screen-name user-a)
   :email       (:user/email user-a)
   :communities []})
(def community-a (gen/generate community/community-gen))

(def ^:private q-list-users
  "query ListUsers {
     users {
       id name email
       communities { id name }
     }
   }")

(deftest list-users ;; not used by mobile
  (let [{:keys [:cfapp.api.db/conn
                :cfapp.api/handler]
         :as   system}
        (tutil/setup)]
    (tutil/domain-add conn [user-a])
    (let [gql-query       (tutil/test-query q-list-users nil)
          response        (handler gql-query)
          parsed-response (-> response
                              :body
                              (json/read-str :key-fn keyword))]
      (is (= 200 (:status response)))
      (is (empty? (:errors parsed-response)))
      (is (= [expected-user-a]
             (-> parsed-response
                 :data
                 :users))))
    (ig/halt! system)))

(def ^:private q-get-user
  "query GetUser($userId: ID!) {
     user(id: $userId) {
       id
       name
     }
   }")

(deftest get-user
  (let
    [{:keys [:cfapp.api.db/conn :cfapp.api/handler] :as system} (tutil/setup)
     gql-query (tutil/test-query
                 q-get-user
                 {:userId (.toString (:user/id user-a))})]
    (tutil/domain-add conn [user-a])
    (let [response        (handler gql-query)
          parsed-response (-> response
                              :body
                              (json/read-str :key-fn keyword))
          expected-user-a {:id   (.toString (:user/id user-a))
                           :name (:user/screen-name user-a)}]
      (is (= 200 (:status response)))
      (is (empty? (:errors parsed-response)))
      (is (= expected-user-a
             (-> parsed-response
                 :data
                 :user))))
    (ig/halt! system)))
