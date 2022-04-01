package domain;

import java.util.ArrayList;
import java.util.List;

import data_source.MyClassNode;
import data_source.MyFieldNode;
import data_source.MyLocalVariableNode;
import data_source.MyMethodNode;

public class NamesCheck implements ClassCheck {
	
	@Override
	public String runCheck(ArrayList<MyClassNode> classes) {
		String printString = "";
		for (MyClassNode classNode : classes) {
			String classString = "";
			classString += "	Class: " + classNode.getCleanName() + "\n";
			classString += this.namesCheck(classNode);
			if (!classString.equals("	Class: " + classNode.getCleanName() + "\n")) {
				 printString += classString;
			}
		}
		
		if (printString == "") {
			return printString;
		}
		return "Names Check:\n" + printString;
	}
	
	public String namesCheck(MyClassNode checkClass) {
		String toPrint = "		Name Style Violations: \n";
		String className = checkClass.getCleanName();
		List<MyMethodNode> methods = checkClass.methods;
		List<MyFieldNode> fields = (List<MyFieldNode>) checkClass.fields;
		String classPrint = "			Class Name checks: \n"; 
		classPrint += this.checkClass(className);
		if (!classPrint.equals("			Class Name checks: \n")) {
			 toPrint += classPrint;
		}
		
		String fieldPrint = "			Field Name checks: \n";
		for (MyFieldNode field : fields) {
			fieldPrint += this.checkField(field);
		}
		if (!fieldPrint.equals("			Field Name checks: \n")) {
			 toPrint += fieldPrint;
		}
		
		String methodPrint = "			Method & Method Variable Name checks: \n";
		for (MyMethodNode method : methods) {
			methodPrint += this.checkMethod(method);
		}
		if (!methodPrint.equals("			Method & Method Variable Name checks: \n")) {
			 toPrint += methodPrint;
		}
		
		if (toPrint.equals("		Name Style Violations: \n")) {
			 return "";
		}
		return toPrint;
	}
	
	protected String checkMethod(MyMethodNode checkMethod) {
		String toPrint = "";
		
		// Local Variable Checks
		List<MyLocalVariableNode> locals = checkMethod.localVariables;
		if (locals != null) {
			for (MyLocalVariableNode local : locals) {
				toPrint += this.checkLocal(local, checkMethod.name);
			}
		}
		
		// Lowercase first letter
		if (Character.isUpperCase(checkMethod.name.charAt(0))) { 
			toPrint += "				Method " + checkMethod.name + " has an uppercase first letter \n";
		}
		
		// Name length
		if (checkMethod.name.length() <= 1) {
			toPrint += "				Method " + checkMethod.name + 
					" has too short of a name (" + checkMethod.name.length() + " character) \n";
		}
		
		return toPrint;
	}
	
	private String checkLocal(MyLocalVariableNode checkVar, String methodName) {
		String toPrint = "";
		
		// Uppercase check
		if (Character.isUpperCase(checkVar.name.charAt(0))) { 
			toPrint += "				Variable " + checkVar.name + " has an uppercase first letter "
					+ "in method " + methodName + "\n";
		}
		
		// Type name correspond check
		String fieldDesc = checkVar.getCleanDesc();
		if (fieldDesc.toLowerCase().equals(checkVar.name.toLowerCase())) {
			toPrint += "				Variable " + checkVar.name + " has the same name as its type "
					+ "in method " + methodName + "\n";
		}
		
		return toPrint;
	}
	
	protected String checkField(MyFieldNode field) {
		String toPrint = "";
		
		// Uppercase check
		if (Character.isUpperCase(field.name.charAt(0))) { 
			if (!(field.isStatic() && field.isFinal())) {
				toPrint += "				Field " + field.name + " has an uppercase first letter, "
						+ "and is not static and final \n";
			}
		}
		
		// Type name correspond check
		String fieldDesc = field.getCleanDesc();
		if (fieldDesc.toLowerCase().equals(field.name.toLowerCase())) {
			toPrint += "				Field " + field.name + " has the same name as its type \n";
		}
		
		// Name length check
		if (field.name.length() <= 1) {
			toPrint += "				Field " + field.name + 
					" has too short of a name (" + field.name.length() + " character) \n";
		}
		
		return toPrint;
	}
	
	protected String checkClass(String name) {
		String toPrint = "";
		
		// Name length check
		if (name.length() <= 1) {
			toPrint += "				Class Name is too short (" + name.length() + " character) \n";
		}
		
		// Lowercase First Letter check
		if (Character.isLowerCase(name.charAt(0))) {
			toPrint += "				Class Name does not start with a capital letter \n";
		}
		
		// Non letters or numbers in the class name
		for (int i = 0; i < name.length(); i++) {
			if (!Character.isLetterOrDigit(name.charAt(i))) {
				toPrint += "				Class Name contains the non-alphanumeric character: " + name.charAt(i)
							+ "\n";
			}
		}
		return toPrint;
	}
	
	@Override
	public String getName() {
		return "Name Style";
	}
	
}
