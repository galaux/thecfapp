(ns cfapp.mobile.ui.component.card.style
  (:require
   [cfapp.mobile.ui.style.theme.core :refer [border-radius color]]
   [garden.util :refer [space-join]]))

(defn card
  [color-key]
  (with-meta
    {:background-color (color :gray80)
     :border-radius    (border-radius :small)
     :min-height       "200px"
     :padding          "20px"
     ;; Required so that the header can use `:position :absolute`
     :position         :relative}
    (when color-key
      {:pseudo
       {:before
        {:content          "''"
         :position         :absolute
         :top              0
         :left             0
         :width            "100%"
         :height           "7px"
         :background-color (color color-key)
         :border-radius    (space-join [(border-radius :small)
                                        (border-radius :small)
                                        0
                                        0])}}})))
