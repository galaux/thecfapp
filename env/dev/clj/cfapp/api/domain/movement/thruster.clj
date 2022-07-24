(ns cfapp.api.domain.movement.thruster
  (:require
   [cfapp.api.domain.movement.common :as common]
   [cfapp.api.domain.movement.util :refer [multiply plus]]
   [cfapp.api.domain.movement.element :refer [thruster]]))

(def all
  (multiply
    (plus nil common/all-hands-configs)
    thruster))
