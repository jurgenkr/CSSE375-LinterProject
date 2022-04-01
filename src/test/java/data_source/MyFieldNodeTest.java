package data_source;

import static org.junit.jupiter.api.Assertions.*;

import org.easymock.asm.Opcodes;
import org.junit.jupiter.api.Test;

class MyFieldNodeTest {

	@Test
	void testNotStaticNotFinal() {
		MyFieldNode node = new MyFieldNode(null, null, 0);
		assertFalse(node.isStatic());
		assertFalse(node.isFinal());
	}
	
	@Test
	void testStaticNotFinal() {
		MyFieldNode node = new MyFieldNode(null, null, Opcodes.ACC_STATIC);
		assertTrue(node.isStatic());
		assertFalse(node.isFinal());
	}
	
	@Test
	void testFinalNotStatic() {
		MyFieldNode node = new MyFieldNode(null, null, Opcodes.ACC_FINAL);
		assertFalse(node.isStatic());
		assertTrue(node.isFinal());
	}
	
	@Test
	void testStaticFinal() {
		MyFieldNode node = new MyFieldNode(null, null, (Opcodes.ACC_STATIC + Opcodes.ACC_FINAL));
		assertTrue(node.isStatic());
		assertTrue(node.isFinal());
	}
	
	@Test
	void testIsBuiltIn() {
		MyFieldNode node = new MyFieldNode(null, "L;java/package/Class2", 0);
		assertTrue(node.isBuiltIn());
	}
	
	@Test
	void testIsNotBuiltIn() {
		MyFieldNode node = new MyFieldNode(null, "L;blava/package/Class3", 0);
		assertFalse(node.isBuiltIn());
	}
	
	@Test
	void testGetCleanDesc() {
		MyFieldNode node = new MyFieldNode(null, "L;java/package/Class2", 0);
		assertEquals("Class2", node.getCleanDesc());
	}

}
