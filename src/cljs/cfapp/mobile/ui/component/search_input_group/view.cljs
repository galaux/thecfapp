(ns cfapp.mobile.ui.component.search-input-group.view
  (:require
   ["lucide-react" :refer [Search]]
   [cfapp.mobile.ui.component.icon.view :refer [icon]]
   [cfapp.mobile.ui.component.search-input-group.style :as style]
   [cfapp.mobile.ui.component.text.view :refer [text]]
   [cfapp.mobile.ui.style.theme.core :refer [border-radius color]]
   [garden.util :refer [space-join]]
   [herb.core :refer [<class]]))

;; TODO Factorize this with input-group
(defn search-input-group
  [{:keys [id label value on-change on-click]}]
  [:div
   {:class (<class style/input-group)}
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

(defn input-group
  [{:keys [id label value on-change]}]
  ;style/input-group)}
  [:div
   {:class (<class (fn []
                     {:display        :flex
                      :flex-direction :column}))}
   [:label
    {:for   id
     ;style/label)}
     :class (<class (fn []
                      {:color         (color :gray50)
                       :margin-bottom ".5rem"}))}
    [text {:text-size :xsmall} label]]
   [:input
    {:id        id
     ;style/input)
     :class     (<class
                  (fn []
                    {:border-radius (border-radius :xxxsmall)
                     :border        (space-join ["1px" "solid" (color :white)])
                     :height        "2rem"}))
     :value     value
     :on-change #(on-change (->> %
                                 .-target
                                 .-value))}]])
