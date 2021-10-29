(ns cfapp.api.db.conn
  (:require
   [cfapp.api.db.bootstrap :refer [bootstrap-tx]]
   [cfapp.api.db.core :refer [db-empty? install-schema schema-missing?]]
   [clojure.tools.logging :as log]
   [datomic.client.api :as d]
   [integrant.core :as ig]))

(defmethod ig/init-key :cfapp.api.db/conn
  [id {:keys [bootstrap-if-empty? client db-name]}]
  (log/info "starting …")
  (d/create-database client {:db-name db-name})
  (let [conn (d/connect client {:db-name db-name})]
    ;; For now we only check schema is installed without handling migrations
    ;; TODO handle schema migrations
    (when (schema-missing? (d/db conn))
      (log/info "Schema not found: installing")
      (install-schema conn))
    (when (and bootstrap-if-empty?
               (db-empty? (d/db conn)))
      (log/info "DB empty: bootstrapping some fake data")
      (d/transact conn {:tx-data (bootstrap-tx)}))
    (log/info "done.")
    conn))

(defmethod ig/halt-key! :cfapp.api.db/conn
  [_ _]
  (log/info "stopping …"))
