package rs.qubit.fel.typecheck;

import rs.qubit.fel.exception.FilterException;
import rs.qubit.fel.exception.TypeCheckException;
import rs.qubit.fel.parser.ast.*;
import rs.qubit.fel.type.Types;
import rs.qubit.fel.visitor.ExpressionVisitor;

public class TypeCheckerVisitor implements ExpressionVisitor<TypeCheckResult, TypeCheckContext, Void> {

    public void check(ExpressionNode node, TypeCheckContext context) {
        node.accept(this, context, null);
    }

    @Override
    public TypeCheckResult visit(OrExpressionNode orExpressionNode, TypeCheckContext env, Void record) {
        var left = orExpressionNode.left().accept(this, env, null);
        var right = orExpressionNode.right().accept(this, env, null);

        ensureBoolean(orExpressionNode.position(), left, "left side of ||");
        ensureBoolean(orExpressionNode.position(), right, "right side of ||");

        return TypeCheckResult.of(Types.BOOLEAN);
    }

    @Override
    public TypeCheckResult visit(AndExpressionNode andExpressionNode, TypeCheckContext env, Void record) {
        var left = andExpressionNode.left().accept(this, env, null);
        var right = andExpressionNode.right().accept(this, env, null);

        ensureBoolean(andExpressionNode.position(), left, "left side of &&");
        ensureBoolean(andExpressionNode.position(), right, "right side of &&");

        return TypeCheckResult.of(Types.BOOLEAN);
    }

    @Override
    public TypeCheckResult visit(EqualsExpressionNode equalsExpressionNode, TypeCheckContext env, Void record) {
        var left = equalsExpressionNode.left().accept(this, env, null);
        var right = equalsExpressionNode.right().accept(this, env, null);

        ensureEqualityCompatible(equalsExpressionNode.position(), left, right, "=");

        return TypeCheckResult.of(Types.BOOLEAN);
    }

    @Override
    public TypeCheckResult visit(NotExpressionNode notExpressionNode, TypeCheckContext env, Void record) {
        var value = notExpressionNode.expression().accept(this, env, null);
        ensureBoolean(notExpressionNode.position(), value, "operand of '!'");
        return TypeCheckResult.of(Types.BOOLEAN);
    }

    @Override
    public TypeCheckResult visit(StringExpressionNode stringExpressionNode, TypeCheckContext env, Void record) {
        return TypeCheckResult.of(Types.STRING);
    }

    @Override
    public TypeCheckResult visit(BooleanExpressionNode booleanExpressionNode, TypeCheckContext env, Void record) {
        return TypeCheckResult.of(Types.BOOLEAN);
    }

    @Override
    public TypeCheckResult visit(NullExpressionNode nullExpressionNode, TypeCheckContext env, Void record) {
        return TypeCheckResult.of(Types.NULL);
    }

    @Override
    public TypeCheckResult visit(IdentifierExpressionNode identifierExpressionNode, TypeCheckContext env, Void record) {
        var rootType = env.rootType();
        if (rootType == null) {
            return TypeCheckResult.of(Types.UNKNOWN, identifierExpressionNode.value());
        }

        var fieldType = env.resolver().resolveField(rootType, identifierExpressionNode.value(), identifierExpressionNode.position());
        return TypeCheckResult.of(fieldType, identifierExpressionNode.value());
    }

    @Override
    public TypeCheckResult visit(LongExpressionNode longExpressionNode, TypeCheckContext env, Void record) {
        return TypeCheckResult.of(Types.LONG);
    }

    @Override
    public TypeCheckResult visit(DoubleExpressionNode doubleExpressionNode, TypeCheckContext env, Void record) {
        return TypeCheckResult.of(Types.DOUBLE);
    }

    @Override
    public TypeCheckResult visit(DateTimeExpressionNode dateTimeExpressionNode, TypeCheckContext env, Void record) {
        return TypeCheckResult.of(Types.DATE_TIME);
    }

    @Override
    public TypeCheckResult visit(GreaterThanExpressionNode greaterThanExpressionNode, TypeCheckContext env, Void record) {
        var left = greaterThanExpressionNode.left().accept(this, env, null);
        var right = greaterThanExpressionNode.right().accept(this, env, null);
        ensureOrdered(greaterThanExpressionNode.position(), left, right, ">");
        return TypeCheckResult.of(Types.BOOLEAN);
    }

    @Override
    public TypeCheckResult visit(LessThanOrEqualsExpressionNode lessThanOrEqualsExpressionNode, TypeCheckContext env, Void record) {
        var left = lessThanOrEqualsExpressionNode.left().accept(this, env, null);
        var right = lessThanOrEqualsExpressionNode.right().accept(this, env, null);
        ensureOrdered(lessThanOrEqualsExpressionNode.position(), left, right, "<=");
        return TypeCheckResult.of(Types.BOOLEAN);
    }

    @Override
    public TypeCheckResult visit(GreaterThanOrEqualsExpressionNode greaterThanOrEqualsExpressionNode, TypeCheckContext env, Void record) {
        var left = greaterThanOrEqualsExpressionNode.left().accept(this, env, null);
        var right = greaterThanOrEqualsExpressionNode.right().accept(this, env, null);
        ensureOrdered(greaterThanOrEqualsExpressionNode.position(), left, right, ">=");
        return TypeCheckResult.of(Types.BOOLEAN);
    }

    @Override
    public TypeCheckResult visit(NotEqualsExpressionNode notEqualsExpressionNode, TypeCheckContext env, Void record) {
        var left = notEqualsExpressionNode.left().accept(this, env, null);
        var right = notEqualsExpressionNode.right().accept(this, env, null);
        ensureEqualityCompatible(notEqualsExpressionNode.position(), left, right, "!=");
        return TypeCheckResult.of(Types.BOOLEAN);
    }

