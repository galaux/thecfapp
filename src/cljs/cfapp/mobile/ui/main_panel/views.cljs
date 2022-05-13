(ns cfapp.mobile.ui.main-panel.views
  (:require
   [cfapp.mobile.controller.auth.core :as auth]
   [cfapp.mobile.ui.main-panel.subs :as subs]
   [cfapp.mobile.ui.page.logged-out.views :refer [logged-out]]
   [cfapp.mobile.ui.component.menu-wrapper.core :as menu]
   [cfapp.mobile.ui.page.find-community.core :refer [find-community]]
   [cfapp.mobile.ui.page.my-communities.core :refer [my-communities]]
   [re-frame.core :as rf]
   [re-frame.db :as rf-db]))

;; TODO remove shadow-cljs config for the "views" project

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
  (let [home        find-community ;; TODO change for real home
        page        (condp = @(rf/subscribe [::subs/curr-page])
                      :home           home
                      ;; TODO Last bit re auth: we configured Auth0 to redirect
                      ;; logged-out users
                      ;; to `/logged-out` but here we handle this page as a
                      ;; regular
                      ;; one which automatically redirects to Auth0.
                      :logged-out     logged-out
                      :find-community find-community
                      :my-communities my-communities
                      home)
        stage       @(auth/subscribe-stage)
        ;; TODO reconciliate backend user with what frontend wants:
        {:keys [id name email]} @(auth/subscribe-logged-user)
        logged-user {:id          id
                     :first-name  name
                     :last-name   email
                     ;; TODO temporarily cheating:
                     :avatar-path (-> @rf-db/app-db
                                      :auth
                                      :user-data
                                      :picture)}
        menu-props  (menu/init-props)]
    (case stage
      :loading    [spinner]
      :logged-in  [page
                   {:logged-user logged-user
                    :menu-props  menu-props}]
      :logged-out [logged-out]
      :error      [error])))
