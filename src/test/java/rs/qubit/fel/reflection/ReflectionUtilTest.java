package rs.qubit.fel.reflection;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReflectionUtilTest {

    static class TestClass {
        private String string;

        public void setString(String string) {
            this.string = string;
        }
    }

    @Test
    void accessFieldFallsBackToPrivateField() {
        var testClass = new TestClass();
        testClass.setString("test");
        var value = ReflectionUtil.accessField(testClass, "string");
        assertEquals("test", value);
    }

    @Test
    void accessesMapByKey() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "map-value");

        var value = ReflectionUtil.accessField(map, "name");

        assertEquals("map-value", value);
    }

    @Test
    void prefersGetterOverField() {
        class WithGetter {
            private final String name = "field";
            public String getName() {
                return "getter";
            }
        }

        var value = ReflectionUtil.accessField(new WithGetter(), "name");

        assertEquals("getter", value);
    }

    @Test
    void supportsBooleanGetterConvention() {
        class WithBoolean {
            private final boolean active = true;
            public boolean isActive() {
                return active;
            }
        }

        var value = ReflectionUtil.accessField(new WithBoolean(), "active");

        assertTrue((Boolean) value);
    }

    @Test
    void traversesSuperclassFields() {
        class Base {
            private final String inherited = "base";
        }
        class Child extends Base {
        }

        var value = ReflectionUtil.accessField(new Child(), "inherited");

        assertEquals("base", value);
    }
}
