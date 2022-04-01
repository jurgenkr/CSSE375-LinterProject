package domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import data_source.MyAbstractInsnNode;
import data_source.MyClassNode;
import data_source.MyFieldInsnNode;
import data_source.MyLineNumberNode;
import data_source.MyLocalVariableNode;
import data_source.MyMethodInsnNode;
import data_source.MyMethodNode;
import data_source.MyVarInsnNode;

public class UnusedInstantiationTest {

	@Test
	public void testGetName() {
		ClassCheck check = new UnusedInstantiationCheck();
		assertEquals("Unused Instantiation", check.getName());
	}
	
	@Test
	public void testDetermineFieldStatusLoading() {
		UnusedInstantiationCheck check = new UnusedInstantiationCheck();
		MyFieldInsnNode fInsn = EasyMock.createMock(MyFieldInsnNode.class);
		EasyMock.expect(fInsn.isLoading()).andReturn(true);
		EasyMock.replay(fInsn);
		
		assertTrue(check.fieldStates.fieldLoading.isEmpty());
		assertTrue(check.fieldStates.fieldStoring.isEmpty());
		check.determineFieldStatus(fInsn);
		assertTrue(!check.fieldStates.fieldLoading.isEmpty());
		assertTrue(check.fieldStates.fieldLoading.contains(fInsn));
		assertTrue(check.fieldStates.fieldStoring.isEmpty());
		
		EasyMock.verify(fInsn);
	}
	
	@Test
	public void testDetermineFieldStatusStoring() {
		UnusedInstantiationCheck check = new UnusedInstantiationCheck();
		MyFieldInsnNode fInsn = EasyMock.createMock(MyFieldInsnNode.class);
		EasyMock.expect(fInsn.isLoading()).andReturn(false);
		EasyMock.replay(fInsn);
		
		assertTrue(check.fieldStates.fieldLoading.isEmpty());
		assertTrue(check.fieldStates.fieldStoring.isEmpty());
		check.determineFieldStatus(fInsn);
		assertTrue(check.fieldStates.fieldLoading.isEmpty());
		assertTrue(!check.fieldStates.fieldStoring.isEmpty());
		assertTrue(check.fieldStates.fieldStoring.contains(fInsn));
		
		EasyMock.verify(fInsn);
	}
	
	@Test
	public void testFindLineNumberStartOnLineNumberNode() {
		UnusedInstantiationCheck check = new UnusedInstantiationCheck();
		MyMethodNode method = EasyMock.createMock(MyMethodNode.class);
		LinkedList<MyAbstractInsnNode> insns = new LinkedList<>();
		MyLineNumberNode lNode = EasyMock.createMock(MyLineNumberNode.class);
		insns.add(lNode);
		EasyMock.expect(lNode.getType()).andReturn(MyAbstractInsnNode.LINE);
		lNode.line = 12;
		
		method.instructions = insns;
		EasyMock.replay(lNode);
		
		assertEquals(12, check.findLineNumber(0, method));
		
		EasyMock.verify(lNode);
	}
	
	@Test
	public void testFindLineNumberStartNotOnLineNumberNode() {
		UnusedInstantiationCheck check = new UnusedInstantiationCheck();
		MyMethodNode method = EasyMock.createMock(MyMethodNode.class);
		LinkedList<MyAbstractInsnNode> insns = new LinkedList<>();
		MyLineNumberNode lNode = EasyMock.createMock(MyLineNumberNode.class);
		MyMethodInsnNode mNode = EasyMock.createMock(MyMethodInsnNode.class);
		MyFieldInsnNode fNode = EasyMock.createMock(MyFieldInsnNode.class);
		MyVarInsnNode vNode = EasyMock.createMock(MyVarInsnNode.class);
		insns.add(lNode);
		insns.add(mNode);
		insns.add(fNode);
		insns.add(vNode);
		EasyMock.expect(vNode.getType()).andReturn(MyAbstractInsnNode.VAR_INSN);
		EasyMock.expect(fNode.getType()).andReturn(MyAbstractInsnNode.FIELD_INSN);
		EasyMock.expect(mNode.getType()).andReturn(MyAbstractInsnNode.METHOD_INSN);
		EasyMock.expect(lNode.getType()).andReturn(MyAbstractInsnNode.LINE);
		lNode.line = 12;
		
		method.instructions = insns;
		EasyMock.replay(lNode, mNode, fNode, vNode, method);
		
		assertEquals(12, check.findLineNumber(3, method));
		
		EasyMock.verify(lNode, mNode, fNode, vNode, method);
	}
	
