(ns cfapp.api.domain.movement.lunge
  (:require
   [cfapp.api.domain.movement.common :as common]
   [cfapp.api.domain.movement.util :refer [multiply plus]]
   [cfapp.api.domain.movement.element :refer [jumping lunge walking]]))

(def all-hands-configs-and-positions-lunges
  (multiply
    common/all-hands-configs-and-positions
    #{nil walking}
    lunge))

(def no-load-lunges
  (multiply
    #{nil jumping walking}
    lunge))

(def all
  (plus
    all-hands-configs-and-positions-lunges
    no-load-lunges))
