(ns cfapp.mobile.ui.component.wod-card.view-spec
  (:require
   [cfapp.mobile.ui.component.wod-card.view :as specd]
   [clojure.spec.alpha :as s]
   [clojure.spec.test.alpha :as stest]))

(s/def ::href string?)
(s/def ::props
  (s/keys
   :req-un [::href]))

(s/def ::wod
  map?) ;; TODO

(s/fdef wod-card
  :args (s/keys*
         :opt-un [::props
                  ::wod]))

(stest/instrument `specd/wod-card)
