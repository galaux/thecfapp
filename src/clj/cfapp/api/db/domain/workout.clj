(ns cfapp.api.db.domain.workout
  (:require
   [clojure.spec.alpha :as s]
   [clojure.spec.gen.alpha :as gen]
   [clojure.string :as str]
   [datomic.client.api :as d]
   [cfapp.api.spec-util :refer [fake-data]])
  (:import
   java.util.UUID))

(s/def :workout/id :util/uuid)
(s/def :workout/name :util/non-blank-string)
(s/def :workout/description :util/non-blank-string)
(s/def :workout/scoring-type
  #{:workout.scoring-type/duration-ms ;; TODO wrong
    :workout.scoring-type/repetitions})

(defn- gen-workout
  []
  (gen/fmap
    (fn [[scoring-type lorem1 lorem2 lorem3 lorem4]]
      {:workout/id           (UUID/randomUUID)
       :workout/name         (->> [lorem1 lorem2 lorem3]
                                  (map str/capitalize)
                                  (str/join " "))
       :workout/description  lorem4
       :workout/scoring-type scoring-type})
    (gen/tuple
      (s/gen :workout/scoring-type)
      (s/gen (set (get-in fake-data [:lorem-ipsum])))
      (s/gen (set (get-in fake-data [:lorem-ipsum])))
      (s/gen (set (get-in fake-data [:lorem-ipsum])))
      (s/gen (set (get-in fake-data [:lorem-ipsum]))))))

(s/def ::workout
  (s/with-gen
    (s/keys :req [:workout/id
                  :workout/name
                  :workout/description
                  :workout/scoring-type])
    gen-workout))

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
  [:workout.loop/cap-time
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
    [:workout.loop/cap-time
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
