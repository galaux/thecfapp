(ns cfapp.api.graphql.resolvers.user
  (:require
   [cfapp.api.db.domain.user :as user]
   [datomic.client.api :as d]))

(defn- shape-user
  [{:user/keys [id screen-name email]}]
  {:id    id
   :name  screen-name
   :email email})

(defn get-user
  [{:keys [conn]} {:keys [id]} _]
  (let [res (user/by-id (d/db conn) id)]
    (when (:user/id res)
      (shape-user res))))

(defn list-users
  [{:keys [conn]} _args _value]
  (->> (user/all (d/db conn))
       (map shape-user)))

(defn by-score-id
  [{:keys [conn]} _args {:keys [id]}]
  (->> (user/by-score-id (d/db conn) id)
       shape-user))
