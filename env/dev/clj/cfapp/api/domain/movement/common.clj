(ns cfapp.api.domain.movement.common
  (:require
   [clojure.java.io :as io]
   [clojure.string :as s]
   [clojure.edn :as edn]))

(defn- rule?
  [line]
  (re-matches #"^[A-Z].*" line))

(def ^:private re-extract-rule-name-and-args
  #"^([^\s:]+)[^/]+//[\s]+(.*)")

(defn- parse-line
  [line]
  (let [[_matched rule-name args] (re-find re-extract-rule-name-and-args line)]
    (when-not (= args "IGNORE")
      (let [[movement-name applications] (edn/read-string args)]
        (if (and line rule-name movement-name applications)
          [movement-name
           {:rule-name    rule-name
            :applications applications}]
          (throw (ex-info "Error while parsing Movement lexer"
                          {:line line})))))))

(def ^:private ignore? nil?)

(def ^:private xf-extract
  (comp
    (filter rule?)
    (map parse-line)
    (remove ignore?)))

(defn- check!
  [movements-info-list]
  (let [more-than-one (->> movements-info-list
                           (group-by first)
                           (map (fn [[name names-list]]
                                  [name (count names-list)]))
                           (filter (fn [[_name occurences]]
                                     (> occurences 1))))]
    (when (seq more-than-one)
      (throw (ex-info "Duplicate entries in Movement Lexer"
                      (into {} more-than-one))))))

(defn parse-lexer
  []
  (let [lines (s/split-lines (slurp (io/resource "grammars/MovementLexer.g4")))
        movements-info-list (sequence xf-extract lines)]
    (check! movements-info-list)
    (println (format "Found %s rules with names" (count movements-info-list)))
    (into {} movements-info-list)))
