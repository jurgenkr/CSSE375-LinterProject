package domain;

import java.util.ArrayList;
import java.util.LinkedList;
import data_source.MyAbstractInsnNode;
import data_source.MyClassNode;
import data_source.MyFieldInsnNode;
import data_source.MyLineNumberNode;
import data_source.MyMethodNode;
import data_source.MyVarInsnNode;

public class UnusedInstantiationCheck implements ClassCheck {
	
	FieldStates fieldStates = new FieldStates();
	
	@Override
	public String runCheck(ArrayList<MyClassNode> classes) {
		String printString = "";
		for (MyClassNode classNode : classes) {
			String classString = "";
			classString += "	Class: " + classNode.getCleanName() + "\n";
			classString += this.unusedInstantiationCheck(classNode);
			if (!classString.equals("	Class: " + classNode.getCleanName() + "\n")) {
				 printString += classString;
			}
		}
		
		if (printString == "") {
			return printString;
		}
		return "Unused Instantiation Check:\n" + printString;
	}
	
	private String unusedInstantiationCheck(MyClassNode classNode) {
		fieldStates.empty();
		
		String varString = "";
		for (MyMethodNode method : classNode.methods) {
			varString += findVariablesMethods(method);
		}
		String printString = findUnusedFields(classNode);
		
		if (!printString.equals("")) {
			printString = "		Unused Variables: \n" + printString + varString;
		}
		return printString;
	}
	
	String findUnusedFields(MyClassNode classNode) {
		String printString = "";
		ArrayList<String> loadedNames = new ArrayList<>();
		for (MyFieldInsnNode var : fieldStates.fieldLoading) {
			loadedNames.add(var.name);
		}
		ArrayList<MyFieldInsnNode> unusedStored = new ArrayList<>();
		for (MyFieldInsnNode var : fieldStates.fieldStoring) {
			if (!loadedNames.contains(var.name)) {
				unusedStored.add(var);
			}
		}
		
		for (MyFieldInsnNode var : unusedStored) {
			boolean found = false;
			for (MyMethodNode mNode : classNode.methods) {
				int index = mNode.instructions.indexOf(var);
				int line = 0;
				if (index != -1) {
					line = findLineNumber(index, mNode);
					printString += "			Line " + line + ": Unused field named " + var.name + "\n";
					found = true;
				}
			}
			if (!found) {
				printString += "			Unknown line number: Unused field named " + var.name + "\n";
			}
		}
		
		return printString;
	}
	
	void determineFieldStatus(MyFieldInsnNode fInsn) {
		if (fInsn.isLoading()) {
			fieldStates.fieldLoading.add(fInsn);
		} else {						
			fieldStates.fieldStoring.add(fInsn);
		}
	}
	
	String findVariablesMethods(MyMethodNode method) {
		LinkedList<MyAbstractInsnNode> instructions = method.instructions;
		ArrayList<MyVarInsnNode> loading = new ArrayList<>();
		ArrayList<MyVarInsnNode> storing = new ArrayList<>();
		for (MyAbstractInsnNode insn : instructions) {
			if (insn.getType() == MyAbstractInsnNode.VAR_INSN) {
				MyVarInsnNode vInsn = (MyVarInsnNode) insn;
				if (vInsn.isLoading()) {
					loading.add(vInsn);
				} else if (vInsn.isStoring()) {
					storing.add(vInsn);
				}
			} else if (insn.getType() == MyAbstractInsnNode.FIELD_INSN) {
				MyFieldInsnNode fInsn = (MyFieldInsnNode) insn;
				determineFieldStatus(fInsn);
			}
		}
		return findUnusedVariables(loading, storing, method);
	}
	
	String findUnusedVariables(ArrayList<MyVarInsnNode> loaded, ArrayList<MyVarInsnNode> stored, MyMethodNode method) {
		String printString = "";
		ArrayList<Integer> loadedIndexes = new ArrayList<>();
		for (MyVarInsnNode var : loaded) {
			loadedIndexes.add(var.var);
		}
		ArrayList<MyVarInsnNode> unusedStored = new ArrayList<>();
		for (MyVarInsnNode var : stored) {
			if (!loadedIndexes.contains(var.var)) {
				unusedStored.add(var);
			}
		}
		
		for (MyVarInsnNode var : unusedStored) {
			int line = findLineNumber(method.instructions.indexOf(var), method);
			if (var.var >= method.localVariables.size()) {
				printString += "			Line " + line + ": Unused variable in method " + method.name + "\n";
			} else {
				String name = method.localVariables.get(var.var).name;
				printString += "			Line " + line + ": Unused variable named " + name + " in method " + method.name + "\n";
			}
		}
		
		return printString;
	}
	
	int findLineNumber(int index, MyMethodNode method) {
		MyAbstractInsnNode node = method.instructions.get(index);
		if (node.getType() == MyAbstractInsnNode.LINE) {
			MyLineNumberNode lNode = (MyLineNumberNode) node;
			return lNode.line;
		} else {
			return findLineNumber(index - 1, method);
		}
	}
	
	@Override
	public String getName() {
		return "Unused Instantiation";
	}
	
}
