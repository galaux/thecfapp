(ns cfapp.mobile.auth.subs
  (:require
   [re-frame.core :as rf]))

(rf/reg-sub
  ::stage
  (fn [db]
    (get-in db [:auth :stage])))

(rf/reg-sub
  ::user-data
  (fn [db]
    (get-in db [:auth :user-data])))

(rf/reg-sub
  ::user-data*
  :<-
  [::user-data]
  (fn [user-data]
    (js->clj user-data :keywordize-keys true)))
