(ns cfapp.mobile.ui.molecule.text-input-group.view
  (:require
   [cfapp.mobile.ui.atom.icon.view :refer [icon]]
   ["lucide-react" :refer [Search]]
   [cfapp.mobile.ui.atom.text.view :refer [text]]
   [cfapp.mobile.ui.molecule.text-input-group.style :as style]
   [herb.core :refer [<class]]))

(defn text-input-group
  [{:keys [id label value on-change on-click]}]
  [:div
   {:class (<class style/text-input-group)}
   [:label
    {:for   id
     :class (<class style/label)}
    [text {:text-size :xsmall} label]]
   [:div
    {:class (<class style/input-line)}
    [:input
     {:id        id
      :class     (<class style/input)
      :value     value
      :on-change #(on-change (->> %
                                  .-target
                                  .-value))}]
    [:button
     {:class    (<class style/button)
      :on-click #(on-click value)}
     [icon Search]]]])
