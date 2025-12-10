# Filter Expression Language (FEL) ![Coverage](.github/badges/jacoco.svg?raw=1)

Filter Expression Language (FEL) is a lightweight, open-source Java library that simplifies filtering collections of objects using human-readable string expressions. FEL aims to provide an intuitive and flexible way to apply filters to your data without writing verbose and complex code.

## Features

- **JIT Compiler**: Generates optimized JVM bytecode for 10-27x performance improvement
- Optional static type-checking when a target class is provided (unknown fields, type mismatches, nullability hints with line/column diagnostics)
- Simple and intuitive string-based filter expressions
- Integration with Java streams
- Built-in and custom functions support
- Custom type mappers
- SQL code generation
- Lightweight and easy to use

## Quick Start

### Standard Interpreted Mode
Basic usage of FEL involves defining a filter expression as a string and applying it to a collection of objects using Java streams:

```java
var filterString = "(address.street = 'Main Street' && age > 30) || toUpperCase(firstName) = 'JOHN'";

var filteredUsers = users
      .stream()
      .filter(Fel.filter(filterString))
      .toList();
```

### JIT Compiled Mode (Recommended for Performance)
For performance-critical applications, use the JIT compiler which generates optimized bytecode:

```java
// JIT compiled - 10-27x faster than interpreted mode!
var jitFilter = Fel.filterJit("age >= 30 && city = 'New York'", User.class);

var filteredUsers = users
      .stream()
      .filter(jitFilter)
      .toList();
```

The JIT compiler provides near-native Java performance while maintaining the flexibility of string-based expressions.

## Installation

To include FEL in your project, add the following dependency to your `pom.xml` if you are using Maven:

```xml
<dependency>
  <groupId>rs.qubit</groupId>
  <artifactId>filter-expression-language</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency>
```

If you are using Gradle, add the following to your `build.gradle`:

```groovy
implementation 'rs.qubit:filter-expression-language:1.0.0'
```

## Usage

Here's a quick example to get you started with FEL:

```java
public class Main {

  @Data
  @AllArgsConstructor
  static class Address {
    private String street;
  }

  @Data
  @AllArgsConstructor
  static class User {
    private String firstName;
    private String lastName;
    private Address address;
  }

  public static void main(String[] args) {

    var users = List.of(
            new User("John", "Doe", new Address("Wall Street")),
            new User("Jane", "Doe", new Address("Wall Street")),
            new User("Alice", "Smith", new Address("Main Street"))
    );

    var filteredUsers = users
            .stream()
            .filter(Fel.filter("address.street = 'Main Street'"))
            .toList();

    System.out.println(filteredUsers);
  }
}
```

`Fel.filter` method is used to create a filter predicate from the filter expression string. The resulting predicate can be applied to a stream of objects to filter the data based on the specified criteria.
It creates a predicate that can be used with Java streams to filter a collection of objects based on the specified criteria.
This means it can also be used to parallelize the filtering process using Java streams.

```java
var filteredUsers = users
      .parallelStream()
      .filter(Fel.filter("address.street = 'Main Street'"))
      .toList();
```

### Filtering Maps (including nested maps)

You can filter `Map<String, Object>` collections the same way you filter objects. Keys are accessed as identifiers, and nested maps work with dot access:

```java
var records = List.of(
        Map.of("name", "John", "age", 25, "address", Map.of("city", "Paris")),
        Map.of("name", "Jane", "age", 30, "address", Map.of("city", "Belgrade"))
);

var adultsInBelgrade = records.stream()
        .filter(Fel.filter("age >= 30 && address.city = 'Belgrade'"))
        .toList();
```


### Explanation

1. **Define Your Classes:** Create the classes you want to filter. In this example, we have `User` and `Address` classes.

2. **Initialize Your Data:** Create a list of users with different addresses.

3. **Apply the Filter:** Use the `fel` method to define your filter expression. In this case, we're filtering users whose `address.street` is `"Main Street"`.

4. **Filter and Collect:** Use Java streams to apply the filter and collect the results.

5. **Output the Results:** Print the filtered list of users.

## JIT Compiler - High-Performance Filtering

FEL includes a **Just-In-Time (JIT) compiler** that generates optimized JVM bytecode for filter expressions at runtime. The JIT compiler provides significant performance improvements over interpreted evaluation, making it ideal for hot-path filtering operations.

### Performance

The JIT compiler delivers exceptional performance gains:

**Simple Expression Benchmark** (`age >= 30 && city = 'New York'`):
- **Interpreted**: 9,050 ms for 50M operations
- **JIT Compiled**: 333 ms for 50M operations
- **Native Java**: 290 ms for 50M operations
- **Speedup: 27x faster than interpreted, 1.15x vs native Java**

**Complex Expression Benchmark** (`toUpperCase(name) = 'ALICE' || (age > 25 && city != 'Boston')`):
- **Interpreted**: 15,587 ms for 50M operations
- **JIT Compiled**: 1,466 ms for 50M operations
- **Native Java**: 1,538 ms for 50M operations
- **Speedup: 10.6x faster than interpreted, 0.95x vs native Java (5% faster!)**

### Basic Usage

Replace `Fel.filter()` with `Fel.filterJit()`:

