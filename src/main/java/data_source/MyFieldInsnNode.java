package data_source;

import org.objectweb.asm.Opcodes;

public class MyFieldInsnNode extends MyAbstractInsnNode{

	public String name;
	private String owner;
	private boolean isLoading;
	private boolean isStoring;
	
	public MyFieldInsnNode(String name, String owner, int opcode) {
		this.name = name;
		this.owner = owner;
		
		setStatus(opcode);
	}
	
	@Override
	public int getType() {
		return super.FIELD_INSN;
	}
	
	public boolean isLoading() {
		return isLoading;
	}
	
	public boolean isStoring() {
		return isStoring;
	}
	
	private void setStatus(int opcode) {
		boolean isLoading = false;
		boolean isStoring = false;

		switch (opcode) {
		case Opcodes.GETSTATIC:
		case Opcodes.GETFIELD:
			isLoading = true;
			break;
		case Opcodes.PUTSTATIC:
		case Opcodes.PUTFIELD:
			isStoring = true;
			break;
		}
		
		this.isLoading = isLoading;
		this.isStoring = isStoring;
	}

	public String getFullOwner() {
		return owner;
	}
	
	public String getCleanOwner() {
		String[] nameSplit = owner.split("/");
		return nameSplit[nameSplit.length-1];
	}

}
