(ns cfapp.mobile.ui.molecule.menu.story
  (:require
   [cfapp.mobile.ui.molecule.menu.view :refer [menu menu-icon]]
   [cfapp.mobile.ui.molecule.menu.core :refer [init-state]]
   [cfapp.mobile.ui.storybook :refer [->default]]
   [reagent.core :as r]))

(def ^:export default
  (->default
    {:title     "Molecules/Menu"
     :component menu}))

(defn ^:export base
  [args]
  (r/as-element
    (let [{:keys [open] :as state} (init-state)
          index (rand-int 100)]
      [:div
       [menu
        (merge
          state
          {:user-name   "Joanna Wallace"
           :avatar-path
           (str "https://randomuser.me/api/portraits/women/" index ".jpg")})]
       [menu-icon {:open open}]])))
