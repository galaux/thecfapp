(ns cfapp.mobile.ui.component.search-input-group.story
  (:require
   [cfapp.mobile.ui.component.search-input-group.view :refer
    [search-input-group]]
   [cfapp.mobile.ui.storybook :refer [->default]]
   [reagent.core :as r]))

(def ^:export default
  (->default
    {:title     "Component/Search Input Group"
     :component search-input-group}))

(defn ^:export base
  [_args]
  (r/as-element
    [search-input-group
     {:label "My label"
      :value "The value"}]))
