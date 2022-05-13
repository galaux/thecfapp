(ns cfapp.mobile.controller.routing.event
  (:require
   [re-frame.core :as rf]))

(rf/reg-event-db
  ::nav-to
  (fn [db [_ page-key]]
    (assoc db :curr-page page-key)))
