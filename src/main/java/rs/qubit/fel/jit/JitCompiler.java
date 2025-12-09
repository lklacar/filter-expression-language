package rs.qubit.fel.jit;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.Method;
import rs.qubit.fel.evaluator.value.*;
import rs.qubit.fel.parser.ast.*;
import rs.qubit.fel.reflection.ReflectionUtil;
import rs.qubit.fel.visitor.ExpressionVisitor;
import rs.qubit.fel.visitor.VisitorContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import static org.objectweb.asm.Opcodes.*;

/**
 * JIT compiler that generates optimized JVM bytecode for filter expressions.
 * This visitor generates a class that implements Predicate<Object> with the
 * filter logic compiled directly to bytecode for maximum performance.
 */
public class JitCompiler implements ExpressionVisitor<Void, JitCompilerContext, Void> {

    private static final AtomicInteger CLASS_COUNTER = new AtomicInteger(0);
    private static final String VALUE_INTERNAL_NAME = Type.getInternalName(Value.class);
    private static final String BOOLEAN_VALUE_INTERNAL_NAME = Type.getInternalName(BooleanValue.class);
    private static final String STRING_VALUE_INTERNAL_NAME = Type.getInternalName(StringValue.class);
    private static final String LONG_VALUE_INTERNAL_NAME = Type.getInternalName(LongValue.class);
    private static final String DOUBLE_VALUE_INTERNAL_NAME = Type.getInternalName(DoubleValue.class);
    private static final String NULL_VALUE_INTERNAL_NAME = Type.getInternalName(NullValue.class);
    private static final String DATE_TIME_VALUE_INTERNAL_NAME = Type.getInternalName(DateTimeValue.class);
    private static final String OBJECT_VALUE_INTERNAL_NAME = Type.getInternalName(ObjectValue.class);

    private GeneratorAdapter mv;
    private JitCompilerContext context;

    /**
     * Compiles an expression tree into a Predicate implementation.
     */
    public Predicate<Object> compile(ExpressionNode ast, VisitorContext visitorContext) {
        String className = "rs/qubit/fel/jit/CompiledPredicate" + CLASS_COUNTER.incrementAndGet();

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        cw.visit(V21, ACC_PUBLIC | ACC_SUPER, className, null,
                Type.getInternalName(Object.class),
                new String[]{Type.getInternalName(Predicate.class)});

        // Add field for VisitorContext
        cw.visitField(ACC_PRIVATE | ACC_FINAL, "context",
                Type.getDescriptor(VisitorContext.class), null, null);

        // Generate constructor
        generateConstructor(cw, className);

        // Generate test method
        generateTestMethod(cw, className, ast);

        cw.visitEnd();

        byte[] bytecode = cw.toByteArray();

        // Load the generated class using the context class loader
        JitClassLoader loader = new JitClassLoader(Thread.currentThread().getContextClassLoader());
        Class<?> predicateClass = loader.defineClass(className.replace('/', '.'), bytecode);

        try {
            return (Predicate<Object>) predicateClass
                    .getConstructor(VisitorContext.class)
                    .newInstance(visitorContext);
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate compiled predicate", e);
        }
    }

    private void generateConstructor(ClassWriter cw, String className) {
        MethodVisitor constructor = cw.visitMethod(ACC_PUBLIC, "<init>",
                "(" + Type.getDescriptor(VisitorContext.class) + ")V", null, null);
        constructor.visitCode();
        constructor.visitVarInsn(ALOAD, 0);
        constructor.visitMethodInsn(INVOKESPECIAL, Type.getInternalName(Object.class),
                "<init>", "()V", false);
        constructor.visitVarInsn(ALOAD, 0);
        constructor.visitVarInsn(ALOAD, 1);
        constructor.visitFieldInsn(PUTFIELD, className, "context",
                Type.getDescriptor(VisitorContext.class));
        constructor.visitInsn(RETURN);
        constructor.visitMaxs(0, 0);
        constructor.visitEnd();
    }

    private void generateTestMethod(ClassWriter cw, String className, ExpressionNode ast) {
        Method testMethod = Method.getMethod("boolean test(Object)");
        mv = new GeneratorAdapter(ACC_PUBLIC, testMethod, null, null, cw);

        context = new JitCompilerContext(mv, className);

        mv.visitCode();

        // Generate bytecode for the expression
        ast.accept(this, context, null);

        // The result should be a Value on the stack, convert to boolean
        mv.invokeInterface(Type.getType(Value.class),
                Method.getMethod("boolean asBoolean()"));

        mv.returnValue();
        mv.endMethod();
    }

