package rs.qubit.fel.parser.ast;

import rs.qubit.fel.visitor.ExpressionVisitor;

public record DotExpressionNode(SourcePosition position, ExpressionNode object, String field) implements ExpressionNode {
    @Override
    public <T, E, R> T accept(ExpressionVisitor<T, E, R> visitor, E env, R record) {
        return visitor.visit(this, env, record);
    }
}
