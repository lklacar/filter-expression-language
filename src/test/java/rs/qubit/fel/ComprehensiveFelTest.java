package rs.qubit.fel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;

import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ComprehensiveFelTest {

    public static class TestEntity {
        public String stringField;
        public int intField;
        public long longField;
        public double doubleField;
        public float floatField;
        public boolean booleanField;
        public LocalDateTime dateField;
        public TestEntity nested;

        public TestEntity(String stringField, int intField, long longField, double doubleField, float floatField,
                boolean booleanField, LocalDateTime dateField, TestEntity nested) {
            this.stringField = stringField;
            this.intField = intField;
            this.longField = longField;
            this.doubleField = doubleField;
            this.floatField = floatField;
            this.booleanField = booleanField;
            this.dateField = dateField;
            this.nested = nested;
        }

        public static TestEntityBuilder builder() {
            return new TestEntityBuilder();
        }

        public static class TestEntityBuilder {
            private String stringField;
            private int intField;
            private long longField;
            private double doubleField;
            private float floatField;
            private boolean booleanField;
            private LocalDateTime dateField;
            private TestEntity nested;

            public TestEntityBuilder stringField(String stringField) {
                this.stringField = stringField;
                return this;
            }

            public TestEntityBuilder intField(int intField) {
                this.intField = intField;
                return this;
            }

            public TestEntityBuilder longField(long longField) {
                this.longField = longField;
                return this;
            }

            public TestEntityBuilder doubleField(double doubleField) {
                this.doubleField = doubleField;
                return this;
            }

            public TestEntityBuilder floatField(float floatField) {
                this.floatField = floatField;
                return this;
            }

            public TestEntityBuilder booleanField(boolean booleanField) {
                this.booleanField = booleanField;
                return this;
            }

            public TestEntityBuilder dateField(LocalDateTime dateField) {
                this.dateField = dateField;
                return this;
            }

            public TestEntityBuilder nested(TestEntity nested) {
                this.nested = nested;
                return this;
            }

            public TestEntity build() {
                return new TestEntity(stringField, intField, longField, doubleField, floatField, booleanField,
                        dateField, nested);
            }
        }

        // Getters needed for FEL to access fields
        public String getStringField() {
            return stringField;
        }

        public int getIntField() {
            return intField;
        }

        public long getLongField() {
            return longField;
        }

        public double getDoubleField() {
            return doubleField;
        }

        public float getFloatField() {
            return floatField;
        }

        public boolean isBooleanField() {
            return booleanField;
        }

        public LocalDateTime getDateField() {
            return dateField;
        }

        public TestEntity getNested() {
            return nested;
        }
    }

    static Stream<Arguments> provideTestCases() {
        return Stream.of(
                // --- String Tests ---
                Arguments.of("String Equality", "stringField = 'test'", true),
                Arguments.of("String Equality False", "stringField = 'other'", false),
                Arguments.of("String Inequality", "stringField != 'other'", true),
                Arguments.of("String Inequality False", "stringField != 'test'", false),

                // --- Number Tests ---
                Arguments.of("Int Equality", "intField = 100", true),
                Arguments.of("Int Greater Than", "intField > 50", true),
                Arguments.of("Int Less Than", "intField < 150", true),
                Arguments.of("Double Equality", "doubleField = 100.5", true),
                Arguments.of("Double Greater Than", "doubleField > 50.0", true),

                // --- Boolean Tests ---
                Arguments.of("Boolean True", "booleanField = true", true),
                Arguments.of("Boolean False", "booleanField = false", false),
                Arguments.of("Boolean Implicit", "booleanField", true),
                Arguments.of("Boolean Negation", "!booleanField", false),

                // --- Null Tests ---
                Arguments.of("Null Check", "nested = null", true),
                Arguments.of("Not Null Check", "nested != null", false),

                // --- Logical Operators ---
                Arguments.of("AND True", "stringField = 'test' && intField = 100", true),
                Arguments.of("AND False", "stringField = 'test' && intField = 50", false),
                Arguments.of("OR True Left", "stringField = 'test' || intField = 50", true),
                Arguments.of("OR True Right", "stringField = 'other' || intField = 100", true),
                Arguments.of("OR False", "stringField = 'other' || intField = 50", false),

                // --- Math Functions ---
                Arguments.of("Math abs", "abs(-10) = 10", true),
                Arguments.of("Math ceil", "ceil(4.1) = 5", true),
                Arguments.of("Math floor", "floor(4.9) = 4", true),
                Arguments.of("Math round", "round(4.5) = 5", true),
                Arguments.of("Math max", "max(10, 20) = 20", true),
                Arguments.of("Math min", "min(10, 20) = 10", true),

                // --- String Functions ---
                Arguments.of("String contains", "contains(stringField, 'es') = true", true),
                Arguments.of("String length", "length(stringField) = 4", true),
                Arguments.of("String toUpperCase", "toUpperCase(stringField) = 'TEST'", true),
                Arguments.of("String toLowerCase", "toLowerCase('TEST') = 'test'", true),
                Arguments.of("String trim", "trim('  test  ') = 'test'", true),
                Arguments.of("String substring", "substring('hello', 0, 2) = 'he'", true),

                // --- Date Functions ---
                // Note: We'll use relative date checks to avoid timing issues
                Arguments.of("Date year", "year(dateField) = " + LocalDateTime.now().getYear(), true),
                Arguments.of("Date month", "month(dateField) = " + LocalDateTime.now().getMonthValue(), true),
                Arguments.of("Date day", "day(dateField) = " + LocalDateTime.now().getDayOfMonth(), true));
    }

    @ParameterizedTest(name = "{0}: {1}")
    @MethodSource("provideTestCases")
    @DisplayName("Comprehensive FEL Tests")
    void testFelExpression(String description, String expression, boolean expectedResult) {
        var entity = TestEntity.builder()
                .stringField("test")
                .intField(100)
                .longField(200L)
                .doubleField(100.5)
                .floatField(50.5f)
                .booleanField(true)
                .dateField(LocalDateTime.now())
                .nested(null)
                .build();

        // Test Interpreted Mode
        Predicate<Object> interpretedFilter = Fel.filter(expression);
        assertEquals(expectedResult, interpretedFilter.test(entity), "Interpreted mode failed for: " + expression);

        // Test JIT Mode
        Predicate<Object> jitFilter = Fel.filterJit(expression, TestEntity.class);
        assertEquals(expectedResult, jitFilter.test(entity), "JIT mode failed for: " + expression);
    }
}
