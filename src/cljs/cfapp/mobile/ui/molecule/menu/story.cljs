(ns cfapp.mobile.ui.molecule.menu.story
  (:require
   [cfapp.mobile.ui.molecule.menu.view :refer [menuable-layout]]
   [cfapp.mobile.ui.molecule.menu.core :refer [init-state]]
   [cfapp.mobile.ui.storybook :refer [->default]]
   [reagent.core :as r]))

(def ^:export default
  (->default
    {:title     "Molecules/Menu layout"
     :component menuable-layout}))

(defn ^:export base
  [args]
  (r/as-element
    (let [{:keys [atom-open? open close] :as menu-state} (init-state true)]
      [menuable-layout
       {:menu-state menu-state
        :user       {:first-name  "Joanna"
                     :last-name   "Wallace"
                     :avatar-path
                     (str "https://randomuser.me/api/portraits/women/83.jpg")}}
       [:button
        {:on-click open}
        "Open"]])))
