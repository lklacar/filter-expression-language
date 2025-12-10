package rs.qubit.fel.functions;

import org.junit.jupiter.api.Test;
import rs.qubit.fel.evaluator.value.DateTimeValue;
import rs.qubit.fel.evaluator.value.LongValue;
import rs.qubit.fel.evaluator.value.StringValue;
import rs.qubit.fel.exception.FilterException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FunctionsTest {

    @Test
    void containsWorks() {
        var fn = new ContainsFunction();
        var result = fn.apply(List.of(new StringValue("hello world"), new StringValue("world")));
        assertEquals("true", result.asString());
    }

    @Test
    void trimWorks() {
        var fn = new TrimFunction();
        var result = fn.apply(List.of(new StringValue("  spaced  ")));
        assertEquals("spaced", result.asString());
    }

    @Test
    void addDaysUpdatesDate() {
        var fn = new AddDaysFunction();
        var date = LocalDateTime.of(2024, 1, 1, 0, 0);
        var result = fn.apply(List.of(new DateTimeValue(date), new LongValue(5L)));
        assertEquals(LocalDateTime.of(2024, 1, 6, 0, 0), result.asDateTime());
    }

    @Test
    void wrongArityThrows() {
        assertThrows(FilterException.class, () -> new AbsFunction().apply(List.of()));
        assertThrows(FilterException.class, () -> new NowFunction().apply(List.of(new StringValue("noop"))));
        assertThrows(FilterException.class,
                () -> new AddMonthsFunction().apply(List.of(new DateTimeValue(LocalDateTime.now()))));
    }
}
