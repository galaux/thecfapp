(ns cfapp.mobile.pages.find-community.subs
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::found-communities
 (fn [db]
   (:found-communities db)))
