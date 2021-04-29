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
    [cfapp.api.db.bootstrap :as bootstrap]
    [cfapp.api.db.core :as db]
    [cfapp.api.domain.community :as community]
    [cfapp.api.domain.score :as score]
    [cfapp.api.domain.user :as user]
    [cfapp.api.domain.wod :as wod]
    [cfapp.api.domain.workout :as workout]
    [cfapp.api.test-util :as tutil]
    [clojure.java.io :as io]
    [cfapp.api.core :as core])
  (:import
    clojure.lang.IPersistentMap
    java.util.UUID))

(add-tap (bound-fn* clojure.pprint/pprint))

;; Work around to enable `cider-ns-refresh` to properly reload code in tests.
;; https://github.com/clojure-emacs/cider/issues/2686#issuecomment-532389499
(set-refresh-dirs "dev" "src" "test")

(defn bootstrap-db
  []
  (let [conn (-> integrant.repl.state/system :cfapp.api.db/conn)]
    (db/install-schema conn)
    (tutil/domain-add conn (bootstrap/bootstrap-tx)))
  nil);; Don't waste the REPL with output

(defn find-with-attr
  [attr]
  (let [db (-> integrant.repl.state/system :cfapp.api.db/conn d/db)]
    (d/q '[:find (pull ?e [*])
           :in $ ?attr
           :where [?e ?attr]]
         db
         attr)))

(defn find-with-attr-and-value
  [attr value]
  (let [db (-> integrant.repl.state/system :cfapp.api.db/conn d/db)]
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

