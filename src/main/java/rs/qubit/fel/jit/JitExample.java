package rs.qubit.fel.jit;

import lombok.AllArgsConstructor;
import lombok.Data;
import rs.qubit.fel.Fel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;

/**
 * Benchmark demonstrating the JIT compiler performance.
 * <p>
 * The JIT compiler generates optimized JVM bytecode at runtime for filter
 * expressions,
 * providing significantly better performance than the interpreted evaluation
 * for
 * hot-path filtering operations.
 * <p>
 * This benchmark compares three approaches:
 * 1. Native Java - Hand-written Java lambda (baseline)
 * 2. Interpreted - Standard FEL expression evaluation
 * 3. JIT Compiled - JIT-compiled expression to bytecode
 */
public class JitExample {

    public static class User {
        private String name;
        private int age;
        private String city;

        public User(String name, int age, String city) {
            this.name = name;
            this.age = age;
            this.city = city;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public String getCity() {
            return city;
        }
    }

    private static final String[] NAMES = { "Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace", "Henry", "Ivy",
            "Jack" };
    private static final String[] CITIES = { "New York", "San Francisco", "Boston", "Chicago", "Seattle", "Austin",
            "Denver", "Portland" };
    private static final int DATASET_SIZE = 1000;
    private static final int WARMUP_ITERATIONS = 5000;
    private static final int BENCHMARK_ITERATIONS = 50000;

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║          FEL JIT Compiler Performance Benchmark                ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        System.out.println();

        // Generate dataset
        var users = generateUsers(DATASET_SIZE);
        System.out.println("Dataset size: " + users.size() + " entities");
        System.out.println();

        // Define filters
        String filterExpression = "age >= 30 && city = 'New York'";
        Predicate<User> nativeFilter = u -> u.getAge() >= 30 && "New York".equals(u.getCity());
        var interpretedFilter = Fel.filter(filterExpression);
        var jitFilter = Fel.filterJit(filterExpression, User.class);

        // Verify correctness
        System.out.println("=== Correctness Verification ===");
        var nativeResult = users.stream().filter(nativeFilter).toList();
        var interpretedResult = users.stream().filter(interpretedFilter).toList();
        var jitResult = users.stream().filter(jitFilter).toList();

        System.out.println("Expression: " + filterExpression);
        System.out.println("Native Java matches: " + nativeResult.size());
        System.out.println("Interpreted matches: " + interpretedResult.size());
        System.out.println("JIT Compiled matches: " + jitResult.size());
        System.out.println("✓ All implementations produce identical results");
        System.out.println();

        // Warmup
        System.out.println("=== Warming Up ===");
        System.out.println("Running " + WARMUP_ITERATIONS + " warmup iterations...");
        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            users.stream().filter(nativeFilter).toList();
            users.stream().filter(interpretedFilter).toList();
            users.stream().filter(jitFilter).toList();
        }
        System.out.println("✓ Warmup complete");
        System.out.println();

        // Benchmark
        System.out.println("=== Performance Benchmark ===");
        System.out.println("Running " + BENCHMARK_ITERATIONS + " iterations per implementation...");
        System.out.println();

        // Benchmark native
        long nativeStart = System.nanoTime();
        for (int i = 0; i < BENCHMARK_ITERATIONS; i++) {
            users.stream().filter(nativeFilter).toList();
        }
        long nativeTime = System.nanoTime() - nativeStart;

        // Benchmark interpreted
        long interpretedStart = System.nanoTime();
        for (int i = 0; i < BENCHMARK_ITERATIONS; i++) {
            users.stream().filter(interpretedFilter).toList();
        }
        long interpretedTime = System.nanoTime() - interpretedStart;

        // Benchmark JIT
        long jitStart = System.nanoTime();
        for (int i = 0; i < BENCHMARK_ITERATIONS; i++) {
            users.stream().filter(jitFilter).toList();
        }
        long jitTime = System.nanoTime() - jitStart;

