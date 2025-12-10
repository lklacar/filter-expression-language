package rs.qubit.fel;

import org.junit.jupiter.api.Test;
import rs.qubit.fel.parser.FilterParser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FelApiTest {

    @Test
    void filterFactoryCreatesPredicate() {
        var factory = new FilterFactory(new rs.qubit.fel.evaluator.DefaultEvaluationContext());
        var predicate = factory.createFilter("value = 10");
        var records = List.of(new Record(10), new Record(5));

        var result = records.stream().filter(predicate).toList();
        assertEquals(1, result.size());
        assertEquals(10, result.get(0).value);
    }

    @Test
    void fromAstOverloadsWork() {
        var ast = new FilterParser().parse("value = 10");
        var interpreted = Fel.fromAst(ast);
        var jit = Fel.fromAstJit(ast);
        var records = List.of(new Record(10), new Record(5));

        assertEquals(1, records.stream().filter(interpreted).count());
        assertEquals(1, records.stream().filter(jit).count());
    }

    static class Record {
        private final int value;

        Record(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
