(ns cfapp.mobile.ui.component.input-group.style
  (:require
   [cfapp.mobile.ui.style.theme.core :refer [border-radius color]]
   [garden.util :refer [space-join]]))

(defn input-group
  []
  {:display        :flex
   :flex-direction :column})

(defn label
  []
  {:color         (color :gray50)
   :margin-bottom ".5rem"})

(defn input
  []
  {:border-radius (border-radius :xxxsmall)
   :border        (space-join ["1px" "solid" (color :white)])
   :height        "2rem"
   :padding-top   "5px"})
