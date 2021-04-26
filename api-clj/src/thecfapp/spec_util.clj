(ns thecfapp.spec-util
  (:require
   [clojure.spec.alpha :as s]
   [clojure.spec.gen.alpha :as gen]
   [clojure.string :as str]
   [java-time :as java-time]
   [thecfapp.util :as util]))

(s/def :util/uuid
  (s/with-gen uuid? gen/uuid))

(s/def :util/non-blank-string
  (s/and string? (complement str/blank?)))
(def non-blank-string-gen (s/gen :util/non-blank-string))

(s/def :util/tld #{"fr" "net" "com" "de" "co.uk" "org" "eu"})

(def email-regex #"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,63}$")
(s/def :util/email
  (s/with-gen
    (s/and string? #(re-matches email-regex %))
    #(gen/fmap (fn [[a b c]] (str/lower-case (str a "@" b "." c)))
               (gen/tuple
                 non-blank-string-gen
                 non-blank-string-gen
                 (s/gen :util/tld)))))

(def local-date
  (java-time/local-date))

;; Generator for a "local date" (ie "2021-04-17") in the vicinity of the current
;; date and typed as a `java.util.Date` (for Datomic compatibility).
;; When used in sampling, dates generated won't be far apart from each other.
;; To circumvent, just `(drop 40 (gen/sample (s/gen :util/near-local-date) 50))`
(s/def :util/near-local-date
  (s/with-gen inst?
    #(gen/fmap
      (fn [r]
        (-> local-date
            (java-time/plus (java-time/days r))
            util/LocalDate->Date))
      (s/gen (s/int-in -100 100)))))
