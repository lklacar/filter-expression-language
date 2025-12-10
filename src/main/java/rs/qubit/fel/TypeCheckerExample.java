package rs.qubit.fel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import rs.qubit.fel.exception.TypeCheckException;

import java.util.Objects;

/**
 * Demonstrates static type checking when a target class is provided.
 * Run with: mvn -q -Dexec.mainClass=rs.qubit.fel.TypeCheckerExample exec:java
 */
public class TypeCheckerExample {

    @Getter
    @AllArgsConstructor
    public static final class Address {
        private String city;
    }

    @Getter
    @AllArgsConstructor
    public static final class User {
        private String name;
        private Integer age;
        private Address address;
    }

    public static void main(String[] args) {
        // This expression passes type checking
        var ok = Fel.filterJit("address.city = 'Paris' && age > 18", User.class);
        var matches = ok.test(new User("Jane", 25, new Address("Paris")));
        System.out.println("Valid expression result: " + matches);

        // This expression fails type checking: mismatched types + unknown field
        try {
            Fel.filterJit("age = 'old' && unknown = 1", User.class);
        } catch (TypeCheckException e) {
            System.out.println("Type-check failed: " + e.getMessage());
        }
    }
}
