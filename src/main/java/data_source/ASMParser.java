package data_source;

import java.util.ArrayList;
import java.util.LinkedList;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class ASMParser {
	public static ArrayList<MyClassNode> parseASM(ArrayList<ClassNode> classes) {
		ArrayList<MyClassNode> myClasses = new ArrayList<>();
		for (ClassNode node : classes) {
			ArrayList<MyFieldNode> fields = parseFields(node);
			ArrayList<MyMethodNode> methods = parseMethods(node);
			myClasses.add(new MyClassNode(node.name, node.superName, node.interfaces, fields, 
					methods, node.access));
		}
		return myClasses;
	}

	private static ArrayList<MyFieldNode> parseFields(ClassNode node) {
		ArrayList<MyFieldNode> fields = new ArrayList<>();
		for (FieldNode field : node.fields) {
			fields.add(new MyFieldNode(field.name, field.desc, field.access));
		}
		return fields;
	}

	private static ArrayList<MyMethodNode> parseMethods(ClassNode node) {
		ArrayList<MyMethodNode> methods = new ArrayList<>();
		for (MethodNode method : node.methods) {
			LinkedList<MyAbstractInsnNode> instructions = parseInstructions(method);

			ArrayList<MyLocalVariableNode> localVariables = new ArrayList<>();
			if (method.localVariables != null) {
				for (LocalVariableNode var : method.localVariables) {
					localVariables.add(new MyLocalVariableNode(var.name, var.desc));
				}
			}
			
			methods.add(new MyMethodNode(method.name, method.desc, instructions, localVariables));
		}
		return methods;
	}

	private static LinkedList<MyAbstractInsnNode> parseInstructions(MethodNode method) {
		LinkedList<MyAbstractInsnNode> instructions = new LinkedList<>();
		for (AbstractInsnNode insn : method.instructions) {
			if (insn.getType() == AbstractInsnNode.FIELD_INSN) {
				FieldInsnNode fInsn = (FieldInsnNode) insn;
				instructions.add(new MyFieldInsnNode(fInsn.name, fInsn.owner, fInsn.getOpcode()));
			} else if (insn.getType() == AbstractInsnNode.METHOD_INSN) {
				MethodInsnNode mInsn = (MethodInsnNode) insn;
				instructions.add(new MyMethodInsnNode(mInsn.name, mInsn.owner, mInsn.getOpcode()));
			} else if (insn.getType() == AbstractInsnNode.VAR_INSN) {
				VarInsnNode vInsn = (VarInsnNode) insn;
				instructions.add(new MyVarInsnNode(vInsn.var, vInsn.getOpcode()));
			} else if (insn.getType() == AbstractInsnNode.LINE) {
				LineNumberNode linsn = (LineNumberNode) insn;
				instructions.add(new MyLineNumberNode(linsn.line));
			}
		}
		return instructions;
	}
}
