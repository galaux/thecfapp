(ns cfapp.mobile.ui.component.wod-card.story
  (:require
   [cfapp.mobile.ui.component.wod-card.view :refer [wod-card]]
   [cfapp.mobile.ui.storybook :as sb-util]
   [reagent.core :as r]))

(def ^:export default
  (sb-util/->default
    {:title     "Component/WoD Card"
     :component wod-card}))

(def href-props
  {:href {:create-score identity}})

(defn ^:export base
  [_args]
  (r/as-element
    [wod-card
     href-props
     sb-util/wod]))
