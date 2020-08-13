(ns thecfapp.core
  (:require
   [aero.core :as aero]
   [clojure.java.io :as io]
   [integrant.core :as ig]))

(defmethod aero/reader
  'ig/ref [_ _ value] (ig/ref value))

(defn app-config
  [profile]
  (aero/read-config (io/resource "system.edn")
                    {:profile profile}))
