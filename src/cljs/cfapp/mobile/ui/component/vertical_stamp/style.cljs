(ns cfapp.mobile.ui.component.vertical-stamp.style
  (:require
   [cfapp.mobile.ui.component.text.style :as text-style]))

(defn vertical-stamp
  []
  {:display        :flex
   :flex-direction :column})

(defn small-word
  []
  ^{:extend [text-style/text
             {:text-transform :uppercase
              :text-size      :xsmall
              :color          :gray50}]}
  {:text-align :center})

(defn big-word
  []
  ^{:extend [text-style/text {:text-size :medium}]}
  {:text-align :center
   :margin-top "-10px"})
