(ns cfapp.mobile.ui.component.community-card.story
  (:require
   [cfapp.mobile.ui.component.community-card.view :refer [community-card]]
   [cfapp.mobile.ui.storybook :as sb]
   [reagent.core :as r]))

(def ^:export default
  (sb/->default
    {:title     "Component/Community Card"
     :component community-card}))

(defn ^:export base
  [_args]
  (r/as-element
    [community-card
     {:community sb/crossfit-lutece}]))

(defn ^:export no-address
  [_args]
  (r/as-element
    [community-card
     {:community (dissoc sb/crossfit-lutece :address)}]))
