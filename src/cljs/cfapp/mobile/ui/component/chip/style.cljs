(ns cfapp.mobile.ui.component.chip.style
  (:require
   [cfapp.mobile.ui.component.text.style :as text-style]
   [cfapp.mobile.ui.style.theme.core :refer [border-radius color]]
   [garden.util :refer [space-join]]))

(defn chip
  [color-key]
  {:background-color (color color-key)
   :border-radius    (border-radius :xsmall)
   :display          :flex
   :height           "25px"
   :align-items      :center})

(defn icon
  []
  {:padding (space-join [0 "4px" 0 "10px"])})

(defn chip-count
  []
  ^{:extend [text-style/text {:text-size :xxsmall}]}
  {:padding (space-join [0 "10px" 0 "4px"])})
