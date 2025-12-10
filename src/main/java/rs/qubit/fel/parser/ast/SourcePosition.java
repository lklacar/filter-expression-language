package rs.qubit.fel.parser.ast;

/**
 * Captures a location inside the parsed filter string.
 * The parser uses 1-based line/column numbers; -1/-1 means unknown.
 */
public record SourcePosition(int line, int column) {

    public static final SourcePosition UNKNOWN = new SourcePosition(-1, -1);

    public boolean isUnknown() {
        return line < 1 || column < 1;
    }

    @Override
    public String toString() {
        return isUnknown()
                ? "unknown location"
                : "line %d, column %d".formatted(line, column);
    }
}
