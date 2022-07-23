(ns cfapp.mobile.controller.workout.util)

(def millis-in-one-minute 60000)

(def default-emom-period 60000)

(defn loop-type
  [{:keys [duration period score-type]}]
  (cond
    ;; TODO turn all these into keywords in state
    (and (some? duration) (some? period))
    :emom
    (and (some? duration) (= score-type "REP_COUNT"))
    :amrap
    (= score-type "DURATION")
    :for-time
    ;; A loop may have none of the above if it has an inner loop for instance
    :else nil))

(defn apply-ui-unit
  "Turn `value` from the unit it is stored in to the unit of the UI.
  Ex: durations are stored in milliseconds but displayed in minutes so a value
  of 60000 with an unit of `MILLISECOND` would be turned into `1'`."
  [value unit]
  (case unit
    "CALORIE"     (str value " cal")
    ;; TODO if <1' then return result in seconds rather than minute
    "MILLISECOND" (str (/ value millis-in-one-minute) "'")
    ;; Distance
    "FOOT"        (str value " ft")
    "METER"       (str value " m")
    "MILE"        (str value " mi")
    ;; Weight
    "BODYWEIGHT"  (str value "× bodyweight")
    "KILOGRAM"    (str value " Kg")
    "POOD"        (str value " pood")
    "POUND"       (str value " lbs")
    (throw (ex-info "Unknown unit" {:unit unit}))))

(defn- ->time-cap
  [time-cap]
  (when time-cap
    (str " (time cap " (/ time-cap millis-in-one-minute) "')")))

(defn loop-title
  [{:keys [duration period round-count score-type time-cap]}]
  (cond
    ;; TODO turn all these into keywords in state
    (and (some? duration) (some? period))
    (if (= period default-emom-period)
      (str "EMOM for " (apply-ui-unit duration "MILLISECOND"))
      (str "Every " (apply-ui-unit period "MILLISECOND")
           " for "  (apply-ui-unit duration "MILLISECOND")))
    ;;
    (and (some? duration) (= score-type "REP_COUNT"))
    (str "AMRAP " (apply-ui-unit duration "MILLISECOND"))
    ;;
    (= score-type "DURATION")
    (if (some? round-count)
      (str round-count
           " rounds for time"
           (when time-cap (str " (time cap " time-cap "')")))
      (str "for time" (->time-cap time-cap)))
    ;;
    (some? round-count)
    (str round-count " rounds")))
;; A loop may have none of the above if it has an inner loop for instance


(defn- reps-per-exercise
  [{:keys [rep-count]}]
  ;; A movement can be "Rest" which has no rep-count
  (or rep-count 0))

;; TODO use defmulti once we have namespaced entities in app state
(defn- reps-per-loop
  [{:keys [tasks]}]
  (->> tasks
       (map (fn [task]
              (if (:movement task)
                (reps-per-exercise task)
                (reps-per-loop task))))
       (apply +)))

(defn reps-per-round
  [{workout-loop :loop}]
  (reps-per-loop workout-loop))
