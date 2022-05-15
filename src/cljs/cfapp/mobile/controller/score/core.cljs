(ns cfapp.mobile.controller.score.core
  (:require
   [re-frame.core :as rf]
   [cfapp.mobile.controller.score.event :as event]
   [re-graph.core :as re-graph]))


(def ^:private gql-create-score
  ;;   (rc/inline "./create-score.gql"))
  "mutation CreateScore($userId: ID!, $wodId: ID!, $value: Int!) {
     createScore(input: {
       userId: $userId
       wodId: $wodId
       value: $value
     }) {
       id
     }
   }")

(defn create
  [user-id wod value]
  (rf/dispatch
    [::re-graph/mutate
     :create-score
     gql-create-score
     {:userId user-id
      :wodId  (:id wod)
      :value  value}
     [::event/on-create-score user-id wod value]]))
