(ns thecfapp.util
  (:require
    [camel-snake-kebab.core :as csk]
    [clojure.spec.alpha :as s]
    [clojure.walk :refer [postwalk]]
    [cognitect.anomalies :as anomalies]))

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


(defn- uppercase-if-enum
  [{:keys [ident] :as all}]
  (if (keyword? ident)
    (csk/->SCREAMING_SNAKE_CASE_KEYWORD (name ident))
    all))

(defn- camel-case-keys-shallow
  [m]
  (->> m
       (map (fn [[k v]]
              [(csk/->camelCaseKeyword (name k)) (uppercase-if-enum v)]))
       (into {})))

(defn camel-case-keys
  [m]
  (postwalk
    (fn [e]
      (if (map? e)
        (camel-case-keys-shallow e)
        e))
    m))
