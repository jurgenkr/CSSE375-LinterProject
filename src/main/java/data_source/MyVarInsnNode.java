package data_source;

import org.objectweb.asm.Opcodes;

public class MyVarInsnNode extends MyAbstractInsnNode{

	public int var;
	private boolean isLoading;
	private boolean isStoring;

	public MyVarInsnNode(int var, int opcode) {
		this.var = var;
		
		setStatus(opcode);
	}
	
	@Override
	public int getType() {
		return super.VAR_INSN;
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
		case Opcodes.ALOAD:
		case Opcodes.ILOAD:
		case Opcodes.LLOAD:
		case Opcodes.FLOAD:
		case Opcodes.DLOAD:
			isLoading = true;
			break;
		case Opcodes.ASTORE:
		case Opcodes.ISTORE:
		case Opcodes.LSTORE:
		case Opcodes.FSTORE:
		case Opcodes.DSTORE:
			isStoring = true;
			break;
		}
		
		this.isLoading = isLoading;
		this.isStoring = isStoring;
	}
}
