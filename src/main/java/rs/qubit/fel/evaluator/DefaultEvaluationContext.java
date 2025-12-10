package rs.qubit.fel.evaluator;

import rs.qubit.fel.evaluator.value.Value;
import rs.qubit.fel.exception.FilterException;
import rs.qubit.fel.functions.*;
import rs.qubit.fel.visitor.VisitorContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class DefaultEvaluationContext implements VisitorContext {

    private final Map<String, FelFunction> functions;
    private final Map<Class<?>, FelMapperFunction> mappers;


    public DefaultEvaluationContext() {
        this.functions = new HashMap<>();
        this.mappers = new HashMap<>();

        functions.put("abs", new AbsFunction());
        functions.put("ceil", new CeilFunction());
        functions.put("contains", new ContainsFunction());
        functions.put("fabs", new FabsFunction());
        functions.put("floor", new FloorFunction());
        functions.put("length", new LengthFunction());
        functions.put("max", new MaxFunction());
        functions.put("min", new MinFunction());
        functions.put("now", new NowFunction());
        functions.put("round", new RoundFunction());
        functions.put("substring", new SubstringFunction());
        functions.put("toLowerCase", new ToLowerCaseFunction());
        functions.put("toUpperCase", new ToUpperCaseFunction());
        functions.put("trim", new TrimFunction());
        functions.put("addDays", new AddDaysFunction());
        functions.put("addMonths", new AddMonthsFunction());
        functions.put("addYears", new AddYearsFunction());
        functions.put("day", new DayFunction());
        functions.put("dayOfWeek", new DayOfWeekFunction());
        functions.put("hour", new HourFunction());
        functions.put("minute", new MinuteFunction());
        functions.put("month", new MonthFunction());
        functions.put("second", new SecondFunction());
        functions.put("year", new YearFunction());


    }

    @Override
    public Function<List<Value>, Value> getFunction(String function) {
        return Optional.ofNullable(functions.get(function))
                .orElseThrow(() -> new FilterException("Function not found: " + function));
    }

    @Override
    public Function<Object, Value> getMapper(Class<?> aClass) {
        FelMapperFunction bestMatch = null;
        Class<?> bestType = null;

        for (var entry : mappers.entrySet()) {
            var mapperType = entry.getKey();
            if (mapperType.isAssignableFrom(aClass)) {
                if (bestType == null || bestType.isAssignableFrom(mapperType)) {
                    bestType = mapperType;
                    bestMatch = entry.getValue();
                }
            }
        }

        return bestMatch;
    }

    @Override
    public <T> void addMapper(Class<T> type, Function<T, Value> mapper) {
        mappers.put(type, (FelMapperFunction) mapper);
    }

    @Override
    public void addFunction(String name, FelFunction function) {
        functions.put(name, function);
    }

    public void extendFunctions(Map<String, FelFunction> additionalFunctions) {
        functions.putAll(additionalFunctions);
    }

    public void extendMappers(Map<Class<?>, FelMapperFunction> additionalMappers) {
        mappers.putAll(additionalMappers);
    }
}
