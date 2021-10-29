(ns cfapp.mobile.pages.find-community.events
  (:require [re-frame.core :as re-frame]))

(re-frame.core/reg-event-db
 ::join-community
 (fn [db [_ community-id]]
   (update db :my-communities conj community-id)))

;; (re-frame.core/reg-event-fx
;;  :join-community
;;  (fn [{:keys [db] :as coeffect} [_ community-id]]
;;    {:db (update db :my-communities conj community-id)}))

(re-frame/reg-event-db
 ::on-thing
 (fn [db [_ {:keys [data errors] :as payload}]]
   ;; TODO handle errors
   (assoc db :found-communities (get data :findCommunities))))
