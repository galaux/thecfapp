(ns cfapp.mobile.state.community
  (:require
   [cfapp.common.graphql :refer [gql->clj]]
   [pyramid.core :as p]))

(defn join
  [db community]
  (let [user-data (-> (p/pull db [{:logged-user [:id {:communities [:id]}]}])
                      (update-in [:logged-user :communities]
                                 ;; TODO distinct here? Or store a set in
                                 ;; Pyramid?
                                 #(cons (gql->clj community) %)))]
    (p/add db user-data)))

(defn leave
  [db {:keys [id] :as community}]
  (let [user-data (-> (p/pull db
                              [{:logged-user [:id {:communities [:id]}]}
                               {[:id id] [:id]}])
                      (update-in [:logged-user :communities]
                                 #(remove (fn [community]
                                            (= (:id community) id))
                                    %))
                      ;; Save fresh community so that state knows – at least –
                      ;; about updated member count
                      (assoc [:id id] (gql->clj community)))]
    (p/add db user-data)))
