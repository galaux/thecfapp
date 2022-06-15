grammar Workout ;

import MovementLexer , MovementParser ;

/*
 * Parser Rules
 */

/* Most examples with Antlr deal with programing languages while I am dealing
 * with a natual language (ambiguous) so there might be areas where I won't be
 * able to follow Antlr advices.
 * For instance, Antlr says:
 * > usually it is considered a good idea to only use semantic predicates when
 * > they cannot be avoided, and leave most of the code to the visitor/listener.
 * In my case, I try to allow rules AMRAP and WORKOUT_NAME to coexist but
 * "amrap " (notice the space at the end) is often wrongly identified as a
 * WORKOUT_NAME because this rule matches more characters than just AMRAP!
 * If I followed the previous advice, I should maybe try to identify a WORD
 * and leave it to the listener/visitor to differenciate but I would end-up
 * with just identifying a list of WORDs and thus completely loosing the
 * usefulness of Antlr.
 * I think a middle-ground where I try to do as much as possible in the Lexer
 * and parser with _tricks_ such as requiring double-quotes around workout
 * names should make it. 

After some testing, I feel like I'm balancing between 2 extremes:

- (my initial solution) having as few lexer rules as possible but very generic
  (ie, having INT, WORD, …) and then many parser rules being articulations of
  lexer rules.
  - pro: visitor is rather simple ; ok there are many visitor functions to
    implement but I don't have to add any other re-find on top because Antlr
    has already identified many precise tokens (INT, …)
  - con: more parser rules and as the parser gets more complicated, I might
    break some use cases I previously had (for instance allowing "2" in
    movement names messed-up the lexer rule for FOR_TIME_SCHEME when scheme has
    "2" in it – Lexer then considers "-2-" is a WORD)
- new solution tested tonight which is the other extreme: have many more lexer
  rules such as DURATION which identifies "1 minute", WORKOUT_NAME
  - pro: less mixes when tokenizing (ie, "no WORD for "-2-" when it actually is
    a FOR_TIME_SCHEME
  - con: forces me to have supplementary `(re-find #"[0-9]+" (.getText duration)`
    for nearly every lexer rule

Good solution looks to be:

- go the first solution: as few lexer rules as possible so use INT, WORD etc
  so that visitor is powerfull and simple (no supplementary re-find) but when
  a modiication in the grammar introduces a tokenizing problem, then create
  a specific lexer rule to properly differenciate cases and use a re-find for
  said case (just like I already do for FOR_TIME_SCHEME where I extract values
  from "1-2-3-4"). */

fragment A : ('A'|'a') ;
fragment B : ('B'|'b') ;
fragment C : ('C'|'c') ;
fragment D : ('D'|'d') ;
fragment E : ('E'|'e') ;
fragment F : ('F'|'f') ;
fragment G : ('G'|'g') ;
fragment H : ('H'|'h') ;
fragment I : ('I'|'i') ;
fragment J : ('J'|'j') ;
fragment K : ('K'|'k') ;
fragment L : ('L'|'l') ;
fragment M : ('M'|'m') ;
fragment N : ('N'|'n') ;
fragment O : ('O'|'o') ;
fragment P : ('P'|'p') ;
fragment Q : ('Q'|'q') ;
fragment R : ('R'|'r') ;
fragment S : ('S'|'s') ;
fragment T : ('T'|'t') ;
fragment U : ('U'|'u') ;
fragment V : ('V'|'v') ;
fragment W : ('W'|'w') ;
fragment X : ('X'|'x') ;
fragment Y : ('Y'|'y') ;
fragment Z : ('Z'|'z') ;


workoutName : DOUBLE_QUOTE WORD ( WS WORD )* DOUBLE_QUOTE ;

roundCount : NUMBER WS ROUNDS ;
duration : NUMBER WS? MINUTE ;
timeCap :  TIME_CAP WS duration ;
// TODO add feature to specify `period` in seconds or minutes with comma
period : NUMBER WS? MINUTE ;
repCount : NUMBER ;

headerForTime
  :                           ( FOR_TIME | roundCount | FOR_TIME_SCHEME | ( PAREN_OPEN? timeCap PAREN_CLOSE? ) )
    ( ( WS | ( COMMA WS? ) )  ( FOR_TIME | roundCount | FOR_TIME_SCHEME | ( PAREN_OPEN? timeCap PAREN_CLOSE? ) ) )*
  ;

// TODO support word "in". For instance "AMRAP in 20'"
headerAMRAP : AMRAP WS duration ;
headerEMOM :  EMOM WS duration  | EVERY WS period WS FOR WS duration  ;
header  : ( headerAMRAP | headerForTime | headerEMOM ) ;

