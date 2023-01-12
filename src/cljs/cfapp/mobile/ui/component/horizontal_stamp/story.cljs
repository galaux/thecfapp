(ns cfapp.mobile.ui.component.horizontal-stamp.story
  (:require
   [cfapp.mobile.ui.component.horizontal-stamp.view
    :refer [horizontal-stamp]]
   [cfapp.mobile.ui.storybook :as sb]
   [reagent.core :as r]))

(def ^:export default
  (sb/->default
    {:title     "Pages/Horizontal Stamp"
     :component (r/reactify-component horizontal-stamp)}))

(defn ^:export base
  []
  (r/as-element
    [horizontal-stamp "24" "WOD" "performed"]))