```java
// Interpreted evaluation
var interpretedFilter = Fel.filter("age >= 30 && city = 'New York'");

// JIT compiled evaluation - 10-27x faster!
var jitFilter = Fel.filterJit("age >= 30 && city = 'New York'");

// With type information for optimized field access
var jitFilterOptimized = Fel.filterJit("age >= 30 && city = 'New York'", User.class);

// Use like any predicate
var results = users.stream().filter(jitFilter).toList();
```

### API Reference

#### Basic JIT Compilation
```java
// Simple - uses default context
Predicate<Object> filter = Fel.filterJit("age >= 30");
```

#### With Type Information (Recommended)
```java
// Provides type information for potential optimizations
Predicate<User> filter = Fel.filterJit("age >= 30 && city = 'New York'", User.class);
```

#### With Custom Context
```java
var context = new DefaultEvaluationContext();
context.addFunction("myFunc", values -> /* custom logic */);
var filter = Fel.filterJit("myFunc(name) = 'test'", context);
```

#### With Type and Custom Context
```java
var context = new DefaultEvaluationContext();
// Add custom functions or mappers
var filter = Fel.filterJit("age >= 30", context, User.class);
```

#### From AST
```java
var parser = new FilterParser();
var ast = parser.parse("age >= 30");

// Simple
var filter = Fel.fromAstJit(ast);

// With type
var filter = Fel.fromAstJit(ast, User.class);

// With context
var filter = Fel.fromAstJit(ast, context);

// With both
var filter = Fel.fromAstJit(ast, context, User.class);
```

### Supported Features

The JIT compiler supports all FEL features:

- ✅ **Literals**: strings, numbers (long/double), booleans, null, dates
- ✅ **Identifiers**: field access
- ✅ **Dot expressions**: nested field access (`address.city`)
- ✅ **Map key access**: flat keys and nested maps (`address.city`)
- ✅ **Comparison operators**: `=`, `!=`, `>`, `<`, `>=`, `<=`
- ✅ **Logical operators**: `&&`, `||`, `!`
- ✅ **Short-circuit evaluation**: Optimized boolean logic
- ✅ **Function calls**: All built-in and custom functions
- ✅ **Custom mappers**: Type conversion functions

### Architecture

The JIT compilation system consists of several key components:

#### Core Components

1. **`JitCompiler`** (`rs.qubit.fel.jit.JitCompiler`)
   - Implements the `ExpressionVisitor` interface
   - Traverses the expression AST and generates JVM bytecode using ASM
   - Creates a class that implements `Predicate<Object>` with compiled filter logic

2. **`JitCompilerContext`** (`rs.qubit.fel.jit.JitCompilerContext`)
   - Holds compilation state during bytecode generation
   - Manages constant storage for complex values like LocalDateTime
   - Tracks type information when provided

3. **`JitCompilerHelper`** (`rs.qubit.fel.jit.JitCompilerHelper`)
   - Provides static helper methods called from generated bytecode
   - Handles value type conversion (Java objects → FEL Value types)
   - Manages constant value storage and retrieval

#### Technology Stack

- **ASM 9.7**: Powerful Java bytecode manipulation library
- **ASM Commons**: High-level bytecode generation utilities
- Generates Java 21 compatible bytecode

#### Generated Class Structure

```java
public class CompiledPredicateN implements Predicate<Object> {
    private final VisitorContext context;

    public CompiledPredicateN(VisitorContext context) {
        this.context = context;
    }

    @Override
    public boolean test(Object record) {
        // Generated bytecode for expression evaluation
    }
}
```

### Optimizations

- **Short-circuit evaluation**: AND/OR operators skip unnecessary evaluations
- **Direct method invocations**: Minimal overhead compared to reflection
- **Value type conversions**: Cached and optimized through helpers
- **No interpretation overhead**: Direct bytecode execution
- **Type-aware compilation**: When type information is provided, enables potential optimizations

### When to Use JIT Compilation

#### Ideal Use Cases ✅

- High-frequency filtering (millions of records)
- Hot-path filtering in performance-critical code
- Long-running services with stable filter expressions
- Batch processing with repeated filter evaluation
- Real-time data processing pipelines

#### Consider Interpreted Evaluation ⚠️

- One-time or infrequent filtering
- Dynamic filters that change frequently
- Initial development and debugging
- Simple expressions on small datasets
- Short-lived scripts or tools

### Testing

Run JIT-specific tests:
```bash
mvn test -Dtest=JitCompilerTest
```

Run performance benchmark:
```bash
mvn test-compile exec:java \
  -Dexec.mainClass="rs.qubit.fel.jit.JitExample" \
  -Dexec.classpathScope=test
```

### Coverage (local)

Run `mvn test` to generate JaCoCo coverage. The HTML report lives at `target/site/jacoco/index.html`. Latest local run (with generated code excluded): **86.7% lines**, **60.6% branches**.

The benchmark compares three implementations:
1. **Native Java** - Hand-written Java lambda (baseline)
2. **Interpreted** - Standard FEL expression evaluation
3. **JIT Compiled** - JIT-compiled expression to bytecode

### Current Limitations

- Generated classes are not cached (each call creates a new class)
- ClassLoader creates one class per compilation
- Function calls still go through interface dispatch
- Field access uses reflection (optimizations planned)

### Future Enhancements

Potential optimizations being considered:

- **Compilation caching**: Reuse generated classes for identical expressions
- **Inline field access**: Generate direct field access bytecode for known types
- **Method handles**: Replace reflection with MethodHandles for faster field access
- **AOT compilation**: Pre-compile common filters at build time

