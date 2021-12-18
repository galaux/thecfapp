(ns cfapp.mobile.main-panel.views
  (:require
   ["/views" :as views]
   [cfapp.mobile.auth.subs :as auth-sub]
   [cfapp.mobile.auth.events :as auth-evt]
   [cfapp.mobile.main-panel.subs :as subs]
   [cfapp.mobile.pages.find-community.views :refer [find-community]]
   [cfapp.mobile.pages.logged-out.views :refer [logged-out]]
   [re-frame.core :as rf]
   [reagent.core :as r]))

;; (def suspense (r/adapt-react-class react/Suspense))
(def layout (r/adapt-react-class views/Layout))

(defn spinner
  []
  [:img
   {:src    "spinner.gif"
    :width  "40"
    :height "40"}])

(defn error
  []
  "An error occurred")

(defn main-panel
  []
  (let [home  find-community ;; TODO change for real home
        page  (condp = @(rf/subscribe [::subs/curr-page])
                :home           home
                ;; TODO Last bit re auth: we configured Auth0 to redirect
                ;; logged-out users
                ;; to `/logged-out` but here we handle this page as a regular
                ;; one which
                ;; automatically redirects to Auth0.
                :logged-out     logged-out
                :find-community find-community
                home)
        stage @(rf/subscribe [::auth-sub/stage])]
    (case stage
      :loading    [spinner]
      :logged-in  [:div
                   [:button
                    {:on-click #(rf/dispatch [::auth-evt/logout])}
                    "Log out"]
                   [layout [page]]]
      :logged-out [logged-out]
      :error      [error])))

