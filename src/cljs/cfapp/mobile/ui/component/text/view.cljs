(ns cfapp.mobile.ui.component.text.view
  (:require
   [herb.core :refer [<class]]
   [cfapp.mobile.ui.component.text.style :as style]))

(defn text
  ([child]
   (text {} child))
  ([props child]
   [:span
    {:class (<class style/text props)}
    child]))
