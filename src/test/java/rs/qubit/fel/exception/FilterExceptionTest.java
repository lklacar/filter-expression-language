package rs.qubit.fel.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class FilterExceptionTest {

    @Test
    void constructorsCoverAllPaths() {
        assertNotNull(new FilterException());
        assertNotNull(new FilterException("msg"));
        assertNotNull(new FilterException("msg", new RuntimeException("cause")));
        assertNotNull(new FilterException(new RuntimeException("cause")));
        assertNotNull(new FilterException("msg", new RuntimeException("cause"), true, true));
    }
}
