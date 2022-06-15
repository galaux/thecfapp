(ns cfapp.mobile.ui.page.create-workout.sub
  (:require
   [pyramid.core :as p]
   [re-frame.core :as rf]))

(def ^:private pp-exercise
  [{:movement
    [:id
     :name]}
   {:application
    [{:value [:all
              :men
              :women]}
     :unit]}
   :rep-count])

(def ^:private workout-pull
  [:id
   :name
   {:loop [:time-cap
           :duration
           :period
           :round-count
           :score-type
           {:tasks [;; Exercise
                    {:movement
                     [:id
                      :name]}
                    {:application
                     [{:value
                       [:all
                        :men
                        :women]}
                      :unit]}
                    :rep-count
                    ;; Workout loop
                    :time-cap
                    :duration
                    :period
                    :round-count
                    :score-type
                    {:tasks pp-exercise}]}]}])

(def ^:private errors-pull
  [:line-nb :char-pos :offender :message])

(def ^:private parse-result-pull
  [{:parse-result
    [{:workout workout-pull}
     :errors errors-pull]}])

(rf/reg-sub
  ::parse-result
  (fn [db]
    (:parse-result (p/pull db parse-result-pull))))
