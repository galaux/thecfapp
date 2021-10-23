(ns cfapp.mobile.pages.find-community.views
  (:require
   ["/views" :as views]
   [cfapp.mobile.pages.find-community.events :as events]
   [cfapp.mobile.pages.find-community.subs :as subs]
   [re-frame.core :as re-frame]
   [re-graph.core :as re-graph]
   [reagent.core :as r]))

(def page-find-community (r/adapt-react-class views/FindCommunity))

(def ^:private gql-find-community
  "query FindCommunity($name: String!) {
     findCommunities(name: $name) {
       id
       name
       memberCount
       address {
         street
         zipCode
         city
         country
       }
     }
   }")

(defn find-communities
  [name]
  (re-frame/dispatch [::re-graph/query
                      :my-query-id
                      gql-find-community
                      {:name name}
                      [::events/on-thing]]))

(defn join-community
  [id]
  (js/console.log "TODO join-community" id " then nav to somewhere else …")
  (re-frame/dispatch [::events/join-community id]))

(defn nav-to-community
  [id]
  (js/console.log "TODO nav-to-community " id))

(defn find-community
  [_]
  (let [found-communities (re-frame/subscribe [::subs/found-communities])]
    [page-find-community
     {:find-communities find-communities
      :join-community   join-community
      :nav-to-community nav-to-community
      :communities      @found-communities}]))
