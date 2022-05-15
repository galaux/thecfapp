(ns cfapp.mobile.ui.page.create-score.style
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

(defn card-body
  []
  {;:background-color :pink
   :display :flex})

(defn input-group
  []
  {;:background-color :tomato
   :width   "50%"
   :padding "10px"})

(defn footer
  []
  {:display         :flex
   :justify-content :end})
