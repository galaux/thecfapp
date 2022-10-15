(ns cfapp.api.domain.movement.core
  (:require
   [cfapp.api.domain.movement.clean :as clean]
   [cfapp.api.domain.movement.clean-and-jerk :as clean-and-jerk]
   [cfapp.api.domain.movement.deadlift :as deadlift]
   [cfapp.api.domain.movement.gymnastic :as gymnastic]
   [cfapp.api.domain.movement.kettlebell-swing :as kettlebell-swing]
   [cfapp.api.domain.movement.lunge :as lunge]
   [cfapp.api.domain.movement.shoulder-to-overhead
    :as shoulder-to-overhead]
   [cfapp.api.domain.movement.snatch :as snatch]
   [cfapp.api.domain.movement.squat :as squat]
   [cfapp.api.domain.movement.thruster :as thruster]
   [cfapp.api.domain.movement.util :refer [plus]]
   [clojure.data :as data]
   [clojure.java.io :as io]
   [clojure.string :as s]
   [camel-snake-kebab.core :as csk]))

(defn ->all-movements
  []
  (plus
    ;; Gymnastic
    gymnastic/all
    ;; Powerlifting
    deadlift/all
    kettlebell-swing/all
    lunge/all
    shoulder-to-overhead/all
    squat/all
    thruster/all
    ;; Weightlifting
    clean/all
    clean-and-jerk/all
    snatch/all))

(defn ->grammar-base
  [movement-list]
  (let [movements (into {} (map (juxt :id :rule) movement-list))
        ancestors (reduce
                    (fn [acc {:keys [ancestors]}] (merge acc ancestors))
                    {}
                    movement-list)
        [rules fragments in-both] (data/diff movements ancestors)]
    {:fragments fragments
     :rules     (merge rules in-both)}))

(defn- ->movement-id
  [id]
  (keyword "movement.id" (csk/->kebab-case-string id)))

(defn- ->fragment-line
  [[id rule]]
  (format "fragment %s : %s ;" id rule))

(defn- ->lexer-rule-line
  [[id rule]]
  (format "%s : %s ;" id rule))

(def ^:private template
  "/* This file is generated, don't edit! */

grammar Movement ;

/*
 * Lexer rules
 */

%s

%s

SEP : ( '-' | ' ' )+ ;


/*
 * Parser rules
 */

movement
  : %s;")

(defn ->parser-rule-line
  [[id _rule]]
  id)

(def ^:private parse-movement-clj-template
  ";; This file is generated, don't edit!
(ns cfapp.api.domain.parse.movement)

(defn- parse-movement*
  [ctx]
  (cond
%s))

(def parse-movement (memoize parse-movement*))
")

(defn ->parse-movement-line
  [[id _rule]]
  (format "    (.%s ctx) %s"
          (csk/->SCREAMING_SNAKE_CASE_STRING id)
          (->movement-id id)))

(defn gen-movement-grammar!
  [{:keys [fragments rules]}]
  (let [fragments-str    (s/join "\n" (sort (map ->fragment-line fragments)))
        lexer-rules-str  (s/join "\n" (sort (map ->lexer-rule-line rules)))
        parser-rules-str (s/join "\n  | "
                                 (sort (map ->parser-rule-line rules)))]
    (spit (io/resource "grammars/Movement.g4")
          (format template fragments-str lexer-rules-str parser-rules-str))))

(defn gen-movement-parsing-fn!
  [{:keys [rules]}]
  (let [parse-movement-lines
        (s/join "\n" (sort (map ->parse-movement-line rules)))]
    (spit "./src/clj/cfapp/api/domain/parse/movement.clj"
          (format parse-movement-clj-template parse-movement-lines))))

(defn ->db-movement
  [[id _rule]]
  (let [movement-id    (->movement-id id)
        movement-words (-> id
                           (s/replace "_" " ")
                           (s/split #" "))
        movement-name  (s/join " " (map s/capitalize movement-words))]
    ;; TODO default application
    ;; TODO valid units
    {:movement/id   movement-id
     :movement/name movement-name}))

(defn gen-datoms!
  [{:keys [rules]}]
  (spit (io/resource "movements.edn")
        ;; Zprint doesn't properly indent the compact notation of ns'd maps
        ;; thus the local `binding`.
        ;; TODO properly configure Zprint to indend ns'd maps
        (binding [*print-namespace-maps* false]
          (pr-str (sort-by :movement/id (map ->db-movement rules))))))

(defn gen!
  []
  (let [grammar-base (->grammar-base (->all-movements))]
    (gen-movement-grammar! grammar-base)
    (gen-movement-parsing-fn! grammar-base)
    (gen-datoms! grammar-base)))
