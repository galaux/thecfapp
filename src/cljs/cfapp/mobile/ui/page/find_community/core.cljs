(ns cfapp.mobile.ui.page.find-community.core
  (:require
   [cfapp.mobile.controller.community.core :as community-ctl]
   [cfapp.mobile.ui.page.find-community.sub :as sub]
   [cfapp.mobile.ui.page.find-community.view :as view]
   [re-frame.core :as rf]))

(defn find-community
  [{:keys [logged-user menu-props]}]
  (let [found-communities @(rf/subscribe [::sub/found-communities])
        logged-user-id    (:id logged-user)]
    [view/find-community
     {:menu-props       menu-props
      :logged-user      logged-user
      :find-communities (partial community-ctl/lookup logged-user-id)
      :join-community   (partial community-ctl/join logged-user-id)
      :leave-community  (partial community-ctl/leave logged-user-id)
      :communities      found-communities}]))


