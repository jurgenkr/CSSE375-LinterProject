package domain;

import java.util.ArrayList;

import data_source.MyAbstractInsnNode;
import data_source.MyClassNode;
import data_source.MyFieldInsnNode;
import data_source.MyFieldNode;
import data_source.MyLocalVariableNode;
import data_source.MyMethodInsnNode;
import data_source.MyMethodNode;


public class HollywoodCheck implements ClassCheck {

	@Override
	public String runCheck(ArrayList<MyClassNode> classes) {
		String toPrint = "\nHollywood Principle Violations: \n";
		for (MyClassNode curClass : classes) {
			if (curClass.getFullSuperName() != null) {
				MyClassNode superClass = null;
				for (MyClassNode curClass2 : classes) {
					if (curClass2.getCleanName().equals(curClass.getCleanSuperName())) {
						superClass = curClass2;
					}
				}
				
				if (superClass != null) {
					toPrint += checkHollywoodViolations(curClass, superClass);
				}
			}
		}
		if (toPrint.equals("\nHollywood Principle Violations: \n")) {
			 return "";
		}
		return toPrint;
	}
	
	protected String checkHollywoodViolations(MyClassNode curClass, MyClassNode superClass) {
		String toPrint = "";
		
		ArrayList<String> superMethodNames = this.getSuperMethodNames(curClass, superClass);
		ArrayList<String> superFieldNames = this.getSuperFieldNames(curClass, superClass);
		
		for (MyMethodNode method : curClass.methods) {
			ArrayList<String> superFieldNamesTemp = superFieldNames;
			ArrayList<String> localVarNames = method.getVarNames();
			for (String localName : localVarNames) {
				superFieldNamesTemp.remove(localName);
			}
			for (MyAbstractInsnNode insn : method.instructions) {
				if (insn instanceof MyMethodInsnNode) {
					MyMethodInsnNode methodInsn = (MyMethodInsnNode) insn;
					if (superMethodNames.contains(methodInsn.name)) {
						toPrint += "	Class " + curClass.getCleanName() + " calls method " + methodInsn.name + " from " + 
								superClass.getCleanName() + " in method " + method.name + "\n";
					}
				} else if (insn instanceof MyFieldInsnNode) {
					MyFieldInsnNode fieldInsn = (MyFieldInsnNode) insn;
					if (superFieldNamesTemp.contains(fieldInsn.name)) {
						toPrint += "	Class " + curClass.getCleanName() + " uses field " + fieldInsn.name + " from " + 
								superClass.getCleanName() + " in method " + method.name + "\n";
					}
				}
			}
		}
		return toPrint;
	}
	
	private ArrayList<String> getSuperMethodNames(MyClassNode curClass, MyClassNode superClass) {
		ArrayList<String> curMethodNames = curClass.getMethodNames();
		ArrayList<String> superMethodNames = superClass.getMethodNames();
		for (String curName : curMethodNames) {
			superMethodNames.remove(curName);
		}
		
		return superMethodNames;
	}
	
	private ArrayList<String> getSuperFieldNames(MyClassNode curClass, MyClassNode superClass) {
		ArrayList<String> curFieldNames = curClass.getFieldNames();
		ArrayList<String> superFieldNames = superClass.getFieldNames();
		for (String curName : curFieldNames) {
			superFieldNames.remove(curName);
		}
		
		return superFieldNames;
	}
	
	@Override
	public String getName() {
		return "Hollywood Principle";
	}
}
