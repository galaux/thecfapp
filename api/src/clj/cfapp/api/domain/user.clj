(ns cfapp.api.domain.user
  (:require
   [clojure.spec.alpha :as s]
   [cognitect.anomalies :as anomalies]
   [com.walmartlabs.lacinia.resolve :refer [resolve-as]]
   [datomic.client.api :as d]
   [cfapp.api.spec-util :as sutil]
   [cfapp.api.util :refer [anomaly anomaly->error]])
  (:import
   (java.util UUID)))

(s/def :user/id :util/uuid)
(s/def :user/screen-name :util/non-blank-string)
(s/def :user/email :util/email)

(s/def ::user
  (s/keys :req [:user/id
                :user/screen-name
                :user/email]))

(def user-gen (s/gen ::user))


(defn shape-user
  [{:user/keys [id screen-name email]}]
  {:id id
   :name screen-name
   :email email})

(defn get-user*
  [db id]
  (d/pull db '[*] [:user/id id]))

(defn get-user
  [{:keys [conn]} {:keys [id]} _]
  (let [res (get-user* (d/db conn) id)]
    (when (:user/id res)
      (shape-user res))))

(defn user-with-name-or-email
  [db username email]
  (-> (d/q '[:find ?user
             :in $ ?screen-name ?email
             :where
             [?user :user/id]
             (or-join [?user ?screen-name ?email]
                      [?user :user/screen-name ?screen-name]
                      [?user :user/email ?email])]
        db username email)
      ffirst))

(defn add-user-tx
  [{:keys [screenName email]}]
  {:user/id (UUID/randomUUID)
   :user/screen-name screenName
   :user/email email})

(defn check-add-user
  [db {:keys [screenName email]}]
  (and (some? (user-with-name-or-email db screenName email))
       (anomaly ::anomalies/incorrect "A user with this name or email already exists")))

(defn add-user
  [context args _value]
  (if-let [anom (check-add-user (d/db (:conn context)) (:input args))]
    (resolve-as false (anomaly->error anom))
    (let [tx-data (add-user-tx (:input args))
          {:keys [db-after]} (d/transact (:conn context) {:tx-data [tx-data]})]
      (->> (:user/id tx-data)
           (get-user* db-after)
           shape-user))))

(defn exists?
  [db id]
  (-> (d/pull db [:db/id] [:user/id id])
      :db/id
      some?))

(def ^:private find-all-query
  '[:find (pull ?e pull-expr)
    :in $ pull-expr
    :where [?e :user/id]])

(defn list-users
  [{:keys [conn]} _ _]
  (->> (d/q find-all-query (d/db conn) '[*])
       flatten
       (map shape-user)))

(def q-by-score-id
  '[:find (pull ?user [*])
    :in $ ?score-id
    :where
    [?score :score/user ?user]
    [?score :score/id ?score-id]])

(defn by-score-id
  [{:keys [conn]} _args {:keys [id]}]
  (let [res (d/q q-by-score-id (d/db conn) id)]
    (->> res
         ffirst
         shape-user)))
