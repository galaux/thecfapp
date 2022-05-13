(ns cfapp.mobile.ui.component.icon.view)

(defn icon
  ([feather-comp]
   (icon {} feather-comp))
  ([{:keys [size]
     :or   {size 20}} feather-comp]
   [:> feather-comp
    {:size size}]))
