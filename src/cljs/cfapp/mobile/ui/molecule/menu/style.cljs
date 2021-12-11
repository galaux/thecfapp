(ns cfapp.mobile.ui.molecule.menu.style
  (:require
   [cfapp.mobile.style.theme.core :refer [color]]))

(defn close-icon
  []
  {:position    "absolute"
   :top         "10px"
   :right       "10px"
   :margin-left "50px"})

(def avatar
  {:height           "100px"
   :width            "100px"
   :background-color (color :gray60)
   :border-radius    "50%"
   :display          "inline-block"})

(def avatar-circle
  {;; Ensures avatar sits on top of its parent div
   :position         "absolute"
   :margin           "3px 0 0 3px"
   :height           "94px"
   :width            "94px"
   :background-color (color :gray80)
   :border-radius    "50%"
   :display          "inline-block"})

(def avatar-image
  {:object-fit    "cover"
   :border-radius "50%"
   :height        "88px"
   :width         "88px"
   ;; Ensures avatar sits on top of its parent div
   :position      "absolute"
   :margin        "3px 0 0 3px"})

(defn menu-icon
  []
  {})

(defn menu-item
  []
  {:padding "20px 0 0 20px"})

(defn menu-item-icon
  []
  {:padding-right "15px"})

(defn menu
  [open?]
  {:height           "100%"
   :width            (if open? "250px" 0)
   :position         "fixed"
   :z-index          1
   :top              0
   :left             0
   :background-color (color :gray80)
   :color            (color :gray10)
   ;; Disable horizontal scroll
   :overflow-x       "hidden"
   :transition       "0.3s"
   ;; FIXME the left padding prevents the menu from disappearing completely
   :padding          (if open?
                       "60px 0 0 30px"
                       "0")
   :border-radius    "0 10px 0 0"})
