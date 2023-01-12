(ns cfapp.mobile.ui.component.date-stamp.story
  (:require
   [cfapp.mobile.ui.component.date-stamp.view :refer [date-stamp]]
   [cfapp.mobile.ui.storybook :refer [->default]]
   [reagent.core :as r]))

(def ^:export default
  (->default
    {:title     "Component/Date Stamp"
     :component date-stamp}))

(defn ^:export base
  [_args]
  (r/as-element
    [date-stamp "2022-03-31"]))
