package rs.qubit.fel.type;

public record SimpleType(TypeKind kind, boolean nullable, Class<?> javaType) implements FelType {

    public SimpleType {
        if (kind == TypeKind.UNKNOWN || kind == TypeKind.NULL || kind == TypeKind.OBJECT) {
            throw new IllegalArgumentException("SimpleType cannot use kind " + kind);
        }
    }

    @Override
    public FelType asNullable() {
        return nullable ? this : new SimpleType(kind, true, javaType);
    }
}
