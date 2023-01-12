(ns cfapp.api.domain.parse.workout-visitor
  (:require
   [clojure.string :as s]
   [cfapp.api.domain.parse.movement :refer [parse-movement]]
   [clojure.tools.logging :as log]
   [integrant.core :as ig]
   [datomic.client.api :as d]
   [cfapp.api.db.domain.movement :as movement])
  (:import
   WorkoutLexer
   WorkoutParser
   WorkoutBaseVisitor
   WorkoutParser$WorkoutContext
   (org.antlr.v4.runtime BaseErrorListener
                         CharStreams
                         CommonTokenStream)))

(def ^:private one-second-in-ms
  1000)

(def ^:private one-minute-in-ms
  60000)

;; TODO move to utils?
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
  {:movement/id (parse-movement ctx)})

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

(defn- fix-application-value
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

;; TODO Is taking the `first` really what I want?
(defn- default-application-unit
  [movements-config movement-id]
  (first (get-in movements-config [movement-id :valid-units])))

(defn- valid-units
  [movements-config movement-id]
  (set (get-in movements-config [movement-id :valid-units])))

;; TODO
;; (def ^:private default-application-unit (memoize default-application-unit*))
;; (def ^:private valid-units (memoize valid-units*))

(defn- with-default-application-unit
  [movements-config {:exercise/keys [movement application] :as exercise}]
  (let [movement-id         (:db/id movement)
        provided-unit       (:exercise.application/unit application)
        default-unit        (default-application-unit movements-config
                                                      movement-id)
        valid-unit?         (valid-units movements-config movement-id)
        user-provided-unit? (some? provided-unit)
        requires-unit?      (some? default-unit)]
    (condp = [user-provided-unit? requires-unit?]
      [false false] exercise
      [false true]  (assoc-in
                      exercise
                      [:exercise/application :exercise.application/unit]
                      default-unit)
      exercise))) ;; TODO re-enable checking for valid unit
      ;; (if (valid-unit? provided-unit)
      ;;   exercise
      ;;   (throw (ex-info "Bad unit for movement"
      ;;                   {:movement-id   movement-id
      ;;                    :provided-unit provided-unit
      ;;                    :valid-units   valid-unit?}))))))

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
  "If user didn't provide any application, and given movement has a default one,
  assign it to the exercise."
  [db {:exercise/keys [movement application] :as exercise}]
  (let [movement-id     (:movement/id movement)
        def-application (-> (movement/by-id db movement-id)
                            (get-in [movement-id :default-application]))
        {:exercise.application/keys [value unit]} application]
    (if (and (nil? value) (nil? unit) def-application)
      (merge exercise {:exercise/application def-application})
      exercise)))

(defn- with-fixed-application
  "Fix the application of an exercise:
  - unit: user may not specify any ; then, if compulsory, assign the default one
  - value: may need to be turned into the correct unit (minutes or seconds into
    milliseconds)"
  [db exercise]
  (->> exercise
       ;; (with-default-application db) ;; TODO
       (with-default-application-unit db)
       with-fixed-application-value))

(defn visit-exercise
  [this db ctx]
  (let [exercise (cond-> {:exercise/movement
                          (.visitMovement this (.movement ctx))}
                   (.application ctx)
                   (merge {:exercise/application
                           (.visitApplication this (.application ctx))})
                   (.repCount ctx)
                   (merge {:exercise/rep-count
                           (.visitRepCount this (.repCount ctx))}))]
    (with-fixed-application db exercise)))

