(ns cfapp.mobile.ui.page.my-communities.sub
  (:require
   [re-frame.core :as rf]))

(rf/reg-sub
  ::user-communities
  (fn [db]
    (:user-communities db)))
