(ns cfapp.mobile.controller.routing.core
  (:require
   [bidi.bidi :as bidi]
   [cfapp.mobile.controller.routing.event :as event]
   [pushy.core :as pushy]
   [re-frame.core :as rf]))

(def ^:private routes
  ["/"
   {"" :home
    "logged-out" :logged-out
    "page-b" :page-b
    "find-community" :find-community
    "user-communities" :user-communities
    ["community/" :id] :community}])


(defn- match-route
  "Function (invoked by Pushy when it hears a click) to check if `url-path`
   matches one of our (Bidi) routes"
  [url-path]
  (bidi/match-route routes url-path))

(defn- dispatch-nav-to
  "Function called by Pushy to dispatch a routing event"
  [{:keys [handler]}]
  (rf/dispatch [::event/nav-to handler]))

(def ^:private nav-handler
  "Handler that listens to click events (`[:a {:href …}]) in the application
   and dispatches a routing event if it finds a match in our routing table."
  (pushy/pushy dispatch-nav-to match-route))


;; Public API

(defn start-browser-history-handler!
  []
  (pushy/start! nav-handler))

(defn stop-browser-history-handler!
  []
  (pushy/stop! nav-handler))

(defn path-for
  [key]
  (bidi/path-for routes key))

(defn nav-to
  [page-key]
  (rf/dispatch [::event/nav-to page-key]))
