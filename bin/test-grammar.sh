#! /bin/bash

# IMPORTANT:
# remember that Parser rules don't end with EOF so in these unit tests,
# we can't test for extraneous content in Parser rules!
# https://github.com/antlr/antlr4/issues/839

# TODO instead of 'noDiagnostics', specify which warning we "expect"

antlrJar='/usr/share/java/antlr-complete.jar'

script=$(readlink -f $0)
dirname=$(dirname "$script")
workoutClassPath="$dirname/../classes/workout"

# Customize the classpath to include the relative path to our classes
# where we generate them instead of the local directory
customGrun() {
  java \
    -cp "$workoutClassPath:$antlrJar" \
    org.antlr.v4.gui.TestRig \
    "$@"
}

check() {
  ruleName=$1
  input=$(echo -ne "$2")
  expected="$3"
  opts=$4
  if [ "x$opts" != "xnoDiagnostics" ];
  then
    diagnostics=$(echo -n "$input" | customGrun 'Workout' $ruleName -diagnostics 2>&1)
    assertEquals "" "$diagnostics"
  fi
  actual=$(echo -n "$input" | customGrun 'Workout' $ruleName -tree | tr -s ' ')
  assertEquals "$expected" "$actual"
}


testWorkoutName() {
  check \
    'workoutName' \
    '"Super duper workout"' \
    '(workoutName "Super duper workout")'
}

testDurationLabelShort() {
  check \
    'duration' \
    "1'" \
    "(duration 1 ')"
}

testDurationLong() {
  check \
    'duration' \
    '1 minute' \
    '(duration 1 minute)'
}

testRoundCount() {
  check \
    'roundCount' \
    '3 rounds' \
    '(roundCount 3 rounds)'
}

testTimeCap() {
  check \
    'timeCap' \
    "time cap 10'" \
    "(timeCap time cap (duration 10 '))"
}

testTimeCapAbbreviated() {
  check \
    'timeCap' \
    "TC 10'" \
    "(timeCap TC (duration 10 '))"
}

testHeaderForTime() {
  check \
    'headerForTime' \
    'for time' \
    '(headerForTime for time)'
}

testHeaderForTimeNRoundsLong() {
  check \
    'headerForTime' \
    '3 rounds, for time' \
    '(headerForTime (roundCount 3 rounds) , for time)'
}

testHeaderForTimeNRoundsShort() {
  check \
    'headerForTime' \
    '3 rounds' \
    '(headerForTime (roundCount 3 rounds))'
}

testHeaderForTimeTimeCap() {
  check \
    'headerForTime' \
    "for time, time cap 5'" \
    "(headerForTime for time , (timeCap time cap (duration 5 ')))"
}

testHeaderForTimeTimeCapShortParen() {
  check \
    'headerForTime' \
    "for time (TC 5')" \
    "(headerForTime for time ( (timeCap TC (duration 5 ')) ))"
}

testHeaderRepScheme() {
  check \
    'headerForTime' \
    '1-2-3-4 for time' \
    '(headerForTime 1-2-3-4 for time)'
}

testHeaderRepSchemeInfiniteWithTrailingDash() {
  check \
    'headerForTime' \
    '1-2-3-4-... for time' \
    '(headerForTime 1-2-3-4-... for time)'
}

testHeaderRepSchemeInfiniteWithoutTrailingDash() {
  check \
    'headerForTime' \
    '1-2-3-4... for time' \
    '(headerForTime 1-2-3-4... for time)'
}

testHeaderAMRAP() {
  check \
    'headerAMRAP' \
    'AMRAP 5 minutes' \
    '(headerAMRAP AMRAP (duration 5 minutes))'
}

testHeaderEMOM() {
  check \
    'headerEMOM' \
    "EMOM 10'" \
    "(headerEMOM EMOM (duration 10 '))"
}

testHeaderEMOMEveryNMinutes() {
  check \
    'headerEMOM' \
    "every 2' for 10'" \
    "(headerEMOM every (period 2 ') for (duration 10 '))"
}

testApplicationUnitCalorie() {
  check \
    'applicationUnit' \
    'cal' \
    '(applicationUnit cal)'
}

testApplicationUnitFeet() {
  check \
    'applicationUnit' \
    'ft' \
    '(applicationUnit ft)'
}

testApplicationUnitMeter() {
  check \
    'applicationUnit' \
    'm' \
    '(applicationUnit m)'
}

testApplicationUnitMile() {
  check \
    'applicationUnit' \
    'mi' \
    '(applicationUnit mi)'
}

testApplicationUnitMinute() {
  check \
    'applicationUnit' \
    "'" \
    "(applicationUnit ')"
}

testApplicationUnitSecond() {
  check \
    'applicationUnit' \
    '"' \
    '(applicationUnit ")'
}

testApplicationUnitBodyweightLong() {
  check \
    'applicationUnit' \
    'bodyweight' \
    '(applicationUnit bodyweight)'
}

testApplicationUnitBodyweightShort() {
  check \
    'applicationUnit' \
    'bw' \
    '(applicationUnit bw)'
}

testApplicationUnitKilogram() {
  check \
    'applicationUnit' \
    'Kg' \
    '(applicationUnit Kg)'
}

testApplicationUnitPood() {
  check \
    'applicationUnit' \
    'pood' \
    '(applicationUnit pood)'
}

testApplicationUnitPoundLong() {
  check \
    'applicationUnit' \
    'pounds' \
    '(applicationUnit pounds)'
}

