(ns cfapp.mobile.ui.component.vertical-stamp.view
  (:require
   [cfapp.mobile.ui.component.vertical-stamp.style :as style]
   [herb.core :refer [<class]]))

(defn vertical-stamp
  [small-word big-word]
  [:div {:class (<class style/vertical-stamp)}
   [:div {:class (<class style/small-word)}
    small-word]
   [:div {:class (<class style/big-word)}
    big-word]])