    @Override
    public Void visit(OrExpressionNode node, JitCompilerContext ctx, Void record) {
        // Evaluate left side
        node.left().accept(this, ctx, record);
        mv.invokeInterface(Type.getType(Value.class),
                Method.getMethod("boolean asBoolean()"));

        Label shortCircuit = new Label();
        Label end = new Label();

        // If left is true, short-circuit
        mv.visitJumpInsn(IFNE, shortCircuit);

        // Evaluate right side
        node.right().accept(this, ctx, record);
        mv.invokeInterface(Type.getType(Value.class),
                Method.getMethod("boolean asBoolean()"));
        mv.visitJumpInsn(GOTO, end);

        // Short-circuit: push true
        mv.visitLabel(shortCircuit);
        mv.push(true);

        mv.visitLabel(end);

        // Wrap boolean in BooleanValue
        mv.newInstance(Type.getType(BooleanValue.class));
        mv.dupX1();
        mv.swap();
        mv.invokeConstructor(Type.getType(BooleanValue.class),
                Method.getMethod("void <init>(boolean)"));

        return null;
    }

    @Override
    public Void visit(AndExpressionNode node, JitCompilerContext ctx, Void record) {
        // Evaluate left side
        node.left().accept(this, ctx, record);
        mv.invokeInterface(Type.getType(Value.class),
                Method.getMethod("boolean asBoolean()"));

        Label shortCircuit = new Label();
        Label end = new Label();

        // If left is false, short-circuit
        mv.visitJumpInsn(IFEQ, shortCircuit);

        // Evaluate right side
        node.right().accept(this, ctx, record);
        mv.invokeInterface(Type.getType(Value.class),
                Method.getMethod("boolean asBoolean()"));
        mv.visitJumpInsn(GOTO, end);

        // Short-circuit: push false
        mv.visitLabel(shortCircuit);
        mv.push(false);

        mv.visitLabel(end);

        // Wrap boolean in BooleanValue
        mv.newInstance(Type.getType(BooleanValue.class));
        mv.dupX1();
        mv.swap();
        mv.invokeConstructor(Type.getType(BooleanValue.class),
                Method.getMethod("void <init>(boolean)"));

        return null;
    }

    @Override
    public Void visit(EqualsExpressionNode node, JitCompilerContext ctx, Void record) {
        node.left().accept(this, ctx, record);
        node.right().accept(this, ctx, record);

        mv.invokeInterface(Type.getType(Value.class),
                Method.getMethod("boolean equal(rs.qubit.fel.evaluator.value.Value)"));

        // Wrap boolean in BooleanValue
        mv.newInstance(Type.getType(BooleanValue.class));
        mv.dupX1();
        mv.swap();
        mv.invokeConstructor(Type.getType(BooleanValue.class),
                Method.getMethod("void <init>(boolean)"));

        return null;
    }

    @Override
    public Void visit(NotEqualsExpressionNode node, JitCompilerContext ctx, Void record) {
        node.left().accept(this, ctx, record);
        node.right().accept(this, ctx, record);

        mv.invokeInterface(Type.getType(Value.class),
                Method.getMethod("boolean equal(rs.qubit.fel.evaluator.value.Value)"));

        // Negate the result
        Label falseLabel = new Label();
        Label endLabel = new Label();
        mv.visitJumpInsn(IFNE, falseLabel);
        mv.push(true);
        mv.visitJumpInsn(GOTO, endLabel);
        mv.visitLabel(falseLabel);
        mv.push(false);
        mv.visitLabel(endLabel);

        // Wrap boolean in BooleanValue
        mv.newInstance(Type.getType(BooleanValue.class));
        mv.dupX1();
        mv.swap();
        mv.invokeConstructor(Type.getType(BooleanValue.class),
                Method.getMethod("void <init>(boolean)"));

        return null;
    }

    @Override
    public Void visit(NotExpressionNode node, JitCompilerContext ctx, Void record) {
        node.expression().accept(this, ctx, record);
        mv.invokeInterface(Type.getType(Value.class),
                Method.getMethod("boolean asBoolean()"));

        // Negate the boolean
        Label falseLabel = new Label();
        Label endLabel = new Label();
        mv.visitJumpInsn(IFNE, falseLabel);
        mv.push(true);
        mv.visitJumpInsn(GOTO, endLabel);
        mv.visitLabel(falseLabel);
        mv.push(false);
        mv.visitLabel(endLabel);

        // Wrap boolean in BooleanValue
        mv.newInstance(Type.getType(BooleanValue.class));
        mv.dupX1();
        mv.swap();
        mv.invokeConstructor(Type.getType(BooleanValue.class),
                Method.getMethod("void <init>(boolean)"));

        return null;
    }

