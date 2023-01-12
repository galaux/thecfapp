(ns cfapp.mobile.ui.component.card.story
  (:require
   ["lucide-react" :refer [Users]]
   [cfapp.mobile.ui.component.card.view :refer [card]]
   [cfapp.mobile.ui.storybook :refer [->default]]
   [reagent.core :as r]))

(def ^:export default
  (->default
    {:title     "Component/Card"
     :component card}))

(defn ^:export base
  [_args]
  (r/as-element
    [card
     {:color :yellow}
     "Child A"
     "Child B"]))
