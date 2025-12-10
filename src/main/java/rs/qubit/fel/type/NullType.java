package rs.qubit.fel.type;

public record NullType() implements FelType {
    @Override
    public TypeKind kind() {
        return TypeKind.NULL;
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
