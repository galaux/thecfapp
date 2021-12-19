(ns cfapp.mobile.ui.page.find-community.story
  (:require
   [cfapp.mobile.ui.page.find-community.view :refer [find-community]]
   [cfapp.mobile.ui.storybook :refer [->default]]
   [reagent.core :as r]
   [cfapp.mobile.ui.molecule.menu.core :as menu]))

(def ^:export default
  (->default
    {:title     "Pages/Find Community"
     :component find-community}))

(defn ^:export primary
  [args]
  (let [menu-state (menu/init-state)]
    (r/as-element
      [find-community
       {:menu-state menu-state
        :user       {:first-name  "Joanna"
                     :last-name   "Wallace"
                     :avatar-path
                     "https://randomuser.me/api/portraits/women/13.jpg"}}])))
