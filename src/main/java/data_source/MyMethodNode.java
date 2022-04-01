package data_source;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.objectweb.asm.Type;

public class MyMethodNode {

	public String name;
	private String desc;
	public LinkedList<MyAbstractInsnNode> instructions;
	public List<MyLocalVariableNode> localVariables;
	private List<String> argTypeNames;
	
	private static final String CONSTRUCTOR_NAME = "<init>";
	
	public MyMethodNode(String name, String desc, LinkedList<MyAbstractInsnNode> instructions,
			List<MyLocalVariableNode> localVariables) {
		this.name = name;
		this.desc = desc;
		this.instructions = instructions;
		this.localVariables = localVariables;
		this.argTypeNames = new ArrayList<String>();
	}
	
	ArrayList<String> getArgTypeNames() {
		ArrayList<String> argTypeNames = new ArrayList<>();
		Type[] argTypes = Type.getArgumentTypes(desc);
		for (Type type : argTypes) {
			argTypeNames.add(type.getInternalName());
		}
		return argTypeNames;
	}
  
	public String getFullDesc() {
		return desc;
	}
	
	public String getCleanDesc() {
    return sanitizeString(desc);
	}
	
	public String getFullArgTypes() {
		return desc;
	}
	
	public boolean isConstructor() {
		return name.equals(CONSTRUCTOR_NAME);
	}
	
	public ArrayList<MyMethodInsnNode> getMethodInstructions() {
		ArrayList<MyMethodInsnNode> output = new ArrayList<>();
		for (MyAbstractInsnNode insn : instructions) {
			if (insn instanceof MyMethodInsnNode) output.add((MyMethodInsnNode)insn);
		}
		return output;
	}
	
	public ArrayList<MyLineNumberNode> getLineNumberNodes() {
		ArrayList<MyLineNumberNode> output = new ArrayList<>();
		for (MyAbstractInsnNode insn : instructions) {
			if (insn instanceof MyLineNumberNode) output.add((MyLineNumberNode)insn);
		}
		return output;
	}
	
	public ArrayList<MyFieldInsnNode> getFieldInstructionNodes() {
		ArrayList<MyFieldInsnNode> output = new ArrayList<>();
		for (MyAbstractInsnNode insn : instructions) {
			if (insn instanceof MyFieldInsnNode) output.add((MyFieldInsnNode)insn);
		}
		return output;
	}
	
	public ArrayList<String> getVarNames() {
		ArrayList<String> output = new ArrayList<>();
		for (MyLocalVariableNode var : localVariables) {
			output.add(var.name);
		}
		return output;
	}
	
	private String sanitizeString(String s) {
		String[] nameSplit = s.split("/");
		return nameSplit[nameSplit.length-1];
	}
	
	public ArrayList<String> getCleanArgTypes() {
		this.argTypeNames = this.getArgTypeNames();
		ArrayList<String> output = new ArrayList<>();
		for (String type : argTypeNames) {
			output.add(sanitizeString(type));
		}
		return output;
	}
	
	public int getLength() {
		ArrayList<Integer> lines = new ArrayList<Integer>();
		int methodLength = 0;
		for (MyLineNumberNode ln : getLineNumberNodes()) {
			int line = ln.line;
			if (!lines.contains(line)) {
				lines.add(line);
				methodLength++;
			}
		}
		return methodLength;
	}
}
