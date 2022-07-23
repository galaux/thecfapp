lexer grammar MovementLexer ;

/*
 * Lexer rules
 */

fragment DIGIT_2 : '2' ;
fragment AND : A N D | '&' ;

fragment ARM : A R M ;
fragment BAR : B A R ;
fragment BIKE : B I K E ;
fragment BOX : B O X ( E S )?;
fragment CARRY : C A R R Y ;
fragment SLED : S L E D ;
fragment CHEST_TO_BAR : ( C H E S T SEP TO SEP BAR | C DIGIT_2 B ) ;
fragment DB : ( D U M B B E L L S? | D B ) ;
fragment DIP : D I P S? ;
fragment DOUBLE : D O U B L E ;
fragment FACING : F A C I N G ;
fragment FRONT : F R O N T ;
fragment HANG : H A N G ;
fragment HIGH_PULL : H I G H SEP PULL ;
fragment JUMP : J U M P S? ;
fragment JUMPING : J U M P I N G ;
fragment KB : ( K B | K E T T L E B E L L S? ) ;
fragment KIPPING : K I P P I N G ;
fragment LEG : L E G ;
fragment MUSCLE : M U S C L E S? ;
fragment ONE : O N E ;
fragment OVER : O V E R ;
fragment OVERHEAD : ( OVER SEP? H E A D | O H ) ;
fragment POWER : P O W E R ;
fragment PULL : P U L L S? ;
fragment PUSH : P U S H ;
fragment RACK : R A C K ;
fragment RING : R I N G S? ;
fragment RUSSIAN : R U S S I A N ;
fragment SHOULDER : S H O U L D E R ;
fragment SINGLE : S I N G L E ;
fragment SINGLE_or_ONE_ARM : ( S I N G L E | ONE ) SEP ARM ;
fragment STEP_UP : S T E P SEP UP ;
fragment STRICT : S T R I C T ;
fragment STRICT_or_SHOULDER : ( STRICT | SHOULDER ) ;
fragment SWING : S W I N G S? ;
fragment TO : T O ;
fragment TOES : T O E S ;
fragment UNDER : U N D E R S? ;
fragment UP : U P S? ;
fragment WALK : W A L K S? ;
fragment WALKING : W A L K I N G ;
fragment WEIGHTED : W E I G H T E D ;
fragment YOKE : Y O K E ;
fragment GHD : G H D ;

fragment AMERICAN_KB_SWING : ( A M E R I C A N SEP )? KB SEP SWING ;
fragment DB_CLEAN : DB SEP CLEAN ;
fragment DB_CLEAN_AND_JERK : DB SEP CLEAN_AND_JERK ;
fragment KB_CLEAN : KB SEP CLEAN ;
fragment KB_CLEAN_AND_JERK : KB SEP CLEAN_AND_JERK ;

// Compound
BURPEE_BAR_MUSCLE_UP : BURPEE SEP BAR SEP MUSCLE_UP ; // ["Burpee Bar Muscle-up" [nil]]
BURPEE_MUSCLE_UP : BURPEE SEP MUSCLE_UP ; // ["Burpee Muscle-up" [nil]]
BURPEE_PULL_UP : BURPEE SEP PULL_UP ; // ["Burpee Pull-up" [nil]]
CLUSTER : C L U S T E R S? ; // ["Cluster" [:weight]]

BAR_FACING_BURPEE : BAR SEP FACING SEP BURPEE ; // ["Bar-facing Burpee" [nil]]
BURPEE_BOX_JUMP_OVER : BURPEE SEP BOX_JUMP_OVER ; // ["Burpee Box Jump Over" [nil :distance]]
BOX_JUMP_OVER : ( BOX_JUMP SEP OVER ) | B J O ; // ["Box Jump Over" [nil :distance]]
BOX_STEP_UP : BOX SEP STEP_UP ; // ["Box Step-up" [nil :distance]]
WEIGHTED_BOX_STEP_UP : WEIGHTED SEP BOX_STEP_UP ; // ["Weighted Box Step-up" [nil :distance]]

