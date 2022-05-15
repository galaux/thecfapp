(ns cfapp.mobile.ui.page.create-score.story
  (:require
   [cfapp.mobile.ui.storybook :as sb]
   [cfapp.mobile.ui.component.menu-wrapper.core :as menu]
   [cfapp.mobile.ui.page.create-score.view :refer [create-score]]
   [reagent.core :as r]))

(def ^:export default
  (sb/->default
    {:title     "Pages/Create Score"
     :component (r/reactify-component create-score)}))

(defn ^:export for-time
  []
  (r/as-element
    [create-score
     {:menu-props  (menu/init-props)
      :logged-user sb/logged-user}
     (assoc sb/wod :workout sb/workout-for-time)]))

(defn ^:export rep-count
  []
  (r/as-element
    [create-score
     {:menu-props  (menu/init-props)
      :logged-user sb/logged-user}
     (assoc sb/wod :workout sb/workout-rep-count)]))
