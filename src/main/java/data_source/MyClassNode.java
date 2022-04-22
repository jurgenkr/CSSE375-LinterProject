package data_source;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Opcodes;

public class MyClassNode {

	private String name;
	private String superName;
	public List<String> interfaces;
	public List<MyFieldNode> fields;
	public List<MyMethodNode> methods;
	private boolean isInterface;
	
	public MyClassNode(String name, String superName, List<String> interfaces, List<MyFieldNode> fields,
			List<MyMethodNode> methods, int accessCode) {
		this.name = name;
		this.superName = superName;
		this.interfaces = interfaces;
		this.fields = fields;
		this.methods = methods;
		
		this.isInterface = (accessCode & Opcodes.ACC_INTERFACE) != 0;
	}
	
	public boolean isInterface() {
		return isInterface;
	}
	public String getFullName() {
		return name;
	}
	
	public String getFullSuperName() {
		return superName;
	}
	
	public String getCleanName() {
		return Sanitizer.sanitizeString(name);
	}
	
	public String getCleanSuperName() {
		return Sanitizer.sanitizeString(superName);
	}
	
	public boolean isSuperBuiltIn() {
		String[] nameSplit = superName.split("/");
		return nameSplit[0].contains("java");
	}
	
	public ArrayList<String> getInterfaces() {
		ArrayList<String> output = new ArrayList<>();
		for (String name : interfaces) {
			output.add(Sanitizer.sanitizeString(name));
		}
		return output;
	}
	
	public ArrayList<String> getNonBuiltInFieldTypes() {
		ArrayList<String> output = new ArrayList<>();
		for (MyFieldNode field : fields) {
			if (!field.isBuiltIn()) output.add(field.getCleanDesc());
		}
		return output;
	}
	
	public ArrayList<String> getMethodNames() {
		ArrayList<String> output = new ArrayList<>();
		for (MyMethodNode method : methods) {
			output.add(method.name);
		}
		return output;
	}
	
	public ArrayList<String> getFieldNames() {
		ArrayList<String> output = new ArrayList<>();
		for (MyFieldNode field : fields) {
			output.add(field.name);
		}
		return output;
	}
	
	public MyMethodNode getConstructor() {
		for (MyMethodNode method : methods) {
			if (method.isConstructor()) return method;
		}
		return null;
	}
}
