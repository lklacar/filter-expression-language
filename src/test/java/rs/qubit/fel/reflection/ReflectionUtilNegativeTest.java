package rs.qubit.fel.reflection;

import org.junit.jupiter.api.Test;
import rs.qubit.fel.exception.FilterException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ReflectionUtilNegativeTest {

    @Test
    void nullObjectThrows() {
        assertThrows(FilterException.class, () -> ReflectionUtil.accessField(null, "field"));
    }

    @Test
    void missingFieldThrows() {
        class Simple {
            private String present = "x";
        }
        assertThrows(FilterException.class, () -> ReflectionUtil.accessField(new Simple(), "absent"));
    }

    @Test
    void getterFailureIsWrapped() {
        class ThrowingGetter {
            public String getValue() {
                throw new IllegalStateException("boom");
            }
        }

        assertThrows(FilterException.class, () -> ReflectionUtil.accessField(new ThrowingGetter(), "value"));
    }
}
