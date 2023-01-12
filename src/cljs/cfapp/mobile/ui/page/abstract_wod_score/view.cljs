(ns cfapp.mobile.ui.page.abstract-wod-score.view
  (:require
   ["lucide-react" :refer [Users]]
   [cfapp.mobile.controller.workout.util :as workout-util]
   [cfapp.mobile.ui.component.vertical-stamp.view :refer [vertical-stamp]]
   [cfapp.mobile.ui.component.chip.view :refer [chip]]
   [cfapp.mobile.ui.component.layout.view :refer [layout]]
   [cfapp.mobile.ui.component.menu-wrapper.view :refer [menu-wrapper]]
   [cfapp.mobile.ui.component.text.view :refer [text]]
   [cfapp.mobile.ui.component.wod-card.view :as wod-card-view]
   [cfapp.mobile.ui.page.abstract-wod-score.style :as style]
   [herb.core :refer [<class]]))

(defn- sub-header
  [{:keys [workout date scores]}]
  [:div
   [text
    {:text-size :xsmall
     :color     :gray50}
    "Workout of the day"]
   [:div
    {:class (<class style/workout-name)}
    (if-let [workout-name (:name workout)]
      (str workout-name ": " (workout-util/loop-title (:loop workout)))
      (workout-util/loop-title (:loop workout)))]
   [:div
    {:class (<class style/header-footer)}
    [vertical-stamp "Jun" "22"] ;; TODO `date`
    [chip Users (count scores)]]])

(defn abstract-wod-score
  [{:as props} {:keys [workout] :as wod} & children]
  [menu-wrapper
   (select-keys props
                #{:logged-user
                  :menu-props})
   [layout
    {:sub-header [sub-header wod]
     :open-menu  (get-in props [:menu-props :open])}
    [:div
     [:div {:class (<class style/workout-loop)}
      [wod-card-view/workout-loop (:loop workout)]]
     children]]])
