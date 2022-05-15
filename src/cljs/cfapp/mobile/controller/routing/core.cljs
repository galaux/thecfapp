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
    ["community-wods/" :id] :community-wods
    ["community/" :id] :community
    ["create-score/" :wod-id] :create-score
    ["wod-scores/" :wod-id] :wod-scores}])


(defn- match-route
  "Function (invoked by Pushy when it hears a click) to check if `url-path`
   matches one of our (Bidi) routes"
  [url-path]
  (bidi/match-route routes url-path))

(defn- dispatch-nav-to
  "Function called by Pushy to dispatch a routing event. `page-id` is the map
  returned by Bidi to identify a page:
  `{:handler :community-wods, :route-params {:id 123}}`"
  [page-id]
  (rf/dispatch [::event/nav-to page-id]))

(def ^:private nav-handler
  "Handler that listens to click events (`[:a {:href …}]) in the application
   and dispatches a routing event if it finds a match in our routing table."
  (pushy/pushy dispatch-nav-to match-route))

(defn- path-for
  [route & params]
  (apply bidi/path-for routes route params))


;; Public API

(defn start-browser-history-handler!
  []
  (pushy/start! nav-handler))

(defn stop-browser-history-handler!
  []
  (pushy/stop! nav-handler))


(def href-find-community (path-for :find-community))
(def href-user-communities (path-for :user-communities))

(defn href-community-wods
  [id]
  (path-for :community-wods :id id))

(defn href-create-score
  [wod-id]
  (path-for :create-score :wod-id wod-id))

(defn href-wod-scores
  [wod-id]
  (path-for :wod-scores :wod-id wod-id))
