(ns cfapp.mobile.ui.molecule.community-card.story
  (:require
   [cfapp.mobile.ui.molecule.community-card.view :refer [community-card]]
   [cfapp.mobile.ui.storybook :refer [->default]]
   [reagent.core :as r]))

(def ^:export default
  (->default
    {:title     "Molecules/Community Card"
     :component community-card}))

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
  [_args]
  (r/as-element
    [community-card community]))

(defn ^:export no-address
  [_args]
  (r/as-element
    [community-card (dissoc community :address)]))
