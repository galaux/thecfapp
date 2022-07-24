(ns cfapp.api.domain.movement.config
  (:require
   [clojure.spec.alpha :as s]
   [cfapp.api.spec-util]
   [cfapp.api.db.domain.workout :as workout]))

(s/def ::movement-name :util/non-blank-string)

(s/def ::valid-unit
  #{:unit/calorie
    :unit/foot
    :unit/meter
    :unit/mile
    :unit/minute
    :unit/second
    :unit/bodyweight
    :unit/kilogram
    :unit/pood
    :unit/pound})
(s/def ::valid-units
  (s/coll-of ::valid-unit))

(s/def ::default-application
  ::workout/application)

(s/def ::movement-config
  (s/keys :req-un [::movement-name]
          :opt-un [::valid-units
                   ::default-application]))

(s/def ::movements-config
  (s/map-of keyword? ::movement-config))