### Dependencies

```xml
<dependency>
    <groupId>org.ow2.asm</groupId>
    <artifactId>asm</artifactId>
    <version>9.7</version>
</dependency>
<dependency>
    <groupId>org.ow2.asm</groupId>
    <artifactId>asm-commons</artifactId>
    <version>9.7</version>
</dependency>
```

### Example Output

```
╔════════════════════════════════════════════════════════════════╗
║          FEL JIT Compiler Performance Benchmark                ║
╚════════════════════════════════════════════════════════════════╝

Dataset size: 1000 entities

=== Correctness Verification ===
Expression: age >= 30 && city = 'New York'
Native Java matches: 73
Interpreted matches: 73
JIT Compiled matches: 73
✓ All implementations produce identical results
Map filtering produces identical results (keys as identifiers, nested keys via dot)

=== Performance Benchmark ===
Running 50000 iterations per implementation...

┌─────────────────────┬──────────────┬─────────────┬─────────────────┐
│ Implementation      │ Time (ms)    │ Relative    │ Throughput      │
├─────────────────────┼──────────────┼─────────────┼─────────────────┤
│ Native Java         │          290 │       1.00x │    171.94 M/s   │
│ Interpreted         │        9,050 │      31.12x │      5.52 M/s   │
│ JIT Compiled        │          333 │       1.15x │    149.89 M/s   │
└─────────────────────┴──────────────┴─────────────┴─────────────────┘

=== Summary ===
JIT vs Interpreted: 27.13x faster
JIT vs Native: 1.15x
Map mode (object input) benchmark is also printed when running `JitExample` to demonstrate filtering `Map<String, Object>` collections.
```

---

**Note**: The JIT compiler maintains 100% compatibility with the interpreted evaluator - all tests that pass with interpreted evaluation also pass with JIT compilation.

## Language Specification

FEL expressions support a variety of operations to filter your data effectively. Here's a breakdown of the supported expressions:

### Basic Expressions

- **Parentheses:** `(expression)` - Group expressions to control evaluation order.
- **Logical NOT:** `! expression` - Negate a boolean expression.
- **Equality:** `left = right` - Check if two expressions are equal.
- **Inequality:** `left != right` - Check if two expressions are not equal.
- **Comparison:**
  - `left > right` - Check if `left` is greater than `right`.
  - `left < right` - Check if `left` is less than `right`.
  - `left >= right` - Check if `left` is greater than or equal to `right`.
  - `left <= right` - Check if `left` is less than or equal to `right`.

### Logical Operations

- **Logical AND:** `left && right` - Combine two expressions with logical &&.
- **Logical OR:** `left || right` - Combine two expressions with logical ||.

### Literals

- **String:** `'string'` - Represents a string literal.
- **Number:**
  - `123` - Represents an integer.
  - `123.45` - Represents a double.
- **Boolean:** `true` or `false` - Represents a boolean value.
- **Null:** `null` - Represents a null value.
- **Date and Time:** `YYYY-MM-DD` or `YYYY-MM-DDTHH:MM:SS` - Represents date and time values.

### Identifiers and Fields

- **Identifiers:** `identifier` - Represents a variable or field name.
- **Field Access:** `object.field` - Access fields within objects.

### Example Expressions

- **Equality Check:** `firstName = 'John'` - Filters objects where `firstName` is `John`.
- **Inequality Check:** `age != 30` - Filters objects where `age` is not `30`.
- **Logical AND:** `firstName = 'John' && lastName = 'Doe'` - Filters objects where `firstName` is `John` and `lastName` is `Doe`.
- **Logical OR:** `age < 20 || age > 60` - Filters objects where `age` is less than `20` or greater than `60`.
- **Nested Fields:** `address.street = 'Main Street'` - Filters objects where `address.street` is `Main Street`.

## Built-in Functions

Filter Expression Language (FEL) provides a set of built-in functions that can be used to perform various operations within your filter expressions. These functions enhance the expressiveness and flexibility of your filters by allowing you to manipulate data and perform common operations directly within the expressions.

### Function List

Here is a list of the available built-in functions in FEL:

- **`abs(value)`**: Returns the absolute value of a number.
  ```java
  abs(-5) // returns 5
  ```

- **`ceil(value)`**: Rounds a number up to the nearest integer.
  ```java
  ceil(4.2) // returns 5
  ```

- **`contains(string, substring)`**: Checks if a string contains a specified substring.
  ```java
  contains('Hello, World', 'World') // returns true
  ```

- **`fabs(value)`**: Returns the absolute value of a floating-point number.
  ```java
  fabs(-3.14) // returns 3.14
  ```

- **`floor(value)`**: Rounds a number down to the nearest integer.
  ```java
  floor(4.7) // returns 4
  ```

- **`length(string)`**: Returns the length of a string.
  ```java
  length('Hello') // returns 5
  ```

- **`max(value1, value2)`**: Returns the maximum of two values.
  ```java
  max(10, 20) // returns 20
  ```

- **`min(value1, value2)`**: Returns the minimum of two values.
  ```java
  min(10, 20) // returns 10
  ```

- **`now()`**: Returns the current date and time as a `LocalDateTime`.
  ```java
  now() // returns the current LocalDateTime
  ```

