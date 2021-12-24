(ns cfapp.mobile.ui.page.find-community.style
  (:require
   [cfapp.mobile.style.theme.core :refer [color]]))

(defn not-found
  []
  {:color      (color :gray50)
   :text-align "center"})

(defn community-list
  []
  {:padding-top "30px"})

(defn community-card
  []
  {:padding-bottom "20px"})
