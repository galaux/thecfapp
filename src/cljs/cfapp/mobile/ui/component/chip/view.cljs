(ns cfapp.mobile.ui.component.chip.view
  (:require
   [cfapp.mobile.ui.component.chip.style :as style]
   [cfapp.mobile.ui.component.icon.view :refer [icon]]
   [herb.core :refer [<class]]))

(defn chip
  [icon-id chip-count & {:keys [color-key] :or {color-key :dark-blue}}]
  [:div {:class (<class style/chip color-key)}
   [:div {:class (<class style/icon)}
    [icon {:size 12} icon-id]]
   [:div {:class (<class style/chip-count)}
    chip-count]])
