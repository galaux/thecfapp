(ns cfapp.mobile.ui.atom.text.style
  (:require
   [cfapp.mobile.style.theme.core :refer [font-weight text-size]]))

(defn text
  [props]
  (let [font-weight-id    (:font-weight props)
        text-size-id      (:text-size props)
        text-transform-id (:text-transform props)]
    (cond-> {}
      font-weight-id    (assoc :font-weight (font-weight font-weight-id))
      text-size-id      (assoc :font-size (text-size text-size-id :size))
      text-transform-id (assoc :text-transform text-transform-id))))
