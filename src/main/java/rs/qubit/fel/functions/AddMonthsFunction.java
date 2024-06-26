package rs.qubit.fel.functions;

import rs.qubit.fel.evaluator.FelFunction;
import rs.qubit.fel.evaluator.value.DateTimeValue;
import rs.qubit.fel.evaluator.value.Value;
import rs.qubit.fel.exception.FilterException;

import java.util.List;

public class AddMonthsFunction implements FelFunction {
    @Override
    public Value apply(List<Value> values) {
        if (values.size() != 2) {
            throw new FilterException("length function accepts only one argument");
        }

        var value = values.get(0).asDateTime();
        var months = values.get(1).asLong();

        var result = value.plusMonths(months);

        return new DateTimeValue(result);
    }
}
