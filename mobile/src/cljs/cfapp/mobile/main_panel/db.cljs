(ns cfapp.mobile.main-panel.db
  (:require [re-frame.core :as re-frame]
            [re-frame.db :refer [app-db]]))

;; Specs for schema validation

(def default-db
  {:curr-page :find-community
   :name "re-frame"
   :found-communities []})

;; (re-frame/reg-fx
;;  :my-communities
;;  (fn [val]
;;    (println ":: reg-fx: val " val)
;;    (swap! app-db assoc :my-communities val)))