    @Override
    public Void visit(StringExpressionNode node, JitCompilerContext ctx, Void record) {
        mv.newInstance(Type.getType(StringValue.class));
        mv.dup();
        mv.push(node.value());
        mv.invokeConstructor(Type.getType(StringValue.class),
                Method.getMethod("void <init>(String)"));
        return null;
    }

    @Override
    public Void visit(BooleanExpressionNode node, JitCompilerContext ctx, Void record) {
        mv.newInstance(Type.getType(BooleanValue.class));
        mv.dup();
        mv.push(node.value());
        mv.invokeConstructor(Type.getType(BooleanValue.class),
                Method.getMethod("void <init>(boolean)"));
        return null;
    }

    @Override
    public Void visit(NullExpressionNode node, JitCompilerContext ctx, Void record) {
        mv.newInstance(Type.getType(NullValue.class));
        mv.dup();
        mv.invokeConstructor(Type.getType(NullValue.class),
                Method.getMethod("void <init>()"));
        return null;
    }

    @Override
    public Void visit(IdentifierExpressionNode node, JitCompilerContext ctx, Void record) {
        // Load the record parameter
        mv.loadArg(0);

        // Push the field name
        mv.push(node.value());

        // Call ReflectionUtil.accessField(record, identifier)
        mv.invokeStatic(Type.getType(ReflectionUtil.class),
                Method.getMethod("Object accessField(Object, String)"));

        // Load context to pass to parseValue
        mv.loadThis();
        mv.getField(Type.getObjectType(ctx.getClassName()), "context",
                Type.getType(VisitorContext.class));

        // Call helper method to parse the value
        mv.invokeStatic(Type.getType(JitCompilerHelper.class),
                Method.getMethod("rs.qubit.fel.evaluator.value.Value parseValue(Object, rs.qubit.fel.visitor.VisitorContext)"));

        return null;
    }

    @Override
    public Void visit(LongExpressionNode node, JitCompilerContext ctx, Void record) {
        mv.newInstance(Type.getType(LongValue.class));
        mv.dup();

        // Push the long value and box it
        mv.push(node.value());
        mv.valueOf(Type.LONG_TYPE);

        mv.invokeConstructor(Type.getType(LongValue.class),
                Method.getMethod("void <init>(Long)"));
        return null;
    }

    @Override
    public Void visit(DoubleExpressionNode node, JitCompilerContext ctx, Void record) {
        mv.newInstance(Type.getType(DoubleValue.class));
        mv.dup();

        // Push the double value (DoubleValue takes primitive double)
        mv.push(node.value());

        mv.invokeConstructor(Type.getType(DoubleValue.class),
                Method.getMethod("void <init>(double)"));
        return null;
    }

    @Override
    public Void visit(DateTimeExpressionNode node, JitCompilerContext ctx, Void record) {
        mv.newInstance(Type.getType(DateTimeValue.class));
        mv.dup();

        // Create LocalDateTime from the node's date value
        // We'll store it as a constant in the helper and retrieve it
        int dateTimeIndex = ctx.addConstant(node.date());
        mv.push(dateTimeIndex);

        mv.invokeStatic(Type.getType(JitCompilerHelper.class),
                Method.getMethod("java.time.LocalDateTime getDateTime(int)"));

        mv.invokeConstructor(Type.getType(DateTimeValue.class),
                Method.getMethod("void <init>(java.time.LocalDateTime)"));

        return null;
    }

    @Override
    public Void visit(GreaterThanExpressionNode node, JitCompilerContext ctx, Void record) {
        node.left().accept(this, ctx, record);
        node.right().accept(this, ctx, record);

        mv.invokeInterface(Type.getType(Value.class),
                Method.getMethod("boolean greaterThan(rs.qubit.fel.evaluator.value.Value)"));

        // Wrap boolean in BooleanValue
        mv.newInstance(Type.getType(BooleanValue.class));
        mv.dupX1();
        mv.swap();
        mv.invokeConstructor(Type.getType(BooleanValue.class),
                Method.getMethod("void <init>(boolean)"));

        return null;
    }

