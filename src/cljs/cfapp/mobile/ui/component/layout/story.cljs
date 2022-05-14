(ns cfapp.mobile.ui.component.layout.story
  (:require
   [cfapp.mobile.ui.component.layout.view :refer [layout]]
   [cfapp.mobile.ui.storybook :refer [->default]]
   [reagent.core :as r]))

(def ^:export default
  (->default
    {:title     "Component/Layout"
     :component layout}))

(defn ^:export primary
  [_args]
  (r/as-element
    [layout
     {:open-menu #(println "Open menu")
      :header    "Page title"}
     "Page body"]))
