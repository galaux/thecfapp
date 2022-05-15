(ns cfapp.mobile.ui.page.wod-scores.story
  (:require
   [cfapp.mobile.ui.storybook :as sb]
   [cfapp.mobile.ui.component.menu-wrapper.core :as menu]
   [cfapp.mobile.ui.page.wod-scores.view :refer [wod-scores]]
   [reagent.core :as r]))

(def ^:export default
  (sb/->default
    {:title     "Pages/Wod Scores"
     :component (r/reactify-component wod-scores)}))

(defn ^:export for-time
  []
  (r/as-element
    [wod-scores
     {:menu-props  (menu/init-props)
      :logged-user sb/logged-user}
     (assoc sb/wod
       :workout sb/workout-for-time
       :scores  sb/scores-for-duration)]))

(defn ^:export rep-count
  []
  (r/as-element
    [wod-scores
     {:menu-props  (menu/init-props)
      :logged-user sb/logged-user}
     (assoc sb/wod
       :workout sb/workout-rep-count
       :scores  sb/scores-for-rep-count)]))
