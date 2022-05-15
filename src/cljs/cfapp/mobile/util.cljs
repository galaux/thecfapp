(ns cfapp.mobile.util
  (:require
   [camel-snake-kebab.core :as csk]
   [clojure.walk :refer [postwalk]]))

(def ^:private month
  ["Jan"
   "Feb"
   "Mar"
   "Apr"
   "May"
   "Jun"
   "Jul"
   "Aug"
   "Sep"
   "Oct"
   "Nov"
   "Dec"])

(defn month-and-day
  [date]
  [(month (.getUTCMonth date)) (str (.getUTCDate date))])

;; TODO remove (use cljc/common instead)
(defn gql->clj
  [m]
  ;; Heavily inspired by `clojure.walk/stringify-keys`
  (let [f (fn [[k v]] [(csk/->kebab-case-keyword k) v])]
    ;; only apply to maps
    (postwalk (fn [x] (if (map? x) (into {} (map f x)) x)) m)))
