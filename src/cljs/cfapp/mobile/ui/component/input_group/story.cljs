(ns cfapp.mobile.ui.component.input-group.story
  (:require
   [cfapp.mobile.ui.component.input-group.view :refer [input-group]]
   [cfapp.mobile.ui.storybook :refer [->default]]
   [reagent.core :as r]))

(def ^:export default
  (->default
    {:title     "Component/Input Group"
     :component input-group}))

(defn wrapper
  [state props]
  (fn []
    [input-group
     (merge
       {:label     "Label"
        :value     @state
        :on-change (fn [v]
                     (println "New value: " v)
                     (reset! state v))}
       props)]))

(defn ^:export text
  [_args]
  (let [state (r/atom "Init")]
    (r/as-element
      [wrapper state {}])))

(defn ^:export number
  [_args]
  (let [state (r/atom 0)]
    (r/as-element
      [wrapper state {:type :number}])))
