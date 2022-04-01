package domain;

import java.util.ArrayList;

import data_source.MyAbstractInsnNode;
import data_source.MyClassNode;
import data_source.MyFieldNode;
import data_source.MyMethodInsnNode;
import data_source.MyMethodNode;

public class AdapterPatternCheck implements ClassCheck {

	@Override
	public String runCheck(ArrayList<MyClassNode> classes) {
		String problems = "";
		for (MyClassNode classNode : classes) {
			problems += checkAdapter(classNode);
		}
		String out = (problems.equals("")) ? "" : "\nAdapter Pattern Checker: \n" + problems;
		return out;
	}

	String checkAdapter(MyClassNode classNode) {
		String name = classNode.getCleanName();
		boolean claimsAdapter = name.toLowerCase().contains("adapter");
		ArrayList<String> interfaceNames = classNode.getInterfaces();
		ArrayList<String> fieldTypes = classNode.getNonBuiltInFieldTypes();
		String className = classNode.getCleanName();

		if (interfaceNames.isEmpty() || fieldTypes.isEmpty()) {
			return (claimsAdapter) ? String.format(
					"	Class %s has \"adapter\" in name, but does not implement an interface and have a field of a user defined class to adapt. \n",
					className) : "";
		}
		boolean adaptsMethods = checkMethods(classNode, fieldTypes);
		if (adaptsMethods) {
			return String.format("	Class %s uses Adapter Pattern to adapt %s to %s \n", className, nameTypes(fieldTypes),
					nameInterfaces(interfaceNames));
		} else if (claimsAdapter) {
			return String.format(
					"	Class %s has \"adapter\" in name, but has methods that are not empty or calling methods of %s \n",
					className, nameTypes(fieldTypes));
		} else
			return "";
	}

	String nameInterfaces(ArrayList<String> interfaces) {
		int size = interfaces.size();
		String interfaceNames = (size == 1) ? "Interface " : "Interfaces ";
		for (int i = 0; i < size; i++) {
			interfaceNames += interfaces.get(i);
			if (i < size - 2)
				interfaceNames += ", ";
			else if (i < size - 1)
				interfaceNames += ", and ";
		}
		return interfaceNames;
	}

	String nameTypes(ArrayList<String> types) {
		int size = types.size();
		String typeNames = "Class ";
		for (int i = 0; i < size; i++) {
			typeNames += types.get(i);
			if (i < size - 2)
				typeNames += ", ";
			else if (i < size - 1)
				typeNames += ", or ";
		}
		return typeNames;
	}

	boolean checkMethods(MyClassNode classNode, ArrayList<String> fieldTypes) {
		for (MyMethodNode method : classNode.methods) {
			if (!checkMethod(method, fieldTypes))
				return false;
		}
		return true;
	}

	boolean checkMethod(MyMethodNode method, ArrayList<String> fieldTypes) {
		if (method.isConstructor())
			return true;
		int methodInsns = 0;
		boolean callsFieldType = false;
		for (MyMethodInsnNode mi : method.getMethodInstructions()) {
			methodInsns++;
			callsFieldType = callsFieldType || fieldTypes.contains(mi.getCleanOwner());
		}
		return (methodInsns == 0 || callsFieldType);
	}

	@Override
	public String getName() {
		return "Adapter Pattern";
	}

}
