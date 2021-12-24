(ns cfapp.mobile.ui.atom.button.view
  (:require
   [herb.core :refer [<class]]
   [cfapp.mobile.ui.atom.button.style :as style]))

(defn button
  [{:keys [variant on-click]
    :or   {variant :primary}}
   label]
  [:button
   {:class    (<class style/button variant)
    :on-click on-click}
   label])
