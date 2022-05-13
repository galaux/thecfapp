(ns cfapp.mobile.controller.auth.core
  (:require
   ["@auth0/auth0-spa-js" :refer [createAuth0Client]]
   [cfapp.mobile.controller.auth.events :as auth]
   [cfapp.mobile.controller.auth.subs :as sub]
   [cfapp.mobile.routing.events :as routing]
   [cljs.core.async :refer [go]]
   [cljs.core.async.interop :refer-macros [<p!]]
   [cljs.reader :refer [read-string]]
   [clojure.string :as s]
   [re-frame.core :as rf]
   [re-graph.core :as re-graph]
   [shadow.resource :as rc]))

(defn- user-being-redirected?
  []
  (let [query-string js/window.location.search]
    (and (string? query-string)
         (.includes query-string "code=")
         (.includes query-string "state="))))

(defn- remove-query-params-from-url!
  []
  (js/window.history.replaceState #js {}
                                  js/document.title
                                  js/window.location.pathname))


(def ^:private gql-get-user
  (rc/inline "./get-user.gql"))

(defn- dispatch-get-user
  [user-id]
  (rf/dispatch [::re-graph/query
                :find-community
                gql-get-user
                {:userId user-id}
                [::auth/set-logged-in]]))

(defn user-data->user-id
  [user-data]
  (s/replace-first (:sub user-data) "auth0|" ""))

;; This function is run at app initialization which can happen in 2 cases
;; A. User is starting the app. She is un-authenticated so we need to:
;;    - init an auth client
;;    - redirect user to Auth0 for authentication (user will then be redirected
;;      back to our app (which is the second case)
;; B. User was redirected to our app after the login process
(defn init!
  [auth-config]
  (rf/dispatch-sync [::auth/set-loading])
  (go
    ;; We don't know yet if this is case A or B but we need an Auth client
    (let [client (<p! (createAuth0Client auth-config))]
      (rf/dispatch [::auth/set-loading client])
      (if-not (user-being-redirected?)
        ;; Case A
        (rf/dispatch [::auth/login])
        ;; Case B
        (let [callback-result (<p! (.handleRedirectCallback client))
              page (some-> callback-result
                           .-appState
                           read-string
                           keyword)]
          ;; Query parameters must be removed from the URL so that if user
          ;; refreshes the page, we don't falsly detect case B again
          ;; https://auth0.com/docs/quickstart/spa/vanillajs#initialize-the-sdk
          (remove-query-params-from-url!)
          (if-not (<p! (.isAuthenticated client))
            (rf/dispatch [::auth/set-logged-out])
            (let [user-data (-> (<p! (.getUser client))
                                (js->clj :keywordize-keys true))]
              (rf/dispatch [::auth/set-authenticated user-data])
              (dispatch-get-user (user-data->user-id user-data))
              (when page
                (rf/dispatch [::routing/nav-to page])))))))))

;; Utils

(defn subscribe-stage
  []
  (rf/subscribe [::sub/stage]))

(defn subscribe-logged-user
  []
  (rf/subscribe [::sub/logged-user]))
