(ns cfapp.mobile.ui.component.text-input-group.story
  (:require
   [cfapp.mobile.ui.component.text-input-group.view :refer [text-input-group]]
   [cfapp.mobile.ui.storybook :refer [->default]]
   [reagent.core :as r]))

(def ^:export default
  (->default
    {:title     "Component/Text Input Group"
     :component text-input-group}))

(defn ^:export base
  [_args]
  (r/as-element
    [text-input-group
     {}]))