// Clean & Jerk
SINGLE_ARM_KB_CLEAN_AND_JERK : ( SINGLE_or_ONE_ARM SEP )? KB_CLEAN_AND_JERK ; // ["Kettlebell Clean & Jerk" [:weight]]
SINGLE_ARM_DB_CLEAN_AND_JERK : ( SINGLE_or_ONE_ARM SEP )? DB_CLEAN_AND_JERK ; // ["Dumbbell Clean & Jerk" [:weight]]
DOUBLE_KB_CLEAN_AND_JERK : DOUBLE SEP KB_CLEAN_AND_JERK ; // ["Double Kettlebell Clean & Jerk" [:weight]]
DOUBLE_DB_CLEAN_AND_JERK : DOUBLE SEP DB_CLEAN_AND_JERK ; // ["Double Dumbbell Clean & Jerk" [:weight]]
SINGLE_ARM_KB_HANG_CLEAN_AND_JERK : ( SINGLE_or_ONE_ARM SEP )? KB SEP HANG_CLEAN_AND_JERK ; // ["Kettlebell Hang Clean & Jerk" [:weight]]
SINGLE_ARM_DB_HANG_CLEAN_AND_JERK : ( SINGLE_or_ONE_ARM SEP )? DB SEP HANG_CLEAN_AND_JERK ; // ["Dumbbell Hang Clean & Jerk" [:weight]]
DOUBLE_KB_HANG_CLEAN_AND_JERK : DOUBLE SEP KB SEP HANG_CLEAN_AND_JERK ; // ["Double Kettlebell Hang Clean & Jerk" [:weight]]
DOUBLE_DB_HANG_CLEAN_AND_JERK : DOUBLE SEP DB SEP HANG_CLEAN_AND_JERK ; // ["Double Dumbbell Hang Clean & Jerk" [:weight]]
SINGLE_ARM_KB_POWER_CLEAN_AND_JERK : ( SINGLE_or_ONE_ARM SEP )? KB SEP POWER_CLEAN_AND_JERK ; // ["Kettlebell Power Clean & Jerk" [:weight]]
SINGLE_ARM_DB_POWER_CLEAN_AND_JERK : ( SINGLE_or_ONE_ARM SEP )? DB SEP POWER_CLEAN_AND_JERK ; // ["Dumbbell Power Clean & Jerk" [:weight]]
DOUBLE_KB_POWER_CLEAN_AND_JERK : DOUBLE SEP KB SEP POWER_CLEAN_AND_JERK ; // ["Double Kettlebell Power Clean & Jerk" [:weight]]
DOUBLE_DB_POWER_CLEAN_AND_JERK : DOUBLE SEP DB SEP POWER_CLEAN_AND_JERK ; // ["Double Dumbbell Power Clean & Jerk" [:weight]]
HANG_CLEAN_AND_JERK : HANG SEP CLEAN_AND_JERK ; // ["Hang Clean & Jerk" [:weight]]
SINGLE_ARM_KB_SQUAT_CLEAN_AND_JERK : ( SINGLE_or_ONE_ARM SEP )? KB SEP SQUAT_CLEAN_AND_JERK ; // ["Kettlebell Squat Clean & Jerk" [:weight]]
SINGLE_ARM_DB_SQUAT_CLEAN_AND_JERK : ( SINGLE_or_ONE_ARM SEP )? DB SEP SQUAT_CLEAN_AND_JERK ; // ["Dumbbell Squat Clean & Jerk" [:weight]]
DOUBLE_KB_SQUAT_CLEAN_AND_JERK : DOUBLE SEP KB SEP SQUAT_CLEAN_AND_JERK ; // ["Double Kettlebell Squat Clean & Jerk" [:weight]]
DOUBLE_DB_SQUAT_CLEAN_AND_JERK : DOUBLE SEP DB SEP SQUAT_CLEAN_AND_JERK ; // ["Double Dumbbell Squat Clean & Jerk" [:weight]]
SINGLE_ARM_KB_HANG_POWER_CLEAN_AND_JERK : ( SINGLE_or_ONE_ARM SEP )? KB SEP HANG_POWER_CLEAN_AND_JERK ; // ["Kettlebell Hang Power Clean & Jerk" [:weight]]
SINGLE_ARM_DB_HANG_POWER_CLEAN_AND_JERK : ( SINGLE_or_ONE_ARM SEP )? DB SEP HANG_POWER_CLEAN_AND_JERK ; // ["Dumbbell Hang Power Clean & Jerk" [:weight]]
DOUBLE_KB_HANG_POWER_CLEAN_AND_JERK : DOUBLE SEP KB SEP HANG_POWER_CLEAN_AND_JERK ; // ["Double Kettlebell Hang Power Clean & Jerk" [:weight]]
DOUBLE_DB_HANG_POWER_CLEAN_AND_JERK : DOUBLE SEP DB SEP HANG_POWER_CLEAN_AND_JERK ; // ["Double Dumbbell Hang Power Clean & Jerk" [:weight]]
HANG_SQUAT_CLEAN_AND_JERK : HANG SEP SQUAT SEP CLEAN_AND_JERK ; // ["Hang Squat Clean & Jerk" [:weight]]
SINGLE_ARM_KB_HANG_SQUAT_CLEAN_AND_JERK : ( SINGLE_or_ONE_ARM SEP )? KB SEP HANG_SQUAT_CLEAN_AND_JERK ; // ["Kettlebell Hang Squat Clean & Jerk" [:weight]]
SINGLE_ARM_DB_HANG_SQUAT_CLEAN_AND_JERK : ( SINGLE_or_ONE_ARM SEP )? HANG_SQUAT_CLEAN_AND_JERK ; // ["Dumbbell Hang Squat Clean & Jerk" [:weight]]
DOUBLE_KB_HANG_SQUAT_CLEAN_AND_JERK : DOUBLE SEP KB SEP HANG_SQUAT_CLEAN_AND_JERK ; // ["Double Kettlebell Hang Squat Clean & Jerk" [:weight]]
DOUBLE_DB_HANG_SQUAT_CLEAN_AND_JERK : DOUBLE SEP DB SEP HANG_SQUAT_CLEAN_AND_JERK ; // ["Double Dumbbell Hang Squat Clean & Jerk" [:weight]]

