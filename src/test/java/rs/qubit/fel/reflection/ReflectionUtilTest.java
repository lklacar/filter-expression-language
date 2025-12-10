package rs.qubit.fel.reflection;

import lombok.Data;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReflectionUtilTest {

    static class TestClass {
        private String string;

        public void setString(String string) {
            this.string = string;
        }
    }

    @org.junit.jupiter.api.Test
    void accessField() {
        var testClass = new TestClass();
        testClass.setString("test");
        var value = ReflectionUtil.accessField(testClass, "string");
        assertEquals("test", value);
    }
}