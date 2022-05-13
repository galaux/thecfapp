(ns cfapp.mobile.ui.page.community-wods.story
  (:require
   [cfapp.mobile.ui.component.menu-wrapper.core :as menu]
   [cfapp.mobile.ui.page.community-wods.view :refer [community-wods]]
   [cfapp.mobile.ui.storybook :refer [->default]]
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

(def ^:private community
  {:id           "89898"
   :name         "CrossFit Dansaert"
   :address      {:street   "Rue Vandenbranden 1"
                  :zip-code "1000"
                  :city     "Brussels"
                  :country  "Belgium"}
   :is-member?   true
   :member-count 28})

(defn ^:export base
  []
  (r/as-element
    [community-wods
     {:menu-props  (menu/init-props)
      :logged-user logged-user}
     community
     [{:name        "DT"
       :description "Run till you die"
       :score-count 23}
      {:name        "Murph"
       :description "Run, pull-ups, push-ups, squat and run"
       :score-count 93}
      {:name        "Annie"
       :description "Are you OK?"
       :score-count 3}]]))
