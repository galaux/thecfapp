(ns cfapp.mobile.ui.component.text-input-group.style
  (:require
   [cfapp.mobile.style.theme.core
    :refer
    [border-radius border-width color]]
   [garden.color :refer [darken]]
   [garden.util :refer [space-join]]))

(defn text-input-group
  []
  {:display        "flex"
   :flex-direction "column"})

(defn label
  []
  {:color         (color :gray50)
   :margin-bottom ".5rem"})

(defn input-line
  []
  {:display        "flex"
   :flex-direction "row"
   :padding-top    "5px"})

(defn input
  []
  {:border-radius
   (space-join [(border-radius :xxxsmall) 0 0 (border-radius :xxxsmall)])
   :border        (space-join ["1px" "solid" (color :white)])
   :border-right  0
   :outline       "none"
   :height        "2rem"
   :flex-grow     1})

(defn button
  []
  (with-meta
    {:background    (color :blue)
     :color         (color :white)
     :border        (space-join ["1px" "solid" (color :blue)])
     :border-radius
     (space-join [0 (border-radius :xxxsmall) (border-radius :xxxsmall) 0])
     :height        "2rem"
     :width         "2.5rem"
     :cursor        "pointer"
     :transition    "all 0.2s ease-in-out"}
    {:pseudo
     {:hover
      {:background   (darken (color :blue) 10)
       :border-color (darken (color :blue) 10)}
      :active
      {:transform "scale(0.95)"}
      :focus
      {:outline "none"
       :border
       (space-join [(border-width :large) "dashed" (color :gray80)])}}}))
