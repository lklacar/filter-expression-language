package rs.qubit.fel.evaluator;

import org.junit.jupiter.api.Test;
import rs.qubit.fel.evaluator.value.*;
import rs.qubit.fel.exception.FilterException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ValueTest {

    @Test
    void booleanComparisonThrows() {
        var value = new BooleanValue(true);
        assertThrows(FilterException.class, () -> value.lessThan(new BooleanValue(false)));
        assertThrows(FilterException.class, () -> value.asDouble());
    }

    @Test
    void nullComparisonThrows() {
        var value = new NullValue();
        assertThrows(UnsupportedOperationException.class, () -> value.lessThan(new LongValue(1L)));
        assertThrows(UnsupportedOperationException.class, value::asDouble);
    }

    @Test
    void dateComparisonTypeMismatchThrows() {
        var value = new DateTimeValue(LocalDateTime.now());
        assertThrows(FilterException.class, () -> value.greaterThan(new LongValue(5L)));
    }
}
