(ns cfapp.mobile.ui.page.create-score.controller
  (:require
   [cfapp.mobile.controller.workout.util :as util]
   [cfapp.mobile.ui.page.create-score.sub :as sub]
   [cfapp.mobile.ui.page.create-score.view :as view]
   [re-frame.core :as rf]
   [cfapp.mobile.controller.score.core :as ctl]))

(defn- total-rep-count
  [{:keys [workout]} {:keys [rounds reps] :or {rounds 0 reps 0}}]
  (+ (* (util/reps-per-round workout) rounds) reps))

(defn- total-duration
  [{:keys [minutes seconds] :or {minutes 0 seconds 0}}]
  (* 1000 (+ (* minutes 60) seconds)))

(defn handle-create-score
  [logged-user-id wod score-type state]
  (let [value (case score-type
                "DURATION"  (total-duration state)
                "REP_COUNT" (total-rep-count wod state))]
    (ctl/create logged-user-id wod value)))

(defn create-score
  [{:keys [route-params logged-user menu-props]}]
  (let [{:keys [wod-id]} route-params
        wod        @(rf/subscribe [::sub/wod wod-id])
        score-type (get-in wod [:workout :loop :score-type])]
    [view/create-score
     {:logged-user  logged-user
      :menu-props   menu-props
      :create-score (partial handle-create-score
                             (:id logged-user)
                             wod
                             score-type)}
     wod]))
