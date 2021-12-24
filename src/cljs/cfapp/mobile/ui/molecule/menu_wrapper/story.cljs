(ns cfapp.mobile.ui.molecule.menu-wrapper.story
  (:require
   [cfapp.mobile.ui.molecule.menu-wrapper.view :refer [menu-wrapper]]
   [cfapp.mobile.ui.molecule.menu-wrapper.core :as core]
   [cfapp.mobile.ui.storybook :refer [->default]]
   [reagent.core :as r]))

(def ^:export default
  (->default
    {:title     "Molecules/Menu Wrapper"
     :component menu-wrapper}))

(def ^:private logged-user
  {:first-name  "Joanna"
   :last-name   "Wallace"
   :avatar-path
   (str "https://randomuser.me/api/portraits/women/83.jpg")})

(defn ^:export base
  [_args]
  (r/as-element
    (let [menu-props (assoc (core/init-props true)
                       :log-out #(println "Log out")
                       :nav-to-find-community
                       #(println "Nav to find-community")
                       :nav-to-my-communities
                       #(println "Nav to my-communities"))]
      [menu-wrapper
       {:menu-props  menu-props
        :logged-user logged-user}
       [:button
        {:on-click (:open menu-props)}
        "Open"]])))
