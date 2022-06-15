(ns cfapp.mobile.ui.main-panel.view
  (:require
   [cfapp.mobile.controller.auth.core :as auth]
   [cfapp.mobile.ui.component.menu-wrapper.core :as menu]
   [cfapp.mobile.ui.main-panel.sub :as sub]
   [cfapp.mobile.ui.page.community-wods.core :refer [community-wods]]
   [cfapp.mobile.ui.page.create-score.controller :refer [create-score]]
   [cfapp.mobile.ui.page.create-workout.controller :refer [create-workout]]
   [cfapp.mobile.ui.page.find-community.core :refer [find-community]]
   [cfapp.mobile.ui.page.logged-out.view :refer [logged-out]]
   [cfapp.mobile.ui.page.user-communities.core :refer [user-communities]]
   [cfapp.mobile.ui.page.wod-scores.core :refer [wod-scores]]
   [re-frame.core :as rf]
   [re-frame.db :as rf-db]))

;; TODO remove shadow-cljs config for the "vpiews" project

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
        {:keys [handler route-params]} @(rf/subscribe [::sub/curr-page])
        page        (case handler
                      :home             home
                      ;; TODO Last bit re auth: we configured Auth0 to redirect
                      ;; logged-out users
                      ;; to `/logged-out` but here we handle this page as a
                      ;; regular
                      ;; one which automatically redirects to Auth0.
                      :logged-out       logged-out
                      :find-community   find-community
                      :user-communities user-communities
                      :community-wods   community-wods
                      :create-score     create-score
                      :wod-scores       wod-scores
                      :create-workout   create-workout
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
                   {:route-params route-params
                    :logged-user  logged-user
                    :menu-props   menu-props}]
      :logged-out [logged-out]
      :error      [error])))
