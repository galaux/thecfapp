(ns cfapp.api.domain.workout.workout-test
  (:require
   [clojure.edn :as edn]
   [cfapp.api.db.domain.workout :as sut]
   [clojure.java.io :as io]
   [clojure.test :refer [deftest is]]
   [clojure.spec.alpha :as s]))

(def ^:private named-workouts
  (edn/read-string (slurp (io/resource "workouts/named.edn"))))

(def ^:private custom-workouts
  (edn/read-string (slurp (io/resource "workouts/custom.edn"))))

(deftest correct-spec-vs-named
  ;(is (s/valid? ::sut/workout (get-in named-workouts [:angie :db]))))
  (doseq [{:keys [db]} (vals named-workouts)]
    (is (empty? (s/explain-data ::sut/workout db)))))

(deftest correct-spec-vs-custom
  (doseq [{:keys [db]} (vals custom-workouts)]
    (is (empty? (s/explain-data ::sut/workout db)))))