	@Test
	public void testFindUnusedVariableLocationsUnusedNamed() {
		UnusedInstantiationCheck check = new UnusedInstantiationCheck();
		ArrayList<MyVarInsnNode> loaded = new ArrayList<>();
		ArrayList<MyVarInsnNode> stored = new ArrayList<>();
		MyMethodNode method = EasyMock.createMock(MyMethodNode.class);
		
		MyVarInsnNode lNode1 = EasyMock.createMock(MyVarInsnNode.class);
		MyVarInsnNode lNode2 = EasyMock.createMock(MyVarInsnNode.class);
		MyVarInsnNode lNode3 = EasyMock.createMock(MyVarInsnNode.class);
		
		MyVarInsnNode sNode1 = EasyMock.createMock(MyVarInsnNode.class);
		MyVarInsnNode sNode2 = EasyMock.createMock(MyVarInsnNode.class);
		MyVarInsnNode sNode3 = EasyMock.createMock(MyVarInsnNode.class);
		
		loaded.add(lNode1);
		loaded.add(lNode2);
		loaded.add(lNode3);
		stored.add(sNode1);
		stored.add(sNode2);
		stored.add(sNode3);
		
		lNode1.var = 2;
		lNode2.var = 3;
		lNode3.var = 4;
		sNode1.var = 0;
		sNode2.var = 2;
		sNode3.var = 3;
		
		LinkedList<MyAbstractInsnNode> insns = new LinkedList<>();
		MyLineNumberNode lNode = EasyMock.createMock(MyLineNumberNode.class);
		insns.add(lNode);
		insns.add(sNode1);
		EasyMock.expect(sNode1.getType()).andReturn(MyAbstractInsnNode.VAR_INSN);
		EasyMock.expect(lNode.getType()).andReturn(MyAbstractInsnNode.LINE);
		lNode.line = 12;
		ArrayList<MyLocalVariableNode> vars = new ArrayList<>();
		MyLocalVariableNode vLNode = EasyMock.createMock(MyLocalVariableNode.class);
		vars.add(vLNode);
		vLNode.name = "counter";
		method.instructions = insns;
		method.localVariables = vars;
		method.name = "countThings";
		
		
		EasyMock.replay(lNode1, lNode2, lNode3, sNode1, sNode2, sNode3, method, lNode, vLNode);
		
		assertEquals("			Line 12: Unused variable named counter in method countThings\n", check.findUnusedVariables(loaded, stored, method));
		
		EasyMock.verify(lNode1, lNode2, lNode3, sNode1, sNode2, sNode3, method, lNode, vLNode);
		
	}
	
