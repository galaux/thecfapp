(ns cfapp.api.domain.movement.shoulder-to-overhead
  (:require
   [cfapp.api.domain.movement.common :as common]
   [cfapp.api.domain.movement.util :refer [multiply plus]]
   [cfapp.api.domain.movement.element
    :refer
    [bench
     jerk
     power
     press
     push
     shoulder-to-overhead
     split
     squat-as-adjective
     strict]]))

(def customs
  (multiply
    (plus nil common/two-one-hands-configs)
    bench ;; TODO Should probably have its own file with more alternatives
    press))

(def all-hands-configs-and-positions-presses
  (multiply
    (plus nil common/all-hands-configs)
    (plus
      (multiply #{nil strict push power} press)
      (multiply #{nil push power squat-as-adjective split} jerk)
      shoulder-to-overhead)))

(def all
  (plus
    all-hands-configs-and-positions-presses
    customs))
