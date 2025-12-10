package rs.qubit.fel.typecheck;

import rs.qubit.fel.exception.TypeCheckException;
import rs.qubit.fel.parser.ast.SourcePosition;
import rs.qubit.fel.type.FelType;
import rs.qubit.fel.type.Types;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;

public class TypeResolver {

    public FelType resolveField(Class<?> ownerType, String fieldName, SourcePosition position) {
        if (Map.class.isAssignableFrom(ownerType)) {
            // Maps are dynamic; skip strict checks.
            return Types.UNKNOWN;
        }

        return findMemberType(ownerType, fieldName)
                .orElseThrow(() -> new TypeCheckException(
                        "Field '%s' not found on type %s".formatted(fieldName, ownerType.getSimpleName()), position));
    }

    private Optional<FelType> findMemberType(Class<?> ownerType, String fieldName) {
        var capitalized = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);

        var getterName = "get" + capitalized;
        var getter = findMethod(ownerType, getterName);
        if (getter.isPresent()) {
            return getter.map(Method::getReturnType).map(Types::fromJavaType);
        }

        var booleanGetterName = "is" + capitalized;
        var booleanGetter = findMethod(ownerType, booleanGetterName);
        if (booleanGetter.isPresent()) {
            return booleanGetter.map(Method::getReturnType).map(Types::fromJavaType);
        }

        var field = findField(ownerType, fieldName);
        if (field.isPresent()) {
            return field.map(Field::getType).map(Types::fromJavaType);
        }

        return Optional.empty();
    }

    private Optional<Method> findMethod(Class<?> ownerType, String name) {
        try {
            return Optional.of(ownerType.getMethod(name));
        } catch (NoSuchMethodException ignored) {
            return Optional.empty();
        }
    }

    private Optional<Field> findField(Class<?> ownerType, String name) {
        Class<?> current = ownerType;
        while (current != null) {
            try {
                return Optional.of(current.getDeclaredField(name));
            } catch (NoSuchFieldException ignored) {
                current = current.getSuperclass();
            }
        }
        return Optional.empty();
    }
}
