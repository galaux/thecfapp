(ns cfapp.mobile.ui.page.find-community.view
  (:require
   [cfapp.mobile.ui.molecule.menu.view :refer [menuable-layout]]
   [cfapp.mobile.ui.atom.icon.view :refer [icon]]
   [cfapp.mobile.ui.page.find-community.style :as style]
   [herb.core :refer [<class]]))

(defn menu-icon
  [{:keys [open]}]
  [:div
   {:class    (<class style/menu-icon)
    :on-click open}
   [icon :bars]])

(defn user-icon
  []
  [:div
   {:class (<class style/user-icon)}
   [icon :user]])

(defn main-heading
  []
  [:div
   {:class (<class style/main-heading)}
   "FIND A COMMUNITY"])

(defn header
  [menu-state]
  [:div
   {:class (<class style/header)}
   [:div
    {:class (<class style/top-line)}
    [menu-icon menu-state]
    [:div
     {:class (<class style/title)}
     "WODME"]
    [user-icon]]
   [main-heading]])

(defn body
  []
  [:div
   {:class (<class style/body)}
   "body"])

(defn footer
  []
  [:div
   {:class (<class style/footer)}
   "© 2021 CFApp, all right reserved"])

(defn find-community
  [{:keys [menu-state] :as props}]
  [menuable-layout
   (select-keys props #{:menu-state :user})
   [:div
    {:class (<class style/container)}
    [header menu-state]
    [body]
    [footer]]])
