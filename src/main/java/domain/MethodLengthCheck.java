package domain;

import java.util.ArrayList;

import data_source.MyClassNode;
import data_source.MyMethodNode;

public class MethodLengthCheck implements ClassCheck {

	private final int maxLength = 35;

	@Override
	public String runCheck(ArrayList<MyClassNode> classes) {
		String printString = "";
		for (MyClassNode classNode : classes) {
			String classString = "";
			classString += "	Class: " + classNode.getCleanName() + "\n";
			classString += this.methodLengthCheck(classNode);
			if (!classString.equals("	Class: " + classNode.getCleanName() + "\n")) {
				 printString += classString;
			}
		}
		
		if (printString == "") {
			return printString;
		}
		return "Method Length Check:\n" + printString;
	}
	
	private String methodLengthCheck(MyClassNode classNode) {
		String classString = "";
		for (MyMethodNode method : classNode.methods) {
			String methodLength = checkLength(method);
			if (!methodLength.equals("")) {
				classString += "		Method: " + method.name + "\n";
				classString += methodLength;
			}
		}
		return classString;
	}

	private String checkLength(MyMethodNode method) {
		int length = method.getLength();
		return (length > maxLength)
				? String.format("			Method too long: (%d lines) Shorten it to %d lines or less. \n", length,
						maxLength)
				: "";
	}
	
	@Override
	public String getName() {
		return "Method Length";
	}

}
