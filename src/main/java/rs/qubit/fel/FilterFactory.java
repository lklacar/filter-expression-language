package rs.qubit.fel;

import rs.qubit.fel.visitor.VisitorContext;

public class FilterFactory {

    private final VisitorContext evaluationContext;

    public FilterFactory(VisitorContext evaluationContext) {
        this.evaluationContext = evaluationContext;
    }

    public FelPredicate createFilter(String filter) {
        return Fel.filter(filter, evaluationContext);
    }

}
