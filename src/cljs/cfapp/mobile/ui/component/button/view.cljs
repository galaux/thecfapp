(ns cfapp.mobile.ui.component.button.view
  (:require
   [herb.core :refer [<class]]
   [cfapp.mobile.ui.component.button.style :as style]))

(defn button
  [{:keys [variant on-click]
    :or   {variant :primary}}
   label]
  [:button
   {:class    (<class style/button variant)
    :on-click on-click}
   label])
