package rs.qubit.fel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MainTest {

    @Test
    void mainRuns() {
        assertDoesNotThrow(() -> Main.main(new String[0]));
    }
}
