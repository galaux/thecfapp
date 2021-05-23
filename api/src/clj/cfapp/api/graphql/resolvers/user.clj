(ns cfapp.api.graphql.resolvers.user
  (:require
    [cfapp.api.db.domain.user :as user]
    [cfapp.api.util :refer [anomaly->error anomaly?]]
    [com.walmartlabs.lacinia.resolve :refer [resolve-as]]
    [datomic.client.api :as d]
    [camel-snake-kebab.core :as csk]
    [cfapp.api.util :refer [kebab-keys]]))

(defn- shape-user
  [{:user/keys [id screen-name email]}]
  {:id id
   :name screen-name
   :email email})

(defn get-user
  [{:keys [conn]} {:keys [id]} _]
  (let [res (user/by-id (d/db conn) id)]
    (when (:user/id res)
      (shape-user res))))

(defn add-user
  [{:keys [conn]} {:keys [input]} _value]
  (let [res (->> input kebab-keys (user/create conn))]
    (if (anomaly? res)
      (resolve-as false (anomaly->error res))
      (shape-user res))))

(defn list-users
  [{:keys [conn]} _args _value]
  (->> (user/all (d/db conn))
       (map shape-user)))

(defn by-score-id
  [{:keys [conn]} _args {:keys [id]}]
  (->> (user/by-score-id (d/db conn) id)
       shape-user))
