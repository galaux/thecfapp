(ns thecfapp.domain.score
  (:require
    [clojure.spec.alpha :as s]
    [datomic.client.api :as d]
    [thecfapp.spec-util :as sutil]))

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
