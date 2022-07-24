(ns cfapp.api.domain.movement.snatch
  (:require
   [cfapp.api.domain.movement.common :as common]
   [cfapp.api.domain.movement.util :refer [multiply plus]]
   [cfapp.api.domain.movement.element
    :refer
    [balance high-pull muscle pull snatch]]))

(def muscle-snatch
  (multiply muscle snatch))

(def all-hands-configs-snatches
  (multiply
    common/all-hands-configs-weightlifting
    snatch))

(def customs
  (plus
    muscle-snatch
    (multiply
      snatch
      #{balance pull high-pull})))

(def all
  (plus
    all-hands-configs-snatches
    customs))