- **`round(value)`**: Rounds a number to the nearest integer.
  ```java
  round(4.5) // returns 5
  ```

- **`substring(string, start, length)`**: Extracts a substring from a string.
  ```java
  substring('Hello, World', 7, 5) // returns 'World'
  ```

- **`toLowerCase(string)`**: Converts a string to lower case.
  ```java
  toLowerCase('HELLO') // returns 'hello'
  ```

- **`toUpperCase(string)`**: Converts a string to upper case.
  ```java
  toUpperCase('hello') // returns 'HELLO'
  ```

- **`trim(string)`**: Removes leading and trailing whitespace from a string.
  ```java
  trim('  Hello  ') // returns 'Hello'
  ```

### Date and Time Functions

FEL includes functions to manipulate dates and times, making it easier to work with temporal data:

- **`addDays(date, days)`**: Adds a specified number of days to a date.
  ```java
  addDays(now(), 5) // returns the date and time 5 days from now
  ```

- **`addMonths(date, months)`**: Adds a specified number of months to a date.
  ```java
  addMonths(now(), 2) // returns the date and time 2 months from now
  ```

- **`addYears(date, years)`**: Adds a specified number of years to a date.
  ```java
  addYears(now(), 1) // returns the date and time 1 year from now
  ```

- **`day(date)`**: Extracts the day of the month from a date.
  ```java
  day(now()) // returns the current day of the month
  ```

- **`dayOfWeek(date)`**: Extracts the day of the week from a date.
  ```java
  dayOfWeek(now()) // returns the current day of the week
  ```

- **`hour(date)`**: Extracts the hour from a date.
  ```java
  hour(now()) // returns the current hour
  ```

- **`minute(date)`**: Extracts the minute from a date.
  ```java
  minute(now()) // returns the current minute
  ```

- **`month(date)`**: Extracts the month from a date.
  ```java
  month(now()) // returns the current month
  ```

- **`second(date)`**: Extracts the second from a date.
  ```java
  second(now()) // returns the current second
  ```

- **`year(date)`**: Extracts the year from a date.
  ```java
  year(now()) // returns the current year
  ```

### Using Built-in Functions in Expressions

Here are some examples of how to use these built-in functions within FEL filter expressions:

#### Example 1: Filtering Users by Uppercase First Name
```java
var filter = Fel.filter("toUpperCase(firstName) = 'JOHN'");
```

#### Example 2: Filtering Products by Availability and Price Range
```java
var filter = Fel.filter("inStock = true && price < 500");
```

#### Example 3: Filtering Events by Upcoming Date
```java
var filter = Fel.filter("addDays(eventDate, 7) > now()");
```

These functions enhance the power and flexibility of FEL, allowing for more complex and expressive filter conditions. Use them to tailor your filtering logic to meet specific application requirements.

## Static Type Checking (optional)

If you provide the target class, FEL validates expressions before running them and reports unknown fields, type mismatches, and nullability problems with line/column info.

```java
import rs.qubit.fel.Fel;
import rs.qubit.fel.exception.TypeCheckException;

record Address(String city) {}
record User(String name, Integer age, Address address) {}

public class Example {
    public static void main(String[] args) {
        // Passes type checking
        var ok = Fel.filterJit("address.city = 'Paris' && age > 18", User.class);

        try {
            Fel.filterJit("age = 'old' && unknown = 1", User.class);
        } catch (TypeCheckException e) {
            System.err.println(e.getMessage());
            // Type mismatch for '=': left is number, right is string at line 1, column 6
            // Field 'unknown' not found on type User at line 1, column 18
        }
    }
}
```

## Internal Types (`Value`)

In Filter Expression Language (FEL), various internal types are used to represent different kinds of values in filter expressions. These internal types extend the `Value` class and provide a consistent way to handle different data types during filtering operations. Here’s an overview of the internal types used in FEL:

### `LongValue`

Represents integer values, including `Byte`, `Short`, `Integer`, and `Long` types.

**Example:**
```java
new LongValue(123L);
```

### `DoubleValue`

Represents floating-point values, including `Float` and `Double` types.

**Example:**
```java
new DoubleValue(123.45);
```

### `StringValue`

Represents string values, including `Character` and `String` types.

**Example:**
```java
new StringValue("Hello, World!");
```

### `BooleanValue`

Represents boolean values.

**Example:**
```java
new BooleanValue(true);
```

### `DateTimeValue`

Represents date and time values, including `LocalDateTime`, `LocalDate`, and `Instant` types. Internally, `LocalDate` and `Instant` are converted to `LocalDateTime`.

**Example:**
```java
new DateTimeValue(LocalDateTime.now());
```

### `NullValue`

Represents a null value.

**Example:**
```java
new NullValue();
```

### `ObjectValue`

Represents custom objects that do not fall into any of the predefined categories. This type is used for complex objects and relies on additional mappers for conversion.

**Example:**
```java
new ObjectValue(customObject);
```

### Usage in Filter Expressions

These internal types are used during the parsing and evaluation of filter expressions. The `parseValue` method converts external types to their corresponding internal `Value` types, ensuring consistent handling and evaluation of filter criteria.

For example, when evaluating a filter expression, the values of fields and constants are converted to the appropriate `Value` type, and operations (such as equality checks, logical operations, etc.) are performed using these internal types.

### Summary

