(ns user
  (:require
   [cfapp.api.core :as core]
   [cfapp.api.db.bootstrap :as bootstrap]
   [cfapp.api.db.core :as db]
   [cfapp.api.test-util :as tutil :refer [find-with-attr find-with-attr-and-value]]
   [clojure.tools.namespace.repl :refer [set-refresh-dirs]]
   [integrant.core :as ig]
   [integrant.repl :refer [halt go reset reset-all]]
   [integrant.repl.state :as state]))

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

(def dev-config
  (core/app-config :dev))

(defn reload-ig
  []
  (halt)
  (ig/load-namespaces dev-config)
  ((integrant.repl/set-prep! (constantly dev-config))))

;; (reload-ig)

