(ns cfapp.mobile.ui.atom.button.story
  (:require
   [cfapp.mobile.ui.atom.button.view :refer [button]]
   [cfapp.mobile.ui.storybook :refer [->default]]
   [reagent.core :as r]))

(def ^:export default
  (->default
    {:title     "Atoms/Button"
     :component button}))

(defn ^:export base
  [_args]
  (r/as-element
    [:div
     (for [variant [:primary :success :danger :warning :info]]
       ^{:key variant}
       [button
        {:variant  variant
         :on-click #(println variant)}
        variant])]))
