(ns cfapp.mobile.ui.atom.text.view
  (:require
   [herb.core :refer [<class]]
   [cfapp.mobile.ui.atom.text.style :as style]))

(defn text
  ([child]
   (text {} child))
  ([props child]
   [:span
    {:class (<class style/text props)}
    child]))
