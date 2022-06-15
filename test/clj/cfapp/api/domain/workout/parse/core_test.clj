(ns cfapp.api.domain.workout.parse.core-test
  (:require
   [cfapp.api.domain.workout.parse.core :as sut]
   [clojure.edn :as edn]
   [clojure.java.io :as io]
   [clojure.test :refer [deftest is]]
   [clojure.string :as s]))

(defn- parse-ok?
  [ctx-fn input expected]
  (let [{:keys [parsed errors]}
        (sut/parse input :ctx-fn ctx-fn)]
    (is (empty? (meta parsed)))
    (is (empty? errors))
    (is (= expected parsed))))

(deftest parse-workout-name
  (parse-ok?
    sut/parser-dot-workout-name
    "\"Super workout\""
    "Super workout"))

(deftest parse-duration-label-short
  (parse-ok?
    sut/parser-dot-duration
    "1'"
    60000))

(deftest parse-duration-label-long
  (parse-ok?
    sut/parser-dot-duration
    "1 minute"
    60000))

(deftest parse-round-count
  (parse-ok?
    sut/parser-dot-round-count
    "3 rounds"
    {:workout.loop/round-count 3}))

(deftest parse-time-cap
  (parse-ok?
    sut/parser-dot-time-cap
    "time cap 1'"
    {:workout.loop/time-cap 60000}))

(deftest parse-time-cap-abbreviated
  (parse-ok?
    sut/parser-dot-time-cap
    "TC 1'"
    {:workout.loop/time-cap 60000}))

(deftest parse-header-for-time
  (parse-ok?
    sut/parser-dot-header-for-time
    "for time"
    nil))

(deftest parse-header-for-time-n-rounds-long
  (parse-ok?
    sut/parser-dot-header-for-time
    "3 rounds, for time"
    {:workout.loop/round-count 3}))

(deftest parse-header-for-time-n-rounds-short
  (parse-ok?
    sut/parser-dot-header-for-time
    "3 rounds"
    {:workout.loop/round-count 3}))

(deftest parse-header-for-time-time-cap
  (parse-ok?
    sut/parser-dot-header-for-time
    "for time, time cap 10 minutes"
    {:workout.loop/time-cap 600000}))

(deftest parse-header-for-time-time-cap-short-paren
  (parse-ok?
    sut/parser-dot-header-for-time
    "for time, (TC 10')"
    {:workout.loop/time-cap 600000}))

(deftest parse-header-amrap
  (parse-ok?
    sut/parser-dot-header-amrap
    "AMRAP 5 minutes"
    {:workout.loop/duration 300000}))

(deftest parse-header-emom
  (parse-ok?
    sut/parser-dot-header-emom
    "EMOM 10'"
    {:workout.loop/duration 600000
     :workout.loop/period   60000}))

(deftest parse-header-emom-custom-period
  (parse-ok?
    sut/parser-dot-header-emom
    "every 2' for 10'"
    {:workout.loop/duration 600000
     :workout.loop/period   120000}))

(deftest parse-application-all-kilogram
  (parse-ok?
    sut/parser-dot-application
    "12Kg"
    {:exercise.application/value {:exercise.application.value/all 12.0}
     :exercise.application/unit  :unit/kilogram}))

(deftest parse-application-all-meter
  (parse-ok?
    sut/parser-dot-application
    "12m"
    {:exercise.application/value {:exercise.application.value/all 12.0}
     :exercise.application/unit  :unit/meter}))

(deftest parse-application-men-women-double-unit
  (parse-ok?
    sut/parser-dot-application
    "12kg/10kg"
    {:exercise.application/value
     {:exercise.application.value/men   12.0
      :exercise.application.value/women 10.0}
     :exercise.application/unit  :unit/kilogram}))

(deftest parse-application-men-women-simple-unit
  (parse-ok?
    sut/parser-dot-application
    "12/10kg"
    {:exercise.application/value
     {:exercise.application.value/men   12.0
      :exercise.application.value/women 10.0}
     :exercise.application/unit  :unit/kilogram}))

(deftest parse-application-at-sign
  (parse-ok?
    sut/parser-dot-application
    "@15kg"
    {:exercise.application/value {:exercise.application.value/all 15.0}
     :exercise.application/unit  :unit/kilogram}))

(deftest parse-application-parenthesis
  (parse-ok?
    sut/parser-dot-application
    "(15kg/10kg)"
    {:exercise.application/value
     {:exercise.application.value/men   15.0
      :exercise.application.value/women 10.0}
     :exercise.application/unit  :unit/kilogram}))

