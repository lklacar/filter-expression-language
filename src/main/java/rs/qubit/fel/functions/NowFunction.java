package rs.qubit.fel.functions;

import rs.qubit.fel.evaluator.FelFunction;
import rs.qubit.fel.evaluator.value.DateTimeValue;
import rs.qubit.fel.evaluator.value.Value;
import rs.qubit.fel.exception.FilterException;

import java.util.List;

public class NowFunction implements FelFunction {
    @Override
    public Value apply(List<Value> values) {
        if (!values.isEmpty()) {
            throw new FilterException("now function expects no arguments");
        }

        var localDateTime = java.time.LocalDateTime.now();

        return new DateTimeValue(localDateTime);
    }
}
