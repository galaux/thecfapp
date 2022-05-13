(ns cfapp.api.test-util
  (:require
   [cfapp.api.core :as core]
   [datomic.client.api :as d]
   [integrant.core :as ig]
   [integrant.repl.state :as ig-state]
   [ring.mock.request :refer [json-body request]])
  (:import
   java.util.UUID))

(def queries
  {;; Queries
   :FindCommunities
     "src/cljs/cfapp/mobile/controller/community/find-community.gql"
   :UserCommunities
     "src/cljs/cfapp/mobile/controller/community/user-communities.gql"
   ;; Mutations
   :JoinCommunity
     "src/cljs/cfapp/mobile/controller/community/join-community.gql"
   :LeaveCommunity
     "src/cljs/cfapp/mobile/controller/community/leave-community.gql"})

(defn test-query
  [query-or-key variables]
  (let [graphql-q (if (string? query-or-key)
                    query-or-key
                    (slurp (get queries query-or-key)))]
    (-> (request :post "/api/graphql")
        (json-body {:query     graphql-q
                    :variables variables}))))

(defn setup
  []
  (let [test-config  (-> (core/app-config :test)
                         (update-in [:cfapp.api.db/conn :db-name]
                                    #(str % "-test-" (UUID/randomUUID))))
        _ (ig/load-namespaces test-config)
        keys-to-init (-> test-config
                         keys
                         set
                         (disj :cfapp.api/http-server))]
    (ig/init test-config keys-to-init)))

(defn domain-add
  [conn tx-data]
  (d/transact conn {:tx-data tx-data}))

(defn find-with-attr
  ([attr]
   (find-with-attr (-> ig-state/system
                       :cfapp.api.db/conn
                       d/db)
                   attr))
  ([db attr]
   (d/q '[:find (pull ?e [*])
          :in $ ?attr
          :where [?e ?attr]]
        db
        attr)))

(defn find-with-attr-and-value
  ([attr value]
   (find-with-attr-and-value (-> ig-state/system
                                 :cfapp.api.db/conn
                                 d/db)
                             attr
                             value))
  ([db attr value]
   (d/q '[:find (pull ?e [*])
          :in $ ?attr ?value
          :where [?e ?attr ?value]]
        db
        attr
        value)))
