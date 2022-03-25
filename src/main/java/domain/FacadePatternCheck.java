package domain;

import java.util.ArrayList;

import data_source.MyAbstractInsnNode;
import data_source.MyClassNode;
import data_source.MyMethodInsnNode;
import data_source.MyMethodNode;

public class FacadePatternCheck implements MultiClassCheck {

	@Override
	public String runCheck(ArrayList<MyClassNode> classes) {
		String printString = "";
		ArrayList<String> classNames = new ArrayList<>();
		for (MyClassNode node : classes) {
			classNames.add(node.name);
		}
		ArrayList<MyClassNode> potentialFacades = new ArrayList<>();
		for (MyClassNode node : classes) {
			int userClasses = 0;
			ArrayList<String> fieldUserClassNames = new ArrayList<>();
			for (int i = 0; i < node.fields.size(); i++) {
				String name = node.fields.get(i).desc;
				String cleanName = name;
				if (name.length() > 1) {
					cleanName = name.substring(1, name.length() - 1);
				}
				if (classNames.contains(cleanName)) {
					userClasses++;
					fieldUserClassNames.add(cleanName);
				}
			}
			
			String[] nameSplit = node.name.split("/");
			String shortClassName = nameSplit[nameSplit.length-1];
			
			if (userClasses > 0) {
				potentialFacades.add(node);
				printString += checkForFacade(node, fieldUserClassNames);
			} else if (shortClassName.contains("Facade") || shortClassName.contains("facade")){
				printString += "	" + node.name + " contains the word 'facade' but is not a facade pattern\n";
			}
		}
		if (printString != "") {
			printString = "\nFacade Pattern Check:\n" + printString;
		}
		return printString;
	}

	String checkForFacade(MyClassNode node, ArrayList<String> fieldUserClassNames) {
		String printString = "";
		ArrayList<String> classNames = new ArrayList<>();
		classNames.addAll(fieldUserClassNames);
		for (MyMethodNode method : node.methods) {
			for (MyAbstractInsnNode insn : method.instructions) {
				if (insn.getType() == MyAbstractInsnNode.METHOD_INSN) {
					MyMethodInsnNode mInsn = (MyMethodInsnNode) insn;
					if (fieldUserClassNames.contains(mInsn.owner) && mInsn.isInvokeVirtual()) {
						classNames.remove(mInsn.owner);
					}
				}
			}
		}
		if (classNames.size() == 0) {
			printString += "	" + node.name + " looks to be a facade for the following classes:\n";
			for (String name : fieldUserClassNames) {
				printString += "		" + name + "\n";
			}
		} else {
			printString += "	" + node.name + " might be an attempt at facade pattern. It is "
					+ "missing calls to methods in these classes:\n";
			for (String name : classNames) {
				printString += "		" + name + "\n";
			}
		}
		return printString;
	}
	
	@Override
	public String getName() {
		return "Facade Pattern";
	}

}
