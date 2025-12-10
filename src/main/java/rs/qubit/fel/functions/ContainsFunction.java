package rs.qubit.fel.functions;

import rs.qubit.fel.evaluator.FelFunction;
import rs.qubit.fel.evaluator.value.BooleanValue;
import rs.qubit.fel.evaluator.value.Value;
import rs.qubit.fel.exception.FilterException;

import java.util.List;

public class ContainsFunction implements FelFunction {
    @Override
    public Value apply(List<Value> values) {
        if (values.size() != 2) {
            throw new FilterException("contains function expects exactly two arguments");
        }
        var first = values.get(0).asString();
        var second = values.get(1).asString();

        var contains = first.contains(second);

        return new BooleanValue(contains);
    }
}
