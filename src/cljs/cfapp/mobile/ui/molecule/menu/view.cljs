(ns cfapp.mobile.ui.molecule.menu.view
  (:require
   [cfapp.mobile.ui.atom.icon.view :refer [icon]]
   [cfapp.mobile.ui.atom.text.view :refer [text]]
   [cfapp.mobile.ui.molecule.menu.style :as style]
   [herb.core :refer [<class]]))

(defn menu-icon
  [{:keys [open]}]
  [:span
   {:class    (<class style/menu-icon)
    :on-click open}
   [icon :bars]])

(defn- close-icon
  [{:keys [close]}]
  [:a
   {:class    (<class style/close-icon)
    :on-click close}
   [icon :times]])

(defn- menu-item
  [{:keys [icon-key label on-click]}]
  [:div
   {:class (<class style/menu-item)}
   [:a {:on-click on-click}
    [:span
     [:span
      {:class (<class style/menu-item-icon)}
      [icon icon-key]]
     label]]])

(defn- avatar
  [path]
  [:div
   {:style style/avatar}
   [:div
    {:style style/avatar-circle}
    [:img
     {:src   path
      :style style/avatar-image}]]])

(defn menu
  [{:keys [atom-open? close user-name avatar-path]}]
  [:div
   {:class (<class style/menu @atom-open?)}
   [:div
    [close-icon {:close close}]
    [avatar avatar-path]
    [:div
     {:style {:display "block"}}
     user-name]
    [menu-item
     {:icon-key :clipboard
      :label    "My box"
      :on-click #(println "My box")}]
    [menu-item
     {:icon-key :search
      :label    "Find a box"
      :on-click #(println "Find a box")}]
    [menu-item
     {:icon-key :bell
      :label    "Notifications"
      :on-click #(println "Notifications")}]
    [menu-item
     {:icon-key :question-circle
      :label    "Help"
      :on-click #(println "Help")}]
    [menu-item
     {:icon-key :shield
      :label    "About us"
      :on-click #(println "About us")}]]
   [text
    {:text-size :xxsmall}
    "© 2021 CFApp. All right reserved"]])
