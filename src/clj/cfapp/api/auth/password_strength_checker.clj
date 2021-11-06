(ns cfapp.api.auth.password-strength-checker
  (:require
   [integrant.core :as ig])
  (:import
   (me.gosimple.nbvcxz Nbvcxz)))

(defmethod ig/init-key :cfapp.api.auth/password-strength-checker
  [_ _]
  (Nbvcxz.))

(defn strong?
  [checker p]
  (.isMinimumEntropyMet (.estimate checker p)))
