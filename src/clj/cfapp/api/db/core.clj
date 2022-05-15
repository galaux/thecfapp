(ns cfapp.api.db.core
  (:require
   [clojure.edn :as edn]
   [clojure.java.io :as io]
   [clojure.pprint]
   [datomic.client.api :as d]))

(defn ident-exists?
  [db ident]
  (not (empty? (d/pull db '[:db/ident] ident))))

(defn remove-db-schema-attrs
  [schema]
  (map (partial into {} (remove (fn [[k _]] (= "db.schema" (namespace k)))))
    schema))

(defn get-schema
  []
  (-> "db/schema.edn"
      io/resource
      slurp
      edn/read-string))

(defn install-schema
  "This function expected to be called at system start up.

   Datomic schema migrations or db preinstalled data can be put into 'migrations/schema.edn'
   Every txes will be executed exactly once no matter how many times system restart."
  [conn]
  (let [tx-data (remove-db-schema-attrs (get-schema))]
    (d/transact conn {:tx-data tx-data})))

(defn schema-missing?
  [db]
  (-> (d/q '[:find ?e :where [?e :db/ident :user/id]] db)
      first
      empty?))

(defn show-schema
  "Show currently installed schema"
  [db]
  (let [system-ns #{"db" "db.type" "db.install" "db.part"
                    "db.lang" "fressian" "db.unique" "db.excise"
                    "db.cardinality" "db.fn" "db.sys" "db.bootstrap"
                    "db.alter" "db.attr" "db.entity"}]
    (d/q '[:find ?ident
           :in $ ?system-ns
           :where
           [?e :db/ident ?ident]

           [(namespace ?ident) ?ns]
           [((comp not contains?) ?system-ns ?ns)]]
         db
         system-ns)))

(defn db-empty?
  [db]
  (-> (d/q '[:find ?user :where [?user :user/id]] db)
      empty?))

;; (defn show-transaction
;;   "Show all the transaction data
;;    e.g.
;;     (-> conn show-transaction count)
;;     => the number of transaction"
;;   [conn]
;;   (seq (d/tx-range (d/log conn) nil nil)))

;; (defn find-one-by
;;   "Given db value and an (attr/val), return the user as EntityMap
;;   (datomic.query.EntityMap)
;;    If there is no result, return nil.

;;    e.g.
;;     (d/touch (find-one-by (d/db conn) :user/email \"user@example.com\"))
;;     => show all fields
;;     (:user/first-name (find-one-by (d/db conn) :user/email
;;     \"user@example.com\"))
;;     => show first-name field"
;;   [db attr val]
;;   (d/entity db
;;             ;;find Specifications using ':find ?a .' will return single
;;             scalar
;;             (d/q '[:find ?e .
;;                    :in $ ?attr ?val
;;                    :where [?e ?attr ?val]]
;;                  db
;;                  attr
;;                  val)))

;; (defn find-user
;;   [db id]
;;   (d/touch (find-one-by db :user/id id)))


;; (defn populate
;;   [k]
;;   (let [generator (case k
;;                     :community community-gen
;;                     :user user-gen
;;                     :workout workout-gen
;;                     "error")] ;; TODO
;;     (d/transact conn {:tx-data (gen/sample generator)})))
