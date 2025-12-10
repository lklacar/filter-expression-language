package rs.qubit.fel.parser.ast;

import rs.qubit.fel.visitor.ExpressionVisitor;

public record IdentifierExpressionNode(SourcePosition position, String value) implements ExpressionNode {
    @Override
    public <T, E, R> T accept(ExpressionVisitor<T, E, R> visitor, E env, R record) {
        return visitor.visit(this, env, record);
    }
}
