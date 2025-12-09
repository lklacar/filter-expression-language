package rs.qubit.fel.jit;

import lombok.AllArgsConstructor;
import lombok.Data;
import rs.qubit.fel.Fel;

import java.util.List;

/**
 * Example demonstrating the JIT compiler usage.
 *
 * The JIT compiler generates optimized JVM bytecode at runtime for filter expressions,
 * providing significantly better performance than the interpreted evaluation for
 * hot-path filtering operations.
 */
public class JitExample {

    @Data
    @AllArgsConstructor
    static class User {
        private String name;
        private int age;
        private String city;
    }

    public static void main(String[] args) {
        var users = List.of(
                new User("Alice", 25, "New York"),
                new User("Bob", 30, "San Francisco"),
                new User("Charlie", 35, "New York"),
                new User("David", 28, "Boston")
        );

        System.out.println("=== Interpreted Evaluation ===");
        var interpretedFilter = Fel.filter("age >= 30 && city = 'New York'");
        var interpretedResult = users.stream().filter(interpretedFilter).toList();
        System.out.println("Results: " + interpretedResult);

        System.out.println("\n=== JIT Compiled Evaluation ===");
        var jitFilter = Fel.filterJit("age >= 30 && city = 'New York'");
        var jitResult = users.stream().filter(jitFilter).toList();
        System.out.println("Results: " + jitResult);

        System.out.println("\n=== Performance Comparison ===");

        // Warm up
        for (int i = 0; i < 10000; i++) {
            users.stream().filter(interpretedFilter).toList();
            users.stream().filter(jitFilter).toList();
        }

        // Benchmark interpreted
        long interpretedStart = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            users.stream().filter(interpretedFilter).toList();
        }
        long interpretedTime = System.nanoTime() - interpretedStart;

        // Benchmark JIT
        long jitStart = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            users.stream().filter(jitFilter).toList();
        }
        long jitTime = System.nanoTime() - jitStart;

        System.out.println("Interpreted: " + (interpretedTime / 1_000_000) + " ms");
        System.out.println("JIT Compiled: " + (jitTime / 1_000_000) + " ms");
        System.out.println("Speedup: " + String.format("%.2fx", (double) interpretedTime / jitTime));

        System.out.println("\n=== Complex Expression with Functions ===");
        var complexFilter = Fel.filterJit("toUpperCase(name) = 'ALICE' || (age > 25 && city != 'Boston')");
        var complexResult = users.stream().filter(complexFilter).toList();
        System.out.println("Results: " + complexResult);
    }
}
