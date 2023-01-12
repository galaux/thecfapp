(ns cfapp.mobile.ui.page.abstract-wod-score.style
  (:require
   [garden.util :refer [space-join]]))

(defn workout-name
  []
  {:text-transform :uppercase
   :padding-top    "10px"})

(defn header-footer
  []
  {:display         :flex
   :justify-content :space-between
   :padding-top     "40px"})

(defn workout-loop
  []
  {:padding (space-join ["10px" 0 "10px" 0])})
