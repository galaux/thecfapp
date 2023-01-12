(ns cfapp.api.domain.movement.common
  (:require
   [cfapp.api.domain.movement.util :refer [multiply plus]]
   [cfapp.api.domain.movement.element
    :refer
    [back
     hang
     power
     barbell
     double-hand
     dumbbell
     front
     kettlebell
     overhead
     single
     squat-as-adjective
     wall-ball]]))

(def one-hand-gear #{kettlebell dumbbell})

(def one-hand-positions #{nil overhead})
(def two-one-hands-positions #{nil overhead})
(def wall-ball-positions #{nil overhead})
(def barbell-positions #{front overhead back})

(def one-hand-configs
  (multiply
    #{nil single}
    one-hand-gear))

(def one-hand-configs-and-positions
  (multiply
    (plus nil one-hand-configs)
    one-hand-positions))

(def two-one-hands-configs
  (multiply
    double-hand
    one-hand-gear))

(def two-hands-configs
  (plus
    barbell
    wall-ball))

(def two-hands-configs-and-positions
  (plus
    (multiply
      barbell-positions
      barbell)
    (multiply
      wall-ball-positions
      wall-ball)))

(def all-hands-configs
  (plus
    one-hand-configs
    two-one-hands-configs
    two-hands-configs))

(def two-one-hands-configs-and-positions
  (multiply
    two-one-hands-configs
    one-hand-positions))

(def all-one-hand-gear-configs
  (plus
    one-hand-configs
    two-one-hands-configs))

(def all-one-hand-gear-configs-and-positions
  (plus
    one-hand-configs-and-positions
    two-one-hands-configs-and-positions))

(def all-hands-configs-weightlifting
  (multiply
    (plus nil all-hands-configs)
    #{nil hang}
    #{nil power squat-as-adjective}))

(def all-hands-configs-and-positions
  (plus
    one-hand-configs-and-positions
    two-one-hands-configs-and-positions
    two-hands-configs-and-positions))