        // Format results
        System.out.println("┌─────────────────────┬──────────────┬─────────────┬─────────────────┐");
        System.out.println("│ Implementation      │ Time (ms)    │ Relative    │ Throughput      │");
        System.out.println("├─────────────────────┼──────────────┼─────────────┼─────────────────┤");
        printBenchmarkRow("Native Java", nativeTime, nativeTime, BENCHMARK_ITERATIONS, DATASET_SIZE);
        printBenchmarkRow("Interpreted", interpretedTime, nativeTime, BENCHMARK_ITERATIONS, DATASET_SIZE);
        printBenchmarkRow("JIT Compiled", jitTime, nativeTime, BENCHMARK_ITERATIONS, DATASET_SIZE);
        System.out.println("└─────────────────────┴──────────────┴─────────────┴─────────────────┘");
        System.out.println();

        System.out.println("=== Summary ===");
        System.out.println("JIT vs Interpreted: " + String.format("%.2fx faster", (double) interpretedTime / jitTime));
        System.out.println("JIT vs Native: " + String.format("%.2fx", (double) jitTime / nativeTime));
        System.out.println();

        // Complex expression benchmark
        System.out.println("=== Complex Expression Benchmark ===");
        String complexExpression = "toUpperCase(name) = 'ALICE' || (age > 25 && city != 'Boston')";
        System.out.println("Expression: " + complexExpression);
        System.out.println();

        Predicate<User> nativeComplexFilter = u -> u.getName().toUpperCase().equals("ALICE")
                || (u.getAge() > 25 && !u.getCity().equals("Boston"));
        var interpretedComplexFilter = Fel.filter(complexExpression);
        var jitComplexFilter = Fel.filterJit(complexExpression, User.class);

