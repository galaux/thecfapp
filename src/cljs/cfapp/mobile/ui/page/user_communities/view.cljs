(ns cfapp.mobile.ui.page.user-communities.view
  (:require
   [cfapp.mobile.ui.component.text.view :refer [text]]
   [cfapp.mobile.ui.component.community-card.view :refer [community-card]]
   [cfapp.mobile.ui.component.layout.view :refer [layout]]
   [cfapp.mobile.ui.component.menu-wrapper.view :refer [menu-wrapper]]
   [cfapp.mobile.ui.page.user-communities.style :as style]
   [herb.core :refer [<class]]))

;; TODO factorize this component to be used with "find-communities"?
(defn- community-list
  [{:keys [communities] :as props}]
  [:div
   (if (empty? communities)
     [:div
      {:class (<class style/not-found)}
      [text
       {:text-size :large}
       "¯\\_(ツ)_/¯"]]
     (for [{:keys [id] :as community} communities]
       ^{:key id}
       [:div
        {:class (<class style/community-card)}
        [community-card
         (-> (select-keys props
                          #{:leave-community})
             (assoc :community community))]]))])

(defn user-communities
  [props]
  [menu-wrapper
   (select-keys props
                #{:logged-user
                  :menu-props})
   [layout
    {:sub-header "My communities"
     :open-menu  (get-in props [:menu-props :open])}
    [community-list
     (select-keys props
                  #{:communities
                    :leave-community})]]])
