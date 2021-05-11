(ns cfapp.api.db.domain.wod
  (:require
    [cfapp.api.db.domain.community :as community]
    [cfapp.api.db.domain.workout :as workout]
    [cfapp.api.util :refer [anomaly]]
    [clojure.spec.alpha :as s]
    [cognitect.anomalies :as anomalies]
    [datomic.client.api :as d])
  (:import
    java.util.UUID))

(s/def :wod/id :util/uuid)
(s/def :wod/date :util/near-local-date)

(s/def ::wod
  (s/keys :req [:wod/id
                :wod/date]))

(def wod-gen (s/gen ::wod))


(defn exists?
  [db id]
  (-> (d/pull db [:db/id] [:wod/id id])
      :db/id
      some?))

(defn by-id
  [db id]
  (d/pull db '[*] [:wod/id id]))

(def q-by-user-id
  '[:find (pull ?wod [*])
    :in $ ?user-id
    :where
    [?score :score/wod ?wod]
    [?score :score/user ?user]
    [?user :user/id ?user-id]])

(defn by-user-id
  [db user-id]
  (->> (d/q q-by-user-id db user-id)
       flatten))

(def q-by-score-id
  '[:find (pull ?wod [*])
    :in $ ?score-id
    :where
    [?score :score/wod ?wod]
    [?score :score/id ?score-id]])

(defn by-score-id
  [db id]
  (->> (d/q q-by-score-id db id)
       ffirst))

(def q-by-community-id
  '[:find (pull ?wod [*])
    :in $ ?community-id
    :where
    [?community :community/id ?community-id]
    [?wod :wod/community ?community]])

(defn by-community-id
  [db id]
  (->> (d/q q-by-community-id db id)
       flatten))

(defn- create-tx
  [community-id workout-id date]
  {:db/id "new-wod-eid"
   :wod/id (UUID/randomUUID)
   :wod/community {:community/id community-id}
   :wod/workout {:workout/id workout-id}
   :wod/date date})

(defn- check-create
  [db community-id workout-id]
  (or (and (not (community/exists? db community-id))
           (anomaly ::anomalies/incorrect "Community doesn't exist"))
      (and (not (workout/exists? db workout-id))
           (anomaly ::anomalies/incorrect "Workout doesn't exist"))))

(defn create
  [conn community-id workout-id date]
  (or (check-create (d/db conn) community-id workout-id)
      (let [tx-data (create-tx community-id workout-id date)
            {:keys [db-after]} (d/transact conn {:tx-data [tx-data]})]
        (by-id db-after (:wod/id tx-data)))))

;; TODO
;; Quandary: should "low level" API function pull the :db/id of children so that
;; they are readily available for the resolver to get them or should they be pulled on demand?
