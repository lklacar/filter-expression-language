package rs.qubit.fel.functions;

import rs.qubit.fel.evaluator.FelFunction;
import rs.qubit.fel.evaluator.value.DoubleValue;
import rs.qubit.fel.evaluator.value.Value;
import rs.qubit.fel.exception.FilterException;

import java.util.List;

public class CeilFunction implements FelFunction {
    @Override
    public Value apply(List<Value> values) {
        if (values.size() != 1) {
            throw new FilterException("ceil function expects exactly one argument");
        }

        var value = values.get(0).asDouble();
        var roundValue = Math.ceil(value);

        return new DoubleValue(roundValue);
    }
}
