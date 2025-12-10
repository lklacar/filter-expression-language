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

    @Test
    void longComparisonDefaultBranchesThrow() {
        var longValue = new LongValue(2L);

        assertThrows(FilterException.class, () -> longValue.greaterThan(new StringValue("x")));
        assertThrows(FilterException.class, () -> longValue.lessThan(new BooleanValue(true)));
        assertThrows(FilterException.class, () -> longValue.lessThanOrEquals(new StringValue("x")));
        assertThrows(FilterException.class, () -> longValue.greaterThanOrEquals(new BooleanValue(false)));
    }
}
