package rs.qubit.fel;


import rs.qubit.fel.evaluator.DefaultEvaluationContext;
import rs.qubit.fel.jit.JitCompiler;
import rs.qubit.fel.visitor.VisitorContext;
import rs.qubit.fel.evaluator.FilterEvaluator;
import rs.qubit.fel.parser.FilterParser;
import rs.qubit.fel.parser.ast.ExpressionNode;
import rs.qubit.fel.typecheck.TypeCheckContext;
import rs.qubit.fel.typecheck.TypeCheckerVisitor;

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

    public static <T> FelPredicate filter(String filter, Class<T> inputType) {
        return filter(filter, new DefaultEvaluationContext(), inputType);
    }

    public static <T> FelPredicate filter(String filter, VisitorContext evaluationContext, Class<T> inputType) {
        var parser = new FilterParser();
        var filterAst = parser.parse(filter);
        typeCheck(filterAst, evaluationContext, inputType);
        return fromAst(filterAst, evaluationContext);
    }

    public static <T> FelPredicate fromAst(ExpressionNode filterAst, VisitorContext evaluationContext, Class<T> inputType) {
        typeCheck(filterAst, evaluationContext, inputType);
        return fromAst(filterAst, evaluationContext);
    }

    /**
     * Creates a JIT-compiled filter predicate from a filter expression string.
     * JIT compilation generates optimized JVM bytecode for maximum performance.
     *
     * @param filter the filter expression string
     * @return a JIT-compiled FelPredicate
     */
    public static FelPredicate filterJit(String filter) {
        return filterJit(filter, new DefaultEvaluationContext(), null);
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
        return filterJit(filter, evaluationContext, null);
    }

    /**
     * Creates a JIT-compiled filter predicate from a filter expression string
     * with type information for optimized field access.
     *
     * @param filter the filter expression string
     * @param inputType the class type of objects to filter (enables direct field access)
     * @param <T> the type parameter
     * @return a JIT-compiled FelPredicate
     */
    public static <T> FelPredicate filterJit(String filter, Class<T> inputType) {
        return filterJit(filter, new DefaultEvaluationContext(), inputType);
    }

    /**
     * Creates a JIT-compiled filter predicate from a filter expression string
     * with a custom evaluation context and type information.
     *
     * @param filter the filter expression string
     * @param evaluationContext the evaluation context with custom functions/mappers
     * @param inputType the class type of objects to filter (enables direct field access)
     * @param <T> the type parameter
     * @return a JIT-compiled FelPredicate
     */
    public static <T> FelPredicate filterJit(String filter, VisitorContext evaluationContext, Class<T> inputType) {
        var parser = new FilterParser();
        var filterAst = parser.parse(filter);
        typeCheck(filterAst, evaluationContext, inputType);
        return fromAstJit(filterAst, evaluationContext, inputType);
    }

    /**
     * Creates a JIT-compiled filter predicate from an AST.
     * JIT compilation generates optimized JVM bytecode for maximum performance.
     *
     * @param filterAst the expression AST
     * @return a JIT-compiled FelPredicate
     */
    public static FelPredicate fromAstJit(ExpressionNode filterAst) {
        return fromAstJit(filterAst, new DefaultEvaluationContext(), null);
    }

    /**
     * Creates a JIT-compiled filter predicate from an AST with a custom evaluation context.
     *
     * @param filterAst the expression AST
     * @param evaluationContext the evaluation context with custom functions/mappers
     * @return a JIT-compiled FelPredicate
     */
    public static FelPredicate fromAstJit(ExpressionNode filterAst, VisitorContext evaluationContext) {
        return fromAstJit(filterAst, evaluationContext, null);
    }

    /**
     * Creates a JIT-compiled filter predicate from an AST with type information.
     *
     * @param filterAst the expression AST
     * @param inputType the class type of objects to filter (enables direct field access)
     * @param <T> the type parameter
     * @return a JIT-compiled FelPredicate
     */
    public static <T> FelPredicate fromAstJit(ExpressionNode filterAst, Class<T> inputType) {
        return fromAstJit(filterAst, new DefaultEvaluationContext(), inputType);
    }

    /**
     * Creates a JIT-compiled filter predicate from an AST with a custom evaluation context and type information.
     *
     * @param filterAst the expression AST
     * @param evaluationContext the evaluation context with custom functions/mappers
     * @param inputType the class type of objects to filter (enables direct field access, can be null)
     * @param <T> the type parameter
     * @return a JIT-compiled FelPredicate
     */
    public static <T> FelPredicate fromAstJit(ExpressionNode filterAst, VisitorContext evaluationContext, Class<T> inputType) {
        typeCheck(filterAst, evaluationContext, inputType);
        var jitCompiler = new JitCompiler();
        var compiledPredicate = jitCompiler.compile(filterAst, evaluationContext, inputType);

        return new FelPredicate(evaluationContext, filterAst) {
            @Override
            public boolean test(Object o) {
                return compiledPredicate.test(o);
            }
        };
    }

    private static <T> void typeCheck(ExpressionNode filterAst, VisitorContext evaluationContext, Class<T> inputType) {
        if (inputType == null) {
            return;
        }
        var typeChecker = new TypeCheckerVisitor();
        typeChecker.check(filterAst, new TypeCheckContext(inputType, evaluationContext));
    }
}
