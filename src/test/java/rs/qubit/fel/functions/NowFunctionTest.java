package rs.qubit.fel.functions;

import org.junit.jupiter.api.Test;
import rs.qubit.fel.evaluator.value.DateTimeValue;
import rs.qubit.fel.exception.FilterException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NowFunctionTest {

    @Test
    void noArgsAllowed() {
        var fn = new NowFunction();
        assertDoesNotThrow(() -> ((DateTimeValue) fn.apply(List.of())).value());
        assertThrows(FilterException.class, () -> fn.apply(List.of(new DateTimeValue(java.time.LocalDateTime.now()))));
    }
}
