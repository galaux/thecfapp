(ns cfapp.api.domain.movement.element
  (:require
   [cfapp.api.domain.movement.util
    :refer
    [combine derive-from elem with-extra-ancestor with-extra-rule]]))

;; All symbols in this file except elements and `all` must be private otherwise
;; they will be picked by `ns-publics`.

(def air (elem "A I R")) ;; TODO isn't this too "wide"?
(def air-bike (elem "A I R SEP B I K E"))
(def american (elem "A M E R I C A N"))
(def assault-bike (elem "A S S A U L T SEP B I K E"))
(def back (elem "B A C K")) ;; TODO Do I want this one? Ins't it too wide too?
(def balance (elem "B A L A N C E"))
(def ball-slam (elem "B A L L SEP S L A M"))
(def banded (elem "B A N D E D"))
(def bar-facing (elem "B A R SEP F A C I N G"))
(def bar (elem "B A R"))
(def barbell (elem "B A R (B E L L)? | B B"))
(def battling-ropes (elem "B A T T L I N G SEP R O P E S"))
(def bench (elem "B E N C H"))
(def box (elem "B O X")) ;; TODO too wide?
(def box-jump (elem "B O X SEP J U M P")) ;; TODO factorize with "box"?
(def burpee (elem "B U R P E E"))
(def chest-to (elem "C H E S T SEP T O"))
(def chin-up (elem "C H I N SEP U P"))
(def clean (elem "C L E A N"))
(def cluster (elem "C L U S T E R"));; TODO make it a combine of clean + thruster?
(def crunch (elem "C R U N C H"))
(def deadlift (elem "D E A D L I F T | D L"))
(def deficit (elem "D E F I C I T"))
(def devil-press (elem "D E V I L SEP P R E S S"))
(def dip (elem "D I P"))
(def double-hand (elem "D O U B L E"))
;; See comment on gymnastic/carries
(def double-kettlebell-walk
  (elem "DOUBLE_KETTLEBELL SEP WALK | F A R M E R SEP W A L K"
        :id
        "DOUBLE_KETTLEBELL_WALK"))
(def double-under (elem "D O U B L E SEP U N D E R | D U"))
(def drag (elem "D R A G"))
(def dumbbell (elem "D U M B B E L L | D B"))
(def front (elem "F R O N T"))
(def ghd-sit-up (elem "G H D SEP S I T SEP? U P"))
(def gobelet (elem "G O B E L E T"))
(def hand-release (elem "H A N D SEP R E L E A S E"))
(def handstand (elem "H A N D S T A N D"))
(def hspu (elem "H A N D S T A N D SEP P U S H SEP U P | H S P U")) ;; TODO factorize all handstand
(def hang (elem "H A N G"))
(def high-pull (elem "H I G H SEP P U L L"))
(def hollow-rock (elem "H O L L O W SEP R O C K"))
(def jerk (elem "J E R K"))
(def jump (elem "J U M P")) ;; TODO factorize with "jumping"?
(def jumping (elem "J U M P I N G"))
(def jumping-jack (elem "J U M P I N G SEP J A C K"))
(def kettlebell (elem "K E T T L E B E L L | K B"))
(def kipping (elem "K I P P I N G"))
(def knees (elem "K N E E S"))
(def knees-to-elbows (elem "K N E E S SEP T O SEP E L B O W"))
(def leg-raise (elem "L E G SEP R A I S E"))
(def lunge (elem "L U N G E"))
(def man-maker (elem "M A N SEP M A K E R"))
(def mountain-climber (elem "M O U N T A I N SEP C L I M B E R"))
(def muscle (elem "M U S C L E"))
(def muscle-up (elem "M U S C L E SEP U P")) ;; TODO factorize with "muscle"?
(def over (elem "O V E R"))
(def over-the-bar (elem "O V E R SEP T H E SEP B A R"))
(def overhead (elem "O V E R H E A D | O H"))
(def parallette (elem "P A R A L L E T T E"))
(def peg-board (elem "P E G SEP B O A R D"))
(def pistol (elem "P I S T O L"))
(def plank (elem "P L A N K"))
(def power (elem "P O W E R"))
(def press (elem "P R E S S"))
(def pull (elem "P U L L"))
(def pull-as-adjective (elem "P U L L")) ;; TODO
(def pull-up (elem "P U L L SEP U P S?"))
(def push (elem "P U S H"))
(def push-up (elem "P U S H SEP U P S?"))
(def resting (elem "R E S T"))
(def ring (elem "R I N G"))
(def rope-climb (elem "R O P E SEP C L I M B"))
(def row (elem "R O W"))
(def run (elem "R U N"))
(def russian (elem "R U S S I A N"))
(def sandbag-carry
  (elem "S A N D SEP? B A G SEP C A R R Y" :id "SANDBAG_CARRY"))