The internal `Value` types in FEL provide a robust and flexible mechanism to handle various data types consistently during filtering operations. By abstracting the underlying data types into a common `Value` hierarchy, FEL ensures that filter expressions can be evaluated accurately and efficiently, regardless of the complexity or variety of the data being filtered.

## Supported Types

Filter Expression Language (FEL) supports a variety of data types, allowing you to filter collections of objects with diverse attributes. Here's a detailed overview of the supported types and how they are handled within FEL.

### Primitive and Wrapper Types

FEL supports the common primitive and their corresponding wrapper types in Java:

- **Byte:** Automatically converted to `LongValue`.
- **Short:** Automatically converted to `LongValue`.
- **Integer:** Automatically converted to `LongValue`.
- **Long:** Directly mapped to `LongValue`.
- **Float:** Automatically converted to `DoubleValue`.
- **Double:** Directly mapped to `DoubleValue`.
- **Character:** Automatically converted to `StringValue`.
- **String:** Directly mapped to `StringValue`.
- **Boolean:** Directly mapped to `BooleanValue`.

### Date and Time Types

FEL supports the Java `Date and Time` API, allowing you to filter based on various date and time representations:

- **LocalDateTime:** Directly mapped to `DateTimeValue`.
- **LocalDate:** Converted to `DateTimeValue` by setting the time to the start of the day.
- **Instant:** Converted to `DateTimeValue` based on the system's default time zone.

### Enum Types

Enumerations (`enum`) are also supported and are automatically converted to their string representations:

- **Enum:** Automatically converted to `StringValue` using the `enum` name.

### Null Values

FEL gracefully handles null values during the filtering process:

- **Null:** Mapped to a `NullValue`.

### Custom Objects

FEL provides support for custom objects through additional mappers. If a custom object does not have a predefined conversion, FEL attempts to use a user-defined mapper from the `additionalMappers` map. If no mapper is found, FEL checks if the object is a standard Java object and raises an exception if it cannot be converted. Otherwise, the object is treated as an `ObjectValue`.

### Summary

FEL's flexible type support ensures that you can filter collections based

on a wide range of data types, including primitive and wrapper types, date and time representations, enums, and custom objects. By leveraging additional mappers, you can extend FEL's capabilities to handle custom filtering requirements specific to your application.

## Advanced Usage: Custom Mappers

Filter Expression Language (FEL) allows for advanced usage scenarios by supporting custom mappers. Custom mappers can be used to convert data types during the filtering process, enabling more complex filtering conditions that involve custom objects or data transformations.

In this section, we will describe how to use custom mappers with FEL to filter objects based on complex criteria.

### Custom Mapper Example: Filtering Users by Date of Birth

Consider a scenario where you want to filter users based on their date of birth. The users' dates of birth are stored as `Instant` objects, but you want to use a custom mapper to convert these `Instant` objects to a more human-readable `LocalDateTime` format during the filtering process.

Here's how you can achieve this using a custom mapper:

```java
public class Main {

    @Data
    @AllArgsConstructor
    static class User {
        private String firstName;
        private String lastName;
        private Instant dateOfBirth;
    }

    public static void main(String[] args) {

        var users = List.of(
              new User("John", "Doe", Instant.parse("1990-01-01T00:00:00Z")),
              new User("Jane", "Doe", Instant.parse("1995-01-01T00:00:00Z"))
        );

        var predicate = Fel.filter("dateOfBirth = 1990-01-01T00:00:00")
                .withMapper(Instant.class, instant -> {
                    var localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
                    return new DateTimeValue(localDateTime);
                });

        var filteredUsers = users.stream()
                .filter(predicate)
                .toList();

        System.out.println(filteredUsers);
    }
}
```

### Explanation

1. **Define Your Classes:** Create the `User` class with a `dateOfBirth` field of type `Instant`.

2. **Initialize Your Data:** Create a list of users with different dates of birth.

3. **Define a Custom Mapper:** Use the `withMapper` method to define a custom mapper for the `Instant` class. This mapper converts an `Instant` object to a `LocalDateTime` object, wrapped in a `DateTimeValue`.

4. **Apply the Filter:** Use the `fel` method to define your filter expression. In this example, we're filtering users whose `dateOfBirth` is `"1990-01-01T00:00:00"`.

5. **Filter and Collect:** Use Java streams to apply the filter and collect the results.

6. **Output the Results:** Print the filtered list of users.

### Advanced Usage Scenarios

Custom mappers can be used for a variety of advanced filtering scenarios, such as:

- **Date and Time Conversions:** Convert between different date and time formats, such as `Instant`, `LocalDateTime`, or custom date classes.
- **Custom Object Transformations:** Map custom objects to simpler representations or intermediary values for filtering purposes.
- **Complex Field Access:** Transform nested fields or perform custom calculations on object fields before filtering.

By leveraging custom mappers, you can extend the functionality of FEL to handle complex filtering logic tailored to your application's specific needs.

## Examples with Custom Mappers

### Filtering Orders by Delivery Date

Suppose you have an `Order` class with a `deliveryDate` field of type `Instant`. You can use a custom mapper to filter orders based on their delivery date:

