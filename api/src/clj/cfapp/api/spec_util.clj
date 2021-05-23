(ns cfapp.api.spec-util
  (:require
    [cfapp.api.util :as util]
    [clojure.spec.alpha :as s]
    [clojure.spec.gen.alpha :as gen]
    [clojure.string :as str]
    [java-time :as java-time]
    [talltale.core :as fake]
    [datomic.client.api :as d]))

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

(def today
  (java-time/local-date))

;; Generator for a "local date" (ie "2021-04-17") in the vicinity of the current
;; date and typed as a `java.util.Date` (for Datomic compatibility).
;; When used in sampling, dates generated won't be far apart from each other.
;; To circumvent, just `(drop 40 (gen/sample (s/gen :util/near-local-date) 50))`
(s/def :util/near-local-date
  (s/with-gen inst?
    #(gen/fmap
      (fn [r]
        (-> today
            (java-time/plus (java-time/days r))
            util/LocalDate->Date))
      (s/gen (s/int-in -100 100)))))

(def lorem-ipsum-words-gen
  (let [words (->> (fake/lorem-ipsum)
                   str/lower-case
                   (remove #(contains? #{\. \,} %))
                   (apply str))]
    (-> words
        (str/split #" ")
        set
        s/gen)))

(defn- gen-lorem-ipsum-text
  []
  (gen/fmap
    (partial str/join " ")
    (gen/vector lorem-ipsum-words-gen)))

(s/def ::lorem-ipsum-text
  (s/with-gen
    string?
    gen-lorem-ipsum-text))

(def lorem-ipsum-text-gen
  "Spec generator to produce Lorem Ipsum like texts."
  (s/gen ::lorem-ipsum-text))
