(ns cfapp.mobile.ui.page.find-community.story
  (:require
   [cfapp.mobile.ui.page.find-community.view :refer [find-community]]
   [cfapp.mobile.ui.storybook :refer [->default]]
   [reagent.core :as r]
   [cfapp.mobile.ui.component.menu-wrapper.core :as menu]
   [cfapp.mobile.ui.storybook :as sb]))

(def ^:export default
  (->default
    {:title     "Pages/Find Community"
     :component find-community}))

(defn ^:export primary
  [_args]
  (let [menu-props (menu/init-props)]
    (r/as-element
      [find-community
       {:menu-props  menu-props
        :logged-user sb/logged-user
        :communities sb/communities}])))

(defn ^:export no-search-yet
  [_args]
  (let [menu-props (menu/init-props)]
    (r/as-element
      [find-community
       {:menu-props  menu-props
        :logged-user sb/logged-user
        :communities nil}])))

(defn ^:export no-communities
  [_args]
  (let [menu-props (menu/init-props)]
    (r/as-element
      [find-community
       {:menu-props  menu-props
        :logged-user sb/logged-user
        :communities []}])))
