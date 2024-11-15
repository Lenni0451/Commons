package net.lenni0451.commons.asm;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import javax.annotation.Nullable;

import static net.lenni0451.commons.asm.Types.*;

public class ASMUtils {

    public static String dot(final String s) {
        return s.replace('/', '.');
    }

    public static String slash(final String s) {
        return s.replace('.', '/');
    }

    public static FieldNode getField(final ClassNode classNode, final String name, final String desc) {
        for (FieldNode fieldNode : classNode.fields) {
            if (fieldNode.name.equals(name) && fieldNode.desc.equals(desc)) {
                return fieldNode;
            }
        }
        return null;
    }

    public static MethodNode getMethod(final ClassNode classNode, final String name, final String desc) {
        for (MethodNode methodNode : classNode.methods) {
            if (methodNode.name.equals(name) && methodNode.desc.equals(desc)) {
                return methodNode;
            }
        }
        return null;
    }

    public static int freeVarIndex(final MethodNode methodNode) {
        int index = Modifiers.has(methodNode.access, Opcodes.ACC_STATIC) ? 0 : 1;
        for (Type type : argumentTypes(methodNode)) index += type.getSize();
        for (AbstractInsnNode instruction : methodNode.instructions) {
            if (instruction instanceof VarInsnNode) {
                VarInsnNode varInsnNode = (VarInsnNode) instruction;
                if (varInsnNode.var >= index) {
                    index = varInsnNode.var;
                    if (varInsnNode.getOpcode() == Opcodes.LLOAD || varInsnNode.getOpcode() == Opcodes.DLOAD) index++;
                    if (varInsnNode.getOpcode() == Opcodes.LSTORE || varInsnNode.getOpcode() == Opcodes.DSTORE) index++;
                    index++;
                }
            } else if (instruction instanceof IincInsnNode) {
                IincInsnNode iincInsnNode = (IincInsnNode) instruction;
                if (iincInsnNode.var >= index) index = iincInsnNode.var + 1;
            }
        }
        return index;
    }

