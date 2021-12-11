(ns cfapp.mobile.ui.page.find-community.style
  (:require
   [cfapp.mobile.style.theme.core :refer [border-radius color font-weight]]
   [garden.util :refer [space-join]]))

(defn top-line
  []
  {:display         "flex"
   :flex-direction  "row"
   :flex-wrap       "nowrap"
   :justify-content "space-between"})

(defn menu-icon
  []
  {:color (color :white)})

(defn user-icon
  []
  {:color (color :white)})

(defn title
  []
  {:color (color :white)})

(defn main-heading
  []
  {:color       (color :white)
   :padding-top "30px"})

(defn header
  []
  (with-meta
    {:height   "150px"
     :position "relative"
     :z-index  1
     :padding  "15px"}
    {:pseudo
     {:before
      {:content          "''"
       :position         "absolute"
       :z-index          -1
       :top              0
       :right            0
       :bottom           0
       :left             0
       :background-image "url(./images/header-background-image.png)"
       :background-position "center left"
       :opacity          0.1
       :border-radius    (space-join [0 0 (border-radius :xsmall)
                                      (border-radius :xsmall)])}}}))

(defn body
  []
  {:background-color "blue"
   :flex-grow        1})

(defn footer
  []
  {:height "30px"
   :background-color "red"})

(defn container
  []
  {:min-height       "100%"
   :display          "flex"
   :flex-direction   "column"
   :background-color (color :gray80)})
