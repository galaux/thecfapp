(ns cfapp.mobile.ui.page.my-communities.story
  (:require
   [cfapp.mobile.ui.page.my-communities.view :refer [my-communities]]
   [cfapp.mobile.ui.storybook :refer [->default]]
   [reagent.core :as r]
   [cfapp.mobile.ui.component.menu-wrapper.core :as menu]))

(def ^:export default
  (->default
    {:title     "Pages/My Communities"
     :component my-communities}))

(def ^:private logged-user
  {:first-name  "Joanna"
   :last-name   "Wallace"
   :avatar-path
   "https://randomuser.me/api/portraits/women/13.jpg"})

(def ^:private communities
  [{:id           "89898"
    :name         "CrossFit Lutèce"
    :address      {:street   "4 rue Jules Simon"
                   :zip-code "75015"
                   :city     "Paris"
                   :country  "France"}
    :is-member?   true
    :member-count 9}
   {:id           "134"
    :name         "Crossfit Dansaert"
    :address      {:street   "Rue Vandenbranden, 1"
                   :zip-code "1000"
                   :city     "Brussels"
                   :country  "Belgium"}
    :is-member?   true
    :member-count 28}])

(defn ^:export base
  [_args]
  (let [menu-props (menu/init-props)]
    (r/as-element
      [my-communities
       {:menu-props      menu-props
        :logged-user     logged-user
        :communities     communities
        :leave-community println}])))

(defn ^:export no-communities
  [_args]
  (let [menu-props (menu/init-props)]
    (r/as-element
      [my-communities
       {:menu-props      menu-props
        :logged-user     logged-user
        :communities     []
        :leave-community println}])))
