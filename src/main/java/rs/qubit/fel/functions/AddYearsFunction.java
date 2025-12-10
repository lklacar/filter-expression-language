package rs.qubit.fel.functions;

import rs.qubit.fel.evaluator.FelFunction;
import rs.qubit.fel.evaluator.value.DateTimeValue;
import rs.qubit.fel.evaluator.value.Value;
import rs.qubit.fel.exception.FilterException;

import java.util.List;

public class AddYearsFunction implements FelFunction {
    @Override
    public Value apply(List<Value> values) {
        if (values.size() != 2) {
            throw new FilterException("addYears function expects exactly two arguments");
        }

        var value = values.get(0).asDateTime();
        var years = values.get(1).asLong();

        var result = value.plusYears(years);

        return new DateTimeValue(result);
    }
}