// Clean
SINGLE_ARM_KB_CLEAN : ( SINGLE_or_ONE_ARM SEP )? KB_CLEAN ; // ["Kettlebell Clean" [:weight]]
SINGLE_ARM_DB_CLEAN : ( SINGLE_or_ONE_ARM SEP )? DB_CLEAN ; // ["Dumbbell Clean" [:weight]]
DOUBLE_KB_CLEAN : DOUBLE SEP KB_CLEAN ; // ["Double Kettlebell Clean" [:weight]]
DOUBLE_DB_CLEAN : DOUBLE SEP DB_CLEAN ; // ["Double Dumbbell Clean" [:weight]]
SINGLE_ARM_KB_HANG_CLEAN : ( SINGLE_or_ONE_ARM SEP )? KB SEP HANG_CLEAN ; // ["Kettlebell Hang Clean" [:weight]]
SINGLE_ARM_DB_HANG_CLEAN : ( SINGLE_or_ONE_ARM SEP )? DB SEP HANG_CLEAN ; // ["Dumbbell Hang Clean" [:weight]]
DOUBLE_KB_HANG_CLEAN : DOUBLE SEP KB SEP HANG_CLEAN ; // ["Double Kettlebell Hang Clean" [:weight]]
DOUBLE_DB_HANG_CLEAN : DOUBLE SEP DB SEP HANG_CLEAN ; // ["Double Dumbbell Hang Clean" [:weight]]
SINGLE_ARM_KB_POWER_CLEAN : ( SINGLE_or_ONE_ARM SEP )? KB SEP POWER_CLEAN ; // ["Kettlebell Power Clean" [:weight]]
SINGLE_ARM_DB_POWER_CLEAN : ( SINGLE_or_ONE_ARM SEP )? DB SEP POWER_CLEAN ; // ["Dumbbell Power Clean" [:weight]]
DOUBLE_KB_POWER_CLEAN : DOUBLE SEP KB SEP POWER_CLEAN ; // ["Double Kettlebell Power Clean" [:weight]]
DOUBLE_DB_POWER_CLEAN : DOUBLE SEP DB SEP POWER_CLEAN ; // ["Double Dumbbell Power Clean" [:weight]]
POWER_CLEAN : POWER SEP CLEAN ; // ["Power Clean" [:weight]]
HANG_CLEAN : HANG SEP CLEAN ; // ["Hang Clean" [:weight]]
SINGLE_ARM_KB_SQUAT_CLEAN : ( SINGLE_or_ONE_ARM SEP )? KB SEP SQUAT_CLEAN ; // ["Kettlebell Squat Clean" [:weight]]
SINGLE_ARM_DB_SQUAT_CLEAN : ( SINGLE_or_ONE_ARM SEP )? DB SEP SQUAT_CLEAN ; // ["Dumbbell Squat Clean" [:weight]]
DOUBLE_KB_SQUAT_CLEAN : DOUBLE SEP KB SEP SQUAT_CLEAN ; // ["Double Kettlebell Squat Clean" [:weight]]
DOUBLE_DB_SQUAT_CLEAN : DOUBLE SEP DB SEP SQUAT_CLEAN ; // ["Double Dumbbell Squat Clean" [:weight]]
SINGLE_ARM_KB_HANG_POWER_CLEAN : ( SINGLE_or_ONE_ARM SEP )? KB SEP HANG_POWER_CLEAN ; // ["Kettlebell Hang Power Clean" [:weight]]
SINGLE_ARM_DB_HANG_POWER_CLEAN : ( SINGLE_or_ONE_ARM SEP )? DB SEP HANG_POWER_CLEAN ; // ["Dumbbell Hang Power Clean" [:weight]]
DOUBLE_KB_HANG_POWER_CLEAN : DOUBLE SEP KB SEP HANG_POWER_CLEAN ; // ["Double Kettlebell Hang Power Clean" [:weight]]
DOUBLE_DB_HANG_POWER_CLEAN : DOUBLE SEP DB SEP HANG_POWER_CLEAN ; // ["Double Dumbbell Hang Power Clean" [:weight]]
HANG_SQUAT_CLEAN : HANG SEP SQUAT SEP CLEAN ; // ["Hang Squat Clean" [:weight]]
SINGLE_ARM_KB_HANG_SQUAT_CLEAN : ( SINGLE_or_ONE_ARM SEP )? KB SEP HANG_SQUAT_CLEAN ; // ["Kettlebell Hang Squat Clean" [:weight]]
SINGLE_ARM_DB_HANG_SQUAT_CLEAN : ( SINGLE_or_ONE_ARM SEP )? HANG_SQUAT_CLEAN ; // ["Dumbbell Hang Squat Clean" [:weight]]
DOUBLE_KB_HANG_SQUAT_CLEAN : DOUBLE SEP KB SEP HANG_SQUAT_CLEAN ; // ["Double Kettlebell Hang Squat Clean" [:weight]]
DOUBLE_DB_HANG_SQUAT_CLEAN : DOUBLE SEP DB SEP HANG_SQUAT_CLEAN ; // ["Double Dumbbell Hang Squat Clean" [:weight]]
WALL_BALL_CLEAN : WALL_BALL SEP CLEAN ; // ["Wall-ball Clean" [:weight]]
MUSCLE_CLEAN : MUSCLE SEP CLEAN ; // ["Muscle Clean" [:weight]]
CLEAN_PULL : CLEAN SEP PULL ; // ["Clean Pull" [:weight]]

