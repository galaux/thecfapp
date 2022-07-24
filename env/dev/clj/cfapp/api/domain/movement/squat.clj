(ns cfapp.api.domain.movement.squat
  (:require
   [cfapp.api.domain.movement.common :as common]
   [cfapp.api.domain.movement.util :refer [multiply plus]]
   [cfapp.api.domain.movement.element
    :refer
    [air gobelet jump pistol squat]]))

(def all-hands-configs-and-positions-squats
  (multiply
    common/all-hands-configs-and-positions
    squat))

(def custom-squats
  (plus
    (multiply
      #{nil gobelet air}
      squat)
    (multiply
      squat
      jump)
    pistol))

(def all
  (plus
    all-hands-configs-and-positions-squats
    custom-squats))
