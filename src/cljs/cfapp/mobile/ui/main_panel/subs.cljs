(ns cfapp.mobile.ui.main-panel.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
  ::name
  (fn [db]
    (:name db)))

(re-frame/reg-sub
 ::curr-page
 (fn [db]
   (:curr-page db)))
