package rs.qubit.fel.functions;

import org.junit.jupiter.api.Test;
import rs.qubit.fel.evaluator.value.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoreFunctionsTest {

    @Test
    void numericFunctionsWork() {
        assertEquals(3.0, ((DoubleValue) new CeilFunction().apply(List.of(new DoubleValue(2.2)))).value());
        assertEquals(2.0, ((DoubleValue) new FloorFunction().apply(List.of(new DoubleValue(2.8)))).value());
        assertEquals(3.0, ((DoubleValue) new RoundFunction().apply(List.of(new DoubleValue(2.6)))).value());
        assertEquals(5.0, ((DoubleValue) new FabsFunction().apply(List.of(new DoubleValue(-5.0)))).value());
        assertEquals(5L, ((LongValue) new LengthFunction().apply(List.of(new StringValue("hello")))).value());
    }

    @Test
    void minAndMaxWork() {
        var min = (DoubleValue) new MinFunction().apply(List.of(new DoubleValue(2.5), new DoubleValue(5.0)));
        var max = (DoubleValue) new MaxFunction().apply(List.of(new DoubleValue(2.5), new DoubleValue(5.0)));
        assertEquals(2.5, min.value());
        assertEquals(5.0, max.value());
    }

    @Test
    void substringWorks() {
        var result = (StringValue) new SubstringFunction().apply(List.of(new StringValue("filter"), new LongValue(1L), new LongValue(3L)));
        assertEquals("il", result.value());
    }

    @Test
    void datePartsAndAddsWork() {
        var date = LocalDateTime.of(2024, 5, 10, 9, 30, 15);
        assertEquals(2025, ((DateTimeValue) new AddYearsFunction().apply(List.of(new DateTimeValue(date), new LongValue(1L)))).value().getYear());
        assertEquals(6, ((DateTimeValue) new AddMonthsFunction().apply(List.of(new DateTimeValue(date), new LongValue(1L)))).value().getMonthValue());
        assertEquals(11, ((DateTimeValue) new AddDaysFunction().apply(List.of(new DateTimeValue(date), new LongValue(1L)))).value().getDayOfMonth());

        assertEquals(2024L, ((LongValue) new YearFunction().apply(List.of(new DateTimeValue(date)))).value());
        assertEquals(5L, ((LongValue) new MonthFunction().apply(List.of(new DateTimeValue(date)))).value());
        assertEquals(10L, ((LongValue) new DayFunction().apply(List.of(new DateTimeValue(date)))).value());
        assertEquals(5L, ((LongValue) new DayOfWeekFunction().apply(List.of(new DateTimeValue(date)))).value());
        assertEquals(9L, ((LongValue) new HourFunction().apply(List.of(new DateTimeValue(date)))).value());
        assertEquals(30L, ((LongValue) new MinuteFunction().apply(List.of(new DateTimeValue(date)))).value());
        assertEquals(15L, ((LongValue) new SecondFunction().apply(List.of(new DateTimeValue(date)))).value());
    }

    @Test
    void toLowerUpperWork() {
        assertEquals("abc", ((StringValue) new ToLowerCaseFunction().apply(List.of(new StringValue("AbC")))).value());
        assertEquals("ABC", ((StringValue) new ToUpperCaseFunction().apply(List.of(new StringValue("aBc")))).value());
    }
}
