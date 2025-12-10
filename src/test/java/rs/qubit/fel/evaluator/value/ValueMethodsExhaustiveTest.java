package rs.qubit.fel.evaluator.value;

import org.junit.jupiter.api.Test;
import rs.qubit.fel.exception.FilterException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValueMethodsExhaustiveTest {

    @Test
    void longValueComparisonVariants() {
        var five = new LongValue(5L);
        var sixDouble = new DoubleValue(6.0);

        assertEquals(true, five.lessThanOrEquals(sixDouble));
        assertEquals(true, five.greaterThanOrEquals(new LongValue(5L)));
        assertThrows(FilterException.class, () -> five.greaterThan(new StringValue("x")));
        assertThrows(FilterException.class, () -> five.lessThanOrEquals(new StringValue("x")));
        assertThrows(FilterException.class, five::asBoolean);
        assertThrows(FilterException.class, five::asObject);
        assertThrows(FilterException.class, five::asDateTime);
    }

    @Test
    void doubleValueComparisonVariants() {
        var five = new DoubleValue(5.0);
        var sixLong = new LongValue(6L);

        assertEquals(true, five.lessThanOrEquals(sixLong));
        assertEquals(true, five.greaterThanOrEquals(new DoubleValue(5.0)));
        assertThrows(FilterException.class, () -> five.lessThan(new BooleanValue(true)));
        assertThrows(FilterException.class, () -> five.lessThanOrEquals(new BooleanValue(true)));
        assertThrows(FilterException.class, five::asBoolean);
        assertThrows(FilterException.class, five::asObject);
        assertThrows(FilterException.class, five::asLong);
        assertThrows(FilterException.class, five::asDateTime);
    }

    @Test
    void stringValueErrors() {
        var s = new StringValue("abc");
        assertThrows(FilterException.class, () -> s.greaterThan(new StringValue("def")));
        assertThrows(FilterException.class, () -> s.lessThanOrEquals(new StringValue("def")));
        assertThrows(FilterException.class, s::asBoolean);
        assertThrows(FilterException.class, s::asObject);
        assertThrows(FilterException.class, s::asLong);
        assertThrows(FilterException.class, s::asDouble);
        assertThrows(FilterException.class, s::asDateTime);
    }

    @Test
    void booleanValueErrors() {
        var b = new BooleanValue(true);
        assertThrows(FilterException.class, () -> b.greaterThan(new BooleanValue(true)));
        assertThrows(FilterException.class, () -> b.lessThanOrEquals(new BooleanValue(true)));
        assertThrows(FilterException.class, b::asObject);
        assertThrows(FilterException.class, b::asLong);
        assertThrows(FilterException.class, b::asDouble);
        assertThrows(FilterException.class, b::asDateTime);
    }

    @Test
    void dateTimeValueErrors() {
        var dt = new DateTimeValue(LocalDateTime.now());
        assertThrows(FilterException.class, () -> dt.greaterThan(new LongValue(1L)));
        assertThrows(FilterException.class, () -> dt.lessThanOrEquals(new LongValue(1L)));
        assertThrows(FilterException.class, dt::asBoolean);
        assertThrows(FilterException.class, dt::asObject);
        assertThrows(FilterException.class, dt::asLong);
        assertThrows(FilterException.class, dt::asDouble);
    }

    @Test
    void objectValueErrors() {
        var ov = new ObjectValue(new Object());
        assertThrows(FilterException.class, () -> ov.greaterThan(new LongValue(1L)));
        assertThrows(FilterException.class, () -> ov.equal(new StringValue("x")));
        assertThrows(FilterException.class, () -> ov.lessThanOrEquals(new LongValue(1L)));
        assertThrows(FilterException.class, ov::asBoolean);
        assertThrows(FilterException.class, ov::asLong);
        assertThrows(FilterException.class, ov::asDouble);
        assertThrows(FilterException.class, ov::asDateTime);
    }

    @Test
    void nullValueErrors() {
        var nv = new NullValue();
        assertThrows(UnsupportedOperationException.class, () -> nv.greaterThan(new LongValue(1L)));
        assertThrows(UnsupportedOperationException.class, () -> nv.lessThanOrEquals(new LongValue(1L)));
        assertThrows(UnsupportedOperationException.class, nv::asBoolean);
        assertThrows(UnsupportedOperationException.class, nv::asObject);
        assertThrows(UnsupportedOperationException.class, nv::asLong);
        assertThrows(UnsupportedOperationException.class, nv::asDouble);
        assertThrows(UnsupportedOperationException.class, nv::asDateTime);
    }
}
