package rs.qubit.fel;

import org.junit.jupiter.api.Test;
import rs.qubit.fel.evaluator.value.LongValue;
import rs.qubit.fel.evaluator.value.Value;
import rs.qubit.fel.functions.AddDaysFunction;
import rs.qubit.fel.generation.PostgreSQLGenerationContext;
import rs.qubit.fel.generation.PostgreSQLGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FelPredicateTest {

    @Test
    void withMapperAndFunctionAndGenerate() {
        var predicate = Fel.filter("custom = addDays(base,1)");

        predicate.withMapper((Class) LocalDateTime.class,
                (java.util.function.Function) (rs.qubit.fel.evaluator.FelMapperFunction) o ->
                        new rs.qubit.fel.evaluator.value.DateTimeValue(((LocalDateTime) o).plusDays(1)));
        predicate.withFunction("addDays", new AddDaysFunction());

        var ctx = new PostgreSQLGenerationContext();
        var sql = predicate.generate(new PostgreSQLGenerator(), ctx);
        assertEquals("(custom = addDays(base, 1))", sql);

        var record = new TestRecord(LocalDateTime.of(2024, 1, 1, 0, 0), LocalDateTime.of(2023, 12, 31, 0, 0));
        var result = predicate.test(record);
        assertEquals(true, result);
    }

    static class TestRecord {
        private final LocalDateTime custom;
        private final LocalDateTime base;

        TestRecord(LocalDateTime custom, LocalDateTime base) {
            this.custom = custom;
            this.base = base;
        }

        public LocalDateTime getCustom() {
            return custom;
        }

        public LocalDateTime getBase() {
            return base;
        }
    }
}
