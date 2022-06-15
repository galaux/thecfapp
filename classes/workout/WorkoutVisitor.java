// Generated from env/dev/resources/grammars/Workout.g4 by ANTLR 4.9.3
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link WorkoutParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface WorkoutVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link WorkoutParser#workoutName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWorkoutName(WorkoutParser.WorkoutNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link WorkoutParser#roundCount}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRoundCount(WorkoutParser.RoundCountContext ctx);
	/**
	 * Visit a parse tree produced by {@link WorkoutParser#duration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDuration(WorkoutParser.DurationContext ctx);
	/**
	 * Visit a parse tree produced by {@link WorkoutParser#timeCap}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTimeCap(WorkoutParser.TimeCapContext ctx);
	/**
	 * Visit a parse tree produced by {@link WorkoutParser#period}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPeriod(WorkoutParser.PeriodContext ctx);
	/**
	 * Visit a parse tree produced by {@link WorkoutParser#repCount}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRepCount(WorkoutParser.RepCountContext ctx);
	/**
	 * Visit a parse tree produced by {@link WorkoutParser#headerForTime}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHeaderForTime(WorkoutParser.HeaderForTimeContext ctx);
	/**
	 * Visit a parse tree produced by {@link WorkoutParser#headerAMRAP}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHeaderAMRAP(WorkoutParser.HeaderAMRAPContext ctx);
	/**
	 * Visit a parse tree produced by {@link WorkoutParser#headerEMOM}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHeaderEMOM(WorkoutParser.HeaderEMOMContext ctx);
	/**
	 * Visit a parse tree produced by {@link WorkoutParser#header}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHeader(WorkoutParser.HeaderContext ctx);
	/**
	 * Visit a parse tree produced by {@link WorkoutParser#applicationValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitApplicationValue(WorkoutParser.ApplicationValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link WorkoutParser#applicationUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitApplicationUnit(WorkoutParser.ApplicationUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link WorkoutParser#application}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitApplication(WorkoutParser.ApplicationContext ctx);
	/**
	 * Visit a parse tree produced by {@link WorkoutParser#exercise}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExercise(WorkoutParser.ExerciseContext ctx);
	/**
	 * Visit a parse tree produced by {@link WorkoutParser#flatLoop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFlatLoop(WorkoutParser.FlatLoopContext ctx);
	/**
	 * Visit a parse tree produced by {@link WorkoutParser#parentLoopNextTask}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParentLoopNextTask(WorkoutParser.ParentLoopNextTaskContext ctx);
	/**
	 * Visit a parse tree produced by {@link WorkoutParser#parentLoop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParentLoop(WorkoutParser.ParentLoopContext ctx);
	/**
	 * Visit a parse tree produced by {@link WorkoutParser#workout}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWorkout(WorkoutParser.WorkoutContext ctx);
	/**
	 * Visit a parse tree produced by {@link WorkoutParser#movement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMovement(WorkoutParser.MovementContext ctx);
}