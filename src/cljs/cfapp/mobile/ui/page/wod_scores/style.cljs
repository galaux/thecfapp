(ns cfapp.mobile.ui.page.wod-scores.style
  (:require
   [cfapp.mobile.ui.component.text.style :as text-style]
   [cfapp.mobile.ui.component.card.style :as card-style]
   [garden.util :refer [space-join]]))

(defn header-label
  []
  ^{:extend [text-style/text {:text-transform :uppercase}]}
  {:padding (space-join ["10px" 0 "10px" 0])})

(defn score-card
  []
  ^{:extend [card-style/card :blue]}
  {:display         :flex
   :flex-direction  :column
   :justify-content :space-between})

(defn user-name
  []
  {:list-style-type :circle
   :padding         "10px"})

(defn value
  []
  {:padding-left "5px"})