(defn workout-visitor
  [db]
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
    (visitExercise [ctx] (visit-exercise this db ctx))))

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
    ;; (reportAmbiguity
    ;;   [recognizer dfa startIndex stopIndex exactSet ambigAltsConfigSet
    ;;   configs]
    ;;   (log/warn "ANTLR reportAmbiguity"))
    ;; (reportAttemptingFullContext
    ;;   [​recognizer dfa startIndex stopIndexSet conflictingAlts configs]
    ;;   (log/warn "ANTRL reportAttemptingFullContext"))
    ;; (reportContextSensitivity
    ;;   [recognizer dfa startIndex stopIndex prediction configs]
    ;;   (log/warn "ANTLR reportContextSensitivity"))
    (syntaxError
      [recognizer offending-symbol line-nb char-pos message exception]
      (swap! !errors #(conj %
                            {:line-nb  line-nb
                             :char-pos char-pos
                             :offender (when offending-symbol
                                         (.getText offending-symbol))
                             :message  message})))))

(defn- with-error-listener
  [instance listener]
  (doto instance
    (.removeErrorListeners)
    (.addErrorListener listener)))

(defmethod ig/init-key :cfapp.api.domain.parse/workout-visitor
  [_ {:keys [conn]}]
  (log/info "starting …")
  (workout-visitor (d/db conn)))

(defmethod ig/halt-key! :cfapp.api.domain.parse/workout-visitor
  [_ _]
  (log/info "stopping … "))

(defn parse
  [visitor input & {:keys [ctx-fn] :or {ctx-fn parser-dot-workout}}]
  (let [!errors      (atom [])
        err-listener (error-listener !errors)
        lexer        (-> (WorkoutLexer. (CharStreams/fromString input))
                         (with-error-listener err-listener))
        parser       (-> (WorkoutParser. (CommonTokenStream. lexer))
                         (with-error-listener err-listener))
        parsed       (.visit visitor (ctx-fn parser))
        errors       @!errors]
    (merge
      {:parsed parsed}
      (when (seq errors)
        {:errors errors}))))



;; (s/def ::movement-name :util/non-blank-string)

;; (s/def ::valid-unit
;;   #{:unit/calorie
;;     :unit/foot
;;     :unit/meter
;;     :unit/mile
;;     :unit/minute
;;     :unit/second
;;     :unit/bodyweight
;;     :unit/kilogram
;;     :unit/pood
;;     :unit/pound})
;; (s/def ::valid-units
;;   (s/coll-of ::valid-unit))

;; (s/def ::default-application
;;   ::workout/application)

;; (s/def ::movement-config
;;   (s/keys :req-un [::movement-name]
;;           :opt-un [::valid-units
;;                    ::default-application]))

;; (s/def ::movements-config
;;   (s/map-of :util/non-blank-string ::movement-config))


;; (defn- units
;;   [kw]
;;   ;; Remember: these are valid and default units for user inputs but they
;;   will
;;   ;; be "fixed" later at the exercise level
;;   (when (some? kw)
;;     (if (sequential? kw)
;;       (mapcat units kw)
;;       (case kw
;;         :duration [:unit/second :unit/minute]
;;         :distance [:unit/meter :unit/mile :unit/foot]
;;         :energy   [:unit/calorie] ;; TODO add watt?
;;         :weight   [:unit/kilogram :unit/pound :unit/pood
;;         :unit/bodyweight]))))

;; (defn- check-movements-config-format!
;;   [config]
;;   (if (s/valid? ::movements-config config)
;;     config
;;     (throw
;;       (ex-info "Wrong format for movement config map"
;;                {:error (s/explain ::movements-config config)}))))

;; (defn- check-no-duplicates!
;;   [config]
;;   (let [dups (->> (vals config)
;;                   (group-by :movement-name)
;;                   (map (fn [[movement-name occurences]]
;;                          (when (> (count occurences) 1)
;;                            [movement-name (count occurences)])))
;;                   (remove nil?)
;;                   (into {}))]
;;     (if (empty? dups)
;;       config
;;       (throw
;;         (ex-info "Duplicate movement names in movement config map"
;;                  {:duplicates dups})))))

;; (def ^:private check-movements-config!
;;   (comp
;;     check-movements-config-format!
;;     check-no-duplicates!))
