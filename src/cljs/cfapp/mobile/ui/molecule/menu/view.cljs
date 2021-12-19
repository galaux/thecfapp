(ns cfapp.mobile.ui.molecule.menu.view
  (:require
   [cfapp.mobile.ui.atom.icon.view :refer [icon]]
   [cfapp.mobile.ui.atom.text.view :refer [text]]
   [cfapp.mobile.ui.molecule.menu.style :as style]
   [herb.core :refer [<class]]))

(defn- close-icon
  [{:keys [close]}]
  [:div
   {:class    (<class style/close-button)
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
   {:class (<class style/avatar)}
   [:div
    {:class (<class style/avatar-circle)}
    [:img
     {:src   path
      :class (<class style/avatar-image)}]]])

(defn- drawer
  [{:keys [close user]}]
  [:div
   {:class (<class style/drawer)}
   [close-icon {:close close}]
   [:div
    {:class (<class style/drawer-body)}
    [avatar (:avatar-path user)]
    [:div
     {:class (<class style/user-first-name)}
     [text
      {:text-size      :large
       :text-transform "uppercase"}
      (:first-name user)]]
    [:div
     {:class (<class style/user-last-name)}
     [text
      {:text-size      :large
       :text-transform "uppercase"}
      (:last-name user)]]
    [:div
     {:class (<class style/menu-items)}
     [:div
      {:class (<class style/menu-items-main)}
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
        :on-click #(println "Help")}]]
     [:div
      {:class (<class style/menu-items-bottom)}
      [menu-item
       {:icon-key :shield
        :label    "About us"
        :on-click #(println "About us")}]]]]
   [:div
    {:class (<class style/drawer-footer)}
    [text
     {:text-size :xxxsmall}
     "© 2021 CFApp. All rights reserved"]]])

(defn menuable-layout
  [{:keys [menu-state user]} child]
  (let [{:keys [atom-open? open close]} menu-state]
    [:div
     {:class (<class style/menuable-layout)}
     [:div
      {:class (<class style/menu-overlay @atom-open?)}
      [drawer
       {:close close
        :user  user}]
      [:div
       {:class    (<class style/overlay-invisible)
        :on-click close}]]
     [:div {:class (<class style/page-body)}
      child]]))
