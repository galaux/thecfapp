(ns cfapp.api.db.domain.score
  (:require
   [cfapp.api.db.domain.user :as user]
   [cfapp.api.db.domain.wod :as wod]
   [cfapp.api.util :as util :refer [anomaly]]
   [clojure.spec.alpha :as s]
   [cognitect.anomalies :as anomalies]
   [datomic.client.api :as d])
  (:import
   java.util.UUID))

(def one-hour-in-ms
  (* 60 60 1000))

(s/def :score/id :util/uuid)
(s/def :score/value (s/int-in 1 one-hour-in-ms))

(s/def ::score
  (s/keys :req [:score/id
                :score/value]))

(def score-gen (s/gen ::score))


(def ^:private q-by-user-id-and-wod-id
  '[:find (pull ?score [*])
    :in $ ?user-id ?wod-id
    :where
    [?wod :wod/id ?wod-id]
    [?score :score/wod ?wod]
    [?user :user/id ?user-id]])

(defn by-user-id-and-wod-id
  [db user-id wod-id]
  (->> (d/q q-by-user-id-and-wod-id db user-id wod-id)
       first))

(def ^:private q-by-wod-id
  '[:find (pull ?score [*])
    :in $ ?wod-id
    :where
    [?score :score/wod ?wod]
    [?wod :wod/id ?wod-id]])

(defn by-wod-id
  [db id]
  (->> (d/q q-by-wod-id db id)
       flatten))

(defn- create-tx
  [{:keys [user-id wod-id value]}]
  [{:score/id    (UUID/randomUUID)
    :score/user  {:user/id user-id}
    :score/wod   {:wod/id wod-id}
    :score/value value}])

(defn- check-create
  [db {:keys [user-id wod-id value]}]
  (or (and (not (user/exists? db user-id))
           (anomaly ::anomalies/incorrect "User doesn't exist"))
      (and (not (wod/exists? db wod-id))
           (anomaly ::anomalies/incorrect "WoD doesn't exist"))))

(defn create
  [conn input]
  (or (check-create (d/db conn) input)
      (d/transact conn {:tx-data (create-tx input)})))
