(ns cfapp.api.domain.score
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]
            [cognitect.anomalies :as anomalies]
            [com.walmartlabs.lacinia.resolve :refer [resolve-as]]
            [datomic.client.api :as d]
            [cfapp.api.domain.user :as user]
            [cfapp.api.domain.wod :as wod]
            [cfapp.api.util :as util :refer [anomaly anomaly->error anomaly?]])
  (:import java.util.UUID))

(def one-hour-in-ms
  (* 60 60 1000))

(s/def :score/id :util/uuid)
(s/def :score/value (s/int-in 1 one-hour-in-ms))

(s/def ::score
  (s/keys :req [:score/id
                :score/value]))

(def score-gen (s/gen ::score))


(defn shape-score
  [{:score/keys [id value]}]
  {:id id
   :value value})

(def q-by-user-id-and-wod-id
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

(def q-by-wod-id
  '[:find (pull ?score [*])
    :in $ ?wod-id
    :where
    [?score :score/wod ?wod]
    [?wod :wod/id ?wod-id]])

(defn by-wod-id
  [{:keys [conn]} _args {:keys [id]}]
  (->> (d/q q-by-wod-id (d/db conn) id)
       flatten
       (map shape-score)))

(defn add-score-tx
  [{:keys [userId wodId value]}]
  [{:score/id (UUID/randomUUID)
    :score/user {:user/id userId}
    :score/wod {:wod/id wodId}
    :score/value value}])

(defn check-add-score
  [db {:keys [userId wodId value]}]
  (or (and (not (user/exists? db userId))
           (anomaly ::anomalies/incorrect "User doesn't exist"))
      (and (not (wod/exists? db wodId))
           (anomaly ::anomalies/incorrect "WoD doesn't exist"))))

(defn add-score*
  [conn {:keys [userId wodId value] :as input}]
  (or (check-add-score (d/db conn) input)
      (d/transact conn {:tx-data (add-score-tx input)})))

(defn add-score
  [{:keys [conn]} {:keys [input]} _value]
  (let [{:keys [userId wodId]} input
        {:keys [db-after] :as res} (add-score* conn input)]
    (if (anomaly? res)
      (resolve-as nil (anomaly->error res))
      (-> (by-user-id-and-wod-id db-after userId wodId)
          first
          shape-score))))
