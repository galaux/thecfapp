(ns cfapp.mobile.ui.component.card.view
  (:require
   [cfapp.mobile.ui.component.card.style :as style]
   [herb.core :refer [<class]]))

(defn card
  [{color-key :color} & children]
  [:div {:class (<class style/card color-key)}
   children])
