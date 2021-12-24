(ns cfapp.mobile.ui.page.my-communities.core
  (:require
   [re-frame.core :as rf]
   [re-graph.core :as re-graph]
   [shadow.resource :as rc]
   [cfapp.mobile.ui.page.my-communities.view :as view]
   [cfapp.mobile.ui.page.my-communities.sub :as sub]
   [cfapp.mobile.ui.page.my-communities.event :as event]))

(def ^:private gql-user-communities
  (rc/inline "./graphql/user-communities.gql"))

(defn- dispatch-user-communities
  [logged-user-id]
  (rf/dispatch [::re-graph/query
                :user-communities
                gql-user-communities
                {:userId logged-user-id}
                [::event/on-user-communities]]))

;; TODO factorize with `find-community`
(def ^:private gql-leave-community
  (rc/inline "./graphql/leave-community.gql"))

(defn- leave-community
  [logged-user-id community-id]
  (rf/dispatch [::re-graph/mutate
                :leave-community
                gql-leave-community
                {:userId      logged-user-id
                 :communityId community-id}
                [::event/on-left-community]]))

(defn my-communities
  [{:keys [logged-user]}]
  (let [logged-user-id (:id logged-user)]
    ;; Watch-out: order is important:
    ;; - dispatch must be out of the `fn` or it immediately makes menu to close
    ;; - subscribe must be inside for the UI to react to changes
    (dispatch-user-communities logged-user-id)
    (fn [{:keys [logged-user menu-props]}]
      (let [communities @(rf/subscribe [::sub/user-communities])]
        (view/my-communities
          {:menu-props      menu-props
           :logged-user     logged-user
           :communities     (vals communities)
           :leave-community (partial leave-community logged-user-id)})))))
