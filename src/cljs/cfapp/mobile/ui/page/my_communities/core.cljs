(ns cfapp.mobile.ui.page.my-communities.core
  (:require
   [cfapp.mobile.controller.community.core :as community-ctl]
   [cfapp.mobile.ui.page.my-communities.sub :as sub]
   [cfapp.mobile.ui.page.my-communities.view :as view]
   [re-frame.core :as rf]))

(defn my-communities
  [{:keys [logged-user]}]
  (let [logged-user-id (:id logged-user)]
    ;; Watch-out: order is important:
    ;; - dispatch must be out of the `fn` or it immediately makes menu to close
    ;; - subscribe must be inside for the UI to react to changes
    (community-ctl/user-communities logged-user-id)
    (fn [{:keys [logged-user menu-props]}]
      (let [communities @(rf/subscribe [::sub/user-communities])]
        (view/my-communities
          {:menu-props      menu-props
           :logged-user     logged-user
           :communities     (vals communities)
           :leave-community (partial community-ctl/leave logged-user-id)})))))
