(ns cfapp.mobile.ui.style.theme.core-spec
  (:require
   [cljs.spec.alpha :as s]
   [cfapp.mobile.ui.ui.style.theme.base :as base]))

;; TODO enable instrumentation

;; (require '[cljs.spec.test.alpha :as stest])
;; (defn ^:dev/after-load instrument []
;;   (stest/instrument))

;; To instrument:
;; (stest/instrument `theme/border-radius)
;; Or simply
;; (stest/instrument)
;; To unstrument: `(stest/unstrument)`

;; Then use expound for better error printing
;; https://github.com/bhb/expound

;; https://clojurians-log.clojureverse.org/shadow-cljs/2018-04-24
;; https://clojureverse.org/t/using-clojure-spec-in-clojurescript-spa/5675

(s/def ::border-radius-key (set (keys base/border-radiuses)))
(s/fdef cfapp.mobile.ui.style.theme.core/border-radius
  :args (s/cat :border-radius-id ::border-radius-key))

(s/def ::border-width-key (set (keys base/border-widths)))
(s/fdef cfapp.mobile.ui.style.theme.core/border-width
  :args (s/cat :border-width-id ::border-width-key))

(s/def ::color-key (set (keys base/colors)))
(s/fdef cfapp.mobile.ui.style.theme.core/color
  :args (s/cat :color-id ::color-key))

(s/def ::edge-size-key (set (keys base/edge-sizes)))
(s/fdef cfapp.mobile.ui.style.theme.core/edge-size
  :args (s/cat :edge-size-key ::edge-size-key))

(s/def ::font-weight-key (set (keys base/font-weights)))
(s/fdef cfapp.mobile.ui.style.theme.core/font-weight
  :args (s/cat :font-weight-key ::font-weight-key))

(s/def ::text-size-key (set (keys base/text-sizes)))
(s/def ::text-size-selector #{:height :size})
(s/fdef cfapp.mobile.ui.style.theme.core/text-size
  :args (s/alt :map (s/cat :text-size-key ::text-size-key
                           :selector      ::text-size-selector)
               :key (s/cat :text-size-key ::text-size-key)))
