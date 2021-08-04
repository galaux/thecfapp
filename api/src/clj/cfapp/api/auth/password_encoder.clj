(ns cfapp.api.auth.password-encoder
  (:require
   [integrant.core :as ig]
   [clojure.tools.logging :as log])
  (:import
   (org.springframework.security.crypto.argon2 Argon2PasswordEncoder)))

(defmethod ig/init-key :cfapp.api.auth/password-encoder
  [_ _]
  ;; https://cheatsheetseries.owasp.org/cheatsheets/Password_Storage_Cheat_Sheet.html#argon2id
  ;; Encoding should take between 0.5s and 1s.
  ;; FIXME Use next logs to determine correct parameters for password encoding
  (Argon2PasswordEncoder.))

(defn- log-time
  [f]
  (let [start (System/nanoTime)
        result (f)]
    (log/info (format "encoding took %s ms"
                      (/ (double (- (. System (nanoTime)) start)) 1000000.0)))
    result))

(defn encode
  [encoder s]
  (log-time (fn [] (.encode encoder s))))

(defn matches?
  [encoder s encoded]
  (.matches encoder s encoded))
