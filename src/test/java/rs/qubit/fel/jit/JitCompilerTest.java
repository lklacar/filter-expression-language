package rs.qubit.fel.jit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import rs.qubit.fel.Fel;
import rs.qubit.fel.evaluator.value.StringValue;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JitCompilerTest {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Address {
        private String city;
        private String street;
        private int number;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class User {
        private String name;
        private int age;
        private LocalDateTime createdAt;
        private Address address;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Product {
        private String name;
        private double price;
        private boolean inStock;
    }

    @Test
    void jitFilterUsersByAgeAndCity() {
        var users = List.of(
                new User("John", 25, LocalDateTime.now(), new Address("Belgrade", "Nemanjina", 4)),
                new User("Jane", 30, LocalDateTime.now(), new Address("Novi Sad", "Trg Slobode", 1)),
                new User("Mark", 35, LocalDateTime.now(), new Address("Belgrade", "Knez Mihailova", 2)),
                new User("Marko", 35, LocalDateTime.now(), new Address("Belgrade", "Knez Mihailova", 2))
        );

        var filter = Fel.filterJit("age >= 30 && address.city = 'Belgrade'");

        var filteredUsers = users.stream().filter(filter).toList();

        assertEquals(2, filteredUsers.size());
        assertEquals("Mark", filteredUsers.get(0).getName());
        assertEquals("Marko", filteredUsers.get(1).getName());
    }

    @Test
    void jitFilterProductsByPriceAndStock() {
        var products = List.of(
                new Product("Laptop", 1200.00, true),
                new Product("Smartphone", 700.00, false),
                new Product("Tablet", 300.00, true),
                new Product("Monitor", 150.00, true)
        );

        var filter = Fel.filterJit("price < 500 && inStock = true");

        var filteredProducts = products.stream().filter(filter).toList();

        assertEquals(2, filteredProducts.size());
        assertEquals("Tablet", filteredProducts.get(0).getName());
        assertEquals("Monitor", filteredProducts.get(1).getName());
    }

    @Test
    void jitFilterNewUsers() {
        var now = LocalDateTime.now();
        var users = List.of(
                new User("Alice", 22, now.minusDays(1), new Address("Paris", "Rue de Rivoli", 7)),
                new User("Bob", 28, now.minusMonths(6), new Address("Berlin", "Unter den Linden", 5)),
                new User("Charlie", 35, now, new Address("London", "Baker Street", 221))
        );

        var filter = Fel.filterJit("createdAt > " + now.minusHours(3));

        var filteredUsers = users.stream().filter(filter).toList();

        assertEquals(1, filteredUsers.size());
        assertEquals("Charlie", filteredUsers.get(0).getName());
    }

    @Test
    void jitFilterByMultipleConditions() {
        var users = List.of(
                new User("Dave", 44, LocalDateTime.now(), new Address("New York", "Fifth Avenue", 1)),
                new User("Eve", 34, LocalDateTime.now(), new Address("San Francisco", "Market Street", 2)),
                new User("Frank", 29, LocalDateTime.now(), new Address("New York", "Broadway", 3))
        );

        var filter = Fel.filterJit("age > 30 && address.city = 'New York'");

        var filteredUsers = users.stream().filter(filter).toList();

        assertEquals(1, filteredUsers.size());
        assertEquals("Dave", filteredUsers.get(0).getName());
    }

    @Test
    void jitFilterByNegation() {
        var users = List.of(
                new User("John", 25, LocalDateTime.now(), new Address("New York", "Fifth Avenue", 1)),
                new User("Alice", 30, LocalDateTime.now(), new Address("Los Angeles", "Sunset Boulevard", 2)),
                new User("Bob", 25, LocalDateTime.now(), new Address("New York", "Fifth Avenue", 1))
        );

        var filter = Fel.filterJit("! (age = 25)");

        var filteredUsers = users.stream().filter(filter).toList();

        assertEquals(1, filteredUsers.size());
        assertEquals("Alice", filteredUsers.get(0).getName());
    }

    @Test
    void jitFilterByLogicalOr() {
        var users = List.of(
                new User("John", 28, LocalDateTime.now(), new Address("San Francisco", "Market Street", 3)),
                new User("Alice", 22, LocalDateTime.now(), new Address("New York", "Wall Street", 12)),
                new User("Bob", 31, LocalDateTime.now(), new Address("San Francisco", "Market Street", 3))
        );

        var filter = Fel.filterJit("age < 25 || address.city = 'San Francisco'");

        var filteredUsers = users.stream().filter(filter).toList();

        assertEquals(3, filteredUsers.size());
        assertEquals("John", filteredUsers.get(0).getName());
        assertEquals("Alice", filteredUsers.get(1).getName());
        assertEquals("Bob", filteredUsers.get(2).getName());
    }

    @Test
    void jitFilterByComparisonAndGrouping() {
        var users = List.of(
                new User("John", 28, LocalDateTime.now(), new Address("San Francisco", "Market Street", 3)),
                new User("Alice", 22, LocalDateTime.now(), new Address("New York", "Wall Street", 12)),
                new User("Bob", 31, LocalDateTime.now(), new Address("San Francisco", "Market Street", 3))
        );

        var filter = Fel.filterJit("(age >= 25 && address.city = 'San Francisco') || (age < 25 && address.city = 'New York')");

        var filteredUsers = users.stream().filter(filter).toList();

        assertEquals(3, filteredUsers.size());
        assertEquals("John", filteredUsers.get(0).getName());
        assertEquals("Alice", filteredUsers.get(1).getName());
        assertEquals("Bob", filteredUsers.get(2).getName());
    }

    @Test
    void jitFilterByInequality() {
        var users = List.of(
                new User("John", 28, LocalDateTime.now(), new Address("San Francisco", "Market Street", 3)),
                new User("Alice", 22, LocalDateTime.now(), new Address("New York", "Wall Street", 12)),
                new User("Bob", 31, LocalDateTime.now(), new Address("San Francisco", "Market Street", 3))
        );

        var filter = Fel.filterJit("age != 28");

        var filteredUsers = users.stream().filter(filter).toList();

        assertEquals(2, filteredUsers.size());
        assertEquals("Alice", filteredUsers.get(0).getName());
        assertEquals("Bob", filteredUsers.get(1).getName());
    }

    @Test
    void jitFilterByNull() {
        var users = List.of(
                new User("John", 28, LocalDateTime.now(), new Address("San Francisco", "Market Street", 3)),
                new User("Alice", 22, LocalDateTime.now(), new Address("New York", "Wall Street", 12)),
                new User("Bob", 31, LocalDateTime.now(), new Address("San Francisco", "Market Street", 3))
        );

        var filter = Fel.filterJit("address = null");

        var filteredUsers = users.stream().filter(filter).toList();

        assertEquals(0, filteredUsers.size());
    }

    @Test
    void jitFilterWithLessThan() {
        var products = List.of(
                new Product("A", 100.0, true),
                new Product("B", 200.0, true),
                new Product("C", 50.0, true)
        );

        var filter = Fel.filterJit("price < 150");

        var filtered = products.stream().filter(filter).toList();

        assertEquals(2, filtered.size());
        assertEquals("A", filtered.get(0).getName());
        assertEquals("C", filtered.get(1).getName());
    }

    @Test
    void jitFilterWithLessThanOrEquals() {
        var products = List.of(
                new Product("A", 100.0, true),
                new Product("B", 200.0, true),
                new Product("C", 50.0, true)
        );

        var filter = Fel.filterJit("price <= 100");

        var filtered = products.stream().filter(filter).toList();

        assertEquals(2, filtered.size());
        assertEquals("A", filtered.get(0).getName());
        assertEquals("C", filtered.get(1).getName());
    }

    @Test
    void jitFilterWithGreaterThanOrEquals() {
        var products = List.of(
                new Product("A", 100.0, true),
                new Product("B", 200.0, true),
                new Product("C", 50.0, true)
        );

        var filter = Fel.filterJit("price >= 100");

        var filtered = products.stream().filter(filter).toList();

        assertEquals(2, filtered.size());
        assertEquals("A", filtered.get(0).getName());
        assertEquals("B", filtered.get(1).getName());
    }

    @Test
    void jitFilterWithCustomFunction() {
        var users = List.of(
                new User("john", 25, LocalDateTime.now(), null),
                new User("ALICE", 30, LocalDateTime.now(), null),
                new User("bob", 35, LocalDateTime.now(), null)
        );

        var filter = Fel.filterJit("toUpperCase(name) = 'JOHN' || toUpperCase(name) = 'BOB'");

        var filteredUsers = users.stream().filter(filter).toList();

        assertEquals(2, filteredUsers.size());
        assertEquals("john", filteredUsers.get(0).getName());
        assertEquals("bob", filteredUsers.get(1).getName());
    }

    @Test
    void jitFilterWithStringLiteral() {
        var users = List.of(
                new User("John", 25, LocalDateTime.now(), null),
                new User("Alice", 30, LocalDateTime.now(), null)
        );

        var filter = Fel.filterJit("name = 'John'");

        var filteredUsers = users.stream().filter(filter).toList();

        assertEquals(1, filteredUsers.size());
        assertEquals("John", filteredUsers.get(0).getName());
    }

    @Test
    void jitFilterWithBooleanLiteral() {
        var products = List.of(
                new Product("A", 100.0, true),
                new Product("B", 200.0, false),
                new Product("C", 50.0, true)
        );

        var filter = Fel.filterJit("inStock = true");

        var filtered = products.stream().filter(filter).toList();

        assertEquals(2, filtered.size());
        assertEquals("A", filtered.get(0).getName());
        assertEquals("C", filtered.get(1).getName());
    }

    @Test
    void jitFilterWithLongLiteral() {
        var users = List.of(
                new User("John", 25, LocalDateTime.now(), null),
                new User("Alice", 30, LocalDateTime.now(), null)
        );

        var filter = Fel.filterJit("age = 30");

        var filteredUsers = users.stream().filter(filter).toList();

        assertEquals(1, filteredUsers.size());
        assertEquals("Alice", filteredUsers.get(0).getName());
    }

    @Test
    void jitFilterWithDoubleLiteral() {
        var products = List.of(
                new Product("A", 100.5, true),
                new Product("B", 200.0, false),
                new Product("C", 50.0, true)
        );

        var filter = Fel.filterJit("price = 100.5");

        var filtered = products.stream().filter(filter).toList();

        assertEquals(1, filtered.size());
        assertEquals("A", filtered.get(0).getName());
    }

    /**
     * Comparison test: Ensures JIT and interpreted versions produce identical results
     */
    @Test
    void jitAndInterpretedProduceSameResults() {
        var users = List.of(
                new User("John", 25, LocalDateTime.now(), new Address("Belgrade", "Nemanjina", 4)),
                new User("Jane", 30, LocalDateTime.now(), new Address("Novi Sad", "Trg Slobode", 1)),
                new User("Mark", 35, LocalDateTime.now(), new Address("Belgrade", "Knez Mihailova", 2)),
                new User("Marko", 35, LocalDateTime.now(), new Address("Belgrade", "Knez Mihailova", 2))
        );

        String filterExpr = "age >= 30 && address.city = 'Belgrade'";
        var jitFilter = Fel.filterJit(filterExpr);
        var interpretedFilter = Fel.filter(filterExpr);

        var jitResults = users.stream().filter(jitFilter).toList();
        var interpretedResults = users.stream().filter(interpretedFilter).toList();

        assertEquals(interpretedResults.size(), jitResults.size());
        for (int i = 0; i < jitResults.size(); i++) {
            assertEquals(interpretedResults.get(i), jitResults.get(i));
        }
    }

    /**
     * Comparison test: Complex expression with multiple operators
     */
    @Test
    void jitAndInterpretedComplexExpressionSameResults() {
        var users = List.of(
                new User("Alice", 22, LocalDateTime.now(), new Address("Paris", "Rue", 1)),
                new User("Bob", 28, LocalDateTime.now(), new Address("Berlin", "Strasse", 2)),
                new User("Charlie", 35, LocalDateTime.now(), new Address("London", "Street", 3)),
                new User("David", 40, LocalDateTime.now(), new Address("Paris", "Avenue", 4))
        );

        String filterExpr = "(age < 30 && address.city = 'Paris') || (age >= 35 && address.city != 'Berlin')";
        var jitFilter = Fel.filterJit(filterExpr);
        var interpretedFilter = Fel.filter(filterExpr);

        var jitResults = users.stream().filter(jitFilter).toList();
        var interpretedResults = users.stream().filter(interpretedFilter).toList();

        assertEquals(interpretedResults.size(), jitResults.size());
        for (int i = 0; i < jitResults.size(); i++) {
            assertEquals(interpretedResults.get(i).getName(), jitResults.get(i).getName());
        }
    }

    @Test
    void jitCompilerHandlesNestedDotExpression() {
        var users = List.of(
                new User("John", 25, LocalDateTime.now(), new Address("Belgrade", "Nemanjina", 4)),
                new User("Jane", 30, LocalDateTime.now(), new Address("Novi Sad", "Trg Slobode", 15))
        );

        var filter = Fel.filterJit("address.number > 10");

        var filteredUsers = users.stream().filter(filter).toList();

        assertEquals(1, filteredUsers.size());
        assertEquals("Jane", filteredUsers.get(0).getName());
    }

    @Test
    void jitCompilerHandlesShortCircuitAnd() {
        var users = List.of(
                new User("John", 25, LocalDateTime.now(), null)
        );

        // This should short-circuit and not throw NPE
        var filter = Fel.filterJit("age > 30 && address.city = 'Belgrade'");

        var filteredUsers = users.stream().filter(filter).toList();

        assertEquals(0, filteredUsers.size());
    }

    @Test
    void jitCompilerHandlesShortCircuitOr() {
        var users = List.of(
                new User("John", 25, LocalDateTime.now(), null)
        );

        // This should short-circuit and not evaluate address.city
        var filter = Fel.filterJit("age = 25 || address.city = 'Belgrade'");

        var filteredUsers = users.stream().filter(filter).toList();

        assertEquals(1, filteredUsers.size());
        assertEquals("John", filteredUsers.get(0).getName());
    }
}