// Deadlift
SINGLE_ARM_KB_DEADLIFT : ( SINGLE_or_ONE_ARM SEP )? KB SEP DEADLIFT ; // ["Sing Arm Kettlebell Deadlift" [:weight]]
SINGLE_ARM_DB_DEADLIFT : ( SINGLE_or_ONE_ARM SEP )? DB SEP DEADLIFT ; // ["Sing Arm Dumbbell Deadlift" [:weight]]
DOUBLE_KB_DEADLIFT : DOUBLE SEP KB DEADLIFT ; // ["Double Kettlebell Deadlift" [:weight]]
DOUBLE_DB_DEADLIFT : DOUBLE SEP DB DEADLIFT ; // ["Double Dumbbell Deadlift" [:weight]]
KB_SUMO_DEADLIFT : KB SEP SUMO_DEADLIFT ; // ["Kettlebell Sumo Deadlift" [:weight]]
KB_SUMO_DEADLIFT_HIGH_PULL : KB SEP S U M O SEP DEADLIFT SEP HIGH_PULL ; // ["Kettlebell Sumo Deadlift High Pull" [:weight]]
STIFF_LEGGED_DEADLIFT : S T I F F SEP L E G G E D SEP DEADLIFT ; // ["Stiff Legged Deadlift" [:weight]]
SUMO_DEADLIFT_HIGH_PULL : SUMO_DEADLIFT SEP HIGH_PULL ; // ["Sumo Deadlift High Pull" [:weight]]
SUMO_DEADLIFT : S U M O SEP DEADLIFT ; // ["Sumo Deadlift" [:weight]]

STRICT_RING_DIP : STRICT SEP RING_DIP ; // ["Strict Ring Dip" [nil]]
RING_DIP : RING SEP DIP ; // ["Ring Dip" [nil]]

HANDSTAND_WALK : H A N D S T A N D SEP WALK ; // ["Handstand Walk" [:distance]]

DEFICIT_HSPU : D E F I C I T SEP HSPU ; // ["Deficit Handstand Push-up" [nil :distance]]
KIPPING_HSPU : KIPPING SEP HSPU ; // ["Kipping Handstand Push-up" [nil]]
STRICT_HSPU : STRICT SEP HSPU ; // ["Strict Handstand Push-up" [nil]]
RING_HSPU : RING SEP HSPU ; // ["Ring Handstand Push-up" [nil]]
PARALLETTE_HSPU : P A R A L L E T T E SEP HSPU ; // ["Parallette Handstand Push-up" [nil]]
HSPU : ( H A N D SEP? S T A N D SEP PUSH_UP | H S P U ) ; // ["Handstand Push-up" [nil]]

SINGLE_ARM_KB_JERK : ( SINGLE_or_ONE_ARM SEP )? KB SEP JERK ; // ["Kettlebell Jerk" [:weight]]
SINGLE_ARM_DB_JERK : ( SINGLE_or_ONE_ARM SEP )? DB SEP JERK ; // ["Dumbbell Jerk" [:weight]]
DOUBLE_KB_JERK : DOUBLE SEP KB SEP JERK ; // ["Double Kettlebell Jerk" [:weight]]
DOUBLE_DB_JERK : DOUBLE SEP DB SEP JERK ; // ["Double Dumbbell Jerk" [:weight]]
SINGLE_ARM_KB_PUSH_JERK : ( SINGLE_or_ONE_ARM SEP )? KB SEP PUSH_JERK ; // ["Kettlebell Push-up" [:weight]]
SINGLE_ARM_DB_PUSH_JERK : ( SINGLE_or_ONE_ARM SEP )? DB SEP PUSH_JERK ; // ["Dumbbell Push-up" [:weight]]
DOUBLE_KB_PUSH_JERK : DOUBLE SEP KB SEP PUSH_JERK ; // ["Double Kettlebell Push Jerk" [:weight]]
DOUBLE_DB_PUSH_JERK : DOUBLE SEP DB SEP PUSH_JERK ; // ["Double Dumbbell Push Jerk" [:weight]]
PUSH_JERK : PUSH SEP JERK ; // ["Push Jerk" [:weight]]
SPLIT_JERK : S P L I T SEP JERK ; // ["Split Jerk" [:weight]]
SQUAT_JERK : SQUAT SEP JERK ; // ["Squat Jerk" [:weight]]

SINGLE_UNDER : S I N G L E SEP UNDER ; // ["Single Under" [nil]]
DOUBLE_UNDER : ( D O U B L E SEP UNDER | D U ) ; // ["Double Under" [nil]]
TRIPLE_UNDER : T R I P L E SEP UNDER ; // ["Triple Under" [nil]]

SINGLE_ARM_KB_SWING : ( SINGLE_or_ONE_ARM SEP )? AMERICAN_KB_SWING ; // ["Kettlebell Swing" [:weight]]
DOUBLE_KB_SWING : DOUBLE SEP AMERICAN_KB_SWING ; // ["Double Kettlebell Swing" [:weight]]
SINGLE_ARM_RUSSIAN_KB_SWING : ( SINGLE_or_ONE_ARM SEP )? RUSSIAN SEP KB SEP SWING ; // ["Russian Kettlebell Sing" [:weight]]
DOUBLE_RUSSIAN_KB_SWING : DOUBLE SEP RUSSIAN SEP KB SEP SWING ; // ["Double Russian Swing" [:weight]]
RUSSIAN_KB_SWING : RUSSIAN SEP KB SEP SWING ; // ["Russian Kettlebell Swing" [:weight]]

