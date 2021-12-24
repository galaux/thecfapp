(ns cfapp.mobile.ui.page.my-communities.view
  (:require
   [cfapp.mobile.ui.atom.text.view :refer [text]]
   [cfapp.mobile.ui.molecule.community-card.view :refer [community-card]]
   [cfapp.mobile.ui.molecule.layout.view :refer [layout]]
   [cfapp.mobile.ui.molecule.menu-wrapper.view :refer [menu-wrapper]]
   [cfapp.mobile.ui.page.my-communities.style :as style]
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

(defn my-communities
  [props]
  [menu-wrapper
   (select-keys props
                #{:logged-user
                  :menu-props})
   [layout
    {:title     "My communities"
     :open-menu (get-in props [:menu-props :open])}
    [community-list
     (select-keys props
                  #{:communities
                    :leave-community})]]])
