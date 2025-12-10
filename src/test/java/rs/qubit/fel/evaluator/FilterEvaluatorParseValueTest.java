package rs.qubit.fel.evaluator;

import org.junit.jupiter.api.Test;
import rs.qubit.fel.evaluator.value.BooleanValue;
import rs.qubit.fel.evaluator.value.DateTimeValue;
import rs.qubit.fel.evaluator.value.DoubleValue;
import rs.qubit.fel.evaluator.value.LongValue;
import rs.qubit.fel.evaluator.value.NullValue;
import rs.qubit.fel.evaluator.value.ObjectValue;
import rs.qubit.fel.evaluator.value.StringValue;
import rs.qubit.fel.evaluator.value.Value;
import rs.qubit.fel.parser.ast.IdentifierExpressionNode;
import rs.qubit.fel.parser.ast.SourcePosition;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FilterEvaluatorParseValueTest {

    private final FilterEvaluator evaluator = new FilterEvaluator();

    enum Color {
        RED, GREEN
    }

    static class CustomPojo {
        private final String value = "custom";

        public String getValue() {
            return value;
        }
    }

    static class RecordWithValues {
        private final Byte byteValue = 1;
        private final Short shortValue = 2;
        private final Integer integerValue = 3;
        private final Long longValue = 4L;
        private final Float floatValue = 5.5f;
        private final Double doubleValue = 6.5;
        private final Character charValue = 'z';
        private final String stringValue = "str";
        private final Boolean booleanValue = true;
        private final Color color = Color.GREEN;
        private final LocalDate date = LocalDate.of(2024, 1, 2);
        private final LocalDateTime dateTime = LocalDateTime.of(2024, 1, 2, 3, 4, 5);
        private final Instant instant = Instant.parse("2024-01-02T03:04:05Z");
        private final Map<String, String> attributes = Map.of("k", "v");
        private final CustomPojo customPojo = new CustomPojo();
        private final String nullable = null;

        public Byte getByteValue() {
            return byteValue;
        }

        public Short getShortValue() {
            return shortValue;
        }

        public Integer getIntegerValue() {
            return integerValue;
        }

        public Long getLongValue() {
            return longValue;
        }

        public Float getFloatValue() {
            return floatValue;
        }

        public Double getDoubleValue() {
            return doubleValue;
        }

        public Character getCharValue() {
            return charValue;
        }

        public String getStringValue() {
            return stringValue;
        }

        public Boolean getBooleanValue() {
            return booleanValue;
        }

        public Color getColor() {
            return color;
        }

        public LocalDate getDate() {
            return date;
        }

        public LocalDateTime getDateTime() {
            return dateTime;
        }

        public Instant getInstant() {
            return instant;
        }

        public Map<String, String> getAttributes() {
            return attributes;
        }

        public CustomPojo getCustomPojo() {
            return customPojo;
        }

        public String getNullable() {
            return nullable;
        }
    }

    private Value read(String field, DefaultEvaluationContext context, Object record) {
        return evaluator.visit(new IdentifierExpressionNode(SourcePosition.UNKNOWN, field), context, record);
    }

    @Test
    void convertsPrimitiveWrappersEnumsAndTemporals() {
        var context = new DefaultEvaluationContext();
        var record = new RecordWithValues();

        assertEquals(1L, ((LongValue) read("byteValue", context, record)).value());
        assertEquals(2L, ((LongValue) read("shortValue", context, record)).value());
        assertEquals(3L, ((LongValue) read("integerValue", context, record)).value());
        assertEquals(4L, ((LongValue) read("longValue", context, record)).value());
        assertEquals(5.5d, ((DoubleValue) read("floatValue", context, record)).value(), 0.0001);
        assertEquals(6.5d, ((DoubleValue) read("doubleValue", context, record)).value(), 0.0001);
        assertEquals("z", ((StringValue) read("charValue", context, record)).value());
        assertEquals("str", ((StringValue) read("stringValue", context, record)).value());
        assertTrue(((BooleanValue) read("booleanValue", context, record)).value());
        assertEquals("GREEN", ((StringValue) read("color", context, record)).value());

        var dateValue = (DateTimeValue) read("date", context, record);
        assertEquals(LocalDate.of(2024, 1, 2).atStartOfDay(), dateValue.value());

        var dateTimeValue = (DateTimeValue) read("dateTime", context, record);
        assertEquals(LocalDateTime.of(2024, 1, 2, 3, 4, 5), dateTimeValue.value());

        var instantValue = (DateTimeValue) read("instant", context, record);
        assertEquals(
                Instant.parse("2024-01-02T03:04:05Z").atZone(ZoneId.systemDefault()).toLocalDateTime(),
                instantValue.value());
    }

    @Test
    void keepsMapsAndObjectsWrappedWhenNoMapperIsDefined() {
        var context = new DefaultEvaluationContext();
        var record = new RecordWithValues();

        var mapValue = (ObjectValue) read("attributes", context, record);
        assertSame(record.getAttributes(), mapValue.value());

        var customValue = (ObjectValue) read("customPojo", context, record);
        assertSame(record.getCustomPojo(), customValue.value());

        assertInstanceOf(NullValue.class, read("nullable", context, record));
    }

    @Test
    void usesMapperBeforeDefaultObjectHandling() {
        var context = new DefaultEvaluationContext();
        context.addMapper((Class) CustomPojo.class, new FelMapperFunction() {
            @Override
            public Value apply(Object o) {
                return new StringValue("mapped");
            }
        });
        var record = new RecordWithValues();

        var mappedValue = (StringValue) read("customPojo", context, record);

        assertEquals("mapped", mappedValue.value());
    }
}
