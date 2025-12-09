// Generated from /Users/luka.klacar/Fivetran/filter-expression-language/src/main/antlr4/Filter.g4 by ANTLR 4.13.2
package rs.qubit.fel.parser.generated;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link FilterParser}.
 */
public interface FilterListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code dateTimeExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterDateTimeExpression(FilterParser.DateTimeExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code dateTimeExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitDateTimeExpression(FilterParser.DateTimeExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code dotExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterDotExpression(FilterParser.DotExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code dotExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitDotExpression(FilterParser.DotExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code identifierExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierExpression(FilterParser.IdentifierExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code identifierExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierExpression(FilterParser.IdentifierExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code notExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNotExpression(FilterParser.NotExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNotExpression(FilterParser.NotExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parenExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterParenExpression(FilterParser.ParenExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parenExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitParenExpression(FilterParser.ParenExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code greaterThanExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterGreaterThanExpression(FilterParser.GreaterThanExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code greaterThanExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitGreaterThanExpression(FilterParser.GreaterThanExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code lessThanOrEqualsExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLessThanOrEqualsExpression(FilterParser.LessThanOrEqualsExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code lessThanOrEqualsExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLessThanOrEqualsExpression(FilterParser.LessThanOrEqualsExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code booleanExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBooleanExpression(FilterParser.BooleanExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code booleanExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBooleanExpression(FilterParser.BooleanExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code orExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterOrExpression(FilterParser.OrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code orExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitOrExpression(FilterParser.OrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code greaterThanOrEqualsExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterGreaterThanOrEqualsExpression(FilterParser.GreaterThanOrEqualsExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code greaterThanOrEqualsExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitGreaterThanOrEqualsExpression(FilterParser.GreaterThanOrEqualsExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code notEqualsExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNotEqualsExpression(FilterParser.NotEqualsExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notEqualsExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNotEqualsExpression(FilterParser.NotEqualsExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAndExpression(FilterParser.AndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAndExpression(FilterParser.AndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stringExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterStringExpression(FilterParser.StringExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stringExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitStringExpression(FilterParser.StringExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code longExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLongExpression(FilterParser.LongExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code longExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLongExpression(FilterParser.LongExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code equalsExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterEqualsExpression(FilterParser.EqualsExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code equalsExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitEqualsExpression(FilterParser.EqualsExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code doubleExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterDoubleExpression(FilterParser.DoubleExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code doubleExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitDoubleExpression(FilterParser.DoubleExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nullExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNullExpression(FilterParser.NullExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nullExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNullExpression(FilterParser.NullExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code lessThanExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLessThanExpression(FilterParser.LessThanExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code lessThanExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLessThanExpression(FilterParser.LessThanExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code functionCallExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCallExpression(FilterParser.FunctionCallExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code functionCallExpression}
	 * labeled alternative in {@link FilterParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCallExpression(FilterParser.FunctionCallExpressionContext ctx);
}