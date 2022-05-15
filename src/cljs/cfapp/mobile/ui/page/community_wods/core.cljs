(ns cfapp.mobile.ui.page.community-wods.core
  (:require
   [cfapp.mobile.ui.page.community-wods.view :as view]
   [cfapp.mobile.controller.routing.core :as routing]
   [re-frame.core :as rf]
   [cfapp.mobile.controller.community.core :as community-ctl]
   [cfapp.mobile.ui.page.community-wods.sub :as sub]))

(defn community-wods
  [{:keys [route-params]}]
  (let [community-id (:id route-params)]
    ;; We might not even need to perform this "get by id" as we must already
    ;; have the community details from the list of commities but still, it
    ;; sounds more logical to ask for fresh data. Also, in the future, the
    ;; "get community" GQL query might ask for more fields than "get community
    ;; list" does.
    (community-ctl/by-id community-id)
    (fn [{:keys [logged-user menu-props]}]
      (let [community @(rf/subscribe [::sub/community community-id])]
        [view/community-wods
         {:menu-props  menu-props
          :logged-user logged-user
          :href        {:create-score routing/href-create-score}}
         community]))))
