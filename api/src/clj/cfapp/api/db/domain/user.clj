(ns cfapp.api.db.domain.user
  (:require
    [cfapp.api.spec-util :as sutil]
    [cfapp.api.util :refer [anomaly anomaly->error]]
    [clojure.spec.alpha :as s]
    [clojure.spec.gen.alpha :as gen]
    [cognitect.anomalies :as anomalies]
    [com.walmartlabs.lacinia.resolve :refer [resolve-as]]
    [datomic.client.api :as d]
    [talltale.core :as fake])
  (:import
    java.util.UUID))

(s/def :user/id :util/uuid)
(s/def :user/screen-name :util/non-blank-string)
(s/def :user/email :util/email)

(defn- gen-user
  []
  (gen/fmap
    (fn [{:keys [email username]}]
      {:user/id (UUID/randomUUID)
       :user/screen-name (str username (rand-int 100))
       :user/email email})
    (fake/person-gen)))

(s/def ::user
  (s/with-gen
    (s/keys :req [:user/id
                  :user/screen-name
                  :user/email])
    gen-user))

(def user-gen (s/gen ::user))

(defn by-id
  [db id]
  (d/pull db '[*] [:user/id id]))

(defn with-name-or-email
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

(defn- create-tx
  [{:keys [screen-name email]}]
  {:user/id (UUID/randomUUID)
   :user/screen-name screen-name
   :user/email email})

(defn- check-create
  [db {:keys [screen-name email]}]
  (and (some? (with-name-or-email db screen-name email))
       (anomaly ::anomalies/incorrect "A user with this name or email already exists")))

(defn create
  [conn input]
  (if-let [anom (check-create (d/db conn) input)]
    (resolve-as false (anomaly->error anom))
    (let [tx-data (create-tx input)
          {:keys [db-after]} (d/transact conn {:tx-data [tx-data]})]
      (by-id db-after (:user/id tx-data)))))

(defn exists?
  [db id]
  (-> (d/pull db [:db/id] [:user/id id])
      :db/id
      some?))

(def ^:private q-all
  '[:find (pull ?e pull-expr)
    :in $ pull-expr
    :where [?e :user/id]])

(defn all
  [db]
  (->> (d/q q-all db '[*])
       flatten))

(def q-by-score-id
  '[:find (pull ?user [*])
    :in $ ?score-id
    :where
    [?score :score/user ?user]
    [?score :score/id ?score-id]])

(defn by-score-id
  [db score-id]
  (->> (d/q q-by-score-id db score-id)
       ffirst))
