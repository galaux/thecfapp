(ns cfapp.api.domain.movement.clean-and-jerk
  (:require
   [cfapp.api.domain.movement.common :as common]
   [cfapp.api.domain.movement.element
    :refer [clean-and clean-and-jerk power push split squat jerk]]
   [cfapp.api.domain.movement.util :refer [multiply plus]]))

(def all
  (multiply
    common/all-hands-configs-weightlifting
    (plus
      (multiply
        clean-and
        #{power push split squat}
        jerk)
      clean-and-jerk)))
