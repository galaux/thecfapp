(ns cfapp.api.domain.wod
  (:require [clojure.spec.alpha :as s]
            [cognitect.anomalies :as anomalies]
            [datomic.client.api :as d]
            [cfapp.api.domain.community :as community]
            [cfapp.api.domain.workout :as workout]
            [cfapp.api.spec-util :as sutil]
            [cfapp.api.util :refer [anomaly anomaly->error anomaly? camel-case-keys]]
            [com.walmartlabs.lacinia.resolve :refer [resolve-as]]
            [cfapp.api.util :refer [anomaly->error]])
  (:import java.util.UUID))

(s/def :wod/id :util/uuid)
(s/def :wod/date :util/near-local-date)

(s/def ::wod
  (s/keys :req [:wod/id
                :wod/date]))

(def wod-gen (s/gen ::wod))


(defn shape-wod
  [{:wod/keys [id date]}]
  {:id id
   :date date})

(defn exists?
  [db id]
  (-> (d/pull db [:db/id] [:wod/id id])
      :db/id
      some?))

(defn get-wod*
  [db id]
  (d/pull db '[*] [:wod/id id]))

(defn get-wod
  [{:keys [conn]} {:keys [id]} _]
  (let [res (get-wod* (d/db conn) id)]
    (when (:wod/id res)
      (camel-case-keys res))))

(def q-by-user-id
  '[:find (pull ?wod [*])
    :in $ ?user-id
    :where
    [?score :score/wod ?wod]
    [?score :score/user ?user]
    [?user :user/id ?user-id]])

(defn by-user-id
  [{:keys [conn]} {:keys [userId]} _value]
  (->> (d/q q-by-user-id (d/db conn) userId)
       flatten
       (map camel-case-keys)))

(def q-by-score-id
  '[:find (pull ?wod [*])
    :in $ ?score-id
    :where
    [?score :score/wod ?wod]
    [?score :score/id ?score-id]])

(defn by-score-id
  [{:keys [conn]} _args {:keys [id]}]
  (->> (d/q q-by-score-id (d/db conn) id)
       ffirst
       camel-case-keys))

(def q-by-community-id
  '[:find (pull ?wod [*])
    :in $ ?community-id
    :where
    [?community :community/id ?community-id]
    [?wod :wod/community ?community]])

(defn by-community-id
  [{:keys [conn]} _args {:keys [id]}]
  (->> (d/q q-by-community-id (d/db conn) id)
       flatten
       (map camel-case-keys)))

(defn add-wod-tx
  [community-id workout-id date]
  {:db/id "new-wod-eid"
   :wod/id (UUID/randomUUID)
   :wod/community {:community/id community-id}
   :wod/workout {:workout/id workout-id}
   :wod/date date})

(defn check-add-wod
  [db community-id workout-id]
  (or (and (not (community/exists? db community-id))
           (anomaly ::anomalies/incorrect "Community doesn't exist"))
      (and (not (workout/exists? db workout-id))
           (anomaly ::anomalies/incorrect "Workout doesn't exist"))))

(defn add-wod*
  [conn community-id workout-id date]
  (or (check-add-wod (d/db conn) community-id workout-id)
      (let [tx-data (add-wod-tx community-id workout-id date)]
        (d/transact conn {:tx-data [tx-data]}))))

(defn add-wod
  [context args _value]
  (let [{:keys [communityId workoutId date]} (:input args)
        {:keys [db-after tempids] :as res} (add-wod* (:conn context) communityId workoutId date)]
    (if (anomaly? res)
      (resolve-as false (anomaly->error res))
      (->> (get tempids "new-wod-eid")
           (d/pull db-after '[*]) ;; TODO should we pull * or just a set of date+id?
           shape-wod))))

;; TODO
;; Quandary: should "low level" API function pull the :db/id of children so that
;; they are readily available for the resolver to get them or should they be pulled on demand?
