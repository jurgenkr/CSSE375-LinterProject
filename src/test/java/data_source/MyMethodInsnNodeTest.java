package data_source;

import static org.junit.jupiter.api.Assertions.*;

import org.easymock.asm.Opcodes;
import org.junit.jupiter.api.Test;

class MyMethodInsnNodeTest {

	@Test 
	void testIsInvokeVirtual() {
		MyMethodInsnNode node = new MyMethodInsnNode(null, null, Opcodes.INVOKEVIRTUAL);
		assertTrue(node.isInvokeVirtual());
	}
	
	@Test 
	void testIsNotInvokeVirtual() {
		MyMethodInsnNode node = new MyMethodInsnNode(null, null, 0);
		assertFalse(node.isInvokeVirtual());
	}
	
	@Test
	void testGetCleanOwner() {
		MyMethodInsnNode node = new MyMethodInsnNode(null, "L;main/java/package/Class1", 0);
		assertEquals("Class1", node.getCleanOwner());
	}
}
