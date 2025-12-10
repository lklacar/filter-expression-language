package rs.qubit.fel.jit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import rs.qubit.fel.evaluator.value.ObjectValue;
import rs.qubit.fel.exception.FilterException;
import rs.qubit.fel.visitor.VisitorContext;

import java.util.Map;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JitCompilerHelperTest {

    private static final String CLASS_KEY = "testClass";

    private final VisitorContext emptyCtx = new VisitorContext() {
        @Override
        public Function<java.util.List<rs.qubit.fel.evaluator.value.Value>, rs.qubit.fel.evaluator.value.Value> getFunction(String function) {
            return null;
        }

        @Override
        public Function<Object, rs.qubit.fel.evaluator.value.Value> getMapper(Class<?> aClass) {
            return null;
        }

        @Override
        public <T> void addMapper(Class<T> type, Function<T, rs.qubit.fel.evaluator.value.Value> mapper) {
        }

        @Override
        public void addFunction(String name, rs.qubit.fel.evaluator.FelFunction function) {
        }
    };

    @AfterEach
    void clear() {
        JitCompilerHelper.clearConstants(CLASS_KEY);
    }

    @Test
    void mapIsWrappedAsObjectValue() {
        var value = JitCompilerHelper.parseValue(Map.of("k", "v"), emptyCtx);
        var objectValue = (ObjectValue) value;
        assertEquals("v", ((Map<?, ?>) objectValue.asObject()).get("k"));
    }

    @Test
    void mapperIsUsedWhenPresent() {
        VisitorContext ctx = new VisitorContext() {
            @Override
            public Function<java.util.List<rs.qubit.fel.evaluator.value.Value>, rs.qubit.fel.evaluator.value.Value> getFunction(String function) {
                return null;
            }

            @Override
            public Function<Object, rs.qubit.fel.evaluator.value.Value> getMapper(Class<?> aClass) {
                if (aClass == Custom.class) {
                    return o -> new rs.qubit.fel.evaluator.value.StringValue("mapped");
                }
                return null;
            }

            @Override
            public <T> void addMapper(Class<T> type, Function<T, rs.qubit.fel.evaluator.value.Value> mapper) {
            }

            @Override
            public void addFunction(String name, rs.qubit.fel.evaluator.FelFunction function) {
            }
        };

        var value = JitCompilerHelper.parseValue(new Custom(), ctx);
        assertEquals("mapped", value.asString());
    }

    static class Custom {
    }

    @Test
    void parseValueCoversPrimitivesAndTemporal() {
        assertEquals(1L, JitCompilerHelper.parseValue((byte) 1, emptyCtx).asLong());
        assertEquals(2L, JitCompilerHelper.parseValue((short) 2, emptyCtx).asLong());
        assertEquals(3L, JitCompilerHelper.parseValue(3, emptyCtx).asLong());
        assertEquals(4L, JitCompilerHelper.parseValue(4L, emptyCtx).asLong());
        assertEquals(5.5, JitCompilerHelper.parseValue(5.5f, emptyCtx).asDouble());
        assertEquals("c", JitCompilerHelper.parseValue('c', emptyCtx).asString());
        assertEquals("ENUM", JitCompilerHelper.parseValue(TestEnum.ENUM, emptyCtx).asString());
        var ld = java.time.LocalDate.of(2024, 1, 1);
        assertEquals(ld.atStartOfDay(), JitCompilerHelper.parseValue(ld, emptyCtx).asDateTime());
        var instant = java.time.Instant.parse("2024-01-01T00:00:00Z");
        assertEquals(instant.atZone(java.time.ZoneId.systemDefault()).toLocalDateTime(),
                JitCompilerHelper.parseValue(instant, emptyCtx).asDateTime());
    }

    enum TestEnum { ENUM }

    @Test
    void javaLangObjectsWithoutMapperThrow() {
        assertThrows(FilterException.class, () -> JitCompilerHelper.parseValue(new java.awt.Point(1, 2), emptyCtx));
    }

    @Test
    void constantsAreScopedPerClass() {
        var dt = java.time.LocalDateTime.of(2024, 1, 1, 10, 0);
        JitCompilerHelper.registerConstant(CLASS_KEY, 0, dt);
        assertEquals(dt, JitCompilerHelper.getDateTime(CLASS_KEY, 0));
        JitCompilerHelper.clearConstants(CLASS_KEY);
        assertThrows(FilterException.class, () -> JitCompilerHelper.getDateTime(CLASS_KEY, 0));
    }
}
