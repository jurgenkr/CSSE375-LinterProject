package data_source;

import org.objectweb.asm.Opcodes;

public class MyMethodInsnNode extends MyAbstractInsnNode{

	public String name;
	private String owner;
	private boolean invokeVirtual; 
	
	public MyMethodInsnNode(String name, String owner, int opcode) {
		this.name = name;
		this.owner = owner;
		
		this.invokeVirtual = ((opcode & Opcodes.INVOKEVIRTUAL) != 0);
	}
	
	public boolean isInvokeVirtual() {
		return invokeVirtual;
	}

	@Override
	public int getType() {
		return super.METHOD_INSN;
	}
	
	public String getFullOwner() {
		return owner;
	}
	
	public String getCleanOwner() {
		String[] nameSplit = owner.split("/");
		return nameSplit[nameSplit.length-1];
	}

}
