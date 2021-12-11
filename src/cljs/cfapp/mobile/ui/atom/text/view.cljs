(ns cfapp.mobile.ui.atom.text.view
  (:require
   [herb.core :refer [<class]]
   [cfapp.mobile.ui.atom.text.style :as style]))

(defn text
  ([child]
   (text {} child))
  ([{:keys [font-weight text-size]} child]
   [:div
    {:class (<class style/text font-weight text-size)}
    child]))
