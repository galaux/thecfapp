(ns cfapp.mobile.ui.page.community-wods.view
  (:require
   ["lucide-react" :refer [Users PlusCircle]]
   [cfapp.mobile.ui.component.carousel.view :refer [carousel]]
   [cfapp.mobile.ui.component.chip.view :refer [chip]]
   [cfapp.mobile.ui.component.horizontal-stamp.view
    :refer [horizontal-stamp]]
   [cfapp.mobile.ui.component.layout.view :refer [layout]]
   [cfapp.mobile.ui.component.menu-wrapper.view :refer [menu-wrapper]]
   [cfapp.mobile.ui.component.text.view :refer [text]]
   [cfapp.mobile.ui.component.wod-card.view :refer [wod-card]]
   [cfapp.mobile.ui.page.community-wods.style :as style]
   [clojure.string :as s]
   [cfapp.mobile.ui.component.icon.view :refer [icon]]
   [herb.core :refer [<class]]
   [cfapp.mobile.controller.routing.core :as routing]))

(defn- ->address
  [{:keys [street zip-code city country]}]
  [text
   {:color     :gray30
    :text-size :xsmall}
   (->> (remove s/blank? [street zip-code city country])
        (s/join " "))])

(defn- sub-header
  [{:keys [name address member-count]} wods-count]
  [:div {:class (<class style/header)}
   [:div ;; header-body
    [text
     {:text-size      :medium
      :text-transform "uppercase"
      :font-weight    :semi-bold}
     name]
    [:div
     [->address address]]]
   [:div {:class (<class style/header-footer)}
    [horizontal-stamp wods-count "WOD" "Performed"]
    [chip Users member-count]]])

(defn community-wods
  [{:keys [href logged-user] :as props} {:keys [wods] :as community}]
  [menu-wrapper
   (select-keys props
                #{:logged-user
                  :menu-props})
   [layout
    {:sub-header [sub-header community (count wods)]
     :open-menu  (get-in props [:menu-props :open])}
    [:div {:class (<class style/parent)}
     [:div {:class (<class style/parent-header)}
      "WODs"
      [:a
       {:href routing/href-create-workout}
       [icon PlusCircle]]]
     [:div
      {:class (<class style/child)}
      [carousel
       (for [{:keys [id] :as wod} wods]
         ^{:key id}
         [wod-card
          {:href href
           :logged-user-id (:id logged-user)}
          wod])]]]]])
