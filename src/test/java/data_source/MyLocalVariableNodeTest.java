package data_source;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MyLocalVariableNodeTest {

	@Test
	void testGetCleanDesc() {
		MyLocalVariableNode node = new MyLocalVariableNode(null, "L;java/package/Class2");
		assertEquals("Class2", node.getCleanDesc());
	}


}
