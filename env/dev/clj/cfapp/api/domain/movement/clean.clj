(ns cfapp.api.domain.movement.clean
  (:require
   [cfapp.api.domain.movement.util :refer [multiply plus]]
   [cfapp.api.domain.movement.common :as common]
   [cfapp.api.domain.movement.element :refer [clean high-pull pull]]))

(def all-hands-configs-cleans
  (multiply
    common/all-hands-configs-weightlifting
    clean))

(def customs
  (multiply
    clean
    #{pull high-pull}))

(def all
  (plus
    all-hands-configs-cleans
    customs))
