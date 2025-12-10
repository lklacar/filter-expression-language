package rs.qubit.fel.parser;

import org.antlr.v4.runtime.ParserRuleContext;
import rs.qubit.fel.parser.ast.*;
import rs.qubit.fel.parser.generated.FilterBaseVisitor;
import rs.qubit.fel.parser.generated.FilterParser;

import java.time.LocalDateTime;

public class ExpressionParserVisitor extends FilterBaseVisitor<ExpressionNode> {

    @Override
    public ExpressionNode visitOrExpression(FilterParser.OrExpressionContext ctx) {
        var left = visit(ctx.left);
        var right = visit(ctx.right);

        return new OrExpressionNode(position(ctx), left, right);
    }

    @Override
    public ExpressionNode visitParenExpression(FilterParser.ParenExpressionContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public ExpressionNode visitAndExpression(FilterParser.AndExpressionContext ctx) {
        var left = visit(ctx.left);
        var right = visit(ctx.right);

        return new AndExpressionNode(position(ctx), left, right);
    }

    @Override
    public ExpressionNode visitEqualsExpression(FilterParser.EqualsExpressionContext ctx) {
        var left = visit(ctx.left);
        var right = visit(ctx.right);

        return new EqualsExpressionNode(position(ctx), left, right);
    }

    @Override
    public ExpressionNode visitNotExpression(FilterParser.NotExpressionContext ctx) {
        var expression = visit(ctx.expression());
        return new NotExpressionNode(position(ctx), expression);
    }

    @Override
    public ExpressionNode visitStringExpression(FilterParser.StringExpressionContext ctx) {
        var text = ctx.getText();
        var value = text.substring(1, text.length() - 1);
        return new StringExpressionNode(position(ctx), value);
    }

    @Override
    public ExpressionNode visitBooleanExpression(FilterParser.BooleanExpressionContext ctx) {
        var text = ctx.getText();
        var value = Boolean.parseBoolean(text);
        return new BooleanExpressionNode(position(ctx), value);
    }

    @Override
    public ExpressionNode visitNullExpression(FilterParser.NullExpressionContext ctx) {
        return new NullExpressionNode(position(ctx));
    }

    @Override
    public ExpressionNode visitIdentifierExpression(FilterParser.IdentifierExpressionContext ctx) {
        var text = ctx.getText();
        return new IdentifierExpressionNode(position(ctx), text);
    }

    @Override
    public ExpressionNode visitLongExpression(FilterParser.LongExpressionContext ctx) {
        var text = ctx.getText();
        var value = Long.parseLong(text);
        return new LongExpressionNode(position(ctx), value);
    }

    @Override
    public ExpressionNode visitDoubleExpression(FilterParser.DoubleExpressionContext ctx) {
        var text = ctx.getText();
        var value = Double.parseDouble(text);
        return new DoubleExpressionNode(position(ctx), value);
    }

    @Override
    public ExpressionNode visitDateTimeExpression(FilterParser.DateTimeExpressionContext ctx) {
        var text = ctx.getText();

        // Parse: YYYY-MM-DD or YYYY-MM-DDTHH:MM:SS or YYYY-MM-DDTHH:MM:SS.fraction
        var parts = text.split("T");
        var datePart = parts[0];
        var dateParts = datePart.split("-");

        var year = Integer.parseInt(dateParts[0]);
        var month = Integer.parseInt(dateParts[1]);
        var day = Integer.parseInt(dateParts[2]);

        int hour = 0, minute = 0, second = 0, nano = 0;

        if (parts.length > 1) {
            var timePart = parts[1];
            var timeAndFraction = timePart.split("\\.");
            var time = timeAndFraction[0];
            var timeParts = time.split(":");

            hour = Integer.parseInt(timeParts[0]);
            minute = Integer.parseInt(timeParts[1]);
            second = Integer.parseInt(timeParts[2]);

            if (timeAndFraction.length > 1) {
                String fractionText = timeAndFraction[1];
                // Pad or truncate to 9 digits (nanoseconds)
                if (fractionText.length() > 9) {
                    fractionText = fractionText.substring(0, 9);
                } else {
                    fractionText = String.format("%-9s", fractionText).replace(' ', '0');
                }
                nano = Integer.parseInt(fractionText);
            }
        }

        var date = LocalDateTime.of(year, month, day, hour, minute, second, nano);
        return new DateTimeExpressionNode(position(ctx), date);
    }


    @Override
    public ExpressionNode visitGreaterThanExpression(FilterParser.GreaterThanExpressionContext ctx) {
        var left = visit(ctx.left);
        var right = visit(ctx.right);

        return new GreaterThanExpressionNode(position(ctx), left, right);
    }

    @Override
    public ExpressionNode visitLessThanOrEqualsExpression(FilterParser.LessThanOrEqualsExpressionContext ctx) {
        var left = visit(ctx.left);
        var right = visit(ctx.right);

        return new LessThanOrEqualsExpressionNode(position(ctx), left, right);
    }

    @Override
    public ExpressionNode visitGreaterThanOrEqualsExpression(FilterParser.GreaterThanOrEqualsExpressionContext ctx) {
        var left = visit(ctx.left);
        var right = visit(ctx.right);

        return new GreaterThanOrEqualsExpressionNode(position(ctx), left, right);
    }

    @Override
    public ExpressionNode visitNotEqualsExpression(FilterParser.NotEqualsExpressionContext ctx) {
        var left = visit(ctx.left);
        var right = visit(ctx.right);

        return new NotEqualsExpressionNode(position(ctx), left, right);
    }

    @Override
    public ExpressionNode visitLessThanExpression(FilterParser.LessThanExpressionContext ctx) {
        var left = visit(ctx.left);
        var right = visit(ctx.right);

        return new LessThanExpressionNode(position(ctx), left, right);
    }

    @Override
    public ExpressionNode visitDotExpression(FilterParser.DotExpressionContext ctx) {
        var object = visit(ctx.object);
        var field = ctx.field.getText();
        return new DotExpressionNode(position(ctx), object, field);
    }

    @Override
    public ExpressionNode visitFunctionCallExpression(FilterParser.FunctionCallExpressionContext ctx) {
        var function = ctx.function.getText();
        var arguments = ctx.expression().stream().map(this::visit).toList();

        return new FunctionCallExpressionNode(position(ctx), function, arguments);
    }

    private SourcePosition position(ParserRuleContext ctx) {
        var start = ctx.getStart();
        return new SourcePosition(start.getLine(), start.getCharPositionInLine() + 1);
    }
}
