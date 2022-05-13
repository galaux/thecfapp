(ns cfapp.mobile.ui.page.user-communities.sub
  (:require
   [cfapp.common.graphql :refer [gql->clj index-by]]
   [pyramid.core :as p]
   [re-frame.core :as rf]))

(def ^:private community-pull
  ;; No need for BE to compute expensive `:is-member?`, we'll set it ourself
  [:id
   :name
   :member-count
   {:address
      [:street
       :zip-code
       :city
       :country]}])

(rf/reg-sub
  ::user-communities
  (fn [db]
    (let [communities (-> (p/pull db
                                  [{:logged-user
                                      [{:communities community-pull}]}])
                          (get-in [:logged-user :communities]))]
      (->> communities
           (map (comp
                  #(assoc % :is-member? true)
                  gql->clj))
           (index-by :id)))))