    @Override
    public Void visit(LessThanOrEqualsExpressionNode node, JitCompilerContext ctx, Void record) {
        node.left().accept(this, ctx, record);
        node.right().accept(this, ctx, record);

        mv.invokeInterface(Type.getType(Value.class),
                Method.getMethod("boolean lessThanOrEquals(rs.qubit.fel.evaluator.value.Value)"));

        // Wrap boolean in BooleanValue
        mv.newInstance(Type.getType(BooleanValue.class));
        mv.dupX1();
        mv.swap();
        mv.invokeConstructor(Type.getType(BooleanValue.class),
                Method.getMethod("void <init>(boolean)"));

        return null;
    }

    @Override
    public Void visit(GreaterThanOrEqualsExpressionNode node, JitCompilerContext ctx, Void record) {
        node.left().accept(this, ctx, record);
        node.right().accept(this, ctx, record);

        mv.invokeInterface(Type.getType(Value.class),
                Method.getMethod("boolean greaterThanOrEquals(rs.qubit.fel.evaluator.value.Value)"));

        // Wrap boolean in BooleanValue
        mv.newInstance(Type.getType(BooleanValue.class));
        mv.dupX1();
        mv.swap();
        mv.invokeConstructor(Type.getType(BooleanValue.class),
                Method.getMethod("void <init>(boolean)"));

        return null;
    }

    @Override
    public Void visit(LessThanExpressionNode node, JitCompilerContext ctx, Void record) {
        node.left().accept(this, ctx, record);
        node.right().accept(this, ctx, record);

        mv.invokeInterface(Type.getType(Value.class),
                Method.getMethod("boolean lessThan(rs.qubit.fel.evaluator.value.Value)"));

        // Wrap boolean in BooleanValue
        mv.newInstance(Type.getType(BooleanValue.class));
        mv.dupX1();
        mv.swap();
        mv.invokeConstructor(Type.getType(BooleanValue.class),
                Method.getMethod("void <init>(boolean)"));

        return null;
    }

    @Override
    public Void visit(DotExpressionNode node, JitCompilerContext ctx, Void record) {
        // Evaluate the object expression
        node.object().accept(this, ctx, record);

        // Get the underlying object
        mv.invokeInterface(Type.getType(Value.class),
                Method.getMethod("Object asObject()"));

        // Push the field name
        mv.push(node.field());

        // Call ReflectionUtil.accessField
        mv.invokeStatic(Type.getType(ReflectionUtil.class),
                Method.getMethod("Object accessField(Object, String)"));

        // Load context to pass to parseValue
        mv.loadThis();
        mv.getField(Type.getObjectType(ctx.getClassName()), "context",
                Type.getType(VisitorContext.class));

        // Call helper method to parse the value
        mv.invokeStatic(Type.getType(JitCompilerHelper.class),
                Method.getMethod("rs.qubit.fel.evaluator.value.Value parseValue(Object, rs.qubit.fel.visitor.VisitorContext)"));

        return null;
    }

    @Override
    public Void visit(FunctionCallExpressionNode node, JitCompilerContext ctx, Void record) {
        String functionName = node.function();
        List<ExpressionNode> arguments = node.arguments();

        // Create a List to hold the argument values
        mv.push(arguments.size());
        mv.newArray(Type.getType(Value.class));

        // Evaluate each argument and add to array
        for (int i = 0; i < arguments.size(); i++) {
            mv.dup(); // Duplicate array reference
            mv.push(i);
            arguments.get(i).accept(this, ctx, record);
            mv.arrayStore(Type.getType(Value.class));
        }

        // Convert array to List using Arrays.asList
        mv.invokeStatic(Type.getType(java.util.Arrays.class),
                Method.getMethod("java.util.List asList(Object[])"));

        // Load context
        mv.loadThis();
        mv.getField(Type.getObjectType(ctx.getClassName()), "context",
                Type.getType(VisitorContext.class));

        // Push function name
        mv.push(functionName);

        // Call getFunction on context (it's an interface method)
        mv.invokeInterface(Type.getType(VisitorContext.class),
                Method.getMethod("java.util.function.Function getFunction(String)"));

        // Swap to get List on top
        mv.swap();

        // Call apply on the function (it's a Function<List<Value>, Value>)
        mv.invokeInterface(Type.getType(java.util.function.Function.class),
                Method.getMethod("Object apply(Object)"));

        // Cast the result back to Value
        mv.checkCast(Type.getType(Value.class));

        return null;
    }

    /**
     * Custom ClassLoader for loading generated bytecode
     */
    private static class JitClassLoader extends ClassLoader {
        public JitClassLoader(ClassLoader parent) {
            super(parent);
        }

        public Class<?> defineClass(String name, byte[] bytecode) {
            return defineClass(name, bytecode, 0, bytecode.length);
        }
    }
}
