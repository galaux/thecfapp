(ns cfapp.mobile.ui.component.wod-card.view
  (:require
   ["lucide-react" :refer [Users]]
   [cfapp.mobile.ui.component.wod-card.style :as style]
   [cfapp.mobile.ui.component.chip.view :refer [chip]]
   [cfapp.mobile.ui.component.text.view :refer [text]]
   [herb.core :refer [<class]]))

(defn wod-card
  [{:keys [name score-count description]}]
  [:div
   {:class (<class style/wod-card)}
   [:div
    [:div
     {:class (<class style/header)}
     [text
      {:text-size      :small
       :text-transform "uppercase"}
      name]
     [chip Users score-count]]
    [:div
     {:class (<class style/description)}
     description]]
   [:div
    {:class (<class style/footer)}
    "Jul 22"]])
