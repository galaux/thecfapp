(ns cfapp.api.domain.movement.kettlebell-swing
  (:require
   [cfapp.api.domain.movement.util :refer [multiply]]
   [cfapp.api.domain.movement.element :refer
    [american double-hand single russian swing kettlebell]]))

(def all
  (multiply
    #{nil single double-hand}
    #{nil american russian}
    kettlebell
    swing))
