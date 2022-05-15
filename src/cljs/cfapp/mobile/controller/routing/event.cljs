(ns cfapp.mobile.controller.routing.event
  (:require
   [re-frame.core :as rf]))

(rf/reg-event-db
  ::nav-to
  (fn [db [_ page-id]]
    (assoc db :curr-page page-id)))
