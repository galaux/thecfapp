(ns thecfapp.db.conn
  (:require
   [datomic.client.api :as d]
   [integrant.core :as ig]))

(defmethod ig/init-key :thecfapp.db/conn
  [id {:keys [client db-name]}]
  (d/create-database client {:db-name db-name}) ;; FIXME Useful?
  (d/connect client {:db-name db-name}))
