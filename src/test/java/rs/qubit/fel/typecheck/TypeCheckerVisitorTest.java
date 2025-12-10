package rs.qubit.fel.typecheck;

import org.junit.jupiter.api.Test;
import rs.qubit.fel.Fel;
import rs.qubit.fel.exception.TypeCheckException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TypeCheckerVisitorTest {

    static class Address {
        private final String city = "City";
    }

    static class User {
        private final int age = 30;
        private final String name = "Jane";
        private final Address address = new Address();
    }

    @Test
    void failsOnUnknownFieldWithLocation() {
        var ex = assertThrows(TypeCheckException.class, () -> Fel.filterJit("unknown = 1", User.class));
        assertTrue(ex.getMessage().contains("Field 'unknown' not found"));
        assertTrue(ex.getMessage().contains("line 1, column 1"));
    }

    @Test
    void failsOnTypeMismatch() {
        var ex = assertThrows(TypeCheckException.class, () -> Fel.filterJit("age = 'old'", User.class));
        assertTrue(ex.getMessage().contains("Type mismatch"));
    }

    @Test
    void failsOnNullabilityViolation() {
        var ex = assertThrows(TypeCheckException.class, () -> Fel.filterJit("age = null", User.class));
        assertTrue(ex.getMessage().contains("not nullable"));
    }

    @Test
    void failsOnDotAccessForNonObject() {
        var ex = assertThrows(TypeCheckException.class, () -> Fel.filterJit("age.value > 1", User.class));
        assertTrue(ex.getMessage().contains("Cannot access field 'value'"));
    }

    @Test
    void allowsValidTypedExpression() {
        assertDoesNotThrow(() -> Fel.filterJit("address.city = 'NYC' && age > 18", User.class));
    }
}
