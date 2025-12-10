package rs.qubit.fel.evaluator.value;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValueConversionsTest {

    @Test
    void longValueConversions() {
        var v = new LongValue(10L);
        assertEquals("10", v.asString());
        assertEquals(10L, v.asLong());
        assertEquals(10.0, v.asDouble());
    }

    @Test
    void doubleValueConversions() {
        var v = new DoubleValue(2.5);
        assertEquals("2.5", v.asString());
        assertEquals(2.5, v.asDouble());
    }

    @Test
    void stringValueConversions() {
        var v = new StringValue("text");
        assertEquals("text", v.asString());
    }

    @Test
    void booleanValueConversions() {
        var v = new BooleanValue(true);
        assertEquals(true, v.asBoolean());
        assertEquals("true", v.asString());
    }

    @Test
    void dateTimeValueConversions() {
        var dt = LocalDateTime.of(2024, 1, 1, 12, 0);
        var v = new DateTimeValue(dt);
        assertEquals(dt, v.asDateTime());
        assertEquals(dt.toString(), v.asString());
        assertEquals(true, v.equal(new DateTimeValue(dt)));
        assertEquals(true, v.lessThanOrEquals(new DateTimeValue(dt)));
    }

    @Test
    void objectValueConversions() {
        var obj = new Object();
        var v = new ObjectValue(obj);
        assertEquals(obj, v.asObject());
        assertEquals(obj.toString(), v.asString());
    }

    @Test
    void nullValueConversions() {
        var v = new NullValue();
        assertEquals("null", v.asString());
        assertEquals(true, v.equal(new NullValue()));
    }
}
