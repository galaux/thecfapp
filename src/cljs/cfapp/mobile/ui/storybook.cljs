(ns cfapp.mobile.ui.storybook
  (:require
   [reagent.core :as r]))

(defn ->params
  [^js args]
  (js->clj args :keywordize-keys true))

(defn ->default
  [options]
  (-> options
      (update :component r/reactify-component)
      clj->js))

(def logged-user
  ;; Watch-out: this is the user returned by Auth0, not the one in Datomic
  {:first-name  "Joanna"
   :last-name   "Wallace"
   :avatar-path
   "https://randomuser.me/api/portraits/women/13.jpg"})

(def user-a
  {:id   "8c99beba-12da-44c8-aeb2-e44f82ca27cd"
   :name "Rick"})

(def user-b
  {:id   "8c99beba-12da-44c8-aeb2-e44f82ca27cd"
   :name "Morty"})

(def user-c
  {:id   "8c99beba-12da-44c8-aeb2-e44f82ca27cd"
   :name "Birdman"})

(def workout-for-time
  {:id   "8548378b-afcb-499f-bd6e-55c1b05beaa6"
   :name "Karen"
   :loop {:time-cap   60000
          :duration   nil
          :period     nil
          :rep-count  nil
          :score-type "DURATION"
          :tasks      [{:movement    {:id
                                      "4ea8db0e-df0c-4547-bbf5-c5e3c65b9f83"
                                      :name "Wall Ball Shot"}
                        :application {:value {:men   9
                                              :women 6}
                                      :unit  "KILOGRAM"}
                        :rep-count   150}]}})

(def workout-emom
  {:id   "0de7b6e8-518d-44cc-8761-839638118352"
   :name "Chelsea"
   :loop {:time-cap   nil
          :duration   1800000
          :period     60000
          :rep-count  nil
          :score-type nil
          :tasks      [{:movement  {:id   "6ed67197-59e5-439e-8166-a13ef9c5b40f"
                                    :name "Pull-Up"}
                        :rep-count 5}
                       {:movement  {:id   "9fd57828-a05e-4e26-9b39-2e0c44578b76"
                                    :name "Push-Up"}
                        :rep-count 10}
                       {:movement  {:id   "08c306bc-847b-4bc8-9fa8-cc2f4fd19ef0"
                                    :name "Air Squat"}
                        :rep-count 15}]}})

(def workout-emom-unusual-period
  {:id   "0de7b6e8-518d-44cc-8761-839638118359"
   :name "Strange Chelsea"
   :loop {:time-cap   nil
          :duration   1800000
          :period     90000
          :rep-count  nil
          :score-type nil
          :tasks      [{:movement  {:id   "6ed67197-59e5-439e-8166-a13ef9c5b40f"
                                    :name "Pull-Up"}
                        :rep-count 5}
                       {:movement  {:id   "9fd57828-a05e-4e26-9b39-2e0c44578b76"
                                    :name "Push-Up"}
                        :rep-count 10}
                       {:movement  {:id   "08c306bc-847b-4bc8-9fa8-cc2f4fd19ef0"
                                    :name "Air Squat"}
                        :rep-count 15}]}})

(def workout-rep-count
  {:id   "89793"
   :name "Mary"
   :loop
   {:duration   1200000
    :score-type "REP_COUNT"
    :tasks
    [{:movement  {:name "Handstand Push-Up"}
      :rep-count 5}
     {:movement  {:name "Pistol"}
      :rep-count 10}
     {:movement  {:name "Pull-Up"}
      :rep-count 15}]}})

