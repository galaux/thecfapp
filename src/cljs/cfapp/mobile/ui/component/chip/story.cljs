(ns cfapp.mobile.ui.component.chip.story
  (:require
   ["lucide-react" :refer [Users]]
   [cfapp.mobile.ui.component.chip.view :refer [chip]]
   [cfapp.mobile.ui.storybook :refer [->default]]
   [reagent.core :as r]))

(def ^:export default
  (->default
    {:title     "Component/Chip"
     :component chip}))

(defn ^:export base
  [_args]
  (r/as-element
    [chip Users 9]))