    public static InsnList castObjectTo(final Type targetType) {
        InsnList list = new InsnList();
        if (targetType.equals(Type.BOOLEAN_TYPE)) {
            list.add(new TypeInsnNode(Opcodes.CHECKCAST, internalName(Boolean.class)));
            list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, internalName(Boolean.class), "booleanValue", methodDescriptor(Type.BOOLEAN_TYPE), false));
        } else if (targetType.equals(Type.BYTE_TYPE)) {
            list.add(new TypeInsnNode(Opcodes.CHECKCAST, internalName(Byte.class)));
            list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, internalName(Byte.class), "byteValue", methodDescriptor(Type.BYTE_TYPE), false));
        } else if (targetType.equals(Type.SHORT_TYPE)) {
            list.add(new TypeInsnNode(Opcodes.CHECKCAST, internalName(Short.class)));
            list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, internalName(Short.class), "shortValue", methodDescriptor(Type.SHORT_TYPE), false));
        } else if (targetType.equals(Type.CHAR_TYPE)) {
            list.add(new TypeInsnNode(Opcodes.CHECKCAST, internalName(Character.class)));
            list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, internalName(Character.class), "charValue", methodDescriptor(Type.CHAR_TYPE), false));
        } else if (targetType.equals(Type.INT_TYPE)) {
            list.add(new TypeInsnNode(Opcodes.CHECKCAST, internalName(Integer.class)));
            list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, internalName(Integer.class), "intValue", methodDescriptor(Type.INT_TYPE), false));
        } else if (targetType.equals(Type.FLOAT_TYPE)) {
            list.add(new TypeInsnNode(Opcodes.CHECKCAST, internalName(Float.class)));
            list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, internalName(Float.class), "floatValue", methodDescriptor(Type.FLOAT_TYPE), false));
        } else if (targetType.equals(Type.LONG_TYPE)) {
            list.add(new TypeInsnNode(Opcodes.CHECKCAST, internalName(Long.class)));
            list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, internalName(Long.class), "longValue", methodDescriptor(Type.LONG_TYPE), false));
        } else if (targetType.equals(Type.DOUBLE_TYPE)) {
            list.add(new TypeInsnNode(Opcodes.CHECKCAST, internalName(Double.class)));
            list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, internalName(Double.class), "doubleValue", methodDescriptor(Type.DOUBLE_TYPE), false));
        } else {
            list.add(new TypeInsnNode(Opcodes.CHECKCAST, targetType.getInternalName()));
        }
        return list;
    }

    @Nullable
    public static AbstractInsnNode boxPrimitive(final Type primitive) {
        if (primitive.equals(Type.BOOLEAN_TYPE)) {
            return new MethodInsnNode(Opcodes.INVOKESTATIC, internalName(Boolean.class), "valueOf", methodDescriptor(Boolean.class, boolean.class), false);
        } else if (primitive.equals(Type.BYTE_TYPE)) {
            return new MethodInsnNode(Opcodes.INVOKESTATIC, internalName(Byte.class), "valueOf", methodDescriptor(Byte.class, byte.class), false);
        } else if (primitive.equals(Type.SHORT_TYPE)) {
            return new MethodInsnNode(Opcodes.INVOKESTATIC, internalName(Short.class), "valueOf", methodDescriptor(Short.class, short.class), false);
        } else if (primitive.equals(Type.CHAR_TYPE)) {
            return new MethodInsnNode(Opcodes.INVOKESTATIC, internalName(Character.class), "valueOf", methodDescriptor(Character.class, char.class), false);
        } else if (primitive.equals(Type.INT_TYPE)) {
            return new MethodInsnNode(Opcodes.INVOKESTATIC, internalName(Integer.class), "valueOf", methodDescriptor(Integer.class, int.class), false);
        } else if (primitive.equals(Type.LONG_TYPE)) {
            return new MethodInsnNode(Opcodes.INVOKESTATIC, internalName(Long.class), "valueOf", methodDescriptor(Long.class, long.class), false);
        } else if (primitive.equals(Type.FLOAT_TYPE)) {
            return new MethodInsnNode(Opcodes.INVOKESTATIC, internalName(Float.class), "valueOf", methodDescriptor(Float.class, float.class), false);
        } else if (primitive.equals(Type.DOUBLE_TYPE)) {
            return new MethodInsnNode(Opcodes.INVOKESTATIC, internalName(Double.class), "valueOf", methodDescriptor(Double.class, double.class), false);
        } else {
            return null;
        }
    }

    @Nullable
    public static Number toNumber(@Nullable final AbstractInsnNode instruction) {
        if (instruction == null) return null;
        if (instruction.getOpcode() >= Opcodes.ICONST_M1 && instruction.getOpcode() <= Opcodes.ICONST_5) return instruction.getOpcode() - Opcodes.ICONST_0;
        else if (instruction.getOpcode() >= Opcodes.LCONST_0 && instruction.getOpcode() <= Opcodes.LCONST_1) return (long) (instruction.getOpcode() - Opcodes.LCONST_0);
        else if (instruction.getOpcode() >= Opcodes.FCONST_0 && instruction.getOpcode() <= Opcodes.FCONST_2) return (float) (instruction.getOpcode() - Opcodes.FCONST_0);
        else if (instruction.getOpcode() >= Opcodes.DCONST_0 && instruction.getOpcode() <= Opcodes.DCONST_1) return (double) (instruction.getOpcode() - Opcodes.DCONST_0);
        else if (instruction.getOpcode() == Opcodes.BIPUSH) return (byte) ((IntInsnNode) instruction).operand;
        else if (instruction.getOpcode() == Opcodes.SIPUSH) return (short) ((IntInsnNode) instruction).operand;
        else if (instruction.getOpcode() == Opcodes.LDC && ((LdcInsnNode) instruction).cst instanceof Number) return (Number) ((LdcInsnNode) instruction).cst;
        return null;
    }

    public static AbstractInsnNode intPush(final int i) {
        if (i >= -1 && i <= 5) return new InsnNode(Opcodes.ICONST_0 + i);
        else if (i >= Byte.MIN_VALUE && i <= Byte.MAX_VALUE) return new IntInsnNode(Opcodes.BIPUSH, i);
        else if (i >= Short.MIN_VALUE && i <= Short.MAX_VALUE) return new IntInsnNode(Opcodes.SIPUSH, i);
        else return new LdcInsnNode(i);
    }

    public static ClassNode createEmptyClass(final String name) {
        ClassNode classNode = new ClassNode();
        classNode.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, slash(name), null, internalName(Object.class), null);

        MethodNode constructor = new MethodNode(Opcodes.ACC_PUBLIC, "<init>", methodDescriptor(Type.VOID_TYPE), null, null);
        constructor.instructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
        constructor.instructions.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, internalName(Object.class), "<init>", methodDescriptor(Type.VOID_TYPE), false));
        constructor.instructions.add(new InsnNode(Opcodes.RETURN));
        classNode.methods.add(constructor);

        return classNode;
    }

    public static int[] parameterIndices(final MethodNode methodNode) {
        Type[] types = argumentTypes(methodNode);
        int[] indices = new int[types.length];
        int current = Modifiers.has(methodNode.access, Opcodes.ACC_STATIC) ? 0 : 1;
        for (int i = 0; i < types.length; i++) {
            Type type = types[i];
            indices[i] = current;
            current += type.getSize();
        }
        return indices;
    }

    public static InsnList swap(final Type top, final Type bottom) {
        InsnList insns = new InsnList();
        if (top.getSize() == 1 && bottom.getSize() == 1) {
            insns.add(new InsnNode(Opcodes.SWAP));
        } else if (top.getSize() == 2 && bottom.getSize() == 2) {
            insns.add(new InsnNode(Opcodes.DUP2_X2));
            insns.add(new InsnNode(Opcodes.POP2));
        } else if (top.getSize() == 2) {
            insns.add(new InsnNode(Opcodes.DUP_X2));
            insns.add(new InsnNode(Opcodes.POP));
        } else {
            insns.add(new InsnNode(Opcodes.DUP2_X1));
            insns.add(new InsnNode(Opcodes.POP2));
        }
        return insns;
    }

}