	@Test
	public void testFindUnusedVariableLocationsUnusedUnknownName() {
		UnusedInstantiationCheck check = new UnusedInstantiationCheck();
		ArrayList<MyVarInsnNode> loaded = new ArrayList<>();
		ArrayList<MyVarInsnNode> stored = new ArrayList<>();
		MyMethodNode method = EasyMock.createMock(MyMethodNode.class);
		
		MyVarInsnNode lNode1 = EasyMock.createMock(MyVarInsnNode.class);
		MyVarInsnNode lNode2 = EasyMock.createMock(MyVarInsnNode.class);
		MyVarInsnNode lNode3 = EasyMock.createMock(MyVarInsnNode.class);
		
		MyVarInsnNode sNode1 = EasyMock.createMock(MyVarInsnNode.class);
		MyVarInsnNode sNode2 = EasyMock.createMock(MyVarInsnNode.class);
		MyVarInsnNode sNode3 = EasyMock.createMock(MyVarInsnNode.class);
		
		loaded.add(lNode1);
		loaded.add(lNode2);
		loaded.add(lNode3);
		stored.add(sNode1);
		stored.add(sNode2);
		stored.add(sNode3);
		
		lNode1.var = 2;
		lNode2.var = 3;
		lNode3.var = 4;
		sNode1.var = 0;
		sNode2.var = 2;
		sNode3.var = 3;
		
		LinkedList<MyAbstractInsnNode> insns = new LinkedList<>();
		MyLineNumberNode lNode = EasyMock.createMock(MyLineNumberNode.class);
		insns.add(lNode);
		insns.add(sNode1);
		EasyMock.expect(sNode1.getType()).andReturn(MyAbstractInsnNode.VAR_INSN);
		EasyMock.expect(lNode.getType()).andReturn(MyAbstractInsnNode.LINE);
		lNode.line = 12;
		ArrayList<MyLocalVariableNode> vars = new ArrayList<>();
		method.instructions = insns;
		method.localVariables = vars;
		method.name = "countThings";
		
		EasyMock.replay(lNode1, lNode2, lNode3, sNode1, sNode2, sNode3, method, lNode);

		assertEquals("			Line 12: Unused variable in method countThings\n", check.findUnusedVariables(loaded, stored, method));
		
		EasyMock.verify(lNode1, lNode2, lNode3, sNode1, sNode2, sNode3, method, lNode);
	}
	
	@Test
	public void testFindVariablesMethodsLocalVars() {
		UnusedInstantiationCheck check = new UnusedInstantiationCheck();
		MyMethodNode method = EasyMock.createMock(MyMethodNode.class);
		LinkedList<MyAbstractInsnNode> insns = new LinkedList<>();
		
		MyVarInsnNode lNode1 = EasyMock.createMock(MyVarInsnNode.class);
		MyVarInsnNode lNode2 = EasyMock.createMock(MyVarInsnNode.class);
		
		MyVarInsnNode sNode1 = EasyMock.createMock(MyVarInsnNode.class);
		MyVarInsnNode sNode2 = EasyMock.createMock(MyVarInsnNode.class);
		MyVarInsnNode sNode3 = EasyMock.createMock(MyVarInsnNode.class);
		
		MyLineNumberNode lNode = EasyMock.createMock(MyLineNumberNode.class);

		insns.add(lNode1);
		insns.add(lNode2);
		insns.add(lNode);
		insns.add(sNode1);
		insns.add(sNode2);
		insns.add(sNode3);
		
		lNode1.var = 2;
		lNode2.var = 3;
		sNode1.var = 0;
		sNode2.var = 2;
		sNode3.var = 3;
		lNode.line = 12;
		
		EasyMock.expect(lNode1.getType()).andReturn(MyAbstractInsnNode.VAR_INSN);
		EasyMock.expect(lNode1.isLoading()).andReturn(true);
		EasyMock.expect(lNode2.getType()).andReturn(MyAbstractInsnNode.VAR_INSN);
		EasyMock.expect(lNode2.isLoading()).andReturn(true);
		EasyMock.expect(lNode.getType()).andReturn(MyAbstractInsnNode.LINE);
		EasyMock.expect(lNode.getType()).andReturn(MyAbstractInsnNode.LINE);
		EasyMock.expect(sNode1.getType()).andReturn(MyAbstractInsnNode.VAR_INSN);
		EasyMock.expect(sNode1.isLoading()).andReturn(false);
		EasyMock.expect(sNode1.isStoring()).andReturn(true);
		EasyMock.expect(sNode2.getType()).andReturn(MyAbstractInsnNode.VAR_INSN);
		EasyMock.expect(sNode2.isLoading()).andReturn(false);
		EasyMock.expect(sNode2.isStoring()).andReturn(true);
		EasyMock.expect(sNode3.getType()).andReturn(MyAbstractInsnNode.VAR_INSN);
		EasyMock.expect(sNode3.isLoading()).andReturn(false);
		EasyMock.expect(sNode3.isStoring()).andReturn(true);
		
		EasyMock.expect(sNode1.getType()).andReturn(MyAbstractInsnNode.VAR_INSN);
		EasyMock.expect(lNode.getType()).andReturn(MyAbstractInsnNode.LINE);
		
		ArrayList<MyLocalVariableNode> vars = new ArrayList<>();
		MyLocalVariableNode vLNode = EasyMock.createMock(MyLocalVariableNode.class);
		vars.add(vLNode);
		vLNode.name = "counter";
		method.instructions = insns;
		method.localVariables = vars;
		method.name = "countThings";
		
		EasyMock.replay(lNode1, lNode2, sNode1, sNode2, sNode3, method, lNode, vLNode);
		
		assertEquals("			Line 12: Unused variable named counter in method countThings\n", check.findVariablesMethods(method));
		
		EasyMock.verify(lNode1, lNode2, sNode1, sNode2, sNode3, method, lNode, vLNode);
	}
	
