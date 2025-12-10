package rs.qubit.fel.typecheck;

import rs.qubit.fel.type.FelType;

public record TypeCheckResult(FelType type, String path) {

    public static TypeCheckResult of(FelType type) {
        return new TypeCheckResult(type, null);
    }

    public static TypeCheckResult of(FelType type, String path) {
        return new TypeCheckResult(type, path);
    }
}