applicationValue : NUMBER ;
applicationUnit : METER | MINUTE | KILOGRAM ;

application
  : // compulsory paren version
    PAREN_OPEN
    ( AT_SIGN WS? )?
    applicationValue WS? applicationUnit? ( '/' applicationValue WS? applicationUnit? )?
    PAREN_CLOSE
  | // compulsory '@' version
    PAREN_OPEN?
    AT_SIGN WS?
    applicationValue WS? applicationUnit? ( '/' applicationValue WS? applicationUnit? )?
    PAREN_CLOSE?
  | // compulsory "men or all" applicationUnit
    PAREN_OPEN?
    ( AT_SIGN WS? )?
    applicationValue WS? applicationUnit  ( '/' applicationValue WS? applicationUnit? )?
    PAREN_CLOSE?
  | // compulsory "women" applicationUnit
    PAREN_OPEN?
    ( AT_SIGN WS? )?
    applicationValue WS? applicationUnit?   '/' applicationValue WS? applicationUnit
    PAREN_CLOSE?
  ;

exercise
  : ( DASH WS? )? ( repCount WS )?                movement ( WS application )?
  | ( DASH WS? )? ( repCount WS )? application WS movement
  ;

flatLoop :  header ( NEWLINE exercise )+ ;

/* We need this parentLoopNextTask parser rule so that in the visitor, we can
   access the parentLoop.exercise first and then only the next tasks.
   Note: we could actually use an AntLR label */
parentLoopNextTask : ( NEWLINE? exercise ) | ( NEWLINE flatLoop )  ;
parentLoop : (header NEWLINE NEWLINE)? ( exercise | flatLoop ) ( NEWLINE parentLoopNextTask )* ;
workout : ( workoutName NEWLINE NEWLINE? )? ( flatLoop | parentLoop ) NEWLINE* EOF ;

/* ignore              : .*? NEWLINE ; */

/*
 * Lexer Rules
 */
fragment DIGIT : [0-9] ;

fragment LOWERCASE  : [a-z] ;
fragment UPPERCASE  : [A-Z] ;

fragment DOT : '.' ;

/* Important to understand: it seems that a lexer rule should not be seen as a
   sort of factorization for another lexer rule. SLASH, DASH, AT_SIGN and COMMA
   are all required here because they are used in a paser rule. But "&" for
   instance, appers in lexer rule WORD but should probably not be its own lexer
   rule as it is not used in any parser rule. Declaring a lexer rule for it
   because it would be used in lexer rule WORD – as some sort of factorization
   serves no purpose and seems to trick Antlr into thinking we are interested
   in knowing when it found an "&" while we are not! We are only interested in
   knowing when it found a WORD!
   It seems in this case, AMPERSAND should/could actually be a `fragment`. */

AT_SIGN : '@' ;
DASH : '-' ;
PAREN_OPEN : '(' ;
PAREN_CLOSE : ')' ;
DOUBLE_QUOTE : '"' ;
COMMA : ',' ;

NUMBER : DIGIT+  ( ( DOT | COMMA ) DIGIT+ )?  ;

FOR : F O R ;
ROUNDS : R O U N D S? ;

AMRAP : A M R A P ;
FOR_TIME : F O R WS T I M E ;
EMOM : E M O M ;
EVERY : E V E R Y ;
TIME_CAP : T I M E WS? C A P | T C ;

METER : M ; // TODO Support "meter"?
MINUTE : ( M I N U T E S? | '\'' | '’' )  ; // TODO change '\''
KILOGRAM : K G ;

/* We need this FOR_TIME_SCHEME lexer rule so that the lexer doesn't take
   "-2-" from "1-2-3-4" for a WORD */
FOR_TIME_SCHEME : ( NUMBER DASH )+ NUMBER ;

NEWLINE             : ( '\r'? '\n' | '\r' ) ;

/* Should I try to handle lexer errors?
   If answer is yes, then there seems to be two ways to handle them:
   - with an error listener
     - https://stackoverflow.com/questions/18132078/handling-errors-in-antlr4
     - https://groups.google.com/g/antlr-discussion/c/FfiwtHCrgc0
   - with a lexer rules as the one under
    - https://tomassetti.me/antlr-mega-tutorial/#chapter56
    - https://stackoverflow.com/questions/18782388/antlr4-lexer-error-reporting-length-of-offending-characters
*/
// ERROR_CHAR : . ; // TODO I should try to check this is empty after parsing?

WS          : ( ' ' | '\t' )+ ;
SEP : ( '-' | WS ) ;
