package rs.qubit.fel.generation;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PostgreSQLGeneratorTest {

    private final PostgreSQLGenerator generator = new PostgreSQLGenerator();

    @Test
    void testLogicalOperations() {
        assertSql("a || b", "(a OR b)", List.of());
        assertSql("a && b", "(a AND b)", List.of());
        assertSql("!a", "NOT (a)", List.of());
    }

    @Test
    void testComparisons() {
        assertSql("a = b", "(a = b)", List.of());
        assertSql("a != b", "(a != b)", List.of());
        assertSql("a > b", "(a > b)", List.of());
        assertSql("a < b", "(a < b)", List.of());
        assertSql("a >= b", "(a >= b)", List.of());
        assertSql("a <= b", "(a <= b)", List.of());
    }

    @Test
    void testLiterals() {
        assertSql("'hello'", "$1", List.of("hello"));
        assertSql("true", "TRUE", List.of());
        assertSql("false", "FALSE", List.of());
        assertSql("null", "NULL", List.of());
        assertSql("123", "123", List.of());
        assertSql("123.45", "123.45", List.of());
    }

    @Test
    void testFunctionCalls() {
        assertSql("myFunc()", "myFunc()", List.of());
        assertSql("myFunc(a)", "myFunc(a)", List.of());
        assertSql("myFunc(a, b)", "myFunc(a, b)", List.of());
    }

    @Test
    void testDotExpression() {
        assertSql("user.name", "user.name", List.of());
        assertSql("user.address.city", "user.address.city", List.of());
    }

    @Test
    void testComplexExpression() {
        assertSql("age >= 18 && (status = 'ACTIVE' || role = 'ADMIN')",
                "((age >= 18) AND ((status = $1) OR (role = $2)))",
                List.of("ACTIVE", "ADMIN"));
    }

    private void assertSql(String expression, String expectedSql, List<Object> expectedParams) {
        var ctx = new PostgreSQLGenerationContext();
        var node = new rs.qubit.fel.parser.FilterParser().parse(expression);
        String sql = node.accept(generator, ctx, null);
        assertEquals(expectedSql, sql);
        assertEquals(expectedParams, ctx.getParameters());
    }
}
