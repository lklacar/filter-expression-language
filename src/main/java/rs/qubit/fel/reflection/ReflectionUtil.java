package rs.qubit.fel.reflection;

public class ReflectionUtil {

    public static Object accessField(Object object, String fieldName) {
        if (object == null) {
            throw new rs.qubit.fel.exception.FilterException("Cannot access field '%s' on null".formatted(fieldName));
        }

        // Map access by key
        if (object instanceof java.util.Map<?, ?> map) {
            return map.get(fieldName);
        }

        var type = object.getClass();

        // Prefer a public getter if present; this is more resilient across classloaders and proxies.
        var getterName = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
        try {
            var getter = type.getMethod(getterName);
            var wasAccessible = getter.canAccess(object);
            getter.setAccessible(true);
            var result = getter.invoke(object);
            getter.setAccessible(wasAccessible);
            return result;
        } catch (NoSuchMethodException ignored) {
            // Fall back to direct field access
        } catch (Exception e) {
            throw new rs.qubit.fel.exception.FilterException("Failed to access getter for '%s'".formatted(fieldName), e);
        }

        // Try boolean accessor convention (isX)
        var booleanGetterName = "is" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
        try {
            var getter = type.getMethod(booleanGetterName);
            var wasAccessible = getter.canAccess(object);
            getter.setAccessible(true);
            var result = getter.invoke(object);
            getter.setAccessible(wasAccessible);
            return result;
        } catch (NoSuchMethodException ignored) {
            // Fall back to direct field access
        } catch (Exception e) {
            throw new rs.qubit.fel.exception.FilterException("Failed to access getter for '%s'".formatted(fieldName), e);
        }

        // Traverse class hierarchy to find the field
        Class<?> current = type;
        while (current != null) {
            try {
                var field = current.getDeclaredField(fieldName);
                var isAccessible = field.canAccess(object);
                field.setAccessible(true);
                var result = field.get(object);
                field.setAccessible(isAccessible);
                return result;
            } catch (NoSuchFieldException ignored) {
                current = current.getSuperclass();
            } catch (IllegalAccessException e) {
                throw new rs.qubit.fel.exception.FilterException("Failed to access field '%s'".formatted(fieldName), e);
            }
        }

        throw new rs.qubit.fel.exception.FilterException(
                "Field '%s' not found on type %s".formatted(fieldName, type.getName()));
    }

}
