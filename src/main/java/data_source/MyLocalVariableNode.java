package data_source;

public class MyLocalVariableNode {

	public String name;
	private String desc;
	
	public MyLocalVariableNode(String name, String desc) {
		this.name = name;
		this.desc = desc;
	}
	
	public String getFullDesc() {
		return desc;
	}
	
	public String getCleanDesc() {
		String[] nameSplit = desc.split("/");
		return nameSplit[nameSplit.length-1];
	}
}