	@Test
	public void testFindVariablesMethodsFields() {
		UnusedInstantiationCheck check = new UnusedInstantiationCheck();
		MyMethodNode method = EasyMock.createMock(MyMethodNode.class);
		LinkedList<MyAbstractInsnNode> insns = new LinkedList<>();
		
		MyLineNumberNode lNode = EasyMock.createMock(MyLineNumberNode.class);
		MyFieldInsnNode fInsn1 = EasyMock.createMock(MyFieldInsnNode.class);
		MyFieldInsnNode fInsn2 = EasyMock.createMock(MyFieldInsnNode.class);
		
		insns.add(lNode);
		insns.add(fInsn1);
		insns.add(fInsn2);
		lNode.line = 12;
		
		EasyMock.expect(lNode.getType()).andReturn(MyAbstractInsnNode.LINE);
		EasyMock.expect(lNode.getType()).andReturn(MyAbstractInsnNode.LINE);
		EasyMock.expect(fInsn1.getType()).andReturn(MyAbstractInsnNode.FIELD_INSN);
		EasyMock.expect(fInsn1.getType()).andReturn(MyAbstractInsnNode.FIELD_INSN);
		EasyMock.expect(fInsn1.isLoading()).andReturn(true);
		EasyMock.expect(fInsn2.getType()).andReturn(MyAbstractInsnNode.FIELD_INSN);
		EasyMock.expect(fInsn2.getType()).andReturn(MyAbstractInsnNode.FIELD_INSN);
		EasyMock.expect(fInsn2.isLoading()).andReturn(false);
		
		method.instructions = insns;
		
		EasyMock.replay(method, lNode, fInsn1, fInsn2);
		
		assertEquals("", check.findVariablesMethods(method));
		assertTrue(check.fieldStates.fieldLoading.contains(fInsn1));
		assertTrue(check.fieldStates.fieldStoring.contains(fInsn2));
		
		EasyMock.verify(method, lNode, fInsn1, fInsn2);
	}
	
	@Test
	public void testFindUnusedFieldsUnknownLine() {
		UnusedInstantiationCheck check = new UnusedInstantiationCheck();
		MyClassNode classNode = EasyMock.createMock(MyClassNode.class);
		
		MyFieldInsnNode fInsn1 = EasyMock.createMock(MyFieldInsnNode.class);
		MyFieldInsnNode fInsn2 = EasyMock.createMock(MyFieldInsnNode.class);
		
		ArrayList<MyFieldInsnNode> fieldLoading = new ArrayList<>();
		ArrayList<MyFieldInsnNode> fieldStoring = new ArrayList<>();
		fieldLoading.add(fInsn1);
		fieldStoring.add(fInsn2);
		check.fieldStates.fieldLoading = fieldLoading;
		check.fieldStates.fieldStoring = fieldStoring;
		
		fInsn1.name = "counter";
		fInsn2.name = "download";
		
		MyMethodNode method1 = EasyMock.createMock(MyMethodNode.class);
		ArrayList<MyMethodNode> methods = new ArrayList<>();
		methods.add(method1);
		classNode.methods = methods;
		method1.instructions = new LinkedList<>();
		
		EasyMock.replay(fInsn1, fInsn2, classNode, method1);
		assertEquals("			Unknown line number: Unused field named download\n", check.findUnusedFields(classNode));
		
		EasyMock.verify(fInsn1, fInsn2, classNode, method1);
	}
	
	@Test
	public void testFindUnusedFieldsKnownLine() {
		UnusedInstantiationCheck check = new UnusedInstantiationCheck();
		MyClassNode classNode = EasyMock.createMock(MyClassNode.class);
		
		MyFieldInsnNode fInsn1 = EasyMock.createMock(MyFieldInsnNode.class);
		MyFieldInsnNode fInsn2 = EasyMock.createMock(MyFieldInsnNode.class);
		
		ArrayList<MyFieldInsnNode> fieldLoading = new ArrayList<>();
		ArrayList<MyFieldInsnNode> fieldStoring = new ArrayList<>();
		fieldLoading.add(fInsn1);
		fieldStoring.add(fInsn2);
		check.fieldStates.fieldLoading = fieldLoading;
		check.fieldStates.fieldStoring = fieldStoring;
		
		fInsn1.name = "counter";
		fInsn2.name = "download";
		
		MyMethodNode method1 = EasyMock.createMock(MyMethodNode.class);
		ArrayList<MyMethodNode> methods = new ArrayList<>();
		methods.add(method1);
		classNode.methods = methods;
		
		MyLineNumberNode lNode = EasyMock.createMock(MyLineNumberNode.class);
		lNode.line = 12;
		
		LinkedList<MyAbstractInsnNode> insns = new LinkedList<>();
		insns.add(lNode);
		insns.add(fInsn2);
		insns.add(fInsn1);
		
		method1.instructions = insns;
		
		EasyMock.expect(fInsn2.getType()).andReturn(MyAbstractInsnNode.FIELD_INSN);
		EasyMock.expect(lNode.getType()).andReturn(MyAbstractInsnNode.LINE);
		
		EasyMock.replay(fInsn1, fInsn2, classNode, method1, lNode);
		assertEquals("			Line 12: Unused field named download\n", check.findUnusedFields(classNode));
		
		EasyMock.verify(fInsn1, fInsn2, classNode, method1, lNode);
	}
	
