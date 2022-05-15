(ns cfapp.mobile.ui.page.community-wods.story
  (:require
   [cfapp.mobile.ui.component.menu-wrapper.core :as menu]
   [cfapp.mobile.ui.page.community-wods.view :refer [community-wods]]
   [cfapp.mobile.ui.storybook :as sb :refer [->default]]
   [reagent.core :as r]))

(def ^:export default
  (->default
    {:title     "Pages/Community WoDs"
     :component community-wods}))

(def ^:private logged-user
  {:first-name  "Joanna"
   :last-name   "Wallace"
   :avatar-path
   "https://randomuser.me/api/portraits/women/13.jpg"})

(defn ^:export base
  []
  (r/as-element
    [community-wods
     {:menu-props  (menu/init-props)
      :logged-user logged-user}
     sb/crossfit-lutece]))
