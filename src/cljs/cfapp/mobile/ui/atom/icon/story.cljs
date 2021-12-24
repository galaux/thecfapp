(ns cfapp.mobile.ui.atom.icon.story
  (:require
   ["lucide-react" :refer [Feather]]
   [cfapp.mobile.ui.atom.icon.view :refer [icon]]
   [cfapp.mobile.ui.storybook :refer [->default]]
   [reagent.core :as r]))

(def ^:export default
  (->default {:title     "Atoms/Icon"
              :component icon}))

(defn ^:export feather
  [_args]
  (r/as-element
    [:div
     [icon Feather]
     [icon {:size 48} Feather]]))
