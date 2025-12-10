package rs.qubit.fel.type;

public interface FelType {

    TypeKind kind();

    boolean nullable();

    /**
     * The backing Java type when known. Null for unknown/null pseudo types.
     */
    Class<?> javaType();

    FelType asNullable();

    default boolean isNumeric() {
        return kind() == TypeKind.LONG || kind() == TypeKind.DOUBLE;
    }

    default boolean isBoolean() {
        return kind() == TypeKind.BOOLEAN;
    }

    default boolean isString() {
        return kind() == TypeKind.STRING;
    }

    default boolean isDateTime() {
        return kind() == TypeKind.DATE_TIME;
    }

    default boolean isObject() {
        return kind() == TypeKind.OBJECT;
    }

    default boolean isUnknown() {
        return kind() == TypeKind.UNKNOWN;
    }

    default boolean isNull() {
        return kind() == TypeKind.NULL;
    }

    default String displayName() {
        return Types.displayName(this);
    }
}
