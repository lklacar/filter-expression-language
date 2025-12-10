package rs.qubit.fel.parser.ast;

import rs.qubit.fel.visitor.ExpressionVisitor;

import java.util.List;

public record FunctionCallExpressionNode(SourcePosition position, String function, List<ExpressionNode> arguments) implements ExpressionNode {
    @Override
    public <T, E, R> T accept(ExpressionVisitor<T, E, R> visitor, E env, R record) {
        return visitor.visit(this, env, record);
    }
}
