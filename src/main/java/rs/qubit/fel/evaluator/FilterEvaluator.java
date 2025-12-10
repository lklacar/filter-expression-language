package rs.qubit.fel.evaluator;

import lombok.Getter;
import lombok.Setter;
import rs.qubit.fel.evaluator.value.*;
import rs.qubit.fel.exception.FilterException;
import rs.qubit.fel.parser.ast.*;
import rs.qubit.fel.reflection.ReflectionUtil;
import rs.qubit.fel.visitor.ExpressionVisitor;
import rs.qubit.fel.visitor.VisitorContext;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Setter
@Getter
public class FilterEvaluator implements ExpressionVisitor<Value, VisitorContext, Object> {


    @Override
    public Value visit(OrExpressionNode orExpressionNode, VisitorContext env, Object record) {
        var left = orExpressionNode.left().accept(this, env, record);
        var leftBoolean = left.asBoolean();
        if (leftBoolean) {
            // Short-circuit: true || _ => true without evaluating the right side
            return new BooleanValue(true);
        }

        var right = orExpressionNode.right().accept(this, env, record);

        return new BooleanValue(right.asBoolean());
    }

    @Override
    public Value visit(AndExpressionNode andExpressionNode, VisitorContext env, Object record) {
        var left = andExpressionNode.left().accept(this, env, record);
        var leftBoolean = left.asBoolean();
        if (!leftBoolean) {
            // Short-circuit: false && _ => false without evaluating the right side
            return new BooleanValue(false);
        }

        var right = andExpressionNode.right().accept(this, env, record);

        return new BooleanValue(right.asBoolean());
    }

    @Override
    public Value visit(EqualsExpressionNode equalsExpressionNode, VisitorContext env, Object record) {
        var left = equalsExpressionNode.left().accept(this, env, record);
        var right = equalsExpressionNode.right().accept(this, env, record);

        return new BooleanValue(left.equal(right));
    }

    @Override
    public Value visit(NotExpressionNode notExpressionNode, VisitorContext env, Object record) {
        var value = notExpressionNode.expression().accept(this, env, record);
        return new BooleanValue(!value.asBoolean());
    }

    @Override
    public Value visit(StringExpressionNode stringExpressionNode, VisitorContext env, Object record) {
        var value = stringExpressionNode.value();
        return new StringValue(value);
    }

    @Override
    public Value visit(BooleanExpressionNode booleanExpressionNode, VisitorContext env, Object record) {
        var value = booleanExpressionNode.value();
        return new BooleanValue(value);
    }

    @Override
    public Value visit(NullExpressionNode nullExpressionNode, VisitorContext env, Object record) {
        return new NullValue();
    }

    @Override
    public Value visit(IdentifierExpressionNode identifierExpressionNode, VisitorContext env, Object record) {
        var identifier = identifierExpressionNode.value();

        var value = ReflectionUtil.accessField(record, identifier);

        return parseValue(value, env);
    }

    private Value parseValue(Object value, VisitorContext evaluationContext) {

        return switch (value) {
            case Byte b -> new LongValue(Long.valueOf(b));
            case Short s -> new LongValue(Long.valueOf(s));
            case Integer d -> new LongValue(Long.valueOf(d));
            case Long l -> new LongValue(l);
            case Float f -> new DoubleValue(f);
            case Double d -> new DoubleValue(d);
            case Character c -> new StringValue(String.valueOf(c));
            case String s -> new StringValue(s);
            case Boolean b -> new BooleanValue(b);
            case Enum<?> e -> new StringValue(e.name());
            case LocalDateTime d -> new DateTimeValue(d);
            case LocalDate d -> new DateTimeValue(d.atStartOfDay());
            case Instant i -> new DateTimeValue(i.atZone(ZoneId.systemDefault()).toLocalDateTime());
            case null -> new NullValue();
            case Object o -> {
                var parser = evaluationContext.getMapper(value.getClass());
                yield Optional.ofNullable(parser)
                        .map(p -> p.apply(value))
                        .orElseGet(() -> {
                            var isJavaObject = value.getClass().getName().startsWith("java");
                            if (isJavaObject) {
                                throw new FilterException("Cannot convert java object %s to a filter value".formatted(value.getClass().getName()));
                            }

                            return new ObjectValue(o);
                        });
            }
        };
    }

    @Override
    public Value visit(LongExpressionNode longExpressionNode, VisitorContext env, Object record) {
        var value = longExpressionNode.value();
        return new LongValue(value);
    }

    @Override
    public Value visit(DoubleExpressionNode doubleExpressionNode, VisitorContext env, Object record) {
        var value = doubleExpressionNode.value();
        return new DoubleValue(value);
    }

    @Override
    public Value visit(DateTimeExpressionNode dateTimeExpressionNode, VisitorContext env, Object record) {
        var value = dateTimeExpressionNode.date();
        return new DateTimeValue(value);
    }

    @Override
    public Value visit(GreaterThanExpressionNode greaterThanExpressionNode, VisitorContext env, Object record) {
        var left = greaterThanExpressionNode.left().accept(this, env, record);
        var right = greaterThanExpressionNode.right().accept(this, env, record);
        return new BooleanValue(left.greaterThan(right));
    }

    @Override
    public Value visit(LessThanOrEqualsExpressionNode lessThanOrEqualsExpressionNode, VisitorContext env, Object record) {
        var left = lessThanOrEqualsExpressionNode.left().accept(this, env, record);
        var right = lessThanOrEqualsExpressionNode.right().accept(this, env, record);
        return new BooleanValue(left.lessThanOrEquals(right));
    }

    @Override
    public Value visit(GreaterThanOrEqualsExpressionNode greaterThanOrEqualsExpressionNode, VisitorContext env, Object record) {
        var left = greaterThanOrEqualsExpressionNode.left().accept(this, env, record);
        var right = greaterThanOrEqualsExpressionNode.right().accept(this, env, record);
        return new BooleanValue(left.greaterThanOrEquals(right));
    }

    @Override
    public Value visit(NotEqualsExpressionNode notEqualsExpressionNode, VisitorContext env, Object record) {
        var left = notEqualsExpressionNode.left().accept(this, env, record);
        var right = notEqualsExpressionNode.right().accept(this, env, record);
        return new BooleanValue(!left.equal(right));
    }

    @Override
    public Value visit(LessThanExpressionNode lessThanExpressionNode, VisitorContext env, Object record) {
        var left = lessThanExpressionNode.left().accept(this, env, record);
        var right = lessThanExpressionNode.right().accept(this, env, record);
        return new BooleanValue(left.lessThan(right));
    }

    @Override
    public Value visit(DotExpressionNode dotExpressionNode, VisitorContext env, Object record) {
        var object = dotExpressionNode.object().accept(this, env, record);
        var field = dotExpressionNode.field();
        var originalObject = object.asObject();
        var fieldValue = ReflectionUtil.accessField(originalObject, field);
        return parseValue(fieldValue, env);
    }

    @Override
    public Value visit(FunctionCallExpressionNode functionCallExpressionNode, VisitorContext env, Object record) {
        var function = functionCallExpressionNode.function();
        var arguments = functionCallExpressionNode.arguments().stream()
                .map(arg -> arg.accept(this, env, record))
                .toList();

        return env.getFunction(function).apply(arguments);
    }

}
