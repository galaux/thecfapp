(ns cfapp.api.util
  (:require
   [camel-snake-kebab.core :as csk]
   [clojure.spec.alpha :as s]
   [clojure.walk :refer [postwalk]]
   [cognitect.anomalies :as anomalies]
   [clojure.string :as str])
  (:import
   (java.util Base64)))

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

(defn base64-encode
  [s]
  (.encodeToString (Base64/getEncoder) (.getBytes s)))

(defn base64-decode
  [encoded]
  (String. (.decode (Base64/getDecoder) encoded)))

(defn encode-basic-auth
  [id password]
  (str "Basic " (base64-encode (str id ":" password))))

(defn basic-auth
  [req]
  (some-> (get-in req [:headers "authorization"])
          (str/replace-first "Basic " "")
          base64-decode
          (str/split #":")))

;; Validation
;; ==========

(def email-regex
  #"(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])")

(defn valid-email?
  [email]
  (boolean (and (seq email)
              (re-matches email-regex email))))

(def some-entity? (comp some? :db/id))
