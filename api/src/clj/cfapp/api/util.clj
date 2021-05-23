(ns cfapp.api.util
  (:require [camel-snake-kebab.core :as csk]
            [clojure.spec.alpha :as s]
            [clojure.walk :refer [postwalk]]
            [cognitect.anomalies :as anomalies]))

;; Anomaly
;; =======

(s/def ::anomaly-args
  (s/cat :category keyword?
    :message string?
    :supplemental (s/nilable map?)))

(defn anomaly
  ([category message supplemental]
   {:pre [(s/valid? ::anomaly-args [category message supplemental])]}
   (merge {::anomalies/category category
           ::anomalies/message message}
          supplemental))
  ([category message]
   (anomaly category message nil)))

(defn anomaly?
  [maybe-anomaly]
  (and (map? maybe-anomaly)
       (contains? maybe-anomaly ::anomalies/category)))

(defn anomaly->error
  [res]
  (assoc res :message (::anomalies/message res)))


;; Date and time
;; =============

;; Stolen from https://github.com/cloojure/tupelo/blob/master/src/clj/tupelo/java_time.clj

(def zoneid-utc (java.time.ZoneId/of "UTC"))

(defn LocalDate->Instant
  "Converts a LocalDate to a java.util.Date,
   using midnight (start of day) and the UTC timezone."
  [ld]
  (.toInstant (.atStartOfDay ld zoneid-utc)))

(defn LocalDate->Date
  "Converts a LocalDate to a java.util.Date,
   using midnight (start of day) and the UTC timezone."
  [ld]
  (java.util.Date/from (LocalDate->Instant ld)))


;; Type conversion
;; ===============

(defn ident->enum-value
  [datom]
  (-> datom
      :db/ident
      name
      csk/->SCREAMING_SNAKE_CASE_KEYWORD))


(defn kebab-keys
  [m]
  ;; Heavily inspired from `clojure.walk/stringify-keys`
  (let [f (fn [[k v]] [(csk/->kebab-case k) v])]
    ;; only apply to maps
    (postwalk (fn [x] (if (map? x) (into {} (map f x)) x)) m)))
