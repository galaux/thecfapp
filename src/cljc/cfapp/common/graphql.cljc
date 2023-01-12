(ns cfapp.common.graphql
  (:require
   [camel-snake-kebab.core :as csk]
   [clojure.walk :refer [postwalk]]))

(defn clj->gql
  [m]
  ;; Heavily inspired by `clojure.walk/stringify-keys`
  (let [f (fn [[k v]] [(csk/->camelCaseString k) v])]
    ;; only apply to maps
    (postwalk (fn [x] (if (map? x) (into {} (map f x)) x)) m)))

(defn gql->clj
  [m]
  ;; Heavily inspired by `clojure.walk/stringify-keys`
  (let [f (fn [[k v]] [(csk/->kebab-case-keyword k) v])]
    ;; only apply to maps
    (postwalk (fn [x] (if (map? x) (into {} (map f x)) x)) m)))

(defn index-by
  ;; TODO replace by some util lib's index-by (potpuri?)
  [gp-key col]
  (->> col
       (group-by gp-key)
       (map (fn [[k v]] [k (first v)]))
       (into {})))
