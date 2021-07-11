(ns cfapp.api.graphql.resolvers.community
  (:require
    [cfapp.api.db.domain.community :as community]
    [cfapp.api.util :refer [anomaly->error anomaly? kebab-keys]]
    [com.walmartlabs.lacinia.resolve :refer [resolve-as]]
    [datomic.client.api :as d]))

(defn shape-community-address
  [community]
  (let [{:community.address/keys [street zip-code city country]} community]
    {:street street
     :zipCode zip-code
     :city city
     :country country}))

(defn shape-community
  [community]
  (let [{:community/keys [id name]} community]
    {:id id
     :name name
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
  (let [{:keys [db-after] :as res} (community/user-join conn (kebab-keys input))]
    (if (anomaly? res)
      (resolve-as false (anomaly->error res))
      (->> (d/pull db-after '[*] [:community/id (:communityId input)])
           shape-community))))

(defn leave-community
  "Returns the communityId (FIXME that doesn't really make sense.
   Return sthing more meaningful like the user or the community
   depending on what mobile could need)"
  [{:keys [conn]} {:keys [input]} _value]
  (community/user-leave conn (kebab-keys input)))
