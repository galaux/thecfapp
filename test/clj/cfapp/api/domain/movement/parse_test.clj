(ns cfapp.api.domain.movement.parse-test
  (:require
   [cfapp.api.domain.movement.parse :as sut]
   [clojure.test :refer [deftest is]]))

(deftest parse-movement
  (let [expected-vs-input
        {"Toes to Bar"
         #{"t2b" "toes-to-bar"}
         "Farmer Walk"
         #{"farmer walk"}
         "Jumping Jack"
         #{"Jumping-jack"}
         "Man Maker"
         #{"Man maker"}
         "Mountain Climber"
         #{"mountain climber"}
         "Ring Row"
         #{"ring-row"}
         "Sandbag Carry"
         #{"Sand-bag carry"}
         "Turkish Get-up"
         #{"turkish getup"}
         "Row"
         #{"row"}
         "Assault Bike"
         #{"assault bike"}
         "SkiErg"
         #{"ski-erg"}
         "Air Bike"
         #{"air-bike"}
         "Run"
         #{"run"}
         "Lunge"
         #{"lunge"}
         "Push-up"
         #{"push up"}
         "Pull-up"
         #{"pull up"}
         "Deadlift"
         #{"DL"
           "deadlift"}
         "Kettlebell Sumo Deadlift"
         #{"Kettlebell Sumo Deadlift"}
         "Jerk"
         #{"jerk"}
         "Squat Clean"
         #{"squat clean"}
         "Hang Power Clean & Jerk"
         #{"hang power clean and jerk"}
         "Power Clean & Jerk"
         #{"power clean and jerk"}
         "Squat Clean & Jerk"
         #{"squat clean and jerk"}
         "Clean & Jerk"
         #{"C&j"
           "Clean and Jerk"
           "Clean & Jerk"}
         "Single Arm Kettlebell Clean & Jerk"
         #{"Single arm kb c&j"}
         "Single Arm Dumbbell Clean & Jerk"
         #{"Single arm db Clean & Jerk"}
         "Hang Power Clean"
         #{"hang power clean"}
         "Squat"
         #{"squat"}
         "Clean"
         #{"clean"}
         "Double Kettlebell Power Clean"
         #{"Double Kettlebell Power Clean"}
         "Bench Press"
         #{"bench-press"}
         "Burpee"
         #{"burpee"}
         "Box Jump"
         #{"box jump"}
         "Muscle-up"
         #{"muscle up"}
         "Burpee Bar Muscle-up"
         #{"burpee bar muscle up"}
         "Cluster"
         #{"cluster"}
         "Burpee Box Jump Over"
         #{"Burpee box-jump Over"}
         "Devil Press"
         #{"devil-press"}
         "Double Under"
         #{"DU"}}]
    (doseq [[expected inputs] expected-vs-input
            input inputs]
      (is (= {:movement expected}
             (sut/parse input))))))

(deftest error
  (is (= {:movement "Clean"
          :errors
          [[1 6 "token recognition error at: 'an'"]
           [1 8 "token recognition error at: 'd '"]
           [1 10 "token recognition error at: 'wh'"]
           [1 12 "token recognition error at: 'at'"]
           [1 5 "extraneous input ' ' expecting <EOF>"]]}
         (sut/parse "clean and what"))))
