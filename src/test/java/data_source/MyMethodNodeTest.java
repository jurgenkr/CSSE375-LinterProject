package data_source;

import static org.junit.jupiter.api.Assertions.*;

import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

class MyMethodNodeTest {

	@Test
	void testGetCleanDesc() {
		MyMethodNode node = new MyMethodNode(null, "L;java/package/Class2", null, null);

		assertEquals("Class2", node.getCleanDesc());
	}
	
	@Test
	void testIsConstructor() {
		MyMethodNode node = new MyMethodNode("<init>", null, null, null);
		assertTrue(node.isConstructor());
	}
	
	@Test
	void testIsNotConstructor() {
		MyMethodNode node = new MyMethodNode("Class5", null, null, null);
		assertFalse(node.isConstructor());
	}
	
	@Test
	void testGetMethodInstructions() {
		MyAbstractInsnNode insn1 = EasyMock.createMock(MyMethodInsnNode.class);
		MyAbstractInsnNode insn2 = EasyMock.createMock(MyFieldInsnNode.class);
		MyAbstractInsnNode insn3 = EasyMock.createMock(MyLineNumberNode.class);
		LinkedList<MyAbstractInsnNode> insns = new LinkedList<>();
		insns.add(insn1);
		insns.add(insn2);
		insns.add(insn3);
		
		MyMethodNode node = new MyMethodNode(null, null, insns, null);
		
		ArrayList<MyMethodInsnNode> methodInsns = new ArrayList<>();
		methodInsns.add((MyMethodInsnNode)insn1);
		
		assertEquals(methodInsns, node.getMethodInstructions());
	}
	
	@Test
	void testLineNumberNodes() {
		MyAbstractInsnNode insn1 = EasyMock.createMock(MyMethodInsnNode.class);
		MyAbstractInsnNode insn2 = EasyMock.createMock(MyFieldInsnNode.class);
		MyAbstractInsnNode insn3 = EasyMock.createMock(MyLineNumberNode.class);
		LinkedList<MyAbstractInsnNode> insns = new LinkedList<>();
		insns.add(insn1);
		insns.add(insn2);
		insns.add(insn3);
		
		MyMethodNode node = new MyMethodNode(null, null, insns, null);
		
		ArrayList<MyLineNumberNode> lineInsns = new ArrayList<>();
		lineInsns.add((MyLineNumberNode)insn3);
		
		assertEquals(lineInsns, node.getLineNumberNodes());
	}
	
	@Test
	void testGetFieldInstructions() {
		MyAbstractInsnNode insn1 = EasyMock.createMock(MyMethodInsnNode.class);
		MyAbstractInsnNode insn2 = EasyMock.createMock(MyFieldInsnNode.class);
		MyAbstractInsnNode insn3 = EasyMock.createMock(MyLineNumberNode.class);
		LinkedList<MyAbstractInsnNode> insns = new LinkedList<>();
		insns.add(insn1);
		insns.add(insn2);
		insns.add(insn3);
		
		MyMethodNode node = new MyMethodNode(null, null, insns, null);
		
		ArrayList<MyFieldInsnNode> fieldInsns = new ArrayList<>();
		fieldInsns.add((MyFieldInsnNode)insn2);
		
		assertEquals(fieldInsns, node.getFieldInstructionNodes());
	}
	
	@Test
	void testGetVarNames() {
		MyLocalVariableNode var = EasyMock.createMock(MyLocalVariableNode.class);
		ArrayList<MyLocalVariableNode> vars = new ArrayList<>();
		vars.add(var);

		MyMethodNode node = new MyMethodNode(null, null, null, vars);
		var.name = "variable";
		
		ArrayList<String> output = new ArrayList<>();
		output.add("variable");
		
		assertEquals(output, node.getVarNames());
	}
	
	@Test
	void testGetCleanArgTypes() {
		MyMethodNode node = EasyMock.partialMockBuilder(MyMethodNode.class)
									.addMockedMethod("getArgTypeNames")
									.withConstructor("", "L;java/package/Class2", new LinkedList<MyAbstractInsnNode>()
											,new ArrayList<MyLocalVariableNode>())
									.createNiceMock();
		
		ArrayList<String> argTypes = new ArrayList<>();
		argTypes.add("L;java/package/Class2");
		EasyMock.expect(node.getArgTypeNames()).andReturn(argTypes);
		
		ArrayList<String> output = new ArrayList<>();
		output.add("Class2");
		
		EasyMock.replay(node);
		assertEquals(output, node.getCleanArgTypes());
		EasyMock.verify(node);
	}
	
	@Test 
	void testGetLength() {
		MyLineNumberNode insn = EasyMock.createMock(MyLineNumberNode.class);
		ArrayList<MyLineNumberNode> insns = new ArrayList<>();
		insns.add(insn);
		
		insn.line = 12;
		
		MyMethodNode node = EasyMock.partialMockBuilder(MyMethodNode.class)
				.addMockedMethod("getLineNumberNodes")
				.withConstructor("", "L;java/package/Class2", new LinkedList<MyAbstractInsnNode>()
						,new ArrayList<MyLocalVariableNode>())
				.createNiceMock();
		
		EasyMock.expect(node.getLineNumberNodes()).andReturn(insns);
		
		EasyMock.replay(node, insn);
		assertEquals(1, node.getLength());
		EasyMock.verify(node, insn);
	}


}
