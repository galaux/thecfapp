(ns cfapp.mobile.ui.component.button.style
  (:require
   [cfapp.mobile.ui.style.theme.core
    :refer
    [border-radius border-width color edge-size font-weight text-size]]
   [garden.util :refer [space-join]]
   [garden.color :refer [darken]]))

(defn button
  [variant]
  (let [color-hex (color (if (= variant :primary)
                           :blue
                           variant))]
    (with-meta
      {:align-items     "center"
       :border-radius   (border-radius :small)
       :display         "flex"
       :font-size       (text-size :xsmall :size)
       :line-height     (text-size :xsmall :height)
       :font-family     "inherit"
       :justify-content "center"
       :width           "auto"
       :height          "2rem"
       :padding         (space-join [0 (edge-size :medium)])
       :text-align      "center"
       :text-decoration "none"
       :transition      "all 0.2s ease-in-out"
       :font-weight     (font-weight :medium)
       :cursor          "pointer"
       :background      color-hex
       :border          (space-join ["1px" "solid" color-hex])
       :color           (color :white)}
      {:pseudo
       {:focus
        {:outline "none"
         :border
         (space-join [(border-width :large) "dashed" (color :gray80)])}
        :hover
        {:background   (darken color-hex 10)
         :border-color (darken color-hex 10)}
        :active
        {:transform "scale(0.95)"}}})))