(def workout-multi-amraps
  {:id   "de5aa274-e487-4f12-992e-ee373ed1fcda"
   :name "Multi AMRAP"
   :loop
   {:time-cap   nil
    :duration   nil
    :period     nil
    :rep-count  nil
    :score-type nil
    :tasks
    [{:rep-count  nil
      :time-cap   nil
      :duration   300000
      :period     nil
      :score-type "REP_COUNT"
      :tasks      [{:movement    {:id   "f008df36-e7c4-4183-958f-85288e4b5577"
                                  :name "Double Kettlebell Squat"}
                    :application {:value {:men 24 :women 16}
                                  :unit  "KILOGRAM"}
                    :rep-count   20}
                   {:movement  {:id   "b33d6443-c735-41fe-9f26-380012b7d003"
                                :name "Double-Under"}
                    :rep-count 40}]}
     {:movement    {:id "78a64670-42eb-4026-b830-479a21aac0fb" :name "Rest"}
      :application {:value {:all 60000} :unit "MILLISECOND"}
      :rep-count   nil}
     {:rep-count  nil
      :time-cap   nil
      :duration   300000
      :period     nil
      :score-type "REP_COUNT"
      :tasks      [{:movement    {:id   "4248df62-fe3d-457d-8a90-9b519423e228"
                                  :name "Double Kettlebell Clean"}
                    :application {:value {:men 24 :women 16} :unit "KILOGRAM"}
                    :rep-count   20}
                   {:movement  {:id   "b33d6443-c735-41fe-9f26-380012b7d003"
                                :name "Double-Under"}
                    :rep-count 40}]}
     {:movement    {:id "78a64670-42eb-4026-b830-479a21aac0fb" :name "Rest"}
      :application {:value {:all 60000} :unit "MILLISECOND"}
      :rep-count   nil}
     {:rep-count  nil
      :time-cap   nil
      :duration   300000
      :period     nil
      :score-type "REP_COUNT"
      :tasks      [{:movement    {:id   "6e9fc94b-437f-4be4-b3a7-ab4571a5da8f"
                                  :name "Double Kettlebell Thruster"}
                    :application {:value {:men 24 :women 16} :unit "KILOGRAM"}
                    :rep-count   20}
                   {:movement  {:id   "b33d6443-c735-41fe-9f26-380012b7d003"
                                :name "Double-Under"}
                    :rep-count 40}]}]}})

(def crossfit-lutece
  {:id           "89898"
   :name         "CrossFit Dansaert"
   :address      {:street   "Rue Vandenbranden 1"
                  :zip-code "1000"
                  :city     "Brussels"
                  :country  "Belgium"}
   :is-member?   true
   :member-count 28
   :wods         [{:id      "1"
                   :workout workout-for-time
                   :scores  []}
                  {:id      "2"
                   :workout workout-emom
                   :scores  []}
                  {:id      "2"
                   :workout workout-emom-unusual-period
                   :scores  []}
                  {:id      "3"
                   :workout workout-multi-amraps
                   :scores  []}]})

(def communities
  [crossfit-lutece
   {:id           "134"
    :name         "Crossfit Dansaert"
    :address      {:street   "Rue Vandenbranden, 1"
                   :zip-code "1000"
                   :city     "Brussels"
                   :country  "Belgium"}
    :is-member?   true
    :member-count 28}])

(def wod
  {:id      "81834"
   :date    #inst "2022-05-01T00:00:00.000-00:00"
   :workout workout-multi-amraps})

(def scores-for-duration
  [{:id    "fc07f468-3bfa-4636-93e9-43b37d346f28"
    :user  user-a
    :value 61000}
   {:id    "102c57e6-8323-4c81-af40-4c1fba46a988"
    :user  user-b
    :value 1000}
   {:id    "b50506fe-bf14-4b07-a27a-336a3d7a70c6"
    :user  user-c
    :value 29000}])

(def scores-for-rep-count
  [{:id    "fc07f468-3bfa-4636-93e9-43b37d346f28"
    :user  user-a
    :value 135}
   {:id    "102c57e6-8323-4c81-af40-4c1fba46a988"
    :user  user-b
    :value 1}
   {:id    "b50506fe-bf14-4b07-a27a-336a3d7a70c6"
    :user  user-c
    :value 31}])
