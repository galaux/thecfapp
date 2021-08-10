(ns cfapp.api.core
  (:gen-class)
  (:require
   [aero.core :as aero]
   [clojure.java.io :as io]
   [clojure.tools.logging :as log]
   [integrant.core :as ig]))

(def system nil)

;; Log uncaught exceptions in threads
(Thread/setDefaultUncaughtExceptionHandler
 (reify
   Thread$UncaughtExceptionHandler
   (uncaughtException [_ thread ex]
     (log/error {:what :uncaught-exception
                 :exception ex
                 :where (str "Uncaught exception on" (.getName thread))}))))

(defmethod aero/reader
  'ig/ref [_ _ value] (ig/ref value))

(defn app-config
  [profile]
  (aero/read-config (io/resource "system.edn")
                    {:profile profile}))

(defn stop-app
  []
  (log/info "stopping …")
  (ig/halt! system)
  (shutdown-agents))

(defn start-app
  [profile]
  (log/info (format "starting with profile %s …" profile))
  (.addShutdownHook (Runtime/getRuntime) (Thread. stop-app))
  (let [config (app-config profile)]
    (ig/load-namespaces config)
    (ig/init config)))

(defn -main
  [& args]
  (let [profile (condp = args
                  ["--dev"] :dev
                  nil       :prod
                  (throw (ex-info "Unknown command line option" {:option args})))]
    (alter-var-root #'system (constantly (start-app profile)))))