```java
@Data
@AllArgsConstructor
class Order {
    private String orderId;
    private Instant deliveryDate;
}

public class Main {

    public static void main(String[] args) {

        var orders = List.of(
                new Order("1", Instant.parse("2023-01-01T00:00:00Z")),
                new Order("2", Instant.parse("2024-01-01T00:00:00Z"))
        );

        var predicate = Fel.filter("deliveryDate = 2023-01-01T00:00:00")
                .withMapper(Instant.class, instant -> {
                    var localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
                    return new DateTimeValue(localDateTime);
                });

        var filteredOrders = orders.stream()
                .filter(predicate)
                .toList();

        System.out.println(filteredOrders);
    }
}
```

### Filtering Events by Start Time

Consider an `Event` class with a `startTime` field of type `Instant`. Use a custom mapper to filter events based on their start time:

```java
@Data
@AllArgsConstructor
class Event {
    private String eventName;
    private Instant startTime;
}

public class Main {

    public static void main(String[] args) {

        var events = List.of(
                new Event("Concert", Instant.parse("2022-06-15T19:00:00Z")),
                new Event("Conference", Instant.parse("2022-06-20T09:00:00Z"))
        );

        var predicate = Fel.filter("startTime = 2022-06-15T19:00:00")
                .withMapper(Instant.class, instant -> {
                    var localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
                    return new DateTimeValue(localDateTime);
                });

        var filteredEvents = events.stream()
                .filter(predicate)
                .toList();

        System.out.println(filteredEvents);
    }
}
```

These examples demonstrate how custom mappers can be used with FEL to filter objects based on various criteria, highlighting the flexibility and power of custom mappers in advanced filtering scenarios.

By incorporating custom mappers, you can tailor FEL to suit your specific filtering needs, ensuring that the library remains a powerful and versatile tool for your Java applications.

## Advanced Usage: Custom Functions

Filter Expression Language (FEL) also supports the usage of custom functions to extend the filtering capabilities. This allows for more complex transformations and evaluations during the filtering process.

### Custom Function Example: Filtering Users by Uppercase Names

Consider a scenario where you want to filter users based on the uppercase version of their first names. You can use a custom function to achieve this:

```java
public class Main {

    @Data
    @AllArgsConstructor
    static class User {
        private String firstName;
        private String lastName;
        private Instant dateOfBirth;
    }

    public static void main(String[] args) {

        var users = List.of(
                new User("John", "Doe", Instant.parse("1990-01-01T00:00:00Z")),
                new User("Jane", "Doe", Instant.parse("1995-01-01T00:00:00Z"))
        );

        var predicate = Fel.filter("toUppercase(firstName) = 'JOHN'")
                .withFunction("toUppercase", values -> {
                    var parameter = values.get(0);
                    if (parameter instanceof NullValue) {
                        return new NullValue();
                    }

                    return new StringValue(parameter.asString().toUpperCase());
                });

        var filteredUsers = users.stream()
                .filter(predicate)
                .toList();

        System.out.println(filteredUsers);
    }
}
```

### Explanation

1. **Define Your Classes:** Create the `User` class with a `firstName`, `lastName`, and `dateOfBirth` fields.

2. **Initialize Your Data:** Create a list of users with different first names.

3. **Define a Custom Function:** Use the `withFunction` method to define a custom function called `toUppercase`. This function takes a string parameter and returns its uppercase version.

4. **Apply the Filter:** Use the `fel` method to define your filter expression, utilizing the custom function. In this example, we're filtering users whose uppercase `firstName` is `"JOHN"`.

5. **Filter and Collect:** Use Java streams to apply the filter and collect the results.

6. **Output the Results:** Print the filtered list of users.

### Advanced Usage Scenarios

Custom functions can be used for a variety of advanced filtering scenarios, such as:

- **String Manipulations:** Perform custom string transformations, such as trimming, concatenation, or pattern matching.
- **Mathematical Operations:** Implement custom mathematical operations or calculations.
- **Data Transformations:** Apply custom transformations to object fields before evaluating

the filter condition.

By leveraging custom functions, you can further extend the functionality of FEL to handle complex filtering logic tailored to your application's specific needs.

## Performance Considerations

Filter Expression Language (FEL) is designed to be lightweight and efficient for filtering collections of objects. However, when working with large datasets or complex filter expressions, consider the following performance considerations:

- **Filter Expression Complexity:** Complex filter expressions may impact performance, especially when they involve nested conditions or operations that require extensive computation. It's advisable to test and optimize filter expressions for performance-critical applications.

- **Data Set Size:** While FEL is efficient for typical use cases, performance can vary with the size of the dataset being filtered. For large datasets, consider implementing pagination or batch processing to manage memory usage and improve performance.

- **Testing and Benchmarking:** Before deploying FEL in production, conduct thorough testing and benchmarking to ensure that performance meets your application's requirements. Identify and optimize any bottlenecks that may arise from filter expression evaluation.

By understanding these considerations and optimizing where necessary, you can leverage FEL effectively for filtering operations while maintaining optimal performance in your applications.

## Examples with Real-World Data

To demonstrate the versatility and practical application of Filter Expression Language (FEL), here are some examples using real-world data scenarios:

### Filtering Users by Age and City

Consider a scenario where you have a list of users with associated cities and ages. You can use FEL to filter users based on specific criteria:

