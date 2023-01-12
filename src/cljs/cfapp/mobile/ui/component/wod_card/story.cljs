(ns cfapp.mobile.ui.component.wod-card.story
  (:require
   [cfapp.mobile.ui.component.wod-card.view :refer [wod-card workout-card]]
   [cfapp.mobile.ui.storybook :as sb-util]
   [reagent.core :as r]))

(def ^:export default
  (sb-util/->default
    {:title     "Component/WoD Card"
     :component wod-card}))

(def ^:private href-props
  {:href {:create-score identity}})

(defn ^:export wod-card-multi-amrap
  [_args]
  (r/as-element
    [wod-card
     href-props
     sb-util/wod]))

(defn ^:export workout-card-for-time
  [_args]
  (r/as-element
    [workout-card sb-util/workout-for-time]))
