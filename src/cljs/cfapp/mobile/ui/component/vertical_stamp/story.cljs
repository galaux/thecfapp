(ns cfapp.mobile.ui.component.vertical-stamp.story
  (:require
   [cfapp.mobile.ui.component.vertical-stamp.view
    :refer [vertical-stamp]]
   [cfapp.mobile.ui.storybook :as sb]
   [reagent.core :as r]))

(def ^:export default
  (sb/->default
    {:title     "Component/Vertical Stamp"
     :component (r/reactify-component vertical-stamp)}))

(defn ^:export base
  []
  (r/as-element
    [vertical-stamp "Jun" "16"]))
