package rs.qubit.fel.functions;

import rs.qubit.fel.evaluator.FelFunction;
import rs.qubit.fel.evaluator.value.LongValue;
import rs.qubit.fel.evaluator.value.Value;
import rs.qubit.fel.exception.FilterException;

import java.util.List;

public class SecondFunction implements FelFunction {
    @Override
    public Value apply(List<Value> values) {
        if (values.size() != 1) {
            throw new FilterException("second function expects exactly one argument");
        }

        var value = values.get(0).asDateTime();
        var second = value.getSecond();

        return new LongValue((long) second);


    }
}
