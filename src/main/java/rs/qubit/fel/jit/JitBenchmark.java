package rs.qubit.fel.jit;

import lombok.Getter;
import org.openjdk.jmh.annotations.*;
import rs.qubit.fel.Fel;
import rs.qubit.fel.FelPredicate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class JitBenchmark {

    @SuppressWarnings("ClassCanBeRecord")
    @Getter
    public static class User {
        private final String name;
        private final int age;
        private final String city;

        public User(String name, int age, String city) {
            this.name = name;
            this.age = age;
            this.city = city;
        }
    }

    private static final String[] NAMES = {
            "Alice", "Bob", "Charlie", "David", "Eve",
            "Frank", "Grace", "Henry", "Ivy", "Jack"
    };

    private static final String[] CITIES = {
            "New York", "San Francisco", "Boston", "Chicago",
            "Seattle", "Austin", "Denver", "Portland"
    };

    private static final int DATASET_SIZE = 1000;

    @State(Scope.Benchmark)
    public static class BenchmarkState {
        List<User> users;

        String simpleExpression;
        Predicate<User> nativeSimpleFilter;
        FelPredicate interpretedSimpleFilter;
        FelPredicate jitSimpleFilter;              // JIT with direct access
        FelPredicate jitSimpleReflectionFilter;    // JIT with reflection

        String complexExpression;
        Predicate<User> nativeComplexFilter;
        FelPredicate interpretedComplexFilter;
        FelPredicate jitComplexFilter;             // JIT with direct access
        FelPredicate jitComplexReflectionFilter;   // JIT with reflection

        @Setup(Level.Trial)
        public void setup() {
            System.out.println("╔════════════════════════════════════════════════════════════════╗");
            System.out.println("║          FEL JIT Compiler Performance Benchmark (JMH)          ║");
            System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

            users = generateUsers(DATASET_SIZE);
            System.out.println("Dataset size: " + users.size() + " entities\n");

            // Simple expression
            simpleExpression = "age >= 30 && city = 'New York'";
            nativeSimpleFilter = u -> u.getAge() >= 30 && "New York".equals(u.getCity());
            interpretedSimpleFilter = Fel.filter(simpleExpression);
            jitSimpleFilter = Fel.filterJit(simpleExpression, User.class);  // direct access
            jitSimpleReflectionFilter = Fel.filterJit(simpleExpression);    // reflection access

            System.out.println("=== Correctness Verification (Simple) ===");
            var nativeSimpleResult = users.stream().filter(nativeSimpleFilter).toList();
            var interpretedSimpleResult = users.stream().filter(interpretedSimpleFilter).toList();
            var jitSimpleResult = users.stream().filter(jitSimpleFilter).toList();
            var jitSimpleReflectionResult = users.stream().filter(jitSimpleReflectionFilter).toList();

            System.out.println("Expression: " + simpleExpression);
            System.out.println("Native Java matches: " + nativeSimpleResult.size());
            System.out.println("Interpreted matches: " + interpretedSimpleResult.size());
            System.out.println("JIT Compiled (direct) matches: " + jitSimpleResult.size());
            System.out.println("JIT Compiled (reflection) matches: " + jitSimpleReflectionResult.size());

            var simpleExpected = nativeSimpleResult.size();
            if (simpleExpected != interpretedSimpleResult.size()
                    || simpleExpected != jitSimpleResult.size()
                    || simpleExpected != jitSimpleReflectionResult.size()) {
                throw new IllegalStateException("Simple expression results differ between implementations");
            }

            // Complex expression
            complexExpression = "toUpperCase(name) = 'ALICE' || (age > 25 && city != 'Boston')";
            nativeComplexFilter = u -> u.getName().equalsIgnoreCase("ALICE")
                    || (u.getAge() > 25 && !"Boston".equals(u.getCity()));
            interpretedComplexFilter = Fel.filter(complexExpression);
            jitComplexFilter = Fel.filterJit(complexExpression, User.class);  // direct access
            jitComplexReflectionFilter = Fel.filterJit(complexExpression);    // reflection access

            System.out.println("\n=== Correctness Verification (Complex) ===");
            System.out.println("Expression: " + complexExpression);

            var nativeComplexResult = users.stream().filter(nativeComplexFilter).toList();
            var interpretedComplexResult = users.stream().filter(interpretedComplexFilter).toList();
            var jitComplexResult = users.stream().filter(jitComplexFilter).toList();
            var jitComplexReflectionResult = users.stream().filter(jitComplexReflectionFilter).toList();

            System.out.println("Native Java matches: " + nativeComplexResult.size());
            System.out.println("Interpreted matches: " + interpretedComplexResult.size());
            System.out.println("JIT Compiled (direct) matches: " + jitComplexResult.size());
            System.out.println("JIT Compiled (reflection) matches: " + jitComplexReflectionResult.size());

            var complexExpected = nativeComplexResult.size();
            if (complexExpected != interpretedComplexResult.size()
                    || complexExpected != jitComplexResult.size()
                    || complexExpected != jitComplexReflectionResult.size()) {
                throw new IllegalStateException("Complex expression results differ between implementations");
            }

            System.out.println("\nSetup complete. JMH will now run the benchmarks.\n");
        }
    }

    @Benchmark
    public List<User> simpleNativeFilter(BenchmarkState state) {
        return state.users.stream().filter(state.nativeSimpleFilter).toList();
    }

    @Benchmark
    public List<User> simpleInterpretedFilter(BenchmarkState state) {
        return state.users.stream().filter(state.interpretedSimpleFilter).toList();
    }

    @Benchmark
    public List<User> simpleJitFilter(BenchmarkState state) {
        return state.users.stream().filter(state.jitSimpleFilter).toList();
    }

    @Benchmark
    public List<User> simpleJitReflectionFilter(BenchmarkState state) {
        return state.users.stream().filter(state.jitSimpleReflectionFilter).toList();
    }

    @Benchmark
    public List<User> complexNativeFilter(BenchmarkState state) {
        return state.users.stream().filter(state.nativeComplexFilter).toList();
    }

    @Benchmark
    public List<User> complexInterpretedFilter(BenchmarkState state) {
        return state.users.stream().filter(state.interpretedComplexFilter).toList();
    }

    @Benchmark
    public List<User> complexJitFilter(BenchmarkState state) {
        return state.users.stream().filter(state.jitComplexFilter).toList();
    }

    @Benchmark
    public List<User> complexJitReflectionFilter(BenchmarkState state) {
        return state.users.stream().filter(state.jitComplexReflectionFilter).toList();
    }

    private static List<User> generateUsers(int count) {
        var random = new Random(42);
        var users = new ArrayList<User>(count);
        for (var i = 0; i < count; i++) {
            var name = NAMES[random.nextInt(NAMES.length)];
            var age = 20 + random.nextInt(40);
            var city = CITIES[random.nextInt(CITIES.length)];
            users.add(new User(name, age, city));
        }
        return users;
    }

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
}
