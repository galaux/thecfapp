(ns cfapp.mobile.ui.style.theme.core
  (:require
   [cfapp.mobile.ui.style.theme.base :as base]))

;; Modify this def live with `(set! theme-id :blue)` to change the theme
(def theme-id :base)

;; To declare a new theme:
;; - copy file `./base.cljs` into a brand new one
;; - update its values
;;   - adding new keys serves no purpose
;;   - the most interesting keys are the three `colors/brand%`
;; - declare a new entry with identical values in the following map

(def ^:private themes
  {:base
   {:border-radiuses base/border-radiuses
    :border-widths   base/border-widths
    :colors          base/colors
    :edge-sizes      base/edge-sizes
    :font-weights    base/font-weights
    :text-sizes      base/text-sizes}})

(defn border-radius
  [border-radius-id]
  (get-in themes [theme-id :border-radiuses border-radius-id]))

(defn border-width
  [border-width-id]
  (get-in themes [theme-id :border-widths border-width-id]))

(defn color
  [color-id]
  (get-in themes [theme-id :colors color-id]))

(defn edge-size
  [edge-size-id]
  (get-in themes [theme-id :edge-sizes edge-size-id]))

(defn font-weight
  [font-weight-id]
  (get-in themes [theme-id :font-weights font-weight-id]))

(defn text-size
  ([text-id]
   (get-in themes [theme-id :text-sizes text-id]))
  ([text-id selector]
   (get-in themes [theme-id :text-sizes text-id selector])))
