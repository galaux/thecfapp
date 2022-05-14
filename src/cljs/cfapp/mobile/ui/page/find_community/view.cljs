(ns cfapp.mobile.ui.page.find-community.view
  (:require
   [cfapp.mobile.ui.component.text.view :refer [text]]
   [cfapp.mobile.ui.component.community-card.view :refer [community-card]]
   [cfapp.mobile.ui.component.menu-wrapper.view :refer [menu-wrapper]]
   [cfapp.mobile.ui.component.layout.view :refer [layout]]
   [cfapp.mobile.ui.component.text-input-group.view
    :refer
    [text-input-group]]
   [cfapp.mobile.ui.page.find-community.style :as style]
   [herb.core :refer [<class]]
   [reagent.core :as r]))

(defn- community-list
  [{:keys [communities] :as props}]
  [:div
   {:class (<class style/community-list)}
   (cond
     (nil? communities)
     nil

     (empty? communities)
     [:div
      {:class (<class style/not-found)}
      [text
       {:text-size :large}
       "¯\\_(ツ)_/¯"]]

     :else
     (for [{:keys [id] :as community} communities]
       ^{:key id}
       [:div
        {:class (<class style/community-card)}
        [community-card
         (-> (select-keys props
                          #{:join-community
                            :leave-community})
             (assoc :community community))]]))])

(defn- body
  [_props]
  (let [state (r/atom nil)]
    (fn [{:keys [find-communities] :as props}]
      [:div
       [text-input-group
        {:id        :search-community
         :label     "Search a community"
         :value     @state
         :on-change #(reset! state %)
         :on-click  find-communities}]
       [community-list
        (select-keys props
                     #{:communities
                       :join-community
                       :leave-community})]])))

(defn find-community
  [props]
  [menu-wrapper
   (select-keys props
                #{:logged-user
                  :menu-props})
   [layout
    {:header    "Find a community"
     :open-menu (get-in props [:menu-props :open])}
    [body
     (select-keys props
                  #{:communities
                    :find-communities
                    :join-community
                    :leave-community})]]])
