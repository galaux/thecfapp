(ns cfapp.mobile.ui.atom.icon.view)

(def ^:private ->variant
  {:duotone "d"
   :light   "l"
   :regular "r"
   :solid   "s"
   :thin    "t"})

(defn icon
  ([id] (icon {} id))
  ([{:keys [variant] :or {variant :light}} id]
   [:div
    {:class (str "fa" (->variant variant) " fa-" (name id))}]))
