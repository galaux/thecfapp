(ns cfapp.mobile.ui.molecule.layout.story
  (:require
   [cfapp.mobile.ui.molecule.layout.view :refer [layout]]
   [cfapp.mobile.ui.storybook :refer [->default]]
   [reagent.core :as r]))

(def ^:export default
  (->default
    {:title     "Molecules/Layout"
     :component layout}))

(defn ^:export primary
  [_args]
  (r/as-element
    [layout
     {:open-menu #(println "Open menu")
      :title     "Page title"}
     "Page body"]))
