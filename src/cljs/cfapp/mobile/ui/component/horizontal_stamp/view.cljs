(ns cfapp.mobile.ui.component.horizontal-stamp.view
  (:require
   [cfapp.mobile.ui.component.text.view :refer [text]]
   [cfapp.mobile.ui.component.horizontal-stamp.style :as style]
   [herb.core :refer [<class]]))

(defn horizontal-stamp
  [big-word & right-words]
  [:div {:class (<class style/horizontal-stamp)}
   [text {:text-size :large}
    big-word]
   (into [:div {:class (<class style/right-words)}]
         (map (fn [word]
                [:div {:class (<class style/right-word)} word])
           right-words))])
