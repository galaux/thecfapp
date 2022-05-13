(ns cfapp.mobile.ui.component.community-card.style
  (:require
   [cfapp.mobile.ui.style.theme.core :refer [border-radius color edge-size]]))

(defn community-card
  []
  {:background-color (color :gray80)
   :border-radius    (border-radius :small)
   :display          "flex"
   :flex-direction   "column"
   :padding          (edge-size :medium)
   :min-height       "120px"})

(defn top-line
  []
  {:display         "flex"
   :flex-direction  "row"
   :justify-content "space-between"})

(defn address
  []
  {:flex-grow 1
   :color     (color :gray30)})

(defn bottom-line
  []
  {:display         "flex"
   :justify-content "flex-end"})
