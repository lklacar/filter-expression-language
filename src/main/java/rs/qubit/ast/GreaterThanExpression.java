package rs.qubit.ast;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import rs.qubit.visitor.Visitor;

@Data
@AllArgsConstructor
@Builder
public class GreaterThanExpression implements ExpressionNode{
    private final ExpressionNode left;
    private final ExpressionNode right;

    @Override
    public <TRet, TArg> TRet accept(Visitor<TRet, TArg> visitor, TArg tArg) {
        return visitor.visit(this, tArg);
    }
}