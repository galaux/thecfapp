(ns cfapp.mobile.ui.page.find-community.core
  (:require
   [cfapp.mobile.ui.page.find-community.event :as event]
   [cfapp.mobile.ui.page.find-community.sub :as sub]
   [cfapp.mobile.ui.page.find-community.view :as view]
   [re-frame.core :as rf]
   [re-graph.core :as re-graph]
   [shadow.resource :as rc]))

(def ^:private gql-find-community
  (rc/inline "./graphql/find-community.gql"))

(defn- find-communities
  [logged-user-id name]
  (rf/dispatch [::re-graph/query
                :find-community
                gql-find-community
                {:name   name
                 :userId logged-user-id}
                [::event/on-found-communities]]))

(def ^:private gql-join-community
  (rc/inline "./graphql/join-community.gql"))

(defn- join-community
  [logged-user-id community-id]
  (rf/dispatch [::re-graph/mutate
                :join-community
                gql-join-community
                {:userId      logged-user-id
                 :communityId community-id}
                [::event/on-joined-community]]))

(def ^:private gql-leave-community
  (rc/inline "./graphql/leave-community.gql"))

(defn- leave-community
  [logged-user-id community-id]
  (rf/dispatch [::re-graph/mutate
                :leave-community
                gql-leave-community
                {:userId      logged-user-id
                 :communityId community-id}
                [::event/on-left-community]]))

(defn find-community
  [{:keys [logged-user menu-props]}]
  (let [found-communities @(rf/subscribe [::sub/found-communities*])
        logged-user-id    (:id logged-user)]
    [view/find-community
     {:menu-props       menu-props
      :logged-user      logged-user
      :find-communities (partial find-communities logged-user-id)
      :join-community   (partial join-community logged-user-id)
      :leave-community  (partial leave-community logged-user-id)
      :communities      found-communities}]))


