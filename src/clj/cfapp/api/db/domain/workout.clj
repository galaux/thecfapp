(ns cfapp.api.db.domain.workout
  (:require
   [clojure.spec.alpha :as s]
   [clojure.spec.gen.alpha :as gen]
   [clojure.string :as str]
   [datomic.client.api :as d]
   [cfapp.api.spec-util :refer [fake-data]]
   [clojure.edn :as edn]
   [clojure.java.io :as io])
  (:import
   java.util.UUID))

(s/def :workout/id :util/uuid)
(s/def :workout/name :util/non-blank-string)

(s/def :movement/id keyword?)
(s/def :movement/name :util/non-blank-string)
(s/def ::movement
  (s/keys
    :req [:movement/id]
    :opt [:movement/name]))
(s/def ::rep-count pos-int?)
(s/def :exercise/movement ::movement)
(s/def :exercise/rep-count ::rep-count)

(s/def ::application-value (s/and float? pos?))
(s/def ::unit
  #{:unit/calorie
    :unit/millisecond
    :unit/foot
    :unit/meter
    :unit/mile
    :unit/bodyweight
    :unit/kilogram
    :unit/pood
    :unit/pound})

(s/def :exercise.application.value/all ::application-value)
(s/def ::exercise-application-value-all
  (s/keys :req [:exercise.application.value/all]))

(s/def :exercise.application.value/men ::application-value)
(s/def :exercise.application.value/women ::application-value)
(s/def ::exercise-application-value-men-women
  (s/keys :req [:exercise.application.value/men
                :exercise.application.value/women]))

(s/def ::exercise-application-value
  (s/or :all       ::exercise-application-value-all
        :men-women ::exercise-application-value-men-women))

(s/def :exercise.application/value ::exercise-application-value)
(s/def :exercise.application/unit ::unit)
(s/def ::application
  (s/keys :req [:exercise.application/value
                :exercise.application/unit]))

(s/def :exercise/application ::application)
(s/def ::exercise
  (s/keys :req [:exercise/movement]
          :opt [:exercise/rep-count
                :exercise/application]))

(s/def :workout.loop/tasks
  (s/coll-of
    (s/or :exercise ::exercise
          :loop     ::workout-loop)
    :min-count 1))
(s/def :workout.loop/duration pos-int?)
(s/def :workout.loop/period pos-int?)
(s/def ::workout-loop-for-time
  (s/keys :req [:workout.loop/tasks]))
(s/def ::workout-loop-amrap
  (s/keys :req [:workout.loop/tasks]
          :opt [:workout.loop/duration]))
(s/def ::workout-loop-emom
  (s/keys :req [:workout.loop/tasks]
          :opt [:workout.loop/duration
                :workout.loop/period]))
(s/def ::workout-loop
  (s/or :amrap    ::workout-loop-amrap
        :for-time ::workout-loop-for-time
        :emom     ::workout-loop-emom))

;; TODO We should probably mimic the strategy used in the Antlr grammar and
;; have a loop be a parent-loop or a flat-loop so that a workout generator
;; would only generate workouts with max depth of 2
(s/def :workout/loop ::workout-loop)

(def known-workouts
  (->> (merge
         (edn/read-string (slurp (io/resource "workouts/custom.edn")))
         (edn/read-string (slurp (io/resource "workouts/named.edn"))))
       vals
       (map :db)
       set))

(s/def ::workout
  (s/with-gen
    (s/keys :req [:workout/loop]
            :opt [:workout/name])
    ;; TODO The spec for a workout is too "wide" it allows generating loops with
    ;; infinite depths so we (temporarily?) make it return know workouts instead
    #(s/gen known-workouts)))

(def workout-gen (s/gen ::workout))


(def pp-exercise
  [{:exercise/movement
    [:movement/id
     :movement/name]}
   {:exercise/application
    [{:exercise.application/value
      [:exercise.application.value/all
       :exercise.application.value/men
       :exercise.application.value/women]}
     :exercise.application/unit]}
   :exercise/rep-count])

(def pp-loop-shallow
  [:workout.loop/time-cap
   :workout.loop/duration
   :workout.loop/period
   :workout.loop/round-count
   :workout.loop/score-type
   {:workout.loop/tasks pp-exercise}])

(def pp-task
  (concat pp-exercise
          pp-loop-shallow))

(def pp-task-shallow
  pp-exercise)

(def pp-workout
  [:workout/id
   :workout/name
   {:workout/loop
    [:workout.loop/time-cap
     :workout.loop/duration
     :workout.loop/period
     :workout.loop/round-count
     :workout.loop/score-type
     {:workout.loop/tasks pp-task}]}])

(def ^:private q-all
  '[:find (pull ?e pull-expr)
    :in $ pull-expr
    :where [?e :workout/id]])

(defn all
  [db]
  (->> (d/q q-all db pp-workout)
       flatten))

(def q-by-wod-id
  '[:find (pull ?workout pull-pattern)
    :in $ ?wod-id pull-pattern
    :where
    [?wod :wod/id ?wod-id]
    [?wod :wod/workout ?workout]])

(defn by-wod-id
  [db id]
  (->> (d/q q-by-wod-id db id pp-workout)
       ffirst))

(def ^:private q-by-user-id
  '[:find (pull ?workout [*])
    :in $ ?user-id
    :where
    [?user :user/id ?user-id]
    [?score :score/user ?user]
    [?score :score/wod ?wod]
    [?wod :wod/workout ?workout]
    [?workout :workout/id ?workout-id]])

(defn by-user-id
  [db id]
  (->> (d/q q-by-user-id db id)
       flatten))

(defn exists?
  [db id]
  (-> (d/pull db [:db/id] [:workout/id id])
      :db/id
      some?))
