package rs.qubit.filter.evaluator.value;

import rs.qubit.filter.exception.FilterException;

public record DoubleValue(double value) implements Value {
    @Override
    public boolean asBoolean() {
        throw new FilterException("Cannot convert double to boolean");
    }

    @Override
    public boolean greaterThan(Value value) {
        return switch (value) {
            case DoubleValue doubleValue -> this.value > doubleValue.value;
            default -> throw new FilterException("Cannot compare double with " + value.getClass().getSimpleName());
        };
    }

    @Override
    public boolean lessThan(Value value) {
        return switch (value) {
            case DoubleValue doubleValue -> this.value < doubleValue.value;
            default -> throw new FilterException("Cannot compare double with " + value.getClass().getSimpleName());
        };
    }

    @Override
    public boolean equal(Value value) {
        return switch (value) {
            case DoubleValue doubleValue -> this.value == doubleValue.value;
            default -> throw new FilterException("Cannot compare double with " + value.getClass().getSimpleName());
        };
    }

    @Override
    public boolean lessThanOrEquals(Value right) {
        return switch (right) {
            case DoubleValue doubleValue -> this.value <= doubleValue.value;
            default -> throw new FilterException("Cannot compare double with " + right.getClass().getSimpleName());
        };
    }

    @Override
    public boolean greaterThanOrEquals(Value right) {
        return switch (right) {
            case DoubleValue doubleValue -> this.value >= doubleValue.value;
            default -> throw new FilterException("Cannot compare double with " + right.getClass().getSimpleName());
        };
    }
}