SINGLE_ARM_KB_LUNGE : ( SINGLE_or_ONE_ARM SEP )? KB SEP LUNGE ; // ["Kettlebell Lunge" [:weight]]
SINGLE_ARM_DB_LUNGE : ( SINGLE_or_ONE_ARM SEP )? DB SEP LUNGE ; // ["Dumbbell Lunge" [:weight]]
SINGLE_ARM_KB_WALKING_LUNGE : ( SINGLE_or_ONE_ARM SEP )? KB SEP WALKING SEP LUNGE ; // ["Kettlebell Walking Lunge" [:weight]]
SINGLE_ARM_DB_WALKING_LUNGE : ( SINGLE_or_ONE_ARM SEP )? DB SEP WALKING SEP LUNGE ; // ["Dumbell Walking Lunge" [:weight]]
DOUBLE_KB_LUNGE : DOUBLE SEP KB SEP LUNGE ; // ["Double Kettlebell Lunge" [:weight]]
DOUBLE_DB_LUNGE : DOUBLE SEP DB SEP LUNGE ; // ["Double Dumbell Lunge" [:weight]]
DOUBLE_KB_WALKING_LUNGE : DOUBLE SEP KB SEP WALKING SEP LUNGE ; // ["Double Kettlebell Walking Lunge" [:weight]]
DOUBLE_DB_WALKING_LUNGE : DOUBLE SEP DB SEP WALKING SEP LUNGE ; // ["Double Dumbell Walking Lunge" [:weight]]
SINGLE_ARM_OVERHEAD_KB_LUNGE : ( SINGLE_or_ONE_ARM SEP )? OVERHEAD SEP KB SEP LUNGE ; // ["Overhead Kettlebell Lunge" [:weight]]
SINGLE_ARM_OVERHEAD_DB_LUNGE : ( SINGLE_or_ONE_ARM SEP )? OVERHEAD SEP DB SEP LUNGE ; // ["Overhead Dumbbell Lunge" [:weight]]
SINGLE_ARM_KB_OVERHEAD_WALKING_LUNGE : ( SINGLE_or_ONE_ARM SEP )? KB SEP OVERHEAD SEP WALKING SEP LUNGE ; // ["Kettlebell Overhead Walking Lunge" [:weight]]
SINGLE_ARM_DB_OVERHEAD_WALKING_LUNGE : ( SINGLE_or_ONE_ARM SEP )? DB SEP OVERHEAD SEP WALKING SEP LUNGE ; // ["Dumbbell Overhead Walking Lunge" [:weight]]
DOUBLE_KB_OVERHEAD_LUNGE : DOUBLE SEP KB SEP OVERHEAD SEP LUNGE ; // ["Double Kettlebell Overhead Lunge" [:weight]]
DOUBLE_DB_OVERHEAD_LUNGE : DOUBLE SEP DB SEP OVERHEAD SEP LUNGE ; // ["Double Dumbbell Overhead Lunge" [:weight]]
DOUBLE_KB_OVERHEAD_WALKING_LUNGE : DOUBLE SEP KB SEP OVERHEAD SEP WALKING SEP LUNGE ; // ["Double Kettlebell Overhead Walking Lunge" [:weight]]
DOUBLE_DB_OVERHEAD_WALKING_LUNGE : DOUBLE SEP DB SEP OVERHEAD SEP WALKING SEP LUNGE ; // ["Double Kettlebell Dumbbell Walking Lunge" [:weight]]
FRONT_RACK_LUNGE : FRONT SEP RACK SEP LUNGE ; // ["Front Rack Lunge" [:weight]]
JUMPING_LUNGE : JUMPING SEP LUNGE ; // ["Jumping Lunge" [:weight]]

BAR_MUSCLE_UP : BAR SEP MUSCLE_UP ; // ["Bar Muscle-up" [nil]]
RING_MUSCLE_UP : RING SEP MUSCLE_UP ; // ["Ring Muscle-up" [nil]]
STRICT_MUSCLE_UP : STRICT SEP MUSCLE_UP ; // ["Strict Muscle-up" [nil]]
MUSCLE_UP : MUSCLE SEP UP ; // ["Muscle-up" [nil]]

SINGLE_KB_STRICT_PRESS : ( SINGLE_or_ONE_ARM SEP )? KB SEP STRICT_or_SHOULDER PRESS ; // ["Kettlebell Strict Press" [:weight]]
SINGLE_KB_PRESS : ( SINGLE_or_ONE_ARM SEP )? KB SEP PRESS ; // ["Single Kettlebell Press" [:weight]]
SINGLE_KB_PUSH_PRESS : ( SINGLE_or_ONE_ARM SEP )? KB SEP PUSH SEP PRESS ; // ["Single Kettlebell Push Press" [:weight]]
SINGLE_KB_SHOULDER_TO_OVERHEAD : ( SINGLE_or_ONE_ARM SEP )? KB SEP SHOULDER_TO_OVERHEAD ; // ["Kettlebell Shoulder to Overhead" [:weight]]
DOUBLE_KB_SHOULDER_PRESS : DOUBLE SEP KB SEP STRICT_or_SHOULDER SEP PRESS ; // ["Double Kettlebell Shoulder Press" [:weight]]
DOUBLE_KB_PRESS : DOUBLE SEP KB SEP PRESS ; // ["Double Kettlebell Press" [:weight]]
DOUBLE_KB_PUSH_PRESS : DOUBLE SEP KB SEP PUSH SEP PRESS ; // ["Double Kettlebell Push Press" [:weight]]
DOUBLE_KB_SHOULDER_TO_OVERHEAD : DOUBLE SEP KB SEP SHOULDER_TO_OVERHEAD ; // ["Double Kettlebell Shoulder to Overhead" [:weight]]
KIPPING_CHEST_TO_BAR_PULL_UP : KIPPING SEP CHEST_TO_BAR ( SEP PULL_UP )? ; // ["Kipping Chest to Bar" [nil]]
KIPPING_PULL_UP : KIPPING SEP PULL_UP ; // ["Kipping Pull-up" [nil]]
CHIN_UP : C H I N SEP UP ; // ["Chin-up" [nil]]
JUMPING_CHEST_TO_BAR_PULL_UP : JUMPING SEP CHEST_TO_BAR ( SEP PULL_UP )? ; // ["Jumping Chest to Bar" [nil]]
CHEST_TO_BAR_PULL_UP : CHEST_TO_BAR ( SEP PULL_UP )? ; // ["Chest to Bar" [nil]]
JUMPING_PULL_UP : JUMPING SEP PULL_UP ; // ["Jumping Pull-up" [nil]]
PULL_UP : PULL SEP? UP ; // ["Pull-up" [nil]]
SHOULDER_TO_OVERHEAD : S H O U L D E R SEP TO SEP OVERHEAD | S T O ; // ["Shoulder to Overhead" [:weight]]

