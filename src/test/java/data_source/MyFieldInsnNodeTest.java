package data_source;

import static org.junit.jupiter.api.Assertions.*;

import org.easymock.asm.Opcodes;
import org.junit.jupiter.api.Test;

class MyFieldInsnNodeTest {

	@Test
	void testNotLoadingNotStoring() {
		MyFieldInsnNode node = new MyFieldInsnNode(null, null, 0);
		assertFalse(node.isLoading());
		assertFalse(node.isStoring());
	}
	
	@Test
	void testLoadingNotStoring() {
		MyFieldInsnNode node = new MyFieldInsnNode(null, null, Opcodes.GETFIELD);
		assertTrue(node.isLoading());
		assertFalse(node.isStoring());
	}
	
	@Test
	void testStoringNotLoading() {
		MyFieldInsnNode node = new MyFieldInsnNode(null, null, Opcodes.PUTFIELD);
		assertFalse(node.isLoading());
		assertTrue(node.isStoring());
	}
	
	@Test
	void testGetCleanOwner() {
		MyFieldInsnNode node = new MyFieldInsnNode(null, "L;main/java/package/Class1", 0);
		assertEquals("Class1", node.getCleanOwner());
	}

}
