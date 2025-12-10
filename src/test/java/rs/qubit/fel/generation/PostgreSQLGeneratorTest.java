package rs.qubit.fel.generation;

import org.junit.jupiter.api.Test;

import rs.qubit.fel.parser.ast.ExpressionNode;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PostgreSQLGeneratorTest {

    private final PostgreSQLGenerator generator = new PostgreSQLGenerator();
    private final PostgreSQLGenerationContext context = new PostgreSQLGenerationContext();

    @Test
    void testLogicalOperations() {
        assertSql("a || b", "(a OR b)");
        assertSql("a && b", "(a AND b)");
        assertSql("!a", "NOT (a)");
    }

    @Test
    void testComparisons() {
        assertSql("a = b", "(a = b)");
        assertSql("a != b", "(a != b)");
        assertSql("a > b", "(a > b)");
        assertSql("a < b", "(a < b)");
        assertSql("a >= b", "(a >= b)");
        assertSql("a <= b", "(a <= b)");
    }

    @Test
    void testLiterals() {
        assertSql("'hello'", "'hello'");
        assertSql("true", "TRUE");
        assertSql("false", "FALSE");
        assertSql("null", "NULL");
        assertSql("123", "123");
        assertSql("123.45", "123.45");
    }

    @Test
    void testFunctionCalls() {
        assertSql("myFunc()", "myFunc()");
        assertSql("myFunc(a)", "myFunc(a)");
        assertSql("myFunc(a, b)", "myFunc(a, b)");
    }

    @Test
    void testDotExpression() {
        assertSql("user.name", "user.name");
        assertSql("user.address.city", "user.address.city");
    }

    @Test
    void testComplexExpression() {
        assertSql("age >= 18 && (status = 'ACTIVE' || role = 'ADMIN')",
                "((age >= 18) AND ((status = 'ACTIVE') OR (role = 'ADMIN')))");
    }

    private void assertSql(String expression, String expectedSql) {
        ExpressionNode node = new rs.qubit.fel.parser.FilterParser().parse(expression);
        String sql = node.accept(generator, context, null);
        assertEquals(expectedSql, sql);
    }
}
