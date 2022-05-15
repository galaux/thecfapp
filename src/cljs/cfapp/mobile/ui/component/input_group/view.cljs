(ns cfapp.mobile.ui.component.input-group.view
  (:require
   [cfapp.mobile.ui.component.input-group.style :as style]
   [cfapp.mobile.ui.component.text.view :refer [text]]
   [herb.core :refer [<class]]))

(defn input-group
  [{:keys [id label value on-change type] :or {type :text}}]
  ;; TODO restrict input to digits when type is :number
  [:div {:class (<class style/input-group)}
   [:label
    {:for   id
     :class (<class style/label)}
    [text {:text-size :xsmall} label]]
   [:input
    (merge
      {:id        id
       :class     (<class style/input)
       :value     value
       :on-change #(on-change (-> %
                                  .-target
                                  .-value))}
      (when (= type :number)
        {:type         type
         :input-mode   :numeric ;; Instruct mobile UI to use numpad
         :on-key-press
         (fn [evt]
           (when-not (< 48 (.-which evt) 57)
             (.preventDefault evt)))
         :on-change    #(on-change (-> %
                                       .-target
                                       .-value
                                       js/parseInt))}))]])
