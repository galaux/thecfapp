(ns cfapp.mobile.ui.component.menu-wrapper.style
  (:require
   [cfapp.mobile.ui.style.theme.core :refer [color]]
   [garden.util :refer [space-join]]))

(defn close-button
  []
  {:position         "absolute"
   :top              "15px"
   :right            "15px"
   :background-color (color :gray60)
   :border-radius    "50%"
   :height           "30px"
   :width            "30px"
   :padding          "5px 5px"
   :cursor           "pointer"})

(defn avatar
  []
  {:height           "100px"
   :width            "100px"
   :background-color (color :gray60)
   :border-radius    "50%"
   :display          "inline-block"})

(defn avatar-circle
  []
  {;; Ensures circle sits on top of its parent div
   :position         "absolute"
   :margin           "3px 0 0 3px"
   :height           "94px"
   :width            "94px"
   :background-color (color :gray80)
   :border-radius    "50%"
   :display          "inline-block"})

(defn avatar-image
  []
  {:object-fit    "cover"
   :border-radius "50%"
   :height        "88px"
   :width         "88px"
   ;; Ensures avatar sits on top of its parent div
   :position      "absolute"
   :margin        "3px 0 0 3px"})

(defn drawer
  []
  {;; Drawer MUST have a "position" because its child (`close-icon`) uses
   ;; `position: absolute` which only work if parent is itself positionned
   :position         "absolute"

   ;; Allow `drawer-body` to `flex-grow`
   :display          "flex"
   :flex-direction   "column"

   :height           "100%"
   :background-color (color :gray80)
   :border-radius    "0 10px 0 0"
   :color            (color :white)
   :padding          "60px 40px 0px 40px"})

(defn drawer-body
  []
  {:display        "flex"
   :flex-direction "column"
   :flex-grow      1
   ;; Ensures text isn't gradually "compressed" when we shrink the drawer width
   :min-width      "250px"})

(defn user-first-name
  []
  {:padding-top "40px"})

(defn menu-items
  []
  {:padding-top    "30px"
   :display        "flex"
   :flex-direction "column"
   :flex-grow      1})

(defn menu-items-main
  []
  {:flex-grow 1})

(defn menu-item
  []
  {:display        "flex"
   :flex-direction "row"
   :padding-top    "25px"
   :cursor         "pointer"})

(defn menu-items-bottom
  []
  {:padding-bottom "25px"})

(defn drawer-footer
  []
  {:border-top (space-join ["1px" "solid" (color :gray60)])
   :padding    "20px 0 20px 0"})

(defn menu-item-icon
  []
  {:padding-right "15px"
   :color         (color :gray50)})

(defn overlay-invisible
  []
  {:opacity   1
   :flex-grow 1
   :height    "100%"})

(defn page-body
  []
  {:position "absolute"
   :top      0
   :left     0
   :height   "100%"
   :width    "100%"})

(defn menu-overlay
  [open?]
  {:position       "absolute"

   ;; For children to be able to use flex-grow
   :display        "flex"
   :flex-direction "row"

   :top            0
   :left           0
   :height         "100%"
   :width          (if open? "100%" 0)
   :z-index        10
   ;; Disable horizontal scroll (and completely hide drawer when closed)
   :overflow-x     "hidden"

   :transition     "0.3s"})

(defn menu-wrapper
  []
  {;; For children (overlay and page body) to be able to stack
   ;; on top of each other with `:position "absolute"`
   :position "relative"
   :height   "100%"
   :width    "100%"})
