(ns cfapp.mobile.ui.component.wod-card.story
  (:require
   [cfapp.mobile.ui.component.wod-card.view :refer [wod-card]]
   [cfapp.mobile.ui.storybook :refer [->default]]
   [reagent.core :as r]))

(def ^:export default
  (->default
    {:title     "Component/WoD Card"
     :component wod-card}))

(defn ^:export base
  [_args]
  (r/as-element
    [wod-card
     {:name        "DT"
      :description "Run till you die"
      :score-count 23}]))
