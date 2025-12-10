package rs.qubit.fel;

import lombok.Getter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ComprehensiveFelTest {

    @Getter
    public static class TestEntity {
        // Getters needed for FEL to access fields
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

    }

    static Stream<Arguments> provideTestCases() {
        return Stream.of(
                // --- String Tests ---
                Arguments.of("String Equality", "stringField = 'test'", true, false),
                Arguments.of("String Equality False", "stringField = 'other'", false, false),
                Arguments.of("String Inequality", "stringField != 'other'", true, false),
                Arguments.of("String Inequality False", "stringField != 'test'", false, false),

                // --- Number Tests ---
                Arguments.of("Int Equality", "intField = 100", true, false),
                Arguments.of("Int Greater Than", "intField > 50", true, false),
                Arguments.of("Int Less Than", "intField < 150", true, false),
                Arguments.of("Double Equality", "doubleField = 100.5", true, false),
                Arguments.of("Double Greater Than", "doubleField > 50.0", true, false),

                // --- Boolean Tests ---
                Arguments.of("Boolean True", "booleanField = true", true, false),
                Arguments.of("Boolean False", "booleanField = false", false, false),
                Arguments.of("Boolean Implicit", "booleanField", true, false),
                Arguments.of("Boolean Negation", "!booleanField", false, false),

                // --- Null Tests ---
                Arguments.of("Null Check", "nested = null", true, false),
                Arguments.of("Not Null Check", "nested != null", false, false),

                // --- Logical Operators ---
                Arguments.of("AND True", "stringField = 'test' && intField = 100", true, false),
                Arguments.of("AND False", "stringField = 'test' && intField = 50", false, false),
                Arguments.of("OR True Left", "stringField = 'test' || intField = 50", true, false),
                Arguments.of("OR True Right", "stringField = 'other' || intField = 100", true, false),
                Arguments.of("OR False", "stringField = 'other' || intField = 50", false, false),

                // --- Math Functions ---
                Arguments.of("Math abs", "abs(-10) = 10", true, false),
                Arguments.of("Math ceil", "ceil(4.1) = 5", true, false),
                Arguments.of("Math floor", "floor(4.9) = 4", true, false),
                Arguments.of("Math round", "round(4.5) = 5", true, false),
                Arguments.of("Math max", "max(10, 20) = 20", true, false),
                Arguments.of("Math min", "min(10, 20) = 10", true, false),

                // --- String Functions ---
                Arguments.of("String contains", "contains(stringField, 'es') = true", true, false),
                Arguments.of("String length", "length(stringField) = 4", true, false),
                Arguments.of("String toUpperCase", "toUpperCase(stringField) = 'TEST'", true, false),
                Arguments.of("String toLowerCase", "toLowerCase('TEST') = 'test'", true, false),
                Arguments.of("String trim", "trim('  test  ') = 'test'", true, false),
                Arguments.of("String substring", "substring('hello', 0, 2) = 'he'", true, false),

                // --- Date Functions ---
                // Note: We'll use relative date checks to avoid timing issues
                Arguments.of("Date year", "year(dateField) = " + LocalDateTime.now().getYear(), true, false),
                Arguments.of("Date month", "month(dateField) = " + LocalDateTime.now().getMonthValue(), true, false),
                Arguments.of("Date day", "day(dateField) = " + LocalDateTime.now().getDayOfMonth(), true, false),

                // --- Dot notation / nested field tests (nestedPresent = true) ---
                Arguments.of("Nested Not Null", "nested != null", true, true),
                Arguments.of("Nested String Equality", "nested.stringField = 'nested'", true, true),
                Arguments.of("Nested String Inequality", "nested.stringField != 'other'", true, true),
                Arguments.of("Nested Int Equality", "nested.intField = 10", true, true),
                Arguments.of("Deep Nested Null", "nested.nested = null", true, true)
        );
    }

    @ParameterizedTest(name = "{0}: {1}")
    @MethodSource("provideTestCases")
    @DisplayName("Comprehensive FEL Tests")
    void testFelExpression(String description, String expression, boolean expectedResult, boolean nestedPresent) {
        TestEntity nestedEntity = null;
        if (nestedPresent) {
            nestedEntity = TestEntity.builder()
                    .stringField("nested")
                    .intField(10)
                    .longField(20L)
                    .doubleField(10.5)
                    .floatField(5.5f)
                    .booleanField(false)
                    .dateField(LocalDateTime.now())
                    .nested(null) // for deep nested null check
                    .build();
        }

        var entity = TestEntity.builder()
                .stringField("test")
                .intField(100)
                .longField(200L)
                .doubleField(100.5)
                .floatField(50.5f)
                .booleanField(true)
                .dateField(LocalDateTime.now())
                .nested(nestedEntity)
                .build();

        // Test Interpreted Mode
        Predicate<Object> interpretedFilter = Fel.filter(expression);
        assertEquals(expectedResult, interpretedFilter.test(entity), "Interpreted mode failed for: " + expression);

        // Test JIT Mode with direct access
        Predicate<Object> jitFilter = Fel.filterJit(expression, TestEntity.class);
        assertEquals(expectedResult, jitFilter.test(entity), "JIT mode failed for: " + expression);

        // Test JIT Mode with reflection access
        Predicate<Object> jitFilterReflection = Fel.filterJit(expression);
        assertEquals(expectedResult, jitFilterReflection.test(entity), "JIT mode with reflection failed for: " + expression);
    }
}