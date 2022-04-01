package domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import data_source.MyAbstractInsnNode;
import data_source.MyClassNode;
import data_source.MyFieldNode;
import data_source.MyMethodInsnNode;
import data_source.MyMethodNode;

class AdapterPatternCheckTest {

	@Test
	void testNameOneInterface() {
		ArrayList<String> interfaceNames = new ArrayList<>();
		interfaceNames.add("interface1");
		AdapterPatternCheck check = new AdapterPatternCheck();
		assertEquals("Interface interface1", check.nameInterfaces(interfaceNames));
	}
	
	@Test
	void testNameTwoInterfaces() {
		ArrayList<String> interfaceNames = new ArrayList<>();
		interfaceNames.add("interface1");
		interfaceNames.add("interface2");
		AdapterPatternCheck check = new AdapterPatternCheck();
		assertEquals("Interfaces interface1, and interface2", check.nameInterfaces(interfaceNames));
	}
	
	@Test
	void testNameManyInterfaces() {
		ArrayList<String> interfaceNames = new ArrayList<>();
		interfaceNames.add("interface1");
		interfaceNames.add("interface2");
		interfaceNames.add("interface3");
		AdapterPatternCheck check = new AdapterPatternCheck();
		assertEquals("Interfaces interface1, interface2, and interface3", check.nameInterfaces(interfaceNames));
	}
	
	@Test
	void testNameOneType() {
		ArrayList<String> classNames = new ArrayList<>();
		classNames.add("class1");
		AdapterPatternCheck check = new AdapterPatternCheck();
		assertEquals("Class class1", check.nameTypes(classNames));
	}
	
	@Test
	void testNameTwoTypes() {
		ArrayList<String> classNames = new ArrayList<>();
		classNames.add("class1");
		classNames.add("class2");
		AdapterPatternCheck check = new AdapterPatternCheck();
		assertEquals("Class class1, or class2", check.nameTypes(classNames));
	}
	
	@Test
	void testNameManyTypes() {
		ArrayList<String> classNames = new ArrayList<>();
		classNames.add("class1");
		classNames.add("class2");
		classNames.add("class3");
		AdapterPatternCheck check = new AdapterPatternCheck();
		assertEquals("Class class1, class2, or class3", check.nameTypes(classNames));
	}
	
	@Test
	void testCheckMethodInit() {
		MyMethodNode method = EasyMock.createMock(MyMethodNode.class);
		method.name = "<init>";
		AdapterPatternCheck check = new AdapterPatternCheck();
		EasyMock.expect(method.isConstructor()).andReturn(true);
		EasyMock.replay(method);
		assertTrue(check.checkMethod(method, null));
		EasyMock.verify(method);
	}
	
	@Test
	void testCheckMethodEmpty() {
		MyMethodNode method = EasyMock.createMock(MyMethodNode.class);
		method.name = "lol";
		method.instructions = new LinkedList<MyAbstractInsnNode>();
		EasyMock.expect(method.isConstructor()).andReturn(true);
		AdapterPatternCheck check = new AdapterPatternCheck();
		EasyMock.replay(method);
		assertTrue(check.checkMethod(method, null));
		EasyMock.verify(method);
	}
	
	@Test
	void testCheckMethodGood() {
		ArrayList<String> types = new ArrayList<>();
		types.add("ArrayList");
		MyMethodNode method = EasyMock.createMock(MyMethodNode.class);
		method.name = "lol";
		ArrayList<MyMethodInsnNode> instructions = new ArrayList<MyMethodInsnNode>();
		MyMethodInsnNode insn = EasyMock.createMock(MyMethodInsnNode.class);
		EasyMock.expect(insn.getCleanOwner()).andReturn("ArrayList");
		instructions.add(insn);
		AdapterPatternCheck check = new AdapterPatternCheck();
		EasyMock.expect(method.isConstructor()).andReturn(false);
		EasyMock.expect(method.getMethodInstructions()).andReturn(instructions);
		EasyMock.replay(insn, method);
		assertTrue(check.checkMethod(method, types));
		EasyMock.verify(insn, method);
	}
	
	@Test
	void testCheckMethodBad() {
		ArrayList<String> types = new ArrayList<>();
		types.add("LinkedList");
		MyMethodNode method = EasyMock.createMock(MyMethodNode.class);
		method.name = "lol";
		ArrayList<MyMethodInsnNode> instructions = new ArrayList<MyMethodInsnNode>();
		MyMethodInsnNode insn = EasyMock.createMock(MyMethodInsnNode.class);
		EasyMock.expect(insn.getCleanOwner()).andReturn("ArrayList");
		instructions.add(insn);
		EasyMock.expect(method.isConstructor()).andReturn(false);
		EasyMock.expect(method.getMethodInstructions()).andReturn(instructions);
		AdapterPatternCheck check = new AdapterPatternCheck();
		EasyMock.replay(method);
		assertFalse(check.checkMethod(method, types));
		EasyMock.verify(method);
	}

}
