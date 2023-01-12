(ns cfapp.mobile.ui.page.wod-scores.core
  (:require
   [cfapp.mobile.ui.page.wod-scores.event :as event]
   [cfapp.mobile.ui.page.wod-scores.sub :as sub]
   [cfapp.mobile.ui.page.wod-scores.view :as view]
   [re-frame.core :as rf]
   [re-graph.core :as re-graph]))

(def ^:private gql-wod-scores
  "query WodScores($wodId: ID!) {
     wodScores(id: $wodId) {
       id
       value
       user { id name }
       wod { id }
     }
   }")

(defn- dispatch-get-wod-scores
  [wod-id]
  (rf/dispatch
    [::re-graph/query
     :wod-scores
     gql-wod-scores
     {:wodId wod-id}
     [::event/on-wod-scores wod-id]]))

(defn wod-scores
  [{:keys [route-params]}]
  (let [wod-id (:wod-id route-params)]
    (dispatch-get-wod-scores wod-id)
    (fn [{:keys [logged-user menu-props]}]
      (let [wod-with-scores @(rf/subscribe [::sub/wod-with-scores wod-id])]
        [view/wod-scores
         {:menu-props  menu-props
          :logged-user logged-user}
         wod-with-scores]))))
