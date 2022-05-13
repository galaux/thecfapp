(ns cfapp.mobile.ui.component.chip.style
  (:require
   [cfapp.mobile.ui.style.theme.core :refer [border-radius color]]))

(defn chip
  []
  {:background-color (color :dark-blue)
   :border-radius    (border-radius :xsmall)
   :display          "flex"
   :flex-direction   "row"
   :height           "25px"
   :width            "50px"
   :padding          "4px 0 0 10px"})

(defn icon
  []
  {:padding-right "5px"})
