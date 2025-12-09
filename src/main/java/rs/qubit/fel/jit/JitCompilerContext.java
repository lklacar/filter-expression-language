package rs.qubit.fel.jit;

import lombok.Getter;
import org.objectweb.asm.commons.GeneratorAdapter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Context for JIT compilation, holding state during bytecode generation.
 */
@Getter
public class JitCompilerContext {
    private final GeneratorAdapter methodVisitor;
    private final String className;
    private final List<Object> constants;
    private final Class<?> inputType;

    public JitCompilerContext(GeneratorAdapter methodVisitor, String className, Class<?> inputType) {
        this.methodVisitor = methodVisitor;
        this.className = className;
        this.constants = new ArrayList<>();
        this.inputType = inputType;
    }

    /**
     * Adds a constant value and returns its index for later retrieval.
     * This is used for storing complex constants like LocalDateTime.
     */
    public int addConstant(Object constant) {
        constants.add(constant);
        int index = constants.size() - 1;
        JitCompilerHelper.registerConstant(index, constant);
        return index;
    }
}
