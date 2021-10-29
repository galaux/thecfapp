(ns cfapp.api.graphql.execute-query
  (:require
   [com.walmartlabs.lacinia :as lacinia]
   [integrant.core :as ig]))

(defmethod ig/init-key :cfapp.api.graphql/execute-query
  [_ {:keys [conn schema]}]
  (fn [gql-query]
    (let [{:keys [query variables operationName]} gql-query
          context                                 {:conn conn}]
      (lacinia/execute schema
                       query
                       variables
                       context
                       {:operation-name operationName}))))
