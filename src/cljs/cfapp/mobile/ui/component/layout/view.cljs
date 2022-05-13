(ns cfapp.mobile.ui.component.layout.view
  (:require
   ["lucide-react" :refer [Menu User]]
   [cfapp.mobile.ui.component.icon.view :refer [icon]]
   [cfapp.mobile.ui.component.text.view :refer [text]]
   [cfapp.mobile.ui.component.layout.style :as style]
   [herb.core :refer [<class]]))

(defn- menu-icon
  [{:keys [open-menu]}]
  [:div
   {:class    (<class style/menu-icon)
    :on-click open-menu}
   [icon Menu]])

(defn- user-icon
  []
  [:div
   {:class (<class style/user-icon)}
   [icon User]])

(defn- page-heading
  [title]
  [:div
   {:class (<class style/page-heading)}
   [text
    {:text-size      :medium
     :font-weight    :semi-bold
     :text-transform "uppercase"}
    title]])

(defn- header
  [props title]
  [:div
   {:class (<class style/header)}
   [:div
    {:class (<class style/header-content)}
    [:div
     {:class (<class style/top-line)}
     [menu-icon (select-keys props #{:open-menu})]
     [:div
      {:class (<class style/app-name)}
      [text
       {:text-size      :medium
        :font-weight    :semi-bold
        :text-transform "uppercase"}
       "WodIt"]]
     [user-icon]]
    [page-heading title]]])

(def ^:private footer
  [:div
   {:class (<class style/footer)}
   [text
    {:text-size :xxxsmall}
    "© 2021 CFApp, all rights reserved"]])

(defn layout
  [{:keys [title] :as props} body]
  [:div
   {:class (<class style/container)}
   [header
    (select-keys props
                 #{:open-menu})
    title]
   [:div
    {:class (<class style/body)}
    body]
   footer])
