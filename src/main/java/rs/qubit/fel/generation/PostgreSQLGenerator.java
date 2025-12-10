package rs.qubit.fel.generation;

import rs.qubit.fel.exception.FilterException;
import rs.qubit.fel.parser.ast.*;
import rs.qubit.fel.visitor.ExpressionVisitor;

public class PostgreSQLGenerator implements ExpressionVisitor<String, PostgreSQLGenerationContext, Void> {

    private static final String IDENTIFIER_REGEX = "[A-Za-z_][A-Za-z0-9_]*";

    private String sanitizeIdentifier(String identifier) {
        if (!identifier.matches(IDENTIFIER_REGEX)) {
            throw new FilterException("Unsafe identifier: %s".formatted(identifier));
        }
        return identifier;
    }

    private String parameter(PostgreSQLGenerationContext env, Object value) {
        var index = env.addParameter(value);
        return "$" + index;
    }

    @Override
    public String visit(OrExpressionNode orExpressionNode, PostgreSQLGenerationContext env, Void record) {
        var left = orExpressionNode.left().accept(this, env, record);
        var right = orExpressionNode.right().accept(this, env, record);

        return String.format("(%s OR %s)", left, right);
    }

    @Override
    public String visit(AndExpressionNode andExpressionNode, PostgreSQLGenerationContext env, Void record) {
        var left = andExpressionNode.left().accept(this, env, record);
        var right = andExpressionNode.right().accept(this, env, record);

        return String.format("(%s AND %s)", left, right);
    }

    @Override
    public String visit(EqualsExpressionNode equalsExpressionNode, PostgreSQLGenerationContext env, Void record) {
        var left = equalsExpressionNode.left().accept(this, env, record);
        var right = equalsExpressionNode.right().accept(this, env, record);

        return String.format("(%s = %s)", left, right);
    }

    @Override
    public String visit(NotExpressionNode notExpressionNode, PostgreSQLGenerationContext env, Void record) {
        var expression = notExpressionNode.expression().accept(this, env, record);

        return String.format("NOT (%s)", expression);
    }

    @Override
    public String visit(StringExpressionNode stringExpressionNode, PostgreSQLGenerationContext env, Void record) {
        return parameter(env, stringExpressionNode.value());
    }

    @Override
    public String visit(BooleanExpressionNode booleanExpressionNode, PostgreSQLGenerationContext env, Void record) {
        var value = booleanExpressionNode.value();
        return value ? "TRUE" : "FALSE";
    }

    @Override
    public String visit(NullExpressionNode nullExpressionNode, PostgreSQLGenerationContext env, Void record) {
        return "NULL";
    }

    @Override
    public String visit(IdentifierExpressionNode identifierExpressionNode, PostgreSQLGenerationContext env, Void record) {
        return sanitizeIdentifier(identifierExpressionNode.value());
    }

    @Override
    public String visit(LongExpressionNode longExpressionNode, PostgreSQLGenerationContext env, Void record) {
        var value = longExpressionNode.value();
        return String.valueOf(value);
    }

    @Override
    public String visit(DoubleExpressionNode doubleExpressionNode, PostgreSQLGenerationContext env, Void record) {
        var value = doubleExpressionNode.value();
        return String.valueOf(value);
    }

    @Override
    public String visit(DateTimeExpressionNode dateTimeExpressionNode, PostgreSQLGenerationContext env, Void record) {
        return parameter(env, dateTimeExpressionNode.date());
    }

    @Override
    public String visit(GreaterThanExpressionNode greaterThanExpressionNode, PostgreSQLGenerationContext env, Void record) {
        var left = greaterThanExpressionNode.left().accept(this, env, record);
        var right = greaterThanExpressionNode.right().accept(this, env, record);

        return String.format("(%s > %s)", left, right);
    }

    @Override
    public String visit(LessThanOrEqualsExpressionNode lessThanOrEqualsExpressionNode, PostgreSQLGenerationContext env, Void record) {
        var left = lessThanOrEqualsExpressionNode.left().accept(this, env, record);
        var right = lessThanOrEqualsExpressionNode.right().accept(this, env, record);

        return String.format("(%s <= %s)", left, right);
    }

    @Override
    public String visit(GreaterThanOrEqualsExpressionNode greaterThanOrEqualsExpressionNode, PostgreSQLGenerationContext env, Void record) {
        var left = greaterThanOrEqualsExpressionNode.left().accept(this, env, record);
        var right = greaterThanOrEqualsExpressionNode.right().accept(this, env, record);

        return String.format("(%s >= %s)", left, right);
    }

    @Override
    public String visit(NotEqualsExpressionNode notEqualsExpressionNode, PostgreSQLGenerationContext env, Void record) {
        var left = notEqualsExpressionNode.left().accept(this, env, record);
        var right = notEqualsExpressionNode.right().accept(this, env, record);

        return String.format("(%s != %s)", left, right);
    }

    @Override
    public String visit(LessThanExpressionNode lessThanExpressionNode, PostgreSQLGenerationContext env, Void record) {
        var left = lessThanExpressionNode.left().accept(this, env, record);
        var right = lessThanExpressionNode.right().accept(this, env, record);

        return String.format("(%s < %s)", left, right);
    }

    @Override
    public String visit(DotExpressionNode dotExpressionNode, PostgreSQLGenerationContext env, Void record) {
        var left = dotExpressionNode.object().accept(this, env, record);
        var right = sanitizeIdentifier(dotExpressionNode.field());

        return String.format("%s.%s", left, right);
    }

    @Override
    public String visit(FunctionCallExpressionNode functionCallExpressionNode, PostgreSQLGenerationContext env, Void record) {
        var function = sanitizeIdentifier(functionCallExpressionNode.function());
        var arguments = functionCallExpressionNode.arguments();

        var args = arguments.stream()
                .map(arg -> arg.accept(this, env, record))
                .reduce((a, b) -> a + ", " + b)
                .orElse("");

        return String.format("%s(%s)", function, args);
    }
}
