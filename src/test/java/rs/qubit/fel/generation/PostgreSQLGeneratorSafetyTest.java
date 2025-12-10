package rs.qubit.fel.generation;

import org.junit.jupiter.api.Test;
import rs.qubit.fel.parser.FilterParser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PostgreSQLGeneratorSafetyTest {

    private final PostgreSQLGenerator generator = new PostgreSQLGenerator();

    @Test
    void parameterizesStringsAndNumbers() {
        var expr = "name = 'Alice' && age = 30";
        var ast = new FilterParser().parse(expr);
        var ctx = new PostgreSQLGenerationContext();

        var sql = ast.accept(generator, ctx, null);

        assertEquals("((name = $1) AND (age = 30))", sql);
        assertEquals(List.of("Alice"), ctx.getParameters());
    }
}
