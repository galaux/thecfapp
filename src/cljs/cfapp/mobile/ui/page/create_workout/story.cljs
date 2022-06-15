(ns cfapp.mobile.ui.page.create-workout.story
  (:require
   [cfapp.mobile.ui.component.menu-wrapper.core :as menu]
   [cfapp.mobile.ui.page.create-workout.view :refer [create-workout]]
   [cfapp.mobile.ui.storybook :as sb :refer [->default]]
   [reagent.core :as r]))

(def ^:export default
  (->default
    {:title     "Pages/Create Workout"
     :component create-workout}))

(def ^:private logged-user
  {:first-name  "Joanna"
   :last-name   "Wallace"
   :avatar-path
   "https://randomuser.me/api/portraits/women/13.jpg"})

(defn ^:export base
  []
  (r/as-element
    [create-workout
     {:menu-props    (menu/init-props)
      :logged-user   logged-user
      :check-workout #(println "Check workout: " %)}]))
