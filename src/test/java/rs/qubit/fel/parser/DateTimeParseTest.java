package rs.qubit.fel.parser;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DateTimeParseTest {

    @Test
    void parsesFractionalSeconds() {
        var ast = new FilterParser().parse("2024-01-01T10:00:00.123456789");
        var node = (rs.qubit.fel.parser.ast.DateTimeExpressionNode) ast;
        assertEquals(LocalDateTime.of(2024, 1, 1, 10, 0, 0, 123_456_789), node.date());
    }
}
