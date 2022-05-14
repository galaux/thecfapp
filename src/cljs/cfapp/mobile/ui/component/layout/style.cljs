(ns cfapp.mobile.ui.component.layout.style
  (:require
   [cfapp.mobile.ui.style.theme.core :refer [border-radius]]
   [garden.util :refer [space-join]]))

(defn top-line
  []
  {:display         :flex
   :justify-content :space-between
   :padding         "15px"
   ;; Allows children to be clickable
   :position        :relative})

(defn header-wrapper
  []
  ;; Required so that the header can use `:position :absolute`
  {:position :relative})

(defn header
  []
  (let [height   "200px"
        h-radius (space-join
                   [0 0 (border-radius :xsmall) (border-radius :xsmall)])]
    (with-meta
      {:min-height    height
       :border-radius h-radius
       :display       :block
       :position      :relative}
      {:pseudo
       {:after
        {:content          "''"
         :position         :absolute
         :top              0
         :right            0
         :bottom           0
         :left             0
         :background-image "url(./images/header-background-image.png)"
         :opacity          0.1
         :z-index          -1}}})))

(defn sub-header
  []
  {:padding "15px"})

(defn body
  []
  {:padding "15px"})

(defn footer
  []
  {:height "30px"})

(defn container
  []
  {:height          "100%"
   :display         :flex
   :flex-direction  :column
   :justify-content :space-between})
