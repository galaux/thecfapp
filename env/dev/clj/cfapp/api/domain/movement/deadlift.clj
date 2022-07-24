(ns cfapp.api.domain.movement.deadlift
  (:require
   [cfapp.api.domain.movement.common :as common]
   [cfapp.api.domain.movement.util :refer [multiply plus]]
   [cfapp.api.domain.movement.element
    :refer
    [deadlift high-pull stiff-legged sumo]]))

(def all
  (multiply
    (plus nil common/all-hands-configs)
    #{nil sumo stiff-legged}
    deadlift
    #{nil high-pull}))
