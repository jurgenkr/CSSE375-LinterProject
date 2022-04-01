package data_source;

import static org.junit.jupiter.api.Assertions.*;

import org.easymock.asm.Opcodes;
import org.junit.jupiter.api.Test;

class MyVarInsnNodeTest {

	@Test
	void testNotLoadingNotStoring() {
		MyVarInsnNode node = new MyVarInsnNode(0, 0);
		assertFalse(node.isLoading());
		assertFalse(node.isStoring());
	}
	
	@Test
	void testLoadingNotStoring() {
		MyVarInsnNode node = new MyVarInsnNode(0, Opcodes.ALOAD);
		assertTrue(node.isLoading());
		assertFalse(node.isStoring());
	}
	
	@Test
	void testStoringNotLoading() {
		MyVarInsnNode node = new MyVarInsnNode(0, Opcodes.ASTORE);
		assertFalse(node.isLoading());
		assertTrue(node.isStoring());
	}

}
