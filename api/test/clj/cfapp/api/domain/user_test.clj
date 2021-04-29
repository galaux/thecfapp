(ns cfapp.api.domain.user-test
  (:require
   [clojure.data.json :as json]
   [clojure.spec.gen.alpha :as gen]
   [clojure.test :refer [deftest is]]
   [integrant.core :as ig]
   [cfapp.api.domain.community :as community]
   [cfapp.api.domain.user :as user]
   [cfapp.api.test-util :as tutil])
  (:import java.util.UUID))

(def user-a (gen/generate user/user-gen))
(def user-b (gen/generate user/user-gen))
(def expected-user-a {:id (.toString (:user/id user-a))
                      :name (:user/screen-name user-a)
                      :email (:user/email user-a)
                      :communities []})
(def community-a (gen/generate community/community-gen))


(deftest add-users-not-used-by-mobile
  (let [{:keys [:cfapp.api.db/conn :cfapp.api/handler] :as system} (tutil/setup)
        graphql-q
        "mutation AddUser($name: String!, $email: String!) {
           createUser(
             input: {
               screenName: $name,
               email: $email
             }
           ) {
             id email name
             communities { id name }
           }
         }"
        variables {:name (:user/screen-name user-a)
                   :email (:user/email user-a)}
        gql-query (tutil/test-query graphql-q [] variables)]
    (let [response (handler gql-query)
          parsed-response (-> response
                              :body
                              (json/read-str :key-fn keyword))
          expected-user-a {:id (.toString (:user/id user-a))
                           :name (:user/screen-name user-a)
                           :email (:user/email user-a)
                           :communities []}]
      (is (= 200 (:status response)))
      (is (empty? (:errors parsed-response)))
      (let [created-user (-> parsed-response :data :createUser)]
        (is (= (dissoc expected-user-a :id)
               (dissoc created-user :id)))
        (is (uuid? (UUID/fromString (:id created-user))))))
    (ig/halt! system)))

(deftest list-users-not-used-by-mobile
  (let [{:keys [:cfapp.api.db/conn :cfapp.api/handler] :as system} (tutil/setup)]
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
  (let [{:keys [:cfapp.api.db/conn :cfapp.api/handler] :as system} (tutil/setup)
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
      (is (= expected-user-a (-> parsed-response :data :user))))
    (ig/halt! system)))

;; (deftest test-macro
;;   (tutil/with-system [{:keys [:cfapp.api.db/client
;;                               :cfapp.api.db/conn
;;                               :cfapp.api/handler] :as system} (tutil/setup)]
;;     (println "|| " system)
;;     (is (= 1
;;           1))))
