(ns cfapp.mobile.ui.component.text-area.story
  (:require
   [cfapp.mobile.ui.component.menu-wrapper.core :as menu]
   [cfapp.mobile.ui.component.text-area.view :refer [text-area]]
   [cfapp.mobile.ui.storybook :as sb :refer [->default]]
   [reagent.core :as r]))

(def ^:export default
  (->default
    {:title     "Component/Text Area"
     :component text-area}))

(defn wrapper
  [!state]
  (fn []
    [text-area
     {:value     @!state
      :on-change #(reset! !state %)}]))

(defn ^:export base
  []
  (let [!state (r/atom nil)]
    (r/as-element
      [wrapper !state])))
