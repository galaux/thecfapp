(ns cfapp.mobile.ui.component.text.story
  (:require
   [cfapp.mobile.ui.component.text.view :refer [text]]
   [reagent.core :as r]))

(def ^:export default
  #js
   {:title     "Component/Text"
    :component (r/reactify-component text)})

(defn ^:export Default
  []
  (r/as-element
    [text
     {}
     "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."]))

(defn ^:export Regular
  []
  (r/as-element
    [text
     {:font-weight :regular}
     "Lorem ipsum dolor sit amet."]))

(defn ^:export Medium
  []
  (r/as-element
    [text
     {:font-weight :medium}
     "Lorem ipsum dolor sit amet."]))

(defn ^:export SemiBold
  []
  (r/as-element
    [text
     {:font-weight :semi-bold}
     "Lorem ipsum dolor sit amet."]))

(defn ^:export Bold
  []
  (r/as-element
    [text
     {:font-weight :bold}
     "Lorem ipsum dolor sit amet."]))
