package net.lenni0451.commons.asm;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.commons.ModuleHashesAttribute;
import org.objectweb.asm.commons.ModuleResolutionAttribute;
import org.objectweb.asm.commons.ModuleTargetAttribute;
import org.objectweb.asm.tree.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cloner {

    public static ClassNode clone(final ClassNode classNode) {
        ClassNode clone = new ClassNode();
        classNode.accept(clone);
        return clone;
    }

    public static FieldNode clone(final FieldNode fieldNode) {
        FieldNode clone = new FieldNode(fieldNode.access, fieldNode.name, fieldNode.desc, fieldNode.signature, fieldNode.value);
        if (fieldNode.visibleAnnotations != null) {
            clone.visibleAnnotations = new ArrayList<>();
            fieldNode.visibleAnnotations.forEach(annotationNode -> clone.visibleAnnotations.add(clone(annotationNode)));
        }
        if (fieldNode.invisibleAnnotations != null) {
            clone.invisibleAnnotations = new ArrayList<>();
            fieldNode.invisibleAnnotations.forEach(annotationNode -> clone.invisibleAnnotations.add(clone(annotationNode)));
        }
        if (fieldNode.visibleTypeAnnotations != null) {
            clone.visibleTypeAnnotations = new ArrayList<>();
            fieldNode.visibleTypeAnnotations.forEach(typeAnnotationNode -> clone.visibleTypeAnnotations.add(clone(typeAnnotationNode)));
        }
        if (fieldNode.invisibleTypeAnnotations != null) {
            clone.invisibleTypeAnnotations = new ArrayList<>();
            fieldNode.invisibleTypeAnnotations.forEach(typeAnnotationNode -> clone.invisibleTypeAnnotations.add(clone(typeAnnotationNode)));
        }
        if (fieldNode.attrs != null) {
            clone.attrs = new ArrayList<>();
            fieldNode.attrs.forEach(attribute -> clone.attrs.add(clone(attribute)));
        }
        return clone;
    }

    public static MethodNode clone(final MethodNode methodNode) {
        MethodNode clone = new MethodNode(methodNode.access, methodNode.name, methodNode.desc, methodNode.signature, methodNode.exceptions == null ? new String[0] : methodNode.exceptions.toArray(new String[0]));
        methodNode.accept(clone);
        return clone;
    }

    public static InsnList clone(final InsnList insnList) {
        InsnList clone = new InsnList();
        Map<LabelNode, LabelNode> labels = cloneLabels(insnList);
        for (AbstractInsnNode insnNode : insnList) {
            clone.add(insnNode.clone(labels));
        }
        return clone;
    }

    public static Map<LabelNode, LabelNode> cloneLabels(final InsnList insnList) {
        Map<LabelNode, LabelNode> clone = new HashMap<>();
        for (AbstractInsnNode insnNode : insnList) {
            if (insnNode instanceof LabelNode) clone.put((LabelNode) insnNode, new LabelNode());
        }
        return clone;
    }

    public static AnnotationNode clone(final AnnotationNode annotationNode) {
        AnnotationNode clone = new AnnotationNode(annotationNode.desc);
        annotationNode.accept(clone);
        return clone;
    }

    public static TypeAnnotationNode clone(final TypeAnnotationNode typeAnnotationNode) {
        TypeAnnotationNode clone = new TypeAnnotationNode(typeAnnotationNode.typeRef, typeAnnotationNode.typePath, typeAnnotationNode.desc);
        typeAnnotationNode.accept(clone);
        return clone;
    }

    public static Attribute clone(final Attribute attribute) {
        if (attribute instanceof ModuleHashesAttribute) {
            ModuleHashesAttribute moduleHashesAttribute = (ModuleHashesAttribute) attribute;
            List<String> modules = null;
            List<byte[]> hashes = null;
            if (moduleHashesAttribute.modules != null) modules = new ArrayList<>(moduleHashesAttribute.modules);
            if (moduleHashesAttribute.hashes != null) hashes = new ArrayList<>(moduleHashesAttribute.hashes);
            return new ModuleHashesAttribute(moduleHashesAttribute.algorithm, modules, hashes);
        } else if (attribute instanceof ModuleResolutionAttribute) {
            ModuleResolutionAttribute moduleResolutionAttribute = (ModuleResolutionAttribute) attribute;
            return new ModuleResolutionAttribute(moduleResolutionAttribute.resolution);
        } else if (attribute instanceof ModuleTargetAttribute) {
            ModuleTargetAttribute moduleTargetAttribute = (ModuleTargetAttribute) attribute;
            return new ModuleTargetAttribute(moduleTargetAttribute.platform);
        } else {
            return attribute; //This is probably better than throwing an exception
        }
    }

}
