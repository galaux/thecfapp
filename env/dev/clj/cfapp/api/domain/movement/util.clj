(ns cfapp.api.domain.movement.util
  (:require
   [clojure.set :refer [union]]
   [clojure.string :as string]))

(defn elem
  [rule & {:keys [id]}]
  (let [computed-id (-> (apply str (take-while #(not= % \|) rule))
                        (string/replace #"SEP\??" "_")
                        (string/replace #"[\s\?()]" ""))]
    {:id   (or id computed-id)
     :rule rule}))

(defn with-extra-rule
  [e extra-rule]
  (update e
          :rule
          (fn [current-rule]
            (format "( %s ) | ( %s )" current-rule extra-rule))))

(defn derive-from
  [e ancestor]
  (merge e ancestor))

(defn with-extra-ancestor
  [e ancestor-id ancestor-rule]
  (update e :ancestors assoc ancestor-id ancestor-rule))

(defn combine
  [{id-a :id rule-a :rule ancestors-a :ancestors}
   {id-b :id rule-b :rule ancestors-b :ancestors}
   &
   {:keys [id rule-format]
    :or   {rule-format "%s SEP %s"}}]
  {:id        (or id (format "%s_%s" id-a id-b))
   :rule      (format rule-format id-a id-b)
   :ancestors (merge
                ancestors-a
                ancestors-b
                {id-a rule-a
                 id-b rule-b})})

(defn multiply
  ([as bs]
   (let [res (for [a (if (or (nil? as) (map? as)) [as] as)
                   b (if (or (nil? bs) (map? bs)) [bs] bs)]
               (case [(some? a) (some? b)]
                 [false false] nil
                 [false true]  b
                 [true false]  a
                 (combine a b)))]
     (set res)))
  ([a b & more]
   (reduce multiply (concat [a b] more))))

(defn plus
  ([a b]
   (union
     (if (set? a) a #{a})
     (if (set? b) b #{b})))
  ([a b & more]
   (reduce plus (concat [a b] more))))
