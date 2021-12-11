(ns cfapp.mobile.ui.atom.text.style
  (:require
   [cfapp.mobile.style.theme.core :refer [font-weight text-size]]))

(defn text
  [font-weight-id text-size-id]
  {:padding     "12px 0"
   :font-weight (font-weight font-weight-id)
   :font-size   (text-size text-size-id :size)})
