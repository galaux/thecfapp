(ns cfapp.api.graphql.resolvers.workout
  (:require
   [cfapp.api.db.domain.workout :as workout]
   [cfapp.api.util :refer [ident->enum-value]]
   [datomic.client.api :as d]))

(defn shape-workout
  [{:workout/keys [id name description scoring-type]}]
  {:id          id
   :name        name
   :description description
   :scoringType (ident->enum-value scoring-type)})

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
