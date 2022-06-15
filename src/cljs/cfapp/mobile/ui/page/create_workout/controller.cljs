(ns cfapp.mobile.ui.page.create-workout.controller
  (:require
   [cfapp.mobile.ui.page.create-workout.event :as event]
   [cfapp.mobile.ui.page.create-workout.sub :as sub]
   [re-frame.core :as rf]
   [re-graph.core :as re-graph]
   [shadow.resource :as rc]
   [cfapp.mobile.ui.page.create-workout.view :as view]))

(def ^:private gql-check-workout
  "query ParseWorkout($workoutStr: String!) {
  parseWorkout(workoutStr: $workoutStr) {
    errors {
      lineNb
      charPos
      offender
      message
    }
    workout {
      ...WorkoutFragment
    }
  }
}

fragment WorkoutFragment on Workout {
  id
  name
  loop {
    #         repCount
    timeCap
    period
    duration
    scoreType
    tasks {
      __typename
      ... on Exercise {
        movement {
          id
          name
        }
        application {
          value {
            all
            men
            women
          }
          unit
        }
        repCount
      }
    }
  }
}")

(defn check-workout
  [workout-str]
  (rf/dispatch
    [::re-graph/query
     :check-workout
     gql-check-workout
     {:workoutStr workout-str}
     [::event/on-checked-workout]]))

(defn create-workout
  [{:keys [route-params logged-user menu-props]}]
  (let [parse-result @(rf/subscribe [::sub/parse-result])]
    [view/create-workout
     {:menu-props    menu-props
      :check-workout check-workout
      :parse-result  parse-result}]))
