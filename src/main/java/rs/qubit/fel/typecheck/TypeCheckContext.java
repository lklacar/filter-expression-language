package rs.qubit.fel.typecheck;

import rs.qubit.fel.visitor.VisitorContext;

public record TypeCheckContext(Class<?> rootType, VisitorContext evaluationContext, TypeResolver resolver) {

    public TypeCheckContext(Class<?> rootType, VisitorContext evaluationContext) {
        this(rootType, evaluationContext, new TypeResolver());
    }
}
