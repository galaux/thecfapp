(ns cfapp.mobile.ui.component.wod-card.style
  (:require
   [cfapp.mobile.ui.style.theme.core :refer [border-radius color]]
   [garden.util :refer [space-join]]))

(defn wod-card
  []
  {:background-color (color :gray80)
   :border-radius    (border-radius :small)
   :min-height       "200px"
   :display          :flex
   :flex-direction   :column
   :justify-content  :space-between
   :padding          "20px"
   ;; Required so that the header can use `:position :absolute`
   :position         :relative})

(defn header
  []
  (with-meta
    {;:background-color "lightblue"
     :display         "flex"
     :justify-content "space-between"}
    {:pseudo
     {:before
      {:content          "''"
       :position         :absolute
       :top              0
       :left             0
       :width            "100%"
       :height           "7px"
       :background-color (color :blue)
       :border-radius    (space-join [(border-radius :small)
                                      (border-radius :small)
                                      0
                                      0])}}}))

(defn description
  []
  {;:background-color "olive"
   :padding-top "15px"})

(defn footer
  []
  {});:background-color "tomato"})
