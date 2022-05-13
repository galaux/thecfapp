(ns cfapp.mobile.ui.component.menu-wrapper.view
  (:require
   ["lucide-react" :refer [X Clipboard Search Bell HelpCircle Shield LogOut]]
   [cfapp.mobile.ui.component.icon.view :refer [icon]]
   [cfapp.mobile.ui.component.text.view :refer [text]]
   [cfapp.mobile.ui.component.menu-wrapper.style :as style]
   [herb.core :refer [<class]]))

(defn- close-icon
  [{:keys [close]}]
  [:div
   {:class    (<class style/close-button)
    :on-click close}
   [icon X]])

(defn- menu-item
  [{:keys [icon-key label on-click]}]
  [:a {:on-click on-click}
   [:div
    {:class (<class style/menu-item)}
    [:div
     {:class (<class style/menu-item-icon)}
     [icon icon-key]]
    label]])

(defn- avatar
  [path]
  [:div
   {:class (<class style/avatar)}
   [:div
    {:class (<class style/avatar-circle)}
    [:img
     {:src   path
      :class (<class style/avatar-image)}]]])

(defn- drawer
  [{:keys [logged-user menu-props]}]
  (let [{:keys [close
                nav-to-my-communities
                nav-to-find-community
                log-out]}
        menu-props]
    [:div
     {:class (<class style/drawer)}
     [close-icon {:close close}]
     [:div
      {:class (<class style/drawer-body)}
      [avatar (:avatar-path logged-user)]
      [:div
       {:class (<class style/user-first-name)}
       [text
        {:text-size      :large
         :text-transform "uppercase"}
        (:first-name logged-user)]]
      [text
       {:text-size      :large
        :text-transform "uppercase"}
       (:last-name logged-user)]
      [:div
       {:class (<class style/menu-items)}
       [:div
        {:class (<class style/menu-items-main)}
        [menu-item
         {:icon-key Clipboard
          :label    "My communities"
          :on-click nav-to-my-communities}]
        [menu-item
         {:icon-key Search
          :label    "Find a community"
          :on-click nav-to-find-community}]
        [menu-item
         {:icon-key Bell
          :label    "Notifications"
          :on-click #(println "Notifications")}]
        [menu-item
         {:icon-key HelpCircle
          :label    "Help"
          :on-click #(println "Help")}]]
       [:div
        {:class (<class style/menu-items-bottom)}
        [menu-item
         {:icon-key LogOut
          :label    "Log-out"
          :on-click log-out}]
        [menu-item
         {:icon-key Shield
          :label    "About us"
          :on-click #(println "About us")}]]]]
     [:div
      {:class (<class style/drawer-footer)}
      [text
       {:text-size :xxxsmall}
       "© 2021 CFApp. All rights reserved"]]]))

(defn menu-wrapper
  [{:keys [menu-props] :as props} child]
  (let [{:keys [atom-open? close]} menu-props]
    [:div
     {:class (<class style/menu-wrapper)}
     [:div
      {:class (<class style/menu-overlay @atom-open?)}
      [drawer
       (select-keys props
                    #{:menu-props
                      :logged-user})]
      [:div
       {:class    (<class style/overlay-invisible)
        :on-click close}]]
     [:div {:class (<class style/page-body)}
      child]]))
