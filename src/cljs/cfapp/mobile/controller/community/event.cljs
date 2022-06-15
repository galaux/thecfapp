(ns cfapp.mobile.controller.community.event
  (:require
   [cfapp.common.graphql :refer [gql->clj]]
   [cfapp.mobile.state.community :as community-state]
   [pyramid.core :as p]
   [re-frame.core :as re-frame]))

(re-frame/reg-event-db
  ::on-found-communities
  (fn [db [_ {:keys [data errors]}]]
    ;; TODO handle errors
    ;; TODO transform GQL format to Clojure camel case
    ;; Note: GQL returning `:isMember` is a convenience from the BE to prevent
    ;; FE from having to run 2 queries. But in no circumstances should
    ;; `:isMember` or `:is-member?` appear in the state. `:is-member?` is a UI
    ;; only field. Info of whether user belongs to a community is encoded in
    ;; the state in `[:logged-user :communities]`. Storing `:is-member?` in
    ;; state would duplicate the info and introduce confusion.
    (let [found-gql-communities (get data :findCommunities)
          found-ui-communities  (map (comp gql->clj #(dissoc % :isMember))
                                  found-gql-communities)
          ui-user-communities   (->> (filter :isMember found-gql-communities)
                                     (map (comp
                                            gql->clj
                                            #(dissoc % :isMember))))
          db-accretion          (-> (p/pull db [{:logged-user [:id]}])
                                    (assoc-in [:logged-user :communities]
                                              ui-user-communities)
                                    (assoc :found-communities
                                           found-ui-communities))]
      ;; TODO have this p/add in the `state` ns?
      (p/add db db-accretion))))

(re-frame/reg-event-db
  ::on-user-communities
  (fn [db [_ {:keys [data errors]}]]
    ;; TODO handle errors
    (let [communities (->> (get data :userCommunities)
                           (map gql->clj))
          user-data   (-> (p/pull db [{:logged-user [:id]}])
                          (assoc-in [:logged-user :communities]
                                    (gql->clj communities)))]
      (p/add db user-data))))

(re-frame/reg-event-db
  ::on-joined-community
  (fn [db [_ {:keys [data errors] :as payload}]]
    ;; TODO handle errors
    (community-state/join db (:addUserToCommunity data))))

(re-frame/reg-event-db
  ::on-left-community
  (fn [db [_ {:keys [data errors] :as payload}]]
    ;; TODO handle errors
    ;; We rely all 3 GQL queries to return the same pattern (fragment)
    ;; TODO protect against "unfound" community?
    ;; (problem will go away once we use normalization anyway)
    (community-state/leave db (:removeUserFromCommunity data))))

(defn str->date
  [str-date]
  (js/Date. str-date))

(re-frame/reg-event-db
  ::on-community-by-id
  (fn [db [_ {:keys [data errors]}]]
    ;; TODO handle errors
    (if errors
      (println "Error: " errors)
      (let [community (-> (:community data)
                          gql->clj
                          (update :wods
                                  #(map (fn [wod] (update wod :date str->date))
                                     %)))]
        (p/add db community)))))