testApplicationUnitPoundShort() {
  check \
    'applicationUnit' \
    'lbs' \
    '(applicationUnit lbs)'
}

testApplicationKg() {
  check \
    'application' \
    '12Kg' \
    '(application (applicationValue 12) (applicationUnit Kg))'
}

testApplicationMeter() {
  check \
    'application' \
    '12m' \
    '(application (applicationValue 12) (applicationUnit m))'
}

testApplicationMinute() {
  check \
    'application' \
    "12'" \
    "(application (applicationValue 12) (applicationUnit '))"
}

testApplicationSecond() {
  check \
    'application' \
    '12"' \
    '(application (applicationValue 12) (applicationUnit "))'
}

testApplicationBodyweight() {
  check \
    'application' \
    '1.25 BW' \
    "(application (applicationValue 1.25) (applicationUnit BW))"
}

testApplicationMenWomenDoubleUnit() {
  check \
    'application' \
    '12kg/10Kg' \
    '(application (applicationValue 12) (applicationUnit kg) / (applicationValue 10) (applicationUnit Kg))' \
    'noDiagnostics' # Ambiguity between branches not relevant to us
}

testApplicationMenWomenSimpleUnit() {
  check \
    'application' \
    '12/10kg' \
    '(application (applicationValue 12) / (applicationValue 10) (applicationUnit kg))'
}

testApplicationAtSign() {
  check \
    'application' \
    '@15kg' \
    '(application @ (applicationValue 15) (applicationUnit kg))' \
    'noDiagnostics' # Ambiguity between branches not relevant to us
}

testApplicationParen() {
  check \
    'application' \
    '(15/12kg)' \
    '(application ( (applicationValue 15) / (applicationValue 12) (applicationUnit kg) ))' \
    'noDiagnostics' # Ambiguity between branches not relevant to us
}

testApplicationNoUnit() {
  check \
    'application' \
    '(15/12)' \
    '(application ( (applicationValue 15) / (applicationValue 12) ))'
}

testMovementSingleArmKBSwing() {
  check \
    'movement' \
    'Single arm KB swing' \
    '(movement Single arm KB swing)'
}

testExercise() {
  # TODO Is this even possible? An exercise without any application nor rep-count?
  check \
    'exercise' \
    'rest' \
    '(exercise (movement rest))'
}

testExerciseWithRepCount() {
  check \
    'exercise' \
    '5 KB swings' \
    '(exercise (repCount 5) (movement KB swings))'
}

testExerciseWithAtSignPostApplication() {
  check \
    'exercise' \
    'KB swing @ 15Kg' \
    '(exercise (movement KB swing) (application @ (applicationValue 15) (applicationUnit Kg)))' \
    'noDiagnostics' # Ambiguity between branches not relevant to us
}

testExerciseWithParenPostApplication() {
  check \
    'exercise' \
    'KB swings (15Kg)' \
    '(exercise (movement KB swings) (application ( (applicationValue 15) (applicationUnit Kg) )))' \
    'noDiagnostics' # Ambiguity between branches not relevant to us
}

testExerciseWithPreApplication() {
  check \
    'exercise' \
    "1' rest" \
    "(exercise (application (applicationValue 1) (applicationUnit ')) (movement rest))"
}

testExerciseWithRepCountAndPreApplication() {
  check \
    'exercise' \
    '10 20Kg kb swing' \
    '(exercise (repCount 10) (application (applicationValue 20) (applicationUnit Kg)) (movement kb swing))'
}

testExerciseWithBullet() {
  check \
    'exercise' \
    '- KB swings (15Kg)' \
    '(exercise - (movement KB swings) (application ( (applicationValue 15) (applicationUnit Kg) )))' \
    'noDiagnostics' # Ambiguity between branches not relevant to us
}

testFlatLoop() {
  check \
    'flatLoop' \
    "for time\n5 pull-ups\n10 push-ups" \
    '(flatLoop (header (headerForTime for time)) \n (exercise (repCount 5) (movement pull-ups)) \n (exercise (repCount 10) (movement push-ups)))' \
    'noDiagnostics' # TODO
}

testParentLoop() {
  check \
    'parentLoop' \
    "AMRAP 15'\n\nfor time\n5 pull-ups\n10 push-ups\n\n1' rest" \
    "(parentLoop (header (headerAMRAP AMRAP (duration 15 '))) \n \n (flatLoop (header (headerForTime for time)) \n (exercise (repCount 5) (movement pull-ups)) \n (exercise (repCount 10) (movement push-ups))) \n (parentLoopNextTask \n (exercise (application (applicationValue 1) (applicationUnit ')) (movement rest))))" \
    'noDiagnostics' # TODO
}

testWorkout() {
  check \
    'workout' \
    "\"Joe\"\nAMRAP 15'\n\nfor time\n5 pull-ups\n10 push-ups\n\n1' rest" \
    "(workout (workoutName \"Joe\") \n (parentLoop (header (headerAMRAP AMRAP (duration 15 '))) \n \n (flatLoop (header (headerForTime for time)) \n (exercise (repCount 5) (movement pull-ups)) \n (exercise (repCount 10) (movement push-ups))) \n (parentLoopNextTask \n (exercise (application (applicationValue 1) (applicationUnit ')) (movement rest)))) <EOF>)" \
    'noDiagnostics' # TODO
}

. /usr/bin/shunit2
