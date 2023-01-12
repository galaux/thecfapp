(ns cfapp.mobile.controller.community.core
  (:require
   [cfapp.mobile.controller.community.event :as event]
   [re-frame.core :as rf]
   [re-graph.core :as re-graph]
   [shadow.resource :as rc]
   [cfapp.mobile.controller.routing.core :as routing]))

(defn with-href
  [{:keys [id] :as community}]
  (assoc community
    :href (routing/href-community-wods id)))

(def ^:private gql-find-community
  (rc/inline "./find-community.gql"))

(defn lookup
  [logged-user-id name]
  (rf/dispatch
    [::re-graph/query
     :find-community
     gql-find-community
     {:name   name
      :userId logged-user-id}
     [::event/on-found-communities]]))

(def ^:private gql-user-communities
  (rc/inline "./user-communities.gql"))

(defn user-communities
  [logged-user-id]
  (rf/dispatch
    [::re-graph/query
     :user-communities
     gql-user-communities
     {:userId logged-user-id}
     [::event/on-user-communities]]))

(def ^:private gql-join-community
  (rc/inline "./join-community.gql"))

(defn join
  [logged-user-id community-id]
  (rf/dispatch
    [::re-graph/mutate
     :join-community
     gql-join-community
     {:userId      logged-user-id
      :communityId community-id}
     [::event/on-joined-community]]))

(def ^:private gql-leave-community
  (rc/inline "./leave-community.gql"))

(defn leave
  [logged-user-id community-id]
  (rf/dispatch
    [::re-graph/mutate
     :leave-community
     gql-leave-community
     {:userId      logged-user-id
      :communityId community-id}
     [::event/on-left-community]]))

(def ^:private gql-community-by-id
  (rc/inline "./community-by-id.gql"))

;; TODO This query should not get the list of scores but maybe just a custom
;; computed `score-count`
(defn by-id
  [community-id]
  (rf/dispatch
    [::re-graph/query
     :community-by-id
     gql-community-by-id
     {:id community-id}
     [::event/on-community-by-id]]))
