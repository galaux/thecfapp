(ns cfapp.api.domain.movement.grammar
  (:require
   [cfapp.api.domain.movement.common :as common]
   [clojure.java.io :as io]
   [clojure.string :as s]))

(def ^:private tag "##BODY##")

(def ^:private parser-template
  (slurp (io/resource "workout-parser/MovementParser.g4.tpl")))

(def ^:private parse-movement-clj-template
  (slurp (io/resource "workout-parser/parse-movement.clj.tpl")))

(defn- ->parser-rules-body
  [movements-info]
  (->> movements-info
       (map (fn [[_movement-name {:keys [rule-name]}]]
              rule-name))
       sort
       (s/join "\n  | ")))

(defn- ->parse-case-body
  [movements-info]
  (->> movements-info
       (map (fn [[movement-name {:keys [rule-name]}]]
              (format "    (.%s ctx) \"%s\"" rule-name movement-name)))
       sort
       (s/join "\n")))

(defn- ->movements-config
  [movements-info]
  (->> movements-info
       (map (fn [[k vals]]
              [k (dissoc vals :rule-name)]))
       (into {})))

(defn generate
  []
  (let [movements-info (common/parse-lexer)]
    (println "Gen Movement parser")
    (spit "./env/dev/resources/grammars/MovementParser.g4"
          (s/replace-first parser-template
                           tag
                           (->parser-rules-body movements-info)))
    (println "Gen parse-cases Clojure source")
    (spit "./src/clj/cfapp/api/domain/workout/parse/movement.clj"
          (s/replace-first parse-movement-clj-template
                           tag
                           (->parse-case-body movements-info)))
    (println "Gen Movement list as EDN")
    (spit "./resources/movements.edn"
          (pr-str (->movements-config movements-info)))))
