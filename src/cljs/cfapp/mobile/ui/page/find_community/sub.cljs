(ns cfapp.mobile.ui.page.find-community.sub
  (:require
   [cfapp.common.graphql :refer [gql->clj]]
   [pyramid.core :as p]
   [re-frame.core :as rf]))

;; TODO factorize with my-communities/sub
(def ^:private community-pull
  [:id
   :name
   :member-count
   {:address
      [:street
       :zip-code
       :city
       :country]}])

(rf/reg-sub
  ::found-communities
  (fn [db]
    ;; TODO move somewhere else (that looks more like controller to me than
    ;; anything else)
    (let [db-data (p/pull db
                          [{:found-communities community-pull}
                           {:logged-user [{:communities [:id]}]}])
          found-communities (:found-communities db-data)
          user-communities-ids (->> (get-in db-data [:logged-user :communities])
                                    (map :id)
                                    set)
          ee (map (fn [{:keys [id] :as community}]
                    (assoc community
                      :is-member? (contains? user-communities-ids id)))
               found-communities)]
      ;; TODO immediately convert to Clojure camel case
      (map gql->clj ee))))
