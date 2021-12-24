(ns cfapp.mobile.ui.page.my-communities.style
  (:require
   [cfapp.mobile.style.theme.core :refer [color]]))

(defn not-found
  []
  {:color      (color :gray50)
   :text-align "center"})

(defn community-card
  []
  {:padding-bottom "20px"})
