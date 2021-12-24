(ns cfapp.mobile.ui.page.find-community.sub
  (:require
   [cfapp.mobile.util :as util]
   [re-frame.core :as rf]))

(rf/reg-sub
  ::found-communities
  (fn [db]
    ;; TODO immediately convert to Clojure camel case
    (:found-communities db)))

(defn- ->ui-community
  [{:keys [isMember] :as community}]
  (-> community
      util/gql->clj
      (dissoc :isMember)
      (assoc :is-member? isMember)))

(rf/reg-sub
  ::found-communities*
  :<-
  [::found-communities]
  (fn [communities]
    (->> communities
         vals
         (map ->ui-community))))
