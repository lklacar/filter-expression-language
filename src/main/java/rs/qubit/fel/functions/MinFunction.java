package rs.qubit.fel.functions;

import rs.qubit.fel.evaluator.FelFunction;
import rs.qubit.fel.evaluator.value.DoubleValue;
import rs.qubit.fel.evaluator.value.Value;
import rs.qubit.fel.exception.FilterException;

import java.util.List;

public class MinFunction implements FelFunction {
    @Override
    public Value apply(List<Value> values) {
        if (values.size() != 2) {
            throw new FilterException("min function expects exactly two arguments");
        }

        var value1 = values.get(0).asDouble();
        var value2 = values.get(1).asDouble();
        var minValue = Math.min(value1, value2);

        return new DoubleValue(minValue);
    }
}
