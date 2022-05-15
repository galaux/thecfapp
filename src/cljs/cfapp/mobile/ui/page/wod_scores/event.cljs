(ns cfapp.mobile.ui.page.wod-scores.event
  (:require
   [cfapp.common.graphql :refer [gql->clj]]
   [pyramid.core :as p]
   [re-frame.core :as rf]))

(rf/reg-event-db
  ::on-wod-scores
  (fn [db [_ wod-id {:keys [data errors]}]]
    (if errors
      (println "GQL errors" errors) ;; TODO handle errors
      (let [scores (gql->clj (:wodScores data))]
        (p/add db
               {:db     wod-id
                :scores scores})))))