HAND_RELEASE_PUSH_UP : H A N D SEP R E L E A S E SEP PUSH_UP | H R P U ; // ["Hand-release Push-up" [nil]]
RING_PUSH_UP : RING SEP PUSH_UP ; // ["Ring Push-up" [nil]]

CRUNCH : C R U N C H ( E S )? ; // ["Crunch" [nil]]
GHD_SIT_UP : G H D SEP SIT_UP ; // ["GHD Sit-up" [nil]]
LEG_RAISE : LEG SEP R A I S E S? ; // ["Leg Raise" [nil]]
V_UP : V SEP UP ; // ["V-up" [nil]]
WEIGHTED_SIT_UP : WEIGHTED SEP SIT_UP ; // ["Weighted Sit-up" [:weight]]
SIT_UP : S I T SEP UP ; // ["Sit-up" [nil]]

// Snatch
SINGLE_KB_SNATCH : ( SINGLE_or_ONE_ARM SEP )? KB SEP SNATCH ; // ["Kettlebell Snatch" [:weight]]
SINGLE_KB_POWER_SNATCH : ( SINGLE_or_ONE_ARM SEP )? KB SEP POWER_SNATCH ; // ["Sing Arm Kettlebell Power Snatch" [:weight]]
SINGLE_KB_SQUAT_SNATCH : ( SINGLE_or_ONE_ARM SEP )? KB SEP SQUAT_SNATCH ; // ["Sing Arm Dumbbell Power Snatch" [:weight]]
SINGLE_KB_HANG_SNATCH : ( SINGLE_or_ONE_ARM SEP )? KB SEP HANG_SNATCH ; // ["Kettlebell Hang Snatch" [:weight]]
SINGLE_KB_HANG_POWER_SNATCH : ( SINGLE_or_ONE_ARM SEP )? KB SEP HANG_POWER_SNATCH ; // ["Kettlebell Hang Power Snatch" [:weight]]
SINGLE_KB_HANG_SQUAT_SNATCH : ( SINGLE_or_ONE_ARM SEP )? KB SEP HANG_SQUAT_SNATCH ; // ["Kettlebell Hang Squat Snatch" [:weight]]
SINGLE_DB_SNATCH : ( SINGLE_or_ONE_ARM SEP )? DB SEP SNATCH ; // ["Dumbbell Snatch" [:weight]]
SINGLE_DB_POWER_SNATCH : ( SINGLE_or_ONE_ARM SEP )? DB SEP POWER_SNATCH ; // ["Dumbbell Squat Snatch" [:weight]]
SINGLE_DB_SQUAT_SNATCH : ( SINGLE_or_ONE_ARM SEP )? DB SEP SQUAT_SNATCH ; // ["Dumbbell Power Snatch" [:weight]]
SINGLE_DB_HANG_SNATCH : ( SINGLE_or_ONE_ARM SEP )? DB SEP HANG_SNATCH ; // ["Dumbbell Hang Snatch" [:weight]]
SINGLE_DB_HANG_POWER_SNATCH : ( SINGLE_or_ONE_ARM SEP )? DB SEP HANG_POWER_SNATCH ; // ["Dumbbell Hang Power Snatch" [:weight]]
SINGLE_DB_HANG_SQUAT_SNATCH : ( SINGLE_or_ONE_ARM SEP )? DB SEP HANG_SQUAT_SNATCH ; // ["Dumbbell Hang Squat Snatch" [:weight]]
DOUBLE_KB_SNATCH : DOUBLE SEP KB SEP SNATCH ; // ["Double Kettlebell Snatch" [:weight]]
DOUBLE_KB_POWER_SNATCH : DOUBLE SEP KB SEP POWER_SNATCH ; // ["Double Kettlebell Power Snatch" [:weight]]
DOUBLE_KB_SQUAT_SNATCH : DOUBLE SEP KB SEP SQUAT_SNATCH ; // ["Double Kettlebell Squat Snatch" [:weight]]
DOUBLE_KB_HANG_SNATCH : DOUBLE SEP KB SEP HANG_SNATCH ; // ["Double Kettlebell Hang Snatch" [:weight]]
DOUBLE_KB_HANG_POWER_SNATCH : DOUBLE SEP KB SEP HANG_POWER_SNATCH ; // ["Double Kettlebell Hang Power Snatch" [:weight]]
DOUBLE_KB_HANG_SQUAT_SNATCH : DOUBLE SEP KB SEP HANG_SQUAT_SNATCH ; // ["Double Kettlebell Hang Squat Snatch" [:weight]]
DOUBLE_DB_SNATCH : DOUBLE SEP DB SEP SNATCH ; // ["Double Dumbbell Snatch" [:weight]]
DOUBLE_DB_POWER_SNATCH : DOUBLE SEP DB SEP POWER_SNATCH ; // ["Double Dumbbell Power Snatch" [:weight]]
DOUBLE_DB_SQUAT_SNATCH : DOUBLE SEP DB SEP SQUAT_SNATCH ; // ["Double Dumbbell Squat Snatch" [:weight]]
DOUBLE_DB_HANG_SNATCH : DOUBLE SEP DB SEP HANG_SNATCH ; // ["Double Dumbbell Hang Snatch" [:weight]]
DOUBLE_DB_HANG_POWER_SNATCH : DOUBLE SEP DB SEP HANG_POWER_SNATCH ; // ["Double Dumbbell Hang Power Snatch" [:weight]]
DOUBLE_DB_HANG_SQUAT_SNATCH : DOUBLE SEP DB SEP HANG_SQUAT_SNATCH ; // ["Double Dumbbell Hang Squat Snatch" [:weight]]

