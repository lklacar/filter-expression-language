package rs.qubit.fel.functions;

import org.junit.jupiter.api.Test;
import rs.qubit.fel.evaluator.value.LongValue;
import rs.qubit.fel.evaluator.value.StringValue;
import rs.qubit.fel.exception.FilterException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class SubstringFunctionTest {

    @Test
    void rejectsOutOfBounds() {
        var fn = new SubstringFunction();
        assertThrows(FilterException.class, () -> fn.apply(List.of(new StringValue("abc"), new LongValue(5L), new LongValue(6L))));
        assertThrows(FilterException.class, () -> fn.apply(List.of(new StringValue("abc"), new LongValue(2L), new LongValue(1L))));
    }
}
