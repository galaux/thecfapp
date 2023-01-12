(ns cfapp.mobile.ui.component.horizontal-stamp.style
  (:require
   [cfapp.mobile.ui.component.text.style :as text-style]))

(defn right-words
  []
  {:padding-left "5px"})

(defn right-word
  []
  ;; Don't factorize into `[text …]`, we need the `[:div …]` to stack words
  ^{:extend
    [text-style/text
     {:text-transform :uppercase
      :text-size      :xxxsmall
      :color          :gray30}]}
  {})

(defn horizontal-stamp
  []
  {:display     :flex
   :align-items :center})
