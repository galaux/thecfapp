(ns cfapp.mobile.auth.subs
  (:require
   [re-frame.core :as rf]))

(rf/reg-sub
  ::stage
  (fn [db]
    (get-in db [:auth :stage])))

(rf/reg-sub
  ::logged-user
  (fn [db]
    (:logged-user db)))
