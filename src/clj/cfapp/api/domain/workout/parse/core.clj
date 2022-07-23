(ns cfapp.api.domain.workout.parse.core
  (:import
   WorkoutLexer
   WorkoutParser
   WorkoutBaseVisitor
   WorkoutParser$WorkoutContext
   (org.antlr.v4.runtime BaseErrorListener CharStreams CommonTokenStream))
  (:require
   [clojure.string :as s]
   [cfapp.api.domain.workout.parse.movement :refer [parse-movement]]
   [clojure.edn :as edn]
   [clojure.java.io :as io]
   [clojure.pprint :refer [pprint]]))

(def ^:private one-second-in-ms
  1000)

(def ^:private one-minute-in-ms
  60000)

(defn- trim-double-quotes
  [s]
  (apply str (remove #(= \" %) s)))

(defn- visit-workout
  [this ctx]
  (merge
    (when-let [workout-name-ctx (.workoutName ctx)]
      {:workout/name (.visitWorkoutName this workout-name-ctx)})
    (when-let [flat-loop-ctx (.flatLoop ctx)]
      {:workout/loop (.visitFlatLoop this flat-loop-ctx)})
    (when-let [parent-loop-ctx (.parentLoop ctx)]
      {:workout/loop (.visitParentLoop this parent-loop-ctx)})))

(defn- visit-workout-name
  [_this ctx]
  (trim-double-quotes (.getText ctx)))

(defn- visit-flat-loop
  [this ctx]
  (merge
    (.visitHeader this (.header ctx))
    {:workout.loop/tasks (map #(.visitExercise this %) (.exercise ctx))}))

(defn- visit-parent-loop-next-task
  [this ctx]
  (merge
    (when-let [exercise-ctx (.exercise ctx)]
      (.visitExercise this exercise-ctx))
    (when-let [flat-loop-ctx (.flatLoop ctx)]
      (.visitFlatLoop this flat-loop-ctx))))

(defn- visit-parent-loop
  [this ctx]
  (let [first-task (if (.exercise ctx)
                     (.visitExercise this (.exercise ctx))
                     (.visitFlatLoop this (.flatLoop ctx)))]
    (merge
      (when-let [header-ctx (.header ctx)]
        (.visitHeader this header-ctx))
      {:workout.loop/tasks
       (cons
         first-task
         (when-let [parent-loop-next-task-ctx (.parentLoopNextTask ctx)]
           (map #(.visitParentLoopNextTask this %)
             parent-loop-next-task-ctx)))})))

(defn- visit-round-count
  [_this ctx]
  ;; FIXME We'll fail if user passes "9.0"
  {:workout.loop/round-count (Long/parseLong (.getText (.NUMBER ctx)))})

(defn- visit-header
  [this ctx]
  (cond
    (.headerAMRAP ctx)
    (.visitHeaderAMRAP this (.headerAMRAP ctx))
    (.headerEMOM ctx)
    (.visitHeaderEMOM this (.headerEMOM ctx))
    (.headerForTime ctx)
    (.visitHeaderForTime this (.headerForTime ctx))
    (.headerMultiAMRAP ctx)
    (.visitHeaderMultiAMRAP this (.headerMultiAMRAP ctx))
    (.headerMultiForTime ctx)
    (.visitHeaderMultiForTime this (.headerMultiForTime ctx))
    :else
    (throw (ex-info "Unknown Workout header type" {:ctx ctx}))))

(defn- parent-loop-header?
  [ctx]
  (instance? WorkoutParser$WorkoutContext
             (some-> ctx
                     (.parent)
                     (.parent)
                     (.parent))))

(defn- rep-scheme-finite
  [scheme]
  (let [[first-reps second-reps & _rest :as reps] (map #(Long/parseLong %)
                                                    (s/split scheme #"\-"))
        last-reps (last reps)
        step      (- second-reps first-reps)
        expected  (conj (into [] (range first-reps last-reps step)) last-reps)]
    (when-not (= expected reps)
      (throw (ex-info "Bad rep scheme" {:rep-scheme scheme})))
    {:workout.loop/rep-scheme
     {:workout.loop.rep-scheme/first-rep-count first-reps
      :workout.loop.rep-scheme/last-rep-count last-reps
      :workout.loop.rep-scheme/step step}}))

(defn- rep-scheme
  [scheme]
  (if (s/ends-with? scheme "...")
    (let [no-ellipsis (re-find #"[0-9-]+-[0-9]+" scheme)]
      (-> (rep-scheme-finite no-ellipsis)
          (update :workout.loop/rep-scheme
                  dissoc
                  :workout.loop.rep-scheme/last-rep-count)))
    (rep-scheme-finite scheme)))

(defn- visit-header-for-time
  [this ctx]
  ;; TODO handle case where user gave more than one roundCount,
  ;; scheme and time-cap as errors
  (merge
    (when (parent-loop-header? ctx)
      {:workout.loop/score-type :score-type/duration})
    (when-let [round-count (first (.roundCount ctx))]
      (.visitRoundCount this round-count))
    (when-let [time-cap (first (.timeCap ctx))]
      (.visitTimeCap this time-cap))
    (when-let [scheme (first (.REP_SCHEME ctx))]
      (rep-scheme (.getText scheme)))))

(defn- visit-time-cap
  [this ctx]
  {:workout.loop/time-cap (.visitDuration this (.duration ctx))})

(defn- visit-header-amrap
  [this ctx]
  (merge
    (when (parent-loop-header? ctx)
      {:workout.loop/score-type :score-type/rep-count})
    (when-let [duration (.duration ctx)]
      {:workout.loop/duration (.visitDuration this duration)})))

(defn- visit-duration
  [_this ctx]
  ;; FIXME we'll fail if user passes "9.0"
  (let [value (Long/parseLong (.getText (.NUMBER ctx)))]
    (cond
      (.MINUTE ctx) (* value one-minute-in-ms)
      (.SECOND ctx) (* value one-second-in-ms)
      :else         value)))

(def ^:private visit-period visit-duration)

(defn- visit-header-emom
  [this ctx]
  (merge
    {:workout.loop/duration (.visitDuration this (.duration ctx))}
    (if-let [period (.period ctx)]
      {:workout.loop/period (.visitPeriod this period)}
      {:workout.loop/period one-minute-in-ms})))

(defn- visit-rep-count
  [_this ctx]
  (Long/parseLong (.getText (.NUMBER ctx))))

(defn- visit-movement
  [_this ctx]
  (let [movement (parse-movement ctx)]
    {:movement/name movement}))

(defn- visit-application-unit
  [_this ctx]
  (cond
    (.CALORIE ctx)    :unit/calorie
    ;; Distance
    (.FOOT ctx)       :unit/foot
    (.METER ctx)      :unit/meter
    (.MILE ctx)       :unit/mile
    ;; Duration
    (.MINUTE ctx)     :unit/minute ;; Turned into ms at exercise level
    (.SECOND ctx)     :unit/second ;; Turned into ms at exercise level
    ;; Weight
    (.BODYWEIGHT ctx) :unit/bodyweight
    (.KILOGRAM ctx)   :unit/kilogram
    (.POOD ctx)       :unit/pood
    (.POUND ctx)      :unit/pound
    :else             (throw (ex-info "Unknown unit"
                                      {:str (.getText ctx)}))))

(defn- visit-application-value
  [_this ctx]
  (Double/parseDouble (.getText ctx)))

(defn- application-men-women
  [this values units]
  ;; TODO return error if (first unit ≠ second unit)
  (merge
    {:exercise.application/value
     {:exercise.application.value/men
      (.visitApplicationValue this (first values))
      :exercise.application.value/women
      (.visitApplicationValue this (second values))}}
    (when-let [unit (first units)]
      {:exercise.application/unit (.visitApplicationUnit this unit)})))

(defn- application-all
  [this value unit]
  (let [unit (.visitApplicationUnit this unit)]
    {:exercise.application/value
     {:exercise.application.value/all
      (.visitApplicationValue this value)}
     :exercise.application/unit  unit}))

(defn- visit-application
  [this ctx]
  (let [values (.applicationValue ctx)
        units  (.applicationUnit ctx)]
    (if (or (> (count values) 1)
            (> (count units) 1))
      (application-men-women this values units)
      (application-all this (first values) (first units)))))

(def ^:private movements-config
  (edn/read-string (slurp (io/resource "movements.edn"))))

(def ^:private unit-family->unit
  {:distance :unit/meter
   :duration :unit/millisecond
   :weight   :unit/kilogram})

(defn- default-application-unit
  [movement-name]
  (-> movements-config
      (get-in [movement-name :applications])
      first
      unit-family->unit))

(defn fix-application-value
  [unit {:exercise.application.value/keys [all men women]}]
  (let [fix (fn [value]
              (case unit
                :unit/second (* value one-second-in-ms)
                :unit/minute (* value one-minute-in-ms)
                value))]
    (merge
      (when all {:exercise.application.value/all (fix all)})
      (when men {:exercise.application.value/men (fix men)})
      (when women {:exercise.application.value/women (fix women)}))))

;; TODO we need to throw if we end-up with no application value/unit when
;; compulsory (keep whether or not it is compulsory in the movements-config
;; map)

(defn- with-default-application-unit
  ;; TODO This function also needs to check if given unit is ok with the
  ;; movement (ie "no kg for pull-up")
  [{:exercise/keys [movement application] :as exercise}]
  (let [unit         (:exercise.application/unit application)
        default-unit (default-application-unit (:movement/name movement))]
    (if (and (nil? unit) default-unit)
      (assoc-in
        exercise
        [:exercise/application :exercise.application/unit]
        default-unit)
      exercise)))

(defn- with-fixed-application-value
  [{:exercise/keys [application] :as exercise}]
  (let [unit (:exercise.application/unit application)]
    (if (#{:unit/minute :unit/second} unit)
      (-> exercise
          (update-in [:exercise/application :exercise.application/value]
                     #(fix-application-value unit %))
          (assoc-in [:exercise/application :exercise.application/unit]
                    :unit/millisecond))
      exercise)))

(defn- with-default-application
  "If user didn't provide any application and given movement has a default one
  in the `movements-config` map, assign it to the exercise."
  [{:exercise/keys [movement application] :as exercise}]
  (let [def-application (get-in movements-config
                                [(:movement/name movement)
                                 :default-application])
        {:exercise.application/keys [value unit]} application]
    (if (and (nil? value) (nil? unit) def-application)
      (merge exercise {:exercise/application def-application})
      exercise)))

(defn- with-fixed-application
  "Fix the application of an exercise:
  - unit: user may not specify any ; then, if compulsory, assign the default one
  - value: may need to be turned into the correct unit (minutes or seconds into
    milliseconds)"
  [exercise]
  (-> exercise
      with-default-application
      with-default-application-unit
      with-fixed-application-value))

(defn visit-exercise
  [this ctx]
  (let [exercise (cond-> {:exercise/movement
                          (.visitMovement this (.movement ctx))}
                   (.application ctx)
                   (merge {:exercise/application
                           (.visitApplication this (.application ctx))})
                   (.repCount ctx)
                   (merge {:exercise/rep-count
                           (.visitRepCount this (.repCount ctx))}))]
    (with-fixed-application exercise)))

(def workout-visitor
  (proxy
    ;; FIXME should I use a base visitor or full visitor?
    [WorkoutBaseVisitor]
    []
    (visitWorkout [ctx] (visit-workout this ctx))
    (visitWorkoutName [ctx] (visit-workout-name this ctx))
    (visitFlatLoop [ctx] (visit-flat-loop this ctx))
    (visitParentLoopNextTask [ctx] (visit-parent-loop-next-task this ctx))
    (visitParentLoop [ctx] (visit-parent-loop this ctx))
    (visitRoundCount [ctx] (visit-round-count this ctx))
    (visitHeader [ctx] (visit-header this ctx))
    (visitHeaderForTime [ctx] (visit-header-for-time this ctx))
    (visitTimeCap [ctx] (visit-time-cap this ctx))
    (visitHeaderAMRAP [ctx] (visit-header-amrap this ctx))
    (visitPeriod [ctx] (visit-period this ctx))
    (visitDuration [ctx] (visit-duration this ctx))
    (visitHeaderEMOM [ctx] (visit-header-emom this ctx))
    (visitRepCount [ctx] (visit-rep-count this ctx))
    (visitMovement [ctx] (visit-movement this ctx))
    (visitApplicationValue [ctx] (visit-application-value this ctx))
    (visitApplicationUnit [ctx] (visit-application-unit this ctx))
    (visitApplication [ctx] (visit-application this ctx))
    (visitExercise [ctx] (visit-exercise this ctx))))

(defn parser-dot-workout-name
  [parser]
  (.workoutName parser))

(defn parser-dot-duration
  [parser]
  (.duration parser))

(defn parser-dot-round-count
  [parser]
  (.roundCount parser))

(defn parser-dot-time-cap
  [parser]
  (.timeCap parser))

(defn parser-dot-header-for-time
  [parser]
  (.headerForTime parser))

(defn parser-dot-header-amrap
  [parser]
  (.headerAMRAP parser))

(defn parser-dot-header-emom
  [parser]
  (.headerEMOM parser))

(defn parser-dot-application-unit
  [parser]
  (.applicationUnit parser))

(defn parser-dot-application
  [parser]
  (.application parser))

(defn parser-dot-movement
  [parser]
  (.movement parser))

(defn parser-dot-exercise
  [parser]
  (.exercise parser))

(defn parser-dot-workout
  [parser]
  (.workout parser))

(defn parser-dot-flat-loop
  [parser]
  (.flatLoop parser))

(defn error-listener
  [!errors]
  (proxy
    [BaseErrorListener]
    []
    (syntaxError
      [recognizer offending-symbol line-nb char-pos message exception]
      (swap! !errors #(conj %
                            {:line-nb  line-nb
                             :char-pos char-pos
                             :offender (when offending-symbol
                                         (.getText offending-symbol))
                             :message  message})))))

(defn- with-error-listener
  [instance !errors]
  (doto instance
    (.removeErrorListeners)
    (.addErrorListener (error-listener !errors))))

(defn parse
  [input & {:keys [ctx-fn] :or {ctx-fn parser-dot-workout}}]
  (let [!errors (atom [])
        lexer   (with-error-listener
                  (WorkoutLexer. (CharStreams/fromString input))
                  !errors)
        parser  (with-error-listener
                  (WorkoutParser. (CommonTokenStream. lexer))
                  !errors)
        parsed  (.visit workout-visitor (ctx-fn parser))
        errors  @!errors]
    ;; (when (= ctx-fn parser-dot-workout)
    ;;   (println (pprint parsed)))
    (merge
      {:parsed parsed}
      (when (seq errors)
        {:errors errors}))))