(def shoulder-to-overhead
  (elem "S H O U L D E R SEP T O SEP O V E R H E A D | S T O H"))
(def shuttle-sprint (elem "S H U T T L E SEP S P R I N T"))
(def single (elem "S I N G L E ( SEP A R M )?"))
(def single-under (elem "S I N G L E SEP U N D E R"))
(def sit-up (elem "S I T SEP? U P"))
(def skierg (elem "S K I SEP? E R G" :id "SKIERG"))
(def sled (elem "S L E D"))
(def snatch (elem "S N A T C H"))
(def split (elem "S P L I T"))
(def squat (elem "S Q U A T"))
(def squat-as-adjective (elem "S Q U A T")) ;; TODO what do we need this one for again?
(def step-over (elem "S T E P SEP O V E R")) ;; TODO factorize with "over"
(def step-up (elem "S T E P SEP U P"))
(def stiff-legged (elem "S T I F F SEP L E G G E D"))
(def strict (elem "S T R I C T"))
(def sumo (elem "S U M O"))
(def swim (elem "S W I M"))
(def swing (elem "S W I N G S?"))
(def thruster (elem "T H R U S T E R"))
(def toes-to (elem "T O E S SEP T O"))
(def triple-under (elem "T R I P L E SEP U N D E R"))
(def turkish-get-up (elem "T U R K I S H SEP G E T SEP? U P"))
(def v-up (elem "V SEP? U P"))
(def walk (elem "W A L K"))
(def walking (elem "W A L K I N G")) ;; TODO factorize with "walk"?
(def wall-ball (elem "( W A L L SEP? B A L L | W B ) ( SEP S H O T S? )?"))
(def wall-climb (elem "W A L L SEP C L I M B"))
(def yoke-carry (elem "Y O K E SEP C A R R Y"))

(def clean-and-jerk
  (-> (combine
        clean
        jerk
        :id "CLEAN_AND_JERK"
        :rule-format "%s SEP ( N | A N D | AMPERSAND ) SEP %S")
      (with-extra-rule "C ( N | AMPERSAND ) J")
      (with-extra-ancestor "AMPERSAND" "'&'")))

;; TODO instead of this, shouldn't we use `combine`'s `:rule-format`?
(def clean-and
  (derive-from clean (elem "C L E A N SEP A N D")))

(def toes-to-bar
  (-> (combine toes-to bar)
      (with-extra-rule "T T B")
      (with-extra-rule "T DIGIT_2 B")
      (with-extra-ancestor "DIGIT_2" "'2'")))

;; Must be private so that it's not picked by `ns-publics`
(defn- var->elem-entry
  [var-elem]
  (let [{:keys [id rule]} (var-get var-elem)]
    [id rule]))

(def all
  (->> (ns-publics *ns*)
       (remove (fn [[sym _var-elem]] (= sym 'all)))
       (map (fn [[_sym var-elem]] (var->elem-entry var-elem)))
       (into {})))
