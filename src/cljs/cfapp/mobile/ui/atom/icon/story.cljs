(ns cfapp.mobile.ui.atom.icon.story
  (:require
   [cfapp.mobile.ui.atom.icon.view :refer [icon]]
   [cfapp.mobile.ui.storybook :refer [->default]]
   [reagent.core :as r]
   [herb.core :refer [<class]]))


(def ^:export default
  (->default {:title     "Atoms/Icon"
              :component icon}))

(defn ^:export primary
  [args]
  (r/as-element
    [:div
     {:class (<class (fn []
                       {:display        "flex"
                        :flex-direction "column"}))}
     (for [ic [:cog :server :file-invoice :hourglass-end
               :cog :file-check :file-certificate]]
       ^{:key ic}
       [:div
        {:class (<class (fn []
                          {:display        "flex"
                           :flex-direction "row"
                           :padding        "10px"}))}
        (for [variant [:duotone :light :regular :solid :thin]]
          ^{:key variant}
          [icon {:variant variant} ic])])]))