(deftest parse-exercise-with-rep-count
  (parse-ok?
    sut/parser-dot-exercise
    "5 kb swing @16Kg" ;; FIXME "5 kb swingeeeee" doesn't fail the test :(
    {:exercise/movement    {:movement/name "American Kettlebell Swing"}
     :exercise/application
     {:exercise.application/value {:exercise.application.value/all 16.0}
      :exercise.application/unit  :unit/kilogram}
     :exercise/rep-count   5}))

(deftest parse-exercise-with-at-sign-post-application
  (parse-ok?
    sut/parser-dot-exercise
    "kb swing @5kg"
    {:exercise/movement    {:movement/name "American Kettlebell Swing"}
     :exercise/application
     {:exercise.application/value {:exercise.application.value/all 5.0}
      :exercise.application/unit  :unit/kilogram}}))

(deftest parse-exercise-with-paren-post-application
  (parse-ok?
    sut/parser-dot-exercise
    "kb swing (15kg)"
    {:exercise/movement    {:movement/name "American Kettlebell Swing"}
     :exercise/application
     {:exercise.application/value {:exercise.application.value/all 15.0}
      :exercise.application/unit  :unit/kilogram}}))

(deftest parse-exercise-with-pre-application
  (parse-ok?
    sut/parser-dot-exercise
    "1' rest"
    {:exercise/movement    {:movement/name "Rest"}
     :exercise/application
     {:exercise.application/value {:exercise.application.value/all 60000.0}
      :exercise.application/unit  :unit/millisecond}}))

(deftest parse-exercise-with-rep-count-and-pre-application
  (parse-ok?
    sut/parser-dot-exercise
    "10 20kg KB swing"
    {:exercise/movement    {:movement/name "American Kettlebell Swing"}
     :exercise/rep-count   10
     :exercise/application
     {:exercise.application/value {:exercise.application.value/all 20.0}
      :exercise.application/unit  :unit/kilogram}}))

(deftest parse-exercise-with-application-but-no-unit
  (parse-ok?
    sut/parser-dot-exercise
    "KB swing (20/15))"
    {:exercise/movement    {:movement/name "American Kettlebell Swing"}
     :exercise/application
     {:exercise.application/value
      {:exercise.application.value/men   20.0
       :exercise.application.value/women 15.0}
      :exercise.application/unit  :unit/kilogram}}))

(deftest parse-exercise-with-bullet
  (parse-ok?
    sut/parser-dot-exercise
    "- 1' rest"
    {:exercise/movement    {:movement/name "Rest"}
     :exercise/application
     {:exercise.application/value
      {:exercise.application.value/all 60000.0}
      :exercise.application/unit  :unit/millisecond}}))

(deftest parse-flat-loop
  (parse-ok?
    sut/parser-dot-flat-loop
    "for time\n5 pull-up\n10 push-ups"
    {:workout.loop/tasks
     [{:exercise/movement  {:movement/name "Pull-up"}
       :exercise/rep-count 5}
      {:exercise/movement  {:movement/name "Push-up"}
       :exercise/rep-count 10}]}))

(deftest parse-loop-with-for-time-scheme
  (parse-ok?
    sut/parser-dot-flat-loop
    "1-2-3-4 for time\npull-up"
    {:workout.loop/tasks
     [{:exercise/movement  {:movement/name "Pull-up"}
       :exercise/rep-count 1}
      {:exercise/movement  {:movement/name "Pull-up"}
       :exercise/rep-count 2}
      {:exercise/movement  {:movement/name "Pull-up"}
       :exercise/rep-count 3}
      {:exercise/movement  {:movement/name "Pull-up"}
       :exercise/rep-count 4}]}))

(deftest parse-unknown-movement
  (let [{:keys [_workout errors] :as actual}
        (sut/parse "garbage" :ctx-fn sut/parser-dot-movement)
        {:keys [message] :as error} (first errors)]
    (is (empty? (meta actual)))
    (is (= {:line-nb  1
            :char-pos 0
            :offender "garbage"}
           (dissoc error :message)))
    (is (s/starts-with? message "mismatched input 'garbage' expecting"))))

(def ^:private named-workouts
  (edn/read-string (slurp (io/resource "workouts/named.edn"))))

(deftest parse-all-named-workouts
  (doseq [{:keys [str db]} (vals named-workouts)]
    (when str
      (is (= {:parsed db} (sut/parse str))))))

(def ^:private custom-workouts
  (edn/read-string (slurp (io/resource "workouts/custom.edn"))))

(deftest parse-all-custom-workouts
  (doseq [{:keys [str db]} (vals custom-workouts)]
    (when str
      (is (= {:parsed db} (sut/parse str))))))

;; (deftest parse-specific
;;   (let [{:keys [str db]} (get named-workouts :helen)]
;;     (is (= db (sut/parse str)))))
