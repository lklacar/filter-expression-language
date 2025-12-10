package rs.qubit.fel.evaluator;

import org.junit.jupiter.api.Test;
import rs.qubit.fel.Fel;
import rs.qubit.fel.exception.FilterException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FilterEvaluatorInvalidTest {

    static class RecordWithJavaObject {
        private final Object payload = new java.util.Date();
        public Object getPayload() { return payload; }
    }

    static class RecordWithMapperHit {
        private final Custom payload = new Custom();
        public Custom getPayload() { return payload; }
    }

    static class Custom {
    }

    @Test
    void throwsOnUnsupportedJavaObject() {
        var records = List.of(new RecordWithJavaObject());
        assertThrows(FilterException.class, () -> records.stream().filter(Fel.filter("payload = null")).toList());
    }

    @Test
    void usesMapperWhenProvided() {
        var ctx = new DefaultEvaluationContext();
        ctx.addMapper((Class) Custom.class, new FelMapperFunction() {
            @Override
            public rs.qubit.fel.evaluator.value.Value apply(Object o) {
                return new rs.qubit.fel.evaluator.value.StringValue("mapped");
            }
        });
        var predicate = Fel.filter("payload = 'mapped'", ctx);

        var records = List.of(new RecordWithMapperHit());
        var result = records.stream().filter(predicate).toList();

        org.junit.jupiter.api.Assertions.assertEquals(1, result.size());
    }
}