MUSCLE_SNATCH : MUSCLE SEP SNATCH ; // ["Muscle Snatch" [:weight]]
SNATCH_BALANCE : SNATCH SEP B A L A N C E ; // ["Snatch Balance" [:weight]]
SNATCH_PULL : SNATCH SEP PULL ; // ["Snatch Pull" [:weight]]

POWER_SNATCH : P O W E R SEP SNATCH ; // ["Power Snatch" [:weight]]
SQUAT_SNATCH : SQUAT SEP SNATCH ; // ["Squat Snatch" [:weight]]
HANG_SNATCH : HANG SEP SNATCH ; // ["Hang Snatch" [:weight]]
HANG_POWER_SNATCH : HANG SEP POWER SEP SNATCH ; // ["Hang Power Snatch" [:weight]]
HANG_SQUAT_SNATCH : HANG SEP SQUAT SEP SNATCH ; // ["Hang Squat Snatch" [:weight]]
SNATCH : S N A T C H S? ; // ["Snatch" [:weight]]

// Squat
SINGLE_ARM_KB_SQUAT : ( SINGLE_or_ONE_ARM SEP )? KB SEP SQUAT ; // ["Kettlebell Squat" [:weight]]
DOUBLE_KB_SQUAT : DOUBLE SEP KB SEP SQUAT ; // ["Double Kettlebell Squat" [:weight]]
SINGLE_ARM_OVERHEAD_KB_SQUAT : ( SINGLE_or_ONE_ARM SEP )? OVERHEAD SEP KB SEP SQUAT ; // ["Overhead Kettlebell Squat" [:weight]]
DOUBLE_KB_OVERHEAD_SQUAT : DOUBLE SEP KB SEP OVERHEAD_SQUAT ; // ["Double Kettlebell Overhead Squat" [:weight]]
SINGLE_ARM_DB_SQUAT : ( SINGLE_or_ONE_ARM SEP )? DB SEP SQUAT ; // ["Dumbbell Squat" [:weight]]
DOUBLE_DB_SQUAT : DOUBLE SEP DB SEP SQUAT ; // ["Double Dumbbell Squat" [:weight]]
SINGLE_ARM_OVERHEAD_DB_SQUAT : ( SINGLE_or_ONE_ARM SEP )? OVERHEAD SEP DB SEP SQUAT ; // ["Overhead Dumbbell Squat" [:weight]]
DOUBLE_DB_OVERHEAD_SQUAT : DOUBLE SEP DB SEP OVERHEAD_SQUAT ; // ["Double Dumbbell Overhead Squat" [:weight]]
GOBELET_SQUAT : G O B E L E T SEP SQUAT ; // ["Gobelet Squat" [:weight]]
AIR_SQUAT : A I R SEP SQUAT ; // ["Air Squat" [nil]]
BACK_SQUAT : B A C K SEP SQUAT ; // ["Back Squat" [:weight]]
FRONT_SQUAT : F R O N T SEP SQUAT ; // ["Front Squat" [:weight]]
PISTOL :  P I S T O L S? ( SEP SQUAT )? | ( SINGLE | ONE ) SEP LEG SQUAT  ; // ["Pistol Squat" [nil]]
SQUAT_JUMP : SQUAT SEP JUMP ; // ["Squat Jump" [:weight]]

LEG_LESS_ROPE_CLIMB : LEG SEP L E S S SEP ROPE_CLIMB; // ["Legless Rope Climb" [nil :distance]]
ROPE_CLIMB : R O P E SEP C L I M B S? ; // ["Rope Climb" [nil :distance]]

// Thruster
SINGLE_KB_THRUSTER : ( SINGLE_or_ONE_ARM SEP )? KB SEP THRUSTER ; // ["Kettlebell Thruster" [:weight]]
DOUBLE_KB_THRUSTER : DOUBLE SEP KB SEP THRUSTER ; // ["Double Kettlebell Thruster" [:weight]]
SINGLE_DB_THRUSTER : ( SINGLE_or_ONE_ARM SEP )? DB SEP THRUSTER ; // ["Dumbbell Thruster" [:weight]]
DOUBLE_DB_THRUSTER : DOUBLE SEP DB SEP THRUSTER ; // ["Double Dumbbell Thruster" [:weight]]
THRUSTER : T H R U S T E R S? ; // ["Thruster" [:weight]]

