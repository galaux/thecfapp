(ns cfapp.mobile.main-panel.events
  (:require
   [cfapp.mobile.main-panel.db :as db]
   [re-frame.core :as re-frame]))

;; reg-event-db, reg-event-fx, interceptors (schema validation for instance)

(re-frame/reg-event-db
  ::initialize-db
  (fn [_ _]
    db/default-db))