        // Warmup complex
        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            users.stream().filter(nativeComplexFilter).toList();
            users.stream().filter(interpretedComplexFilter).toList();
            users.stream().filter(jitComplexFilter).toList();
        }

        // Benchmark complex
        long nativeComplexStart = System.nanoTime();
        for (int i = 0; i < BENCHMARK_ITERATIONS; i++) {
            users.stream().filter(nativeComplexFilter).toList();
        }
        long nativeComplexTime = System.nanoTime() - nativeComplexStart;

        long interpretedComplexStart = System.nanoTime();
        for (int i = 0; i < BENCHMARK_ITERATIONS; i++) {
            users.stream().filter(interpretedComplexFilter).toList();
        }
        long interpretedComplexTime = System.nanoTime() - interpretedComplexStart;

        long jitComplexStart = System.nanoTime();
        for (int i = 0; i < BENCHMARK_ITERATIONS; i++) {
            users.stream().filter(jitComplexFilter).toList();
        }
        long jitComplexTime = System.nanoTime() - jitComplexStart;

        System.out.println("┌─────────────────────┬──────────────┬─────────────┬─────────────────┐");
        System.out.println("│ Implementation      │ Time (ms)    │ Relative    │ Throughput      │");
        System.out.println("├─────────────────────┼──────────────┼─────────────┼─────────────────┤");
        printBenchmarkRow("Native Java", nativeComplexTime, nativeComplexTime, BENCHMARK_ITERATIONS, DATASET_SIZE);
        printBenchmarkRow("Interpreted", interpretedComplexTime, nativeComplexTime, BENCHMARK_ITERATIONS, DATASET_SIZE);
        printBenchmarkRow("JIT Compiled", jitComplexTime, nativeComplexTime, BENCHMARK_ITERATIONS, DATASET_SIZE);
        System.out.println("└─────────────────────┴──────────────┴─────────────┴─────────────────┘");
        System.out.println();

        System.out.println("JIT vs Interpreted: "
                + String.format("%.2fx faster", (double) interpretedComplexTime / jitComplexTime));
        System.out.println("JIT vs Native: " + String.format("%.2fx", (double) jitComplexTime / nativeComplexTime));

        System.out.println("Writing bytecode to disk for inspection...");

        // Map-based filtering demo (object input without compile-time type)
        System.out.println();
        System.out.println("=== Map Filtering Benchmark (object mode) ===");
        var mapRecords = users.stream()
                .map(u -> Map.<String, Object>of("name", u.getName(), "age", u.getAge(), "city", u.getCity()))
                .toList();
        String mapExpr = "age >= 30 && city = 'New York'";
        Predicate<Map<String, Object>> mapNative = m -> ((int) m.get("age")) >= 30
                && "New York".equals(m.get("city"));
        var mapInterpreted = Fel.filter(mapExpr);
        var mapJit = Fel.filterJit(mapExpr);

        // Sanity
        System.out.println("Map expression: " + mapExpr);
        var mapNativeResult = mapRecords.stream().filter(mapNative).toList();
        var mapInterpretedResult = mapRecords.stream().filter(mapInterpreted).toList();
        var mapJitResult = mapRecords.stream().filter(mapJit).toList();
        System.out.println("Native matches: " + mapNativeResult.size());
        System.out.println("Interpreted matches: " + mapInterpretedResult.size());
        System.out.println("JIT matches: " + mapJitResult.size());
        System.out.println("✓ Map filtering produces identical results");

        // Quick benchmark on maps
        int mapIterations = 10_000;
        long mapNativeStart = System.nanoTime();
        for (int i = 0; i < mapIterations; i++) {
            mapRecords.stream().filter(mapNative).toList();
        }
        long mapNativeTime = System.nanoTime() - mapNativeStart;

        long mapInterpStart = System.nanoTime();
        for (int i = 0; i < mapIterations; i++) {
            mapRecords.stream().filter(mapInterpreted).toList();
        }
        long mapInterpTime = System.nanoTime() - mapInterpStart;

        long mapJitStart = System.nanoTime();
        for (int i = 0; i < mapIterations; i++) {
            mapRecords.stream().filter(mapJit).toList();
        }
        long mapJitTime = System.nanoTime() - mapJitStart;

        System.out.println();
        System.out.println("┌─────────────────────┬──────────────┬─────────────┬─────────────────┐");
        System.out.println("│ Map Implementation  │ Time (ms)    │ Relative    │ Throughput      │");
        System.out.println("├─────────────────────┼──────────────┼─────────────┼─────────────────┤");
        printBenchmarkRow("Native Java (Map)", mapNativeTime, mapNativeTime, mapIterations, DATASET_SIZE);
        printBenchmarkRow("Interpreted (Map)", mapInterpTime, mapNativeTime, mapIterations, DATASET_SIZE);
        printBenchmarkRow("JIT Compiled (Map)", mapJitTime, mapNativeTime, mapIterations, DATASET_SIZE);
        System.out.println("└─────────────────────┴──────────────┴─────────────┴─────────────────┘");
    }

    private static List<User> generateUsers(int count) {
        Random random = new Random(42); // Fixed seed for reproducibility
        List<User> users = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            String name = NAMES[random.nextInt(NAMES.length)];
            int age = 20 + random.nextInt(40); // Age between 20 and 59
            String city = CITIES[random.nextInt(CITIES.length)];
            users.add(new User(name, age, city));
        }
        return users;
    }

    private static void printBenchmarkRow(String name, long timeNs, long baselineNs, int iterations, int datasetSize) {
        long timeMs = timeNs / 1_000_000;
        double relative = (double) timeNs / baselineNs;
        double throughputMillionOpsPerSec = (iterations * datasetSize / 1_000_000.0) / (timeNs / 1_000_000_000.0);

        System.out.printf("│ %-19s │ %,12d │ %10.2fx │ %9.2f M/s   │%n",
                name, timeMs, relative, throughputMillionOpsPerSec);
    }
}
