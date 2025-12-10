package rs.qubit.fel.type;

public record UnknownType() implements FelType {
    @Override
    public TypeKind kind() {
        return TypeKind.UNKNOWN;
    }

    @Override
    public boolean nullable() {
        return true;
    }

    @Override
    public Class<?> javaType() {
        return null;
    }

    @Override
    public FelType asNullable() {
        return this;
    }
}
