(ns cfapp.mobile.ui.component.text.style
  (:require
   [cfapp.mobile.ui.style.theme.core :refer [color font-weight text-size]]))

(defn text
  [props]
  (let [color-id          (:color props)
        font-weight-id    (:font-weight props)
        text-size-id      (:text-size props :small)
        text-transform-id (:text-transform props)]
    (cond-> {}
      color-id          (assoc :color (color color-id))
      font-weight-id    (assoc :font-weight (font-weight font-weight-id))
      text-size-id      (assoc
                          :font-size
                          (text-size text-size-id :size)
                          :line-height
                          (text-size text-size-id :height))
      text-transform-id (assoc :text-transform text-transform-id))))
