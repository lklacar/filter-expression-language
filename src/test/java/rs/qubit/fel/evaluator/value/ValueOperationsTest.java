package rs.qubit.fel.evaluator.value;

import org.junit.jupiter.api.Test;
import rs.qubit.fel.exception.FilterException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ValueOperationsTest {

    @Test
    void numericComparisonsAcrossTypesWork() {
        var longVal = new LongValue(5L);
        var doubleVal = new DoubleValue(5.0);

        assertTrue(longVal.equal(doubleVal));
        assertTrue(doubleVal.equal(longVal));
        assertTrue(doubleVal.greaterThan(new LongValue(4L)));
        assertTrue(new LongValue(6L).greaterThan(new DoubleValue(5.0)));
        assertFalse(new LongValue(4L).greaterThan(new LongValue(5L)));
        assertFalse(new LongValue(4L).greaterThan(new DoubleValue(5.0)));
        assertTrue(longVal.lessThan(new DoubleValue(6.0)));
        assertTrue(longVal.greaterThanOrEquals(new DoubleValue(5.0)));
        assertTrue(doubleVal.lessThanOrEquals(new LongValue(5L)));
        assertTrue(longVal.lessThanOrEquals(new DoubleValue(5.0)));
        assertFalse(longVal.lessThanOrEquals(new LongValue(4L)));
        assertThrows(FilterException.class, longVal::asBoolean);
    }

    @Test
    void stringComparisonsWork() {
        var a = new StringValue("abc");
        var b = new StringValue("abd");

        assertTrue(a.equal(new StringValue("abc")));
        assertTrue(a.lessThan(b));
    }

    @Test
    void dateTimeComparisonsWork() {
        var a = new DateTimeValue(LocalDateTime.of(2024, 1, 1, 10, 0));
        var b = new DateTimeValue(LocalDateTime.of(2024, 1, 1, 11, 0));

        assertTrue(a.lessThan(b));
        assertTrue(b.greaterThanOrEquals(a));
    }

    @Test
    void nullAndObjectEquality() {
        var nullValue = new NullValue();
        var objectValue = new ObjectValue(null);

        assertTrue(nullValue.equal(new NullValue()));
        assertTrue(objectValue.equal(new NullValue()));
    }

    @Test
    void booleanEquality() {
        var a = new BooleanValue(true);
        var b = new BooleanValue(true);
        assertTrue(a.equal(b));
    }
}
