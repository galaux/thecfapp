(ns cfapp.api.graphql.resolvers.wod
  (:require
   [cfapp.api.db.domain.wod :as wod]
   [cfapp.api.util :refer [anomaly->error anomaly?]]
   [com.walmartlabs.lacinia.resolve :refer [resolve-as]]
   [datomic.client.api :as d]))

(defn shape-wod
  [{:wod/keys [id date]}]
  {:id   id
   :date date})

(defn get-wod
  [{:keys [conn]} {:keys [id]} _]
  (let [res (wod/by-id (d/db conn) id)]
    (when (:wod/id res)
      (shape-wod res))))

(defn by-user-id
  [{:keys [conn]} {:keys [userId]} _value]
  (->> (wod/by-user-id (d/db conn) userId)
       (map shape-wod)))

(defn by-score-id
  [{:keys [conn]} _args {:keys [id]}]
  (->> (wod/by-score-id (d/db conn) id)
       shape-wod))

(defn by-community-id
  [{:keys [conn]} _args {:keys [id]}]
  (->> (wod/by-community-id (d/db conn) id)
       (map shape-wod)))

(defn add-wod
  [{:keys [conn]} {:keys [input]} _value]
  (let [{:keys [communityId workoutId date]} input
        res                                  (wod/create conn
                                                         communityId
                                                         workoutId
                                                         date)]
    (if (anomaly? res)
      (resolve-as false (anomaly->error res))
      (shape-wod res))))