RUN : R U N ; // ["Run" [:distance]]
SHUTTLE_SPRINT : S H U T T L E SEP S P R I N T S? ; // ["Shuttle Sprint" [:distance]]

TOES_TO_BAR : ( TOES SEP TO SEP BAR | T DIGIT_2 B ) ; // ["Toes to Bar" [nil]]
TOES_TO_RING : TOES SEP TO SEP RING ; // ["Toes to Ring" [nil]]
KNEES_TO_ELBOWS : K N E E S? SEP TO SEP E L B O W S? ; // ["Knees to Elbows" [nil]]

BALL_SLAM : B A L L SEP S L A M S? ; // ["Ball Slam" [:weight]]
BATTLING_ROPES : B A T T L I N G SEP R O P E S? ; // ["Battling Ropes" [nil]]
FARMER_WALK : F A R M E R SEP WALK ; // ["Farmer Walk" [:weight]]
HOLLOW_ROCK : H O L L O W SEP R O C K S? ; // ["Hollow Rock" [nil]]
JUMPING_JACK : JUMPING SEP J A C K S? ; // ["Jumping Jack" [nil]]
MAN_MAKER : M A N SEP M A K E R S? ; // ["Man Maker" [:weight]]
MOUNTAIN_CLIMBER : M O U N T A I N SEP C L I M B E R S? ; // ["Mountain Climber" [nil]]
OVERHEAD_WALK : OVERHEAD SEP WALK ; // ["Overhead Walk" [:weight]]
PEG_BOARD : P E G SEP B O A R D ; // ["Peg Board" [nil]]
PLANK : P L A N K ; // ["Plank" [nil]]
PROWLER_PUSH : P R O W L E R SEP PUSH ; // ["Prowler Push" [:weight]]
RING_ROW : RING SEP ROW ; // ["Ring Row" [nil]]
SANDBAG_CARRY : S A N D SEP? B A G SEP CARRY ; // ["Sandbag Carry" [:weight]]
YOKE_CARRY : YOKE SEP CARRY ; // ["Yoke Carry" [:weight]]
SLED_DRAG : SLED SEP D R A G ; // ["Sled Drag" [:distance]]
SLED_PUSH : SLED SEP PUSH ; // ["Sled Push" [:distance]]
SWIM : S W I M ; // ["Swim" [:distance]]
TURKISH_GET_UP : T U R K I S H SEP G E T SEP? UP ; // ["Turkish Get-up" [:weight]]
WALL_CLIMB : W A L L SEP? C L I M B S? ; // ["Wall Climb" [nil]]

// Calories
ROW : R O W ; // ["Row" [:calories :distance]]
SKI : S K I ( SEP? E R G )? ; // ["SkiErg" [:calories :distance]]
ASSAULT_BIKE :  A S S A U L T SEP  BIKE ; // ["Assault Bike" [:calories :distance]]
AIR_BIKE :  A I R SEP  BIKE ; // ["Air Bike" [:calories :distance]]

OVERHEAD_SQUAT : OVERHEAD SEP SQUAT ; // ["Overhead Squat" [:weight]]
LUNGE : ( L U N G E S? | S P L I T SEP SQUAT ) ; // ["Lunge" [:weight]]
PUSH_UP : P U S H SEP? UP ; // ["Push-up" [nil]]
DEADLIFT : ( D E A D L I F T S? | D L ) ; // ["Deadlift" [:weight]]
JERK : J E R K S? ; // ["Jerk" [:weight]]
HANG_POWER_CLEAN_AND_JERK : HANG SEP POWER_CLEAN_AND_JERK ; // ["Hang Power Clean & Jerk" [:weight]]
POWER_CLEAN_AND_JERK : POWER SEP CLEAN_AND_JERK ; // ["Power Clean & Jerk" [:weight]]
HANG_POWER_CLEAN : HANG SEP POWER_CLEAN ; // ["Hang Power Clean" [:weight]]
SQUAT_CLEAN_AND_JERK : S Q U A T SEP CLEAN_AND_JERK ; // ["Squat Clean & Jerk" [:weight]]
CLEAN_AND_JERK : ( CLEAN | C ) SEP? AND SEP? ( JERK | J ) ; // ["Clean & Jerk" [:weight]]
SQUAT_CLEAN : S Q U A T SEP CLEAN ; // ["Squat Clean" [:weight]]
SQUAT : S Q U A T S? ; // ["Squat" [:weight]]
CLEAN : C L E A N S? ; // ["Clean" [:weight]]
DEVIL_PRESS : D E V I L SEP PRESS ; // ["Devil Press" [:weight]]
BENCH_PRESS : B E N C H SEP PRESS ; // ["Bench Press" [:weight]]
PRESS : P R E S S (E S)? ; // ["Press" [:weight]]
BURPEE : B U R P E E S? ; // ["Burpee" [nil]]
BOX_JUMP : B O X SEP JUMP ; // ["Box Jump" [:distance]]
WALL_BALL : ( M E D I C I N E | W A L L ) SEP B A L L ( SEP S H O T S? )? ; // ["Wall-ball" [:weight]]
REST :  R E S T ; // ["Rest" [:duration]]

// Order is important here
WS  : ( ' ' | '\t' )+ ; // IGNORE
SEP : ( '-' | WS ) ;    // IGNORE
