package rs.qubit.fel.type;

public record ObjectType(Class<?> javaType, boolean nullable) implements FelType {

    public ObjectType {
        if (javaType == null) {
            throw new IllegalArgumentException("javaType must not be null");
        }
    }

    @Override
    public TypeKind kind() {
        return TypeKind.OBJECT;
    }

    @Override
    public FelType asNullable() {
        return nullable ? this : new ObjectType(javaType, true);
    }
}
