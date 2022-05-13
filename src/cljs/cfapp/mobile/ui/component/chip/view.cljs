(ns cfapp.mobile.ui.component.chip.view
  (:require
   [cfapp.mobile.ui.component.text.view :refer [text]]
   [cfapp.mobile.ui.component.chip.style :as style]
   [herb.core :refer [<class]]
   [cfapp.mobile.ui.component.icon.view :refer [icon]]))

(defn chip
  [icon-id chip-count]
  [:div
   {:class (<class style/chip)}
   [:div
    {:class (<class style/icon)}
    [icon {:size 12} icon-id]]
   [text
    {:text-size :xxsmall}
    chip-count]])