```java
@Test
void filterUsersByAgeAndCity() {
    var users = List.of(
            new User("John", 25, LocalDateTime.now(), new Address("Belgrade", "Nemanjina", 4)),
            new User("Jane", 30, LocalDateTime.now(), new Address("Novi Sad", "Trg Slobode", 1)),
            new User("Mark", 35, LocalDateTime.now(), new Address("Belgrade", "Knez Mihailova", 2)),
            new User("Marko", 35, LocalDateTime.now(), new Address("Belgrade", "Knez Mihailova", 2))
    );

    var filter = Fel.filter("age >= 30 && address.city = 'Belgrade'");

    var filteredUsers = users.stream().filter(filter).toList();

    assertEquals(2, filteredUsers.size());
    assertEquals("Mark", filteredUsers.get(0).getName());
    assertEquals("Marko", filteredUsers.get(1).getName());
}
```

### Filtering Products by Price and Stock Availability

In an e-commerce application, you might want to filter products based on their price and availability status:

```java
@Test
void filterProductsByPriceAndStock() {
    var products = List.of(
            new Product("Laptop", 1200.00, true),
            new Product("Smartphone", 700.00, false),
            new Product("Tablet", 300.00, true),
            new Product("Monitor", 150.00, true)
    );

    var filter = Fel.filter("price < 500 && inStock = true");

    var filteredProducts = products.stream().filter(filter).toList();

    assertEquals(2, filteredProducts.size());
    assertEquals("Tablet", filteredProducts.get(0).getName());
    assertEquals("Monitor", filteredProducts.get(1).getName());
}
```

### Filtering Users by Date of Creation

Filtering users based on the date they were created can be essential for applications that manage user lifecycle:

```java
@Test
void filterNewUsers() {
    var now = LocalDateTime.now();
    var users = List.of(
            new User("Alice", 22, now.minusDays(1), new Address("Paris", "Rue de Rivoli", 7)),
            new User("Bob", 28, now.minusMonths(6), new Address("Berlin", "Unter den Linden", 5)),
            new User("Charlie", 35, now, new Address("London", "Baker Street", 221))
    );

    var filter = Fel.filter("createdAt > " + now.minusHours(3));

    var filteredUsers = users.stream().filter(filter).toList();

    assertEquals(1, filteredUsers.size());
    assertEquals("Charlie", filteredUsers.get(0).getName());
}
```

These examples showcase how FEL can be used to filter diverse datasets effectively, demonstrating its utility across different types of applications and scenarios.

This section provides concrete examples using real-world data scenarios, demonstrating how FEL can be applied to filter users, products, and other entities based on various criteria.

## Using Factory for Reusable Context

Filter Expression Language (FEL) allows you to create a reusable evaluation context for your filter expressions. This is particularly useful when you have custom functions or variables that you want to reuse across multiple filter expressions. By using a `FilterFactory` with a pre-defined context, you can efficiently create and apply filters without redefining the context each time.

### Example: Reusing Context with Custom Function

Here's an example that demonstrates how to create a reusable evaluation context with a custom function and use it to filter a list of users.

```java
public class Main {

    @Data
    @AllArgsConstructor
    static class User {
        private String firstName;
        private String lastName;
        private Instant dateOfBirth;
    }

    public static void main(String[] args) {

        var users = List.of(
                new User("John", "Doe", Instant.parse("1990-01-01T00:00:00Z")),
                new User("Jane", "Doe", Instant.parse("1995-01-01T00:00:00Z"))
        );

        // Create a reusable evaluation context
        var context = new DefaultEvaluationContext();
        context.addFunction("toUppercase", values -> {
            var parameter = values.get(0);
            if (parameter instanceof NullValue) {
                return new NullValue();
            }

            return new StringValue(parameter.asString().toUpperCase());
        });

        // Create a FilterFactory with the reusable context
        var filterFactory = new FilterFactory(context);

        // Create a filter using the factory
        var predicate = filterFactory.createFilter("toUppercase(firstName) = 'JOHN'");

        // Apply the filter to the list of users
        var filteredUsers = users.stream()
                .filter(predicate)
                .toList();

        System.out.println(filteredUsers);
    }
}
```

### Explanation

1. **Define Your Classes:** Create the `User` class with fields for `firstName`, `lastName`, and `dateOfBirth`.

2. **Initialize Your Data:** Create a list of users with different names and dates of birth.

3. **Create a Reusable Evaluation Context:**
  - Instantiate a `DefaultEvaluationContext`.
  - Add a custom function (`toUppercase`) to the context. This function converts a string to uppercase, handling null values appropriately.

4. **Create a `FilterFactory`:**
  - Instantiate a `FilterFactory` with the reusable context.
  - This factory will use the same context for creating filters, ensuring consistency and reusability of the custom functions.

5. **Create a Filter Using the Factory:**
  - Use the `createFilter` method of the `FilterFactory` to define your filter expression. In this example, the filter checks if the uppercase version of `firstName` is `"JOHN"`.

6. **Apply the Filter and Collect Results:**
  - Use Java streams to apply the filter and collect the filtered results.

7. **Output the Results:** Print the filtered list of users.

### Benefits of Using a Reusable Context

- **Consistency:** Ensures that all filters created with the factory use the same set of custom functions and variables.
- **Efficiency:** Reduces the overhead of redefining the context for each filter expression.
- **Maintainability:** Makes it easier to manage and update custom functions and variables in one place.

By using a reusable evaluation context with `FilterFactory`, you can streamline the process of creating and applying filters in your application, ensuring consistent and efficient filtering logic.

