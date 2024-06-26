package rs.qubit.fel.evaluator.value;

import rs.qubit.fel.exception.FilterException;

import java.time.LocalDateTime;

public record StringValue(String value) implements Value {
    @Override
    public boolean asBoolean() {
        throw new FilterException("Cannot convert string to boolean");
    }

    @Override
    public boolean greaterThan(Value value) {
        throw new FilterException("Cannot compare string values");
    }

    @Override
    public boolean lessThan(Value value) {
        return switch (value) {
            case StringValue stringValue -> this.value.compareTo(stringValue.value) < 0;
            default -> throw new FilterException("Cannot compare string with " + value.getClass().getSimpleName());
        };
    }

    @Override
    public boolean equal(Value value) {
        return switch (value) {
            case StringValue stringValue -> this.value.equals(stringValue.value);
            default -> throw new FilterException("Cannot compare string with " + value.getClass().getSimpleName());
        };
    }

    @Override
    public boolean lessThanOrEquals(Value right) {
        throw new FilterException("Cannot compare string values");
    }

    @Override
    public boolean greaterThanOrEquals(Value right) {
        throw new FilterException("Cannot compare string values");
    }

    @Override
    public Object asObject() {
        throw new FilterException("Cannot convert string to object");
    }

    @Override
    public String asString() {
        return value;
    }

    @Override
    public Long asLong() {
        throw new FilterException("Cannot convert string to long");
    }

    @Override
    public Double asDouble() {
        throw new FilterException("Cannot convert string to double");
    }

    @Override
    public LocalDateTime asDateTime() {
        throw new FilterException("Cannot convert string to date time");
    }
}
