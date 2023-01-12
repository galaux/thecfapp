(ns cfapp.mobile.ui.component.button.view
  (:require
   [herb.core :refer [<class]]
   [cfapp.mobile.ui.component.button.style :as style]))

(defn button
  [{:keys [type variant on-click href]
    :or   {type :button variant :primary}}
   label]
  (case type
    :button
    [:button
     {:class    (<class style/button variant)
      :on-click on-click}
     label]
    :link
    [:a
     {:class (<class style/button variant)
      :href  href}
     label]))
