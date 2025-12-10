package rs.qubit.fel.type;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public final class Types {

    private Types() {
        throw new IllegalStateException("Utility class");
    }

    public static final SimpleType BOOLEAN = new SimpleType(TypeKind.BOOLEAN, false, Boolean.class);
    public static final SimpleType STRING = new SimpleType(TypeKind.STRING, false, String.class);
    public static final SimpleType LONG = new SimpleType(TypeKind.LONG, false, Long.class);
    public static final SimpleType DOUBLE = new SimpleType(TypeKind.DOUBLE, false, Double.class);
    public static final SimpleType DATE_TIME = new SimpleType(TypeKind.DATE_TIME, false, LocalDateTime.class);
    public static final UnknownType UNKNOWN = new UnknownType();
    public static final NullType NULL = new NullType();

    public static FelType asNullable(FelType type) {
        return type.asNullable();
    }

    public static String displayName(FelType type) {
        var nullability = type.nullable() ? "nullable " : "";
        return switch (type.kind()) {
            case BOOLEAN -> nullability + "boolean";
            case STRING -> nullability + "string";
            case LONG, DOUBLE -> nullability + "number";
            case DATE_TIME -> nullability + "date/time";
            case OBJECT -> nullability + type.javaType().getSimpleName();
            case UNKNOWN -> "unknown";
            case NULL -> "null";
        };
    }

    public static FelType fromJavaType(Class<?> javaType) {
        if (javaType == null) {
            return UNKNOWN;
        }

        var nullable = !javaType.isPrimitive();

        // Booleans
        if (javaType == boolean.class || javaType == Boolean.class) {
            return new SimpleType(TypeKind.BOOLEAN, nullable, javaType);
        }

        // Integers
        if (javaType == byte.class || javaType == Byte.class
                || javaType == short.class || javaType == Short.class
                || javaType == int.class || javaType == Integer.class
                || javaType == long.class || javaType == Long.class) {
            return new SimpleType(TypeKind.LONG, nullable, javaType);
        }

        // Floating point
        if (javaType == float.class || javaType == Float.class
                || javaType == double.class || javaType == Double.class) {
            return new SimpleType(TypeKind.DOUBLE, nullable, javaType);
        }

        // Strings and enums
        if (javaType == String.class || javaType.isEnum()) {
            return new SimpleType(TypeKind.STRING, nullable, javaType);
        }

        // Date/time
        if (javaType == LocalDate.class || javaType == LocalDateTime.class || javaType == Instant.class) {
            return new SimpleType(TypeKind.DATE_TIME, nullable, javaType);
        }

        if (Map.class.isAssignableFrom(javaType)) {
            return new ObjectType(javaType, true);
        }

        return new ObjectType(javaType, true);
    }

    public static boolean isUnknown(FelType type) {
        return type == null || type.isUnknown();
    }

    public static boolean isNull(FelType type) {
        return type != null && type.isNull();
    }
}
