package rs.qubit.fel.generation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PostgreSQLGenerationContextTest {

    @Test
    void addParameterReturnsIndexAndIsUnmodifiable() {
        var ctx = new PostgreSQLGenerationContext();
        var idx1 = ctx.addParameter("v1");
        var idx2 = ctx.addParameter("v2");

        assertEquals(1, idx1);
        assertEquals(2, idx2);
        assertEquals("v2", ctx.getParameters().get(1));
    }
}
