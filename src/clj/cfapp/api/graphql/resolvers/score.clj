(ns cfapp.api.graphql.resolvers.score
  (:require
   [cfapp.api.db.domain.score :as score]
   [cfapp.api.util :as util :refer [anomaly->error anomaly? kebab-keys]]
   [com.walmartlabs.lacinia.resolve :refer [resolve-as]]
   [datomic.client.api :as d]))

(defn shape-score
  [{:score/keys [id value]}]
  {:id    id
   :value value})

(defn for-wod
  [{:keys [conn]} _args {:keys [id]}]
  (->> (score/by-wod-id (d/db conn) id)
       (map shape-score)))

(defn by-wod-id
  [{:keys [conn]} {:keys [id]} _value]
  (->> (score/by-wod-id (d/db conn) id)
       (map shape-score)))

(defn add-score
  [{:keys [conn]} {:keys [input]} _value]
  (let [{:keys [user-id wod-id] :as kebab-input} (kebab-keys input)
        {:keys [db-after] :as res} (score/create conn
                                                 kebab-input)]
    (if (anomaly? res)
      (resolve-as nil (anomaly->error res))
      (-> (score/by-user-id-and-wod-id db-after user-id wod-id)
          first
          shape-score))))
