(ns cfapp.mobile.ui.component.text-area.view)

(defn text-area
  [{:keys [value on-change]}]
  [:textarea
   {:rows      15
    :cols      50
    :value     value
    :on-change #(on-change (.-value (.-target %)))}])