## Accessing the Abstract Syntax Tree (AST)

Filter Expression Language (FEL) allows you to access the Abstract Syntax Tree (AST) of your filter expressions. The AST represents the syntactic structure of your filter expression in a tree format, which can be useful for debugging, analysis, or further manipulation of the expression.
Predicate can also be created from AST. `Fel.fromAst` method is used to create a filter predicate from the AST of the filter expression.

### Example: Accessing the AST

Here's an example that demonstrates how to access and print the AST of a filter expression:

```java
public class Main {
    public static void main(String[] args) {
        var predicate = Fel.filter("toUppercase(firstName) = 'JOHN' || age > 18");
        var ast = predicate.getAst();
        
        var predicateFromAst = Fel.fromAst(ast);
        
        System.out.println(ast);
    }
}
```

### Explanation

1. **Create a Filter Expression:** Use the `Fel.filter` method to define your filter expression. In this example, the filter checks if the uppercase version of `firstName` is `"JOHN"` or if `age` is greater than `18`.

2. **Access the AST:** Call the `getAst` method on the filter predicate to retrieve the AST of the filter expression.

3. **Print the AST:** Print the AST to the console for inspection.

### Benefits of Accessing the AST

- **Debugging:** The AST provides a detailed view of the filter expression's structure, making it easier to identify and fix issues.
- **Analysis:** Analyze the filter expression's structure to understand how it is parsed and evaluated.
- **Manipulation:** Modify the AST for advanced use cases, such as dynamically altering the filter expression.

By accessing the AST, you can gain deeper insights into how FEL processes filter expressions and leverage this information for various advanced use cases in your application.

---

This section provides an overview of how to access and utilize the AST in the Filter Expression Language (FEL) library. For more detailed documentation and examples, please refer to the project's repository.



## Code Generation with FEL

Filter Expression Language (FEL) is not only capable of evaluating filter expressions directly on collections of objects but also supports generating code from these expressions. This feature can be particularly useful when you need to transform your filter expressions into SQL queries or other forms of code to integrate with different data processing systems.

### Generating SQL Code Example

FEL can generate SQL queries from filter expressions, allowing you to apply the same filtering logic directly within your database. This can improve performance by offloading the filtering process to the database layer.

Here's an example of how to generate a SQL query using FEL:

```java
public class Main {
    public static void main(String[] args) {
        // Define a filter expression
        var predicate = Fel.filter("toUppercase(firstName) = 'JOHN' || age > 18");

        // Create a SQL generator and context
        var postgreSQLGenerator = new PostgreSQLGenerator();
        var postgreSQLGenerationContext = new PostgreSQLGenerationContext();

        // Generate the SQL query
        var sql = predicate.generate(postgreSQLGenerator, postgreSQLGenerationContext);

        // Print the generated SQL query
        System.out.println(sql);
    }
}
```

### Explanation

1. **Define a Filter Expression:** Use the `Fel.filter` method to create a filter predicate from a string-based filter expression. In this example, the filter checks if the uppercase version of `firstName` is `"JOHN"` or if `age` is greater than `18`.

2. **Create a SQL Generator and Context:** Instantiate a `PostgreSQLGenerator` and a `PostgreSQLGenerationContext`. These classes are part of FEL's code generation feature and are used to generate SQL queries from filter expressions.

3. **Generate the SQL Query:** Call the `generate` method on the filter predicate, passing in the SQL generator and context. This method transforms the filter expression into a SQL query.

4. **Print the Generated SQL Query:** Output the generated SQL query to the console for inspection.

### Benefits of Code Generation

- **Database Integration:** Directly translate filter expressions into SQL queries, enabling efficient filtering at the database level.
- **Flexibility:** Generate different types of code (e.g., SQL for various databases) by implementing custom code generators.
- **Consistency:** Maintain consistent filtering logic across different layers of your application by using the same filter expressions.

By leveraging FEL's code generation capabilities, you can seamlessly integrate complex filtering logic with your data processing systems, enhancing both performance and maintainability.

### Custom Code Generators

FEL supports creating custom code generators for different use cases. For example, you can create generators for other SQL dialects, NoSQL queries, or even custom scripts. This extensibility allows FEL to adapt to a wide range of applications and environments.
In order to create a custom code generator you need to implement the `ExpressionVisitor` interface. Here is an example of a custom PostgreSQL code generator: 
```java
public class PostgreSQLGenerator implements ExpressionVisitor<String, PostgreSQLGenerationContext, Void> {
  @Override
  public String visit(OrExpressionNode orExpressionNode, PostgreSQLGenerationContext env, Void record) {
    var left = orExpressionNode.left().accept(this, env, record);
    var right = orExpressionNode.right().accept(this, env, record);

    return String.format("(%s OR %s)", left, right);
  }
  
  // Implement other visit methods for different expression nodes
}
```

### Summary

The ability to generate code from filter expressions makes FEL a powerful and versatile tool for integrating filtering logic across different layers of your application. Whether you need to filter data in-memory or translate expressions into database queries, FEL provides the tools to streamline and unify your filtering approach.

## Contributing

Contributions are welcome! Please feel free to submit a pull request or open an issue to improve the library.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact

For any inquiries or feedback, feel free to open an issue.

---

This README provides an overview of how to use the Filter Expression Language (FEL) library. For more detailed documentation and examples, please refer to the project's repository.
