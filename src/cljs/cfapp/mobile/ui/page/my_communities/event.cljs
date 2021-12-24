(ns cfapp.mobile.ui.page.my-communities.event
  (:require
   [re-frame.core :as rf]
   [cfapp.mobile.util :as util]))

;; TODO factorize with `find-community.sub/->ui-community`?
(defn ->ui-community
  [community]
  (-> (util/gql->clj community)
      (assoc :is-member? true)))

(rf/reg-event-db
  ::on-user-communities
  (fn [db [_ {:keys [data errors]}]]
    ;; TODO handle errors
    (let [communities (->> (get data :userCommunities)
                           (map ->ui-community)
                           (group-by :id)
                           (map (fn [[k v]] [k (first v)]))
                           (into {}))]
      (assoc db :user-communities communities))))

;; TODO factorize with find-community?
(rf/reg-event-db
  ::on-left-community
  (fn [db [_ {:keys [data errors] :as payload}]]
    ;; TODO handle errors
    (let [{:keys [id]} (:removeUserFromCommunity data)]
      ;; We rely all 3 GQL queries to return the same pattern (fragment)
      ;; TODO protect against "unfound" community?
      ;; (problem will go away once we use normalization anyway)
      (update db :user-communities dissoc id))))
