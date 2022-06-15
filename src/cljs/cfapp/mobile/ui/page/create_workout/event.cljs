(ns cfapp.mobile.ui.page.create-workout.event
  (:require
   [cfapp.common.graphql :refer [gql->clj]]
   [re-frame.core :as rf]
   [pyramid.core :as p]))

(rf/reg-event-db
  ::on-checked-workout
  (fn [db [_ {:keys [data errors]}]]
    ;; TODO handle errors
    (if errors
      (do
        (println "Error: " errors)
        db)
      (p/add db {:parse-result (gql->clj (:parseWorkout data))}))))
