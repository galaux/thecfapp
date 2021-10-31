(ns cfapp.api.spec-util
  (:require
   [cfapp.api.util :as util :refer [email-regex]]
   [clojure.edn :as edn]
   [clojure.java.io :as io]
   [clojure.spec.alpha :as s]
   [clojure.spec.gen.alpha :as gen]
   [clojure.string :as str]
   [java-time :as java-time]))

(def fake-data
  (->> (io/resource "db/fake-data.edn")
       slurp
       edn/read-string))

(def argon2id-regex
  #"^\$argon2id\$v=19\$m=4096,t=3,p=1\$[A-Za-z0-9+/]{22}\$[A-Za-z0-9+/]{43}$")

(def strong-password "rieo3*@ifi3iDE#b")

;; Encoded argon2id of the previous "strong" password (useful for tests)
(def argon2id-encoded-str
  "$argon2id$v=19$m=4096,t=3,p=1$YfWMqBvHXasExi5Iw3tqvw$tq3A7T6TFRt6gl/l2Y1Lx0JNrUGbfamfjWegczTIPDY")

(s/def :util/argon2id
  (s/with-gen (partial re-matches argon2id-regex)
              (constantly (s/gen #{argon2id-encoded-str}))))


(s/def :util/uuid
  (s/with-gen uuid? gen/uuid))

(s/def :util/non-blank-string
  (s/and string? (complement str/blank?)))
(def non-blank-string-gen (s/gen :util/non-blank-string))

(s/def :util/tld #{"fr" "net" "com" "de" "co.uk" "org" "eu"})

(s/def :util/email
  (s/with-gen
    (s/and string? #(re-matches email-regex %))
    #(gen/fmap (fn [[a b c]] (str/lower-case (str a "@" b "." c)))
               (gen/tuple
                 non-blank-string-gen
                 non-blank-string-gen
                 (s/gen :util/tld)))))

(def today
  (java-time/local-date))

;; Generator for a "local date" (ie "2021-04-17") in the vicinity of the
;; current
;; date and typed as a `java.util.Date` (for Datomic compatibility).
;; When used in sampling, dates generated won't be far apart from each other.
;; To circumvent, just `(drop 40 (gen/sample (s/gen :util/near-local-date)
;; 50))`
(s/def :util/near-local-date
  (s/with-gen inst?
              #(gen/fmap
                 (fn [r]
                   (-> today
                       (java-time/plus (java-time/days r))
                       util/LocalDate->Date))
                 (s/gen (s/int-in -100 100)))))
