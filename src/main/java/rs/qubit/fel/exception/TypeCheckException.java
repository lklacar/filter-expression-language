package rs.qubit.fel.exception;

import rs.qubit.fel.parser.ast.SourcePosition;

public class TypeCheckException extends FilterException {

    private final SourcePosition position;

    public TypeCheckException(String message, SourcePosition position) {
        super(formatMessage(message, position));
        this.position = position;
    }

    public TypeCheckException(String message, SourcePosition position, Throwable cause) {
        super(formatMessage(message, position), cause);
        this.position = position;
    }

    public SourcePosition getPosition() {
        return position;
    }

    private static String formatMessage(String message, SourcePosition position) {
        if (position == null || position.isUnknown()) {
            return message;
        }
        return "%s at %s".formatted(message, position);
    }
}
