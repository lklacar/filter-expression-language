package rs.qubit.fel.evaluator.value;

import org.junit.jupiter.api.Test;
import rs.qubit.fel.exception.FilterException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

class InvalidOperationsTest {

    @Test
    void longVsNullThrows() {
        assertThrows(FilterException.class, () -> new LongValue(1L).equal(new NullValue()));
    }

    @Test
    void doubleVsStringThrows() {
        assertThrows(FilterException.class, () -> new DoubleValue(1.2).lessThanOrEquals(new StringValue("x")));
    }

    @Test
    void dateTimeVsLongThrows() {
        assertThrows(FilterException.class, () -> new DateTimeValue(LocalDateTime.now()).lessThanOrEquals(new LongValue(1L)));
    }
}
