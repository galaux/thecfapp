(ns cfapp.mobile.ui.component.layout.style
  (:require
   [cfapp.mobile.ui.style.theme.core :refer [border-radius color]]
   [garden.util :refer [space-join]]))

(defn top-line
  []
  {:display         "flex"
   :flex-direction  "row"
   :flex-wrap       "nowrap"
   :justify-content "space-between"})

(defn menu-icon
  []
  {:color  (color :white)
   :cursor "pointer"})

(defn user-icon
  []
  {:color (color :white)})

(defn app-name
  []
  {:padding-top "10px"
   :color       (color :white)})

(defn page-heading
  []
  {:color   (color :white)
   :padding "40px 0 0 30px"})

(defn header
  []
  (with-meta
    {:height           "175px"
     :position         "relative"
     :background-color (color :gray80)
     :padding          "15px"
     :border-radius
     (space-join [0 0 (border-radius :xsmall) (border-radius :xsmall)])}
    {:pseudo
     {:before
      {:content          "''"
       :position         "absolute"
       :top              0
       :right            0
       :bottom           0
       :left             0
       :background-image "url(./images/header-background-image.png)"
       :opacity          0.1
       :background-position "center left"}}}))

(defn header-content
  []
  ;; Allows children to be clickable
  {:position "relative"})

(defn body
  []
  {:flex-grow 1
   :padding   "2.5rem 2rem 2.5rem 2rem"})

(defn footer
  []
  {:height "30px"})

(defn container
  []
  {:min-height       "100%"
   :display          "flex"
   :flex-direction   "column"
   :background-color (color :background)})
