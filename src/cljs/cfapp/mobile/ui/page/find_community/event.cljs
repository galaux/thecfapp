(ns cfapp.mobile.ui.page.find-community.event
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-event-db
  ::on-found-communities
  (fn [db [_ {:keys [data errors]}]]
    ;; TODO handle errors
    ;; TODO transform GQL format to Clojure camel case
    (let [communities (->> (get data :findCommunities)
                           (group-by :id)
                           (map (fn [[k v]] [k (first v)]))
                           (into {}))]
      (assoc db :found-communities communities))))

(re-frame/reg-event-db
  ::on-joined-community
  (fn [db [_ {:keys [data errors] :as payload}]]
    ;; TODO handle errors
    (let [{:keys [id] :as community} (:addUserToCommunity data)]
      ;; TODO protect against "unfound" community?
      ;; (problem will go away once we use normalization anyway)
      (assoc-in db [:found-communities id] community))))

(re-frame/reg-event-db
  ::on-left-community
  (fn [db [_ {:keys [data errors] :as payload}]]
    ;; TODO handle errors
    (let [{:keys [id] :as community} (:removeUserFromCommunity data)]
      ;; We rely all 3 GQL queries to return the same pattern (fragment)
      ;; TODO protect against "unfound" community?
      ;; (problem will go away once we use normalization anyway)
      (assoc-in db [:found-communities id] community))))
