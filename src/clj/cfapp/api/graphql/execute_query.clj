(ns cfapp.api.graphql.execute-query
  (:require
   [com.walmartlabs.lacinia :as lacinia]
   [integrant.core :as ig]))

(defmethod ig/init-key :cfapp.api.graphql/execute-query
  [_ {:keys [schema] :as deps}]
  (fn [gql-query]
    (let [{:keys [query variables operationName]} gql-query
          context (select-keys deps [:conn :workout-visitor])]
      (lacinia/execute schema
                       query
                       variables
                       context
                       {:operation-name operationName}))))
