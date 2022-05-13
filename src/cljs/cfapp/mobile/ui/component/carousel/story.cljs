(ns cfapp.mobile.ui.component.carousel.story
  (:require
   [cfapp.mobile.ui.component.carousel.view :refer [carousel]]
   [cfapp.mobile.ui.storybook :refer [->default]]
   [reagent.core :as r]))

(def ^:export default
  (->default
    {:title     "Component/Carousel"
     :component carousel}))

(defn ^:export base
  [_args]
  (r/as-element
    [carousel
     [:div
      {:style {:background-color "tomato"
               :height 100}}
      "First"]
     [:div
      {:style {:background-color "lightblue"
               :height 100}}
      "Second"]
     [:div
      {:style {:background-color "olive"
               :height 100}}
      "Third"]]))
