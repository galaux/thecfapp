(ns cfapp.api.graphql.resolvers.community
  (:require
   [cfapp.api.db.domain.community :as community]
   [cfapp.api.util :refer [anomaly->error anomaly? kebab-keys]]
   [com.walmartlabs.lacinia.resolve :refer [resolve-as]]
   [datomic.client.api :as d]))

(defn shape-community-address
  [community]
  (let [{:community.address/keys [street zip-code city country]} community]
    {:street  street
     :zipCode zip-code
     :city    city
     :country country}))

(defn shape-community
  [community]
  (let [{:community/keys [id name]} community]
    {:id      id
     :name    name
     :address (shape-community-address community)}))

(defn get-community
  [{:keys [conn]} {:keys [id]} _value]
  (->> (community/by-id (d/db conn) id)
       shape-community))

(defn user->communities
  [{:keys [conn]} _args {:keys [id]}]
  (if id
    (->> (community/by-user-id (d/db conn) id)
         (map shape-community))
    []))

(defn user-communities
  [{:keys [conn]} {:keys [userId]} _value]
  (->> (community/by-user-id (d/db conn) userId)
       (map shape-community)))

(defn list-communities
  [{:keys [conn]} _args _value]
  (->> (community/all (d/db conn))
       (map shape-community)))

(defn find-with-name-like
  [{:keys [conn]} {:keys [name]} _value]
  (->> (community/find-with-name-like (d/db conn) name)
       (map shape-community)))

(defn add-community
  [{:keys [conn]} {:keys [creatorId input]} _value]
  (let [res (community/create conn creatorId (kebab-keys input))]
    (if (anomaly? res)
      (resolve-as nil (anomaly->error res))
      (shape-community res))))

(defn by-wod-id
  [{:keys [conn]} _args {:keys [id]}]
  (->> (community/by-wod-id (d/db conn) id)
       shape-community))

(defn member-count
  [{:keys [conn]} _args {:keys [id]}]
  (community/member-count (d/db conn) id))

(defn member?
  [{:keys [conn]} args community]
  (community/member? (d/db conn) (:id community) (:userId args)))

(defn user-join
  [{:keys [conn]} {:keys [input]} _value]
  (let [{:keys [community-id] :as input} (kebab-keys input)
        {:keys [db-after] :as res}       (community/user-join conn input)]
    (if (anomaly? res)
      (resolve-as false (anomaly->error res))
      (shape-community (community/by-id db-after community-id)))))

(defn leave-community
  [{:keys [conn]} {:keys [input]} _value]
  (let [{:keys [community-id] :as input} (kebab-keys input)
        {:keys [db-after] :as result}    (community/user-leave conn input)]
    (if (anomaly? result)
      (resolve-as nil (anomaly->error result))
      (shape-community (community/by-id db-after community-id)))))
