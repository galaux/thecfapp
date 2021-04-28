(ns thecfapp.domain.wod
  (:require
    [clojure.spec.alpha :as s]
    [datomic.client.api :as d]
    [thecfapp.spec-util :as sutil]
    [thecfapp.util :refer [anomaly anomaly? camel-case-keys anomaly->error]]))

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
