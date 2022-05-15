(ns cfapp.api.graphql.resolvers.workout
  (:require
   [cfapp.api.db.domain.workout :as workout]
   [cfapp.api.util :refer [ident->enum-value]]
   [com.walmartlabs.lacinia.schema :refer [tag-with-type]]
   [datomic.client.api :as d]))

(defn- shape-movement
  [{:movement/keys [id name]}]
  {:id   id
   :name name})

(defn shape-application-value
  [{:exercise.application.value/keys [all men women]}]
  (cond-> {}
    all   (assoc :all all)
    men   (assoc :men men)
    women (assoc :women women)))

(defn- shape-application
  [{:exercise.application/keys [value unit class]}]
  (cond->
    {:value (shape-application-value value)}
    unit  (assoc :unit (ident->enum-value unit))
    class (assoc :class (ident->enum-value class))))

(defn- shape-exercise
  [{:exercise/keys [movement application rep-count]}]
  (tag-with-type
    (cond->
      {:movement (shape-movement movement)
       :repCount rep-count}
      application (assoc :application (shape-application application)))
    :Exercise))

(defn- shape-loop
  [{:workout.loop/keys
    [round-count score-type cap-time duration period tasks]}]
  (let [gql-loop (cond->
                   {:tasks (map
                             (fn [task]
                               (cond
                                 (some? (:exercise/movement task))
                                 (shape-exercise task)
                                 (some? (:workout.loop/tasks task))
                                 (shape-loop task)
                                 :else
                                 (throw (ex-info "Bad Task type"
                                                 {:task task}))))
                             tasks)}
                   cap-time    (assoc :capTime cap-time)
                   duration    (assoc :duration duration)
                   period      (assoc :period period)
                   round-count (assoc :roundCount round-count)
                   score-type  (assoc :scoreType
                                 (ident->enum-value score-type)))]
    (tag-with-type gql-loop :WorkoutLoop)))

(defn shape-workout
  [{:workout/keys [id name loop]}]
  (cond->
    {:id   id
     :loop (shape-loop loop)}
    (some? name) (assoc :name name)))

(defn list-workouts
  [{:keys [conn]} _args _value]
  (->> (workout/all (d/db conn))
       (map shape-workout)))

(defn by-wod-id
  [{:keys [conn]} _args {:keys [id]}]
  (->> (workout/by-wod-id (d/db conn) id)
       shape-workout))

(defn by-user-id
  [{:keys [conn]} {:keys [id]} _value]
  (->> (workout/by-user-id (d/db conn) id)
       (map shape-workout)))