    @Override
    public TypeCheckResult visit(LessThanExpressionNode lessThanExpressionNode, TypeCheckContext env, Void record) {
        var left = lessThanExpressionNode.left().accept(this, env, null);
        var right = lessThanExpressionNode.right().accept(this, env, null);
        ensureOrdered(lessThanExpressionNode.position(), left, right, "<", true);
        return TypeCheckResult.of(Types.BOOLEAN);
    }

    @Override
    public TypeCheckResult visit(DotExpressionNode dotExpressionNode, TypeCheckContext env, Void record) {
        var object = dotExpressionNode.object().accept(this, env, null);
        var ownerType = object.type();
        var path = buildPath(object.path(), dotExpressionNode.field());

        if (Types.isNull(ownerType)) {
            throw new TypeCheckException(
                    "Cannot access field '%s' on null".formatted(dotExpressionNode.field()),
                    dotExpressionNode.position());
        }

        if (Types.isUnknown(ownerType)) {
            return TypeCheckResult.of(Types.UNKNOWN, path);
        }

        if (!ownerType.isObject()) {
            throw new TypeCheckException(
                    "Cannot access field '%s' on %s".formatted(dotExpressionNode.field(), ownerType.displayName()),
                    dotExpressionNode.position());
        }

        var javaType = ownerType.javaType();
        if (javaType == null) {
            return TypeCheckResult.of(Types.UNKNOWN, path);
        }

        var fieldType = env.resolver().resolveField(javaType, dotExpressionNode.field(), dotExpressionNode.position());

        return TypeCheckResult.of(fieldType, path);
    }

    @Override
    public TypeCheckResult visit(FunctionCallExpressionNode functionCallExpressionNode, TypeCheckContext env, Void record) {
        var function = functionCallExpressionNode.function();
        // Validate arguments first to surface inner errors.
        functionCallExpressionNode.arguments().forEach(arg -> arg.accept(this, env, null));

        if (env.evaluationContext() != null) {
            try {
                env.evaluationContext().getFunction(function);
            } catch (FilterException e) {
                throw new TypeCheckException("Unknown function '%s'".formatted(function), functionCallExpressionNode.position(), e);
            }
        }

        // Without explicit function signatures default to unknown.
        return TypeCheckResult.of(Types.UNKNOWN, function);
    }

    private void ensureOrdered(SourcePosition position, TypeCheckResult left, TypeCheckResult right, String operator) {
        ensureOrdered(position, left, right, operator, false);
    }

    private void ensureOrdered(SourcePosition position, TypeCheckResult left, TypeCheckResult right, String operator, boolean allowStringLessThan) {
        var leftType = left.type();
        var rightType = right.type();

        if (Types.isUnknown(leftType) || Types.isUnknown(rightType)) {
            return;
        }

        if (Types.isNull(leftType) || Types.isNull(rightType)) {
            throw new TypeCheckException("Operator '%s' does not allow null operands".formatted(operator), position);
        }

        var bothNumeric = leftType.isNumeric() && rightType.isNumeric();
        var bothDateTime = leftType.isDateTime() && rightType.isDateTime();
        var bothStrings = allowStringLessThan && leftType.isString() && rightType.isString();

        if (bothNumeric || bothDateTime || bothStrings) {
            return;
        }

        throw new TypeCheckException(
                "Operator '%s' requires comparable operands but found %s and %s"
                        .formatted(operator, describe(left), describe(right)),
                position);
    }

    private void ensureEqualityCompatible(SourcePosition position, TypeCheckResult left, TypeCheckResult right, String operator) {
        var leftType = left.type();
        var rightType = right.type();

        if (Types.isUnknown(leftType) || Types.isUnknown(rightType)) {
            return;
        }

        if (Types.isNull(leftType)) {
            ensureNullableAllowed(position, right);
            return;
        }

        if (Types.isNull(rightType)) {
            ensureNullableAllowed(position, left);
            return;
        }

        if (leftType.isObject() || rightType.isObject()) {
            throw new TypeCheckException(
                    "Object comparison with '%s' is not supported (only null checks)".formatted(operator),
                    position);
        }

        var numeric = leftType.isNumeric() && rightType.isNumeric();
        var sameKind = leftType.kind() == rightType.kind();

        if (numeric || sameKind) {
            return;
        }

        throw new TypeCheckException(
                "Type mismatch for '%s': left is %s, right is %s"
                        .formatted(operator, left.type().displayName(), right.type().displayName()),
                position);
    }

    private void ensureBoolean(SourcePosition position, TypeCheckResult candidate, String usage) {
        var type = candidate.type();
        if (Types.isUnknown(type)) {
            return;
        }

        if (Types.isNull(type)) {
            throw new TypeCheckException("Null is not allowed for " + usage, position);
        }

        if (!type.isBoolean()) {
            throw new TypeCheckException("Expected boolean for %s but found %s".formatted(usage, type.displayName()), position);
        }
    }

    private void ensureNullableAllowed(SourcePosition position, TypeCheckResult target) {
        var targetType = target.type();
        if (Types.isUnknown(targetType)) {
            return;
        }

        if (!targetType.nullable()) {
            var path = target.path() != null ? " '" + target.path() + "'" : "";
            throw new TypeCheckException("Field%s is not nullable".formatted(path), position);
        }
    }

    private String describe(TypeCheckResult result) {
        if (result.path() == null) {
            return result.type().displayName();
        }
        return "%s (%s)".formatted(result.path(), result.type().displayName());
    }

    private String buildPath(String left, String field) {
        if (left == null || left.isBlank()) {
            return field;
        }
        return left + "." + field;
    }
}
