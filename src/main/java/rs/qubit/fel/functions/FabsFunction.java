package rs.qubit.fel.functions;

import rs.qubit.fel.evaluator.FelFunction;
import rs.qubit.fel.evaluator.value.DoubleValue;
import rs.qubit.fel.evaluator.value.Value;
import rs.qubit.fel.exception.FilterException;

import java.util.List;

public class FabsFunction implements FelFunction {
    @Override
    public Value apply(List<Value> values) {
        if (values.size() != 1) {
            throw new FilterException("fabs function expects exactly one argument");
        }

        var value = values.get(0).asDouble();

        var absValue = Math.abs(value);

        return new DoubleValue(absValue);
    }
}
