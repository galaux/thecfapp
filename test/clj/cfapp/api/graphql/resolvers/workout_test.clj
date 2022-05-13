(ns cfapp.api.graphql.resolvers.workout-test
  (:require
   [camel-snake-kebab.core :as csk]
   [cfapp.api.db.core :as db]
   [cfapp.api.db.domain.score :as score]
   [cfapp.api.db.domain.user :as user]
   [cfapp.api.db.domain.wod :as wod]
   [cfapp.api.db.domain.workout :as workout]
   [cfapp.api.test-util :as tutil]
   [clojure.data.json :as json]
   [clojure.spec.gen.alpha :as gen]
   [clojure.test :refer [deftest is]]
   [integrant.core :as ig]))

(defn make-scores
  [owner]
  (map (fn [score wod workout]
         (assoc score
           :score/wod  (assoc wod :wod/workout workout)
           :score/user owner))
    (gen/sample score/score-gen 3)
    (gen/sample wod/wod-gen 3)
    (gen/sample workout/workout-gen 3)))

(deftest user-workout-not-used-by-mobile
  (let
    [{:keys [:cfapp.api.db/conn :cfapp.api/handler] :as system} (tutil/setup)
     user-a        (-> (gen/generate user/user-gen)
                       (assoc :db/id "user-a"))
     user-b        (-> (gen/generate user/user-gen)
                       (assoc :db/id "user-b"))
     user-scores   (make-scores user-a)
     other-scores  (make-scores user-b)
     variables     {:userId (.toString (:user/id user-a))}
     gql-query-str
       "query UserWorkouts($userId: ID!) {
                         userWorkouts(id: $userId) {
                           id name description
                         }
                       }"
     gql-query     (tutil/test-query gql-query-str variables)]
    (tutil/domain-add conn user-scores)
    (let [response          (handler gql-query)
          parsed-response   (-> response
                                :body
                                (json/read-str :key-fn keyword))
          expected-workouts (map
                              (fn [db-score]
                                (let [db-workout (-> db-score
                                                     :score/wod
                                                     :wod/workout)]
                                  {:id          (.toString (:workout/id
                                                             db-workout))
                                   :name        (:workout/name db-workout)
                                   :description (:workout/description
                                                  db-workout)}))
                              user-scores)]
      (is (= 200 (:status response)))
      (is (empty? (:errors parsed-response)))
      (is (= expected-workouts
             (-> parsed-response
                 :data
                 :userWorkouts))))
    (ig/halt! system)))

(def ^:private q-user-workouts
  "query UserWorkouts {
     workouts {
       id
       name
       description
       scoringType
     }
   }")

(deftest list-workouts
  (let [{:keys [:cfapp.api.db/conn :cfapp.api/handler] :as system} (tutil/setup)
        workouts  (gen/sample workout/workout-gen 3)
        gql-query (tutil/test-query q-user-workouts nil)]
    (tutil/domain-add conn workouts)
    (let [response          (handler gql-query)
          parsed-response   (-> response
                                :body
                                (json/read-str :key-fn keyword))
          expected-workouts (map
                              (fn [db-workout]
                                {:id          (.toString (:workout/id
                                                           db-workout))
                                 :name        (:workout/name db-workout)
                                 :description (:workout/description db-workout)
                                 :scoringType (csk/->SCREAMING_SNAKE_CASE_STRING
                                                (:workout/scoring-type
                                                  db-workout))})
                              workouts)]
      (is (= 200 (:status response)))
      (is (empty? (:errors parsed-response)))
      (is (= expected-workouts
             (-> parsed-response
                 :data
                 :workouts))))
    (ig/halt! system)))