	@Test
	public void testRunCheck() {
		UnusedInstantiationCheck check = new UnusedInstantiationCheck();
		MyClassNode classNode = EasyMock.createMock(MyClassNode.class);
		ArrayList<MyClassNode> classes = new ArrayList<>();
		classes.add(classNode);
		LinkedList<MyAbstractInsnNode> insns = new LinkedList<>();
		
		MyVarInsnNode lInsn1 = EasyMock.createMock(MyVarInsnNode.class);
		MyVarInsnNode lInsn2 = EasyMock.createMock(MyVarInsnNode.class);
		
		MyVarInsnNode sNode1 = EasyMock.createMock(MyVarInsnNode.class);
		MyVarInsnNode sNode2 = EasyMock.createMock(MyVarInsnNode.class);
		MyVarInsnNode sNode3 = EasyMock.createMock(MyVarInsnNode.class);
		
		MyLineNumberNode lNode1 = EasyMock.createMock(MyLineNumberNode.class);
		MyLineNumberNode lNode2 = EasyMock.createMock(MyLineNumberNode.class);
		
		MyFieldInsnNode fInsn1 = EasyMock.createMock(MyFieldInsnNode.class);
		MyFieldInsnNode fInsn2 = EasyMock.createMock(MyFieldInsnNode.class);
		
		MyMethodNode method = EasyMock.createMock(MyMethodNode.class);
		ArrayList<MyMethodNode> methods = new ArrayList<>();
		methods.add(method);
		classNode.methods = methods;

		insns.addAll(Arrays.asList(lInsn1, lInsn2, lNode1, sNode1, sNode2, sNode3, lNode2, fInsn1, fInsn2));
		
		lInsn1.var = 2;
		lInsn2.var = 3;
		sNode1.var = 0;
		sNode2.var = 2;
		sNode3.var = 3;
		lNode1.line = 15;
		lNode2.line = 12;
		fInsn1.name = "counter";
		fInsn2.name = "download";
		
		EasyMock.expect(classNode.getCleanName()).andReturn(null);
		EasyMock.expect(classNode.getCleanName()).andReturn(null);
		EasyMock.expect(lInsn1.getType()).andReturn(MyAbstractInsnNode.VAR_INSN);
		EasyMock.expect(lInsn1.isLoading()).andReturn(true);
		EasyMock.expect(lInsn2.getType()).andReturn(MyAbstractInsnNode.VAR_INSN);
		EasyMock.expect(lInsn2.isLoading()).andReturn(true);
		EasyMock.expect(lNode1.getType()).andReturn(MyAbstractInsnNode.LINE);
		EasyMock.expect(lNode1.getType()).andReturn(MyAbstractInsnNode.LINE);
		EasyMock.expect(sNode1.getType()).andReturn(MyAbstractInsnNode.VAR_INSN);
		EasyMock.expect(sNode1.isLoading()).andReturn(false);
		EasyMock.expect(sNode1.isStoring()).andReturn(true);
		EasyMock.expect(sNode2.getType()).andReturn(MyAbstractInsnNode.VAR_INSN);
		EasyMock.expect(sNode2.isLoading()).andReturn(false);
		EasyMock.expect(sNode2.isStoring()).andReturn(true);
		EasyMock.expect(sNode3.getType()).andReturn(MyAbstractInsnNode.VAR_INSN);
		EasyMock.expect(sNode3.isLoading()).andReturn(false);
		EasyMock.expect(sNode3.isStoring()).andReturn(true);
		EasyMock.expect(lNode2.getType()).andReturn(MyAbstractInsnNode.LINE);
		EasyMock.expect(lNode2.getType()).andReturn(MyAbstractInsnNode.LINE);
		EasyMock.expect(fInsn1.getType()).andReturn(MyAbstractInsnNode.FIELD_INSN);
		EasyMock.expect(fInsn1.getType()).andReturn(MyAbstractInsnNode.FIELD_INSN);
		EasyMock.expect(fInsn1.isLoading()).andReturn(true);
		EasyMock.expect(fInsn2.getType()).andReturn(MyAbstractInsnNode.FIELD_INSN);
		EasyMock.expect(fInsn2.getType()).andReturn(MyAbstractInsnNode.FIELD_INSN);
		EasyMock.expect(fInsn2.isLoading()).andReturn(false);
		
		EasyMock.expect(sNode1.getType()).andReturn(MyAbstractInsnNode.VAR_INSN);
		EasyMock.expect(lNode1.getType()).andReturn(MyAbstractInsnNode.LINE);
		
		EasyMock.expect(fInsn1.getType()).andReturn(MyAbstractInsnNode.FIELD_INSN);
		EasyMock.expect(fInsn2.getType()).andReturn(MyAbstractInsnNode.FIELD_INSN);
		EasyMock.expect(lNode2.getType()).andReturn(MyAbstractInsnNode.LINE);

		ArrayList<MyLocalVariableNode> vars = new ArrayList<>();
		MyLocalVariableNode vLNode = EasyMock.createMock(MyLocalVariableNode.class);
		vars.add(vLNode);
		vLNode.name = "counter";
		method.instructions = insns;
		method.localVariables = vars;
		method.name = "countThings";

		EasyMock.replay(lInsn1, lInsn2, sNode1, sNode2, sNode3, method, lNode1, lNode2, fInsn1, fInsn2, vLNode, classNode);
		
		assertEquals("Unused Instantiation Check:\n	Class: null\n		Unused Variables: \n" + "			Line 12: Unused field named download\n" 
				+ "			Line 15: Unused variable named counter in method countThings\n" , check.runCheck(classes));

		assertTrue(check.fieldStates.fieldLoading.contains(fInsn1));
		assertTrue(check.fieldStates.fieldStoring.contains(fInsn2));
		
		EasyMock.verify(lInsn1, lInsn2, sNode1, sNode2, sNode3, method, lNode1, lNode2, fInsn1, fInsn2, vLNode, classNode);
	}
	
}
