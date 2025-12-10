package rs.qubit.fel.evaluator;

import org.junit.jupiter.api.Test;
import rs.qubit.fel.evaluator.value.StringValue;
import rs.qubit.fel.evaluator.value.Value;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultEvaluationContextTest {

    @Test
    void mostSpecificMapperIsChosen() {
        var ctx = new DefaultEvaluationContext();
        ctx.addMapper((Class) Number.class, new FelMapperFunction() {
            @Override
            public Value apply(Object o) {
                return new StringValue("number");
            }
        });
        ctx.addMapper((Class) Integer.class, new FelMapperFunction() {
            @Override
            public Value apply(Object o) {
                return new StringValue("int");
            }
        });

        Function<Object, rs.qubit.fel.evaluator.value.Value> mapper = ctx.getMapper(Integer.class);
        var result = mapper.apply(5);
        assertEquals("int", result.asString());
    }
}
