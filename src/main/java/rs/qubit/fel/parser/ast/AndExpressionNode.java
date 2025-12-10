package rs.qubit.fel.parser.ast;

import rs.qubit.fel.visitor.ExpressionVisitor;

public record AndExpressionNode(SourcePosition position, ExpressionNode left, ExpressionNode right) implements ExpressionNode {

    @Override
    public <T, E, R> T accept(ExpressionVisitor<T, E, R> visitor, E env, R object) {
        return visitor.visit(this, env, object);
    }
}
