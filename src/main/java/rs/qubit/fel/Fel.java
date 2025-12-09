package rs.qubit.fel;


import rs.qubit.fel.evaluator.DefaultEvaluationContext;
import rs.qubit.fel.jit.JitCompiler;
import rs.qubit.fel.visitor.VisitorContext;
import rs.qubit.fel.evaluator.FilterEvaluator;
import rs.qubit.fel.parser.FilterParser;
import rs.qubit.fel.parser.ast.ExpressionNode;

import java.util.function.Predicate;

public class Fel {

    private Fel() {
        throw new IllegalStateException("Utility class");
    }

    public static FelPredicate filter(String filter) {
        return filter(filter, new DefaultEvaluationContext());
    }

    public static FelPredicate fromAst(ExpressionNode filterAst) {
        return fromAst(filterAst, new DefaultEvaluationContext());
    }

    public static FelPredicate filter(String filter, VisitorContext evaluationContext) {
        var parser = new FilterParser();
        var filterAst = parser.parse(filter);
        return fromAst(filterAst, evaluationContext);
    }

    public static FelPredicate fromAst(ExpressionNode filterAst, VisitorContext evaluationContext) {
        var evaluator = new FilterEvaluator();

        return new FelPredicate(evaluationContext, filterAst) {
            @Override
            public boolean test(Object o) {
                return filterAst.accept(evaluator, evaluationContext, o).asBoolean();
            }
        };
    }

    /**
     * Creates a JIT-compiled filter predicate from a filter expression string.
     * JIT compilation generates optimized JVM bytecode for maximum performance.
     *
     * @param filter the filter expression string
     * @return a JIT-compiled FelPredicate
     */
    public static FelPredicate filterJit(String filter) {
        return filterJit(filter, new DefaultEvaluationContext());
    }

    /**
     * Creates a JIT-compiled filter predicate from a filter expression string
     * with a custom evaluation context.
     *
     * @param filter the filter expression string
     * @param evaluationContext the evaluation context with custom functions/mappers
     * @return a JIT-compiled FelPredicate
     */
    public static FelPredicate filterJit(String filter, VisitorContext evaluationContext) {
        var parser = new FilterParser();
        var filterAst = parser.parse(filter);
        return fromAstJit(filterAst, evaluationContext);
    }

    /**
     * Creates a JIT-compiled filter predicate from an AST.
     * JIT compilation generates optimized JVM bytecode for maximum performance.
     *
     * @param filterAst the expression AST
     * @return a JIT-compiled FelPredicate
     */
    public static FelPredicate fromAstJit(ExpressionNode filterAst) {
        return fromAstJit(filterAst, new DefaultEvaluationContext());
    }

    /**
     * Creates a JIT-compiled filter predicate from an AST with a custom evaluation context.
     *
     * @param filterAst the expression AST
     * @param evaluationContext the evaluation context with custom functions/mappers
     * @return a JIT-compiled FelPredicate
     */
    public static FelPredicate fromAstJit(ExpressionNode filterAst, VisitorContext evaluationContext) {
        var jitCompiler = new JitCompiler();
        var compiledPredicate = jitCompiler.compile(filterAst, evaluationContext);

        return new FelPredicate(evaluationContext, filterAst) {
            @Override
            public boolean test(Object o) {
                return compiledPredicate.test(o);
            }
        };
    }
}
