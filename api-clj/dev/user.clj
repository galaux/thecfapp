(ns user
  (:require
    [clojure.java.browse :refer [browse-url]]
    [clojure.spec.alpha :as s]
    [clojure.spec.gen.alpha :as gen]
    [clojure.tools.namespace.repl :refer [set-refresh-dirs]]
    [clojure.walk :as walk]
    [com.walmartlabs.lacinia :as lacinia]
    [datomic.client.api :as d]
    [integrant.core :as ig]
    [integrant.repl :refer [clear go halt init prep reset reset-all]]
    [integrant.repl.state :as state]
    [thecfapp.db.bootstrap :as bootstrap]
    [thecfapp.db.core :as db]
    [thecfapp.domain.community :as community]
    [thecfapp.domain.score :as score]
    [thecfapp.domain.user :as user]
    [thecfapp.domain.wod :as wod]
    [thecfapp.domain.workout :as workout]
    [thecfapp.test-util :as tutil]
    [clojure.java.io :as io]
    [thecfapp.core :as core])
  (:import
    clojure.lang.IPersistentMap
    java.util.UUID))

;; Work around to enable `cider-ns-refresh` to properly reload code in tests.
;; https://github.com/clojure-emacs/cider/issues/2686#issuecomment-532389499
(set-refresh-dirs "dev" "src" "test")

;; (defn simplify
;;   "Converts all ordered maps nested within the map into standard hash maps, and
;;    sequences into vectors, which makes for easier constants in the tests, and
;;    eliminates ordering problems."
;;   [m]
;;   (walk/postwalk (fn [node]
;;                    (cond (instance? IPersistentMap node) (into {} node)
;;                      (seq? node) (vec node)
;;                      :else node))
;;                  m))

;; (defn q
;;   [query-string]
;;   (-> (:thecfapp/schema state/system)
;;       (lacinia/execute query-string nil nil)
;;       simplify))

(defn bootstrap-db
  []
  (let [conn (-> integrant.repl.state/system :thecfapp.db/conn)]
    (db/install-schema conn)
    (tutil/domain-add conn (bootstrap/bootstrap-tx)))
  nil);; Don't waste the REPL with output

(defn find-with-attr
  [attr]
  (let [db (-> integrant.repl.state/system :thecfapp.db/conn d/db)]
    (d/q '[:find (pull ?e [*])
           :in $ ?attr
           :where [?e ?attr]]
         db
         attr)))

(defn find-with-attr-and-value
  [attr value]
  (let [db (-> integrant.repl.state/system :thecfapp.db/conn d/db)]
    (d/q '[:find (pull ?e [*])
           :in $ ?attr ?value
           :where [?e ?attr ?value]]
         db
         attr
         value)))

(def dev-config
  (core/app-config :dev))

(defn reload-ig
  []
  (halt)
  (ig/load-namespaces dev-config)
  ((integrant.repl/set-prep! (constantly dev-config))))

;; (reload-ig)

