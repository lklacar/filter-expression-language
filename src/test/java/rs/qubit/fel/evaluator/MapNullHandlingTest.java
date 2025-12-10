package rs.qubit.fel.evaluator;

import org.junit.jupiter.api.Test;
import rs.qubit.fel.Fel;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MapNullHandlingTest {

    @Test
    void missingMapKeyTreatsAsNull() {
        var records = List.of(
                Map.of("other", 2),
                Map.of("other2", 3)
        );

        var result = records.stream()
                .filter(Fel.filter("missing = null"))
                .toList();

        assertEquals(2, result.size());
    }
}
