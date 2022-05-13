(ns cfapp.mobile.ui.page.community-wods.view
  (:require
   [cfapp.mobile.ui.component.carousel.view :refer [carousel]]
   [cfapp.mobile.ui.component.layout.view :refer [layout]]
   [cfapp.mobile.ui.component.menu-wrapper.view :refer [menu-wrapper]]
   [cfapp.mobile.ui.component.wod-card.view :refer [wod-card]]
   [cfapp.mobile.ui.page.community-wods.style :as style]
   [herb.core :refer [<class]]))

(defn community-wods
  [props community wods]
  [menu-wrapper
   (select-keys props
                #{:logged-user
                  :menu-props})
   [layout
    {:title     (:name community)
     :open-menu (get-in props [:menu-props :open])}
    [:div
     {:class (<class style/parent)}
     "WODs"
     [:div
      {:class (<class style/child)}
      [carousel
       (map wod-card wods)]]]]])
