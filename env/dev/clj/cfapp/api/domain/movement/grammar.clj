(ns cfapp.api.domain.movement.grammar
  (:require
   [cfapp.api.domain.workout.parse.core :refer [movements-config]]
   [clojure.java.io :as io]
   [clojure.string :as s]
   [camel-snake-kebab.core :as csk]))

(def ^:private tag "##BODY##")

(def ^:private parser-template
  (slurp (io/resource "workout-parser/MovementParser.g4.tpl")))

(def ^:private parse-movement-clj-template
  (slurp (io/resource "workout-parser/parse-movement.clj.tpl")))

(defn- ->parser-rules-body
  [movements-info]
  (->> movements-info
       (map (fn [[id _values]]
              (csk/->SCREAMING_SNAKE_CASE_STRING id)))
       sort
       (s/join "\n  | ")))

(defn- ->parse-case-body
  [movements-info]
  (->> movements-info
       (map (fn [[id _values]]
              (format "    (.%s ctx) %s"
                      (csk/->SCREAMING_SNAKE_CASE_STRING id)
                      id)))
       sort
       (s/join "\n")))

(defn generate
  []
  (let [] ;;movements-info (common/parse-lexer)]
    (println "Gen Movement parser")
    (spit "./env/dev/resources/grammars/MovementParser.g4"
          (s/replace-first parser-template
                           tag
                           (->parser-rules-body movements-config)))
    (println "Gen parse-cases Clojure source")
    (spit "./src/clj/cfapp/api/domain/workout/parse/movement.clj"
          (s/replace-first parse-movement-clj-template
                           tag
                           (->parse-case-body movements-config)))))
