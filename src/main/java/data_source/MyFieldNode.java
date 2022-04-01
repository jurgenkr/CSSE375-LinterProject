package data_source;

import org.objectweb.asm.Opcodes;

public class MyFieldNode {

	public String name;
	private String desc;
	private boolean isStatic;
	private boolean isFinal;
	
	public MyFieldNode(String name, String desc, int accessCode) {
		this.name = name;
		this.desc = desc;
		
		this.isStatic = (accessCode & Opcodes.ACC_STATIC) != 0;
		this.isFinal = (accessCode & Opcodes.ACC_FINAL) != 0;
	}
	
	public boolean isStatic() {
		return isStatic;
	}
	
	public boolean isFinal() {
		return isFinal;
	}
	
	public String getFullDesc() {
		return desc;
	}

	public boolean isBuiltIn() {
		String[] nameSplit = desc.split("/");
		return nameSplit[0].contains("java");
	}
	
	public String getCleanDesc() {
		String[] nameSplit = desc.split("/");
		return nameSplit[nameSplit.length-1];
	}
	
}
