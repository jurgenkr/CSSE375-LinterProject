package domain;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import data_source.MyAbstractInsnNode;
import data_source.MyClassNode;
import data_source.MyFieldInsnNode;
import data_source.MyLocalVariableNode;
import data_source.MyMethodInsnNode;
import data_source.MyMethodNode;


// Note: These tests were written for when the system did not use personalized class nodes
public class HollywoodCheckTest {
	HollywoodCheck checker = new HollywoodCheck();
	
	@Test
	public void testUsingField() {
		MyClassNode curClass = EasyMock.createMock(MyClassNode.class);
		MyClassNode superClass = EasyMock.createMock(MyClassNode.class);
		ArrayList<String> emptyArrayList = new ArrayList<>();
		
		EasyMock.expect(curClass.getMethodNames()).andReturn(emptyArrayList);
		EasyMock.expect(superClass.getMethodNames()).andReturn(emptyArrayList);
		
		ArrayList<String> superFieldNames = new ArrayList<>();
		superFieldNames.add("repeated");
		EasyMock.expect(curClass.getFieldNames()).andReturn(emptyArrayList);
		EasyMock.expect(superClass.getFieldNames()).andReturn(superFieldNames);
		
		MyMethodNode m1 = EasyMock.createMock(MyMethodNode.class);
		ArrayList<MyMethodNode> methods = new ArrayList<MyMethodNode>();
		methods.add(m1);
		EasyMock.expect(curClass.getCleanName()).andReturn("SubClass");
		curClass.methods = methods;
		
		LinkedList<MyAbstractInsnNode> insns = new LinkedList<MyAbstractInsnNode>();
		MyFieldInsnNode n1a = EasyMock.createMock(MyFieldInsnNode.class);
		n1a.name = "repeated";
		MyAbstractInsnNode n1 = n1a;
		insns.add(n1);
		
		m1.instructions = insns;
		m1.name = "badMethod";
		EasyMock.expect(m1.getVarNames()).andReturn(new ArrayList<String>());
		
		EasyMock.expect(superClass.getCleanName()).andReturn("SuperClass");
		
		EasyMock.replay(curClass, superClass, m1, n1a);
		assertEquals("	Class SubClass uses field repeated from SuperClass in method badMethod\n",
				checker.checkHollywoodViolations(curClass, superClass));
		EasyMock.verify(curClass, superClass, m1, n1a);
	}
	
	@Test
	public void testUsingFieldVarInMethod() {
		MyClassNode curClass = EasyMock.createMock(MyClassNode.class);
		MyClassNode superClass = EasyMock.createMock(MyClassNode.class);
		ArrayList<String> emptyArrayList = new ArrayList<>();

		EasyMock.expect(curClass.getMethodNames()).andReturn(emptyArrayList);
		EasyMock.expect(superClass.getMethodNames()).andReturn(emptyArrayList);
		
		ArrayList<String> superFieldNames = new ArrayList<>();
		superFieldNames.add("repeated");
		EasyMock.expect(curClass.getFieldNames()).andReturn(emptyArrayList);
		EasyMock.expect(superClass.getFieldNames()).andReturn(superFieldNames);
		
		MyMethodNode m1 = EasyMock.createMock(MyMethodNode.class);
		ArrayList<MyMethodNode> methods = new ArrayList<MyMethodNode>();
		methods.add(m1);
		curClass.methods = methods;
		
		LinkedList<MyAbstractInsnNode> insns = new LinkedList<MyAbstractInsnNode>();
		MyFieldInsnNode n1a = EasyMock.createMock(MyFieldInsnNode.class);
		n1a.name = "repeated";
		MyAbstractInsnNode n1 = n1a;
		insns.add(n1);
		
		MyLocalVariableNode lv1 = EasyMock.createMock(MyLocalVariableNode.class);
		ArrayList<String> locals = new ArrayList<>();
		locals.add("repeated");
		m1.instructions = insns;
		m1.name = "badMethod";
		EasyMock.expect(m1.getVarNames()).andReturn(locals);
		
		
		EasyMock.replay(curClass, superClass, m1, n1a, lv1);
		assertEquals("",
				checker.checkHollywoodViolations(curClass, superClass));
		
		EasyMock.verify(curClass, superClass, m1, n1a, lv1);
	}
	
	@Test
	public void testUsingMethod() {
		MyClassNode curClass = EasyMock.createMock(MyClassNode.class);
		MyClassNode superClass = EasyMock.createMock(MyClassNode.class);
		ArrayList<String> emptyArrayList = new ArrayList<>();
		
		ArrayList<String> superMethodNames = new ArrayList<>();
		superMethodNames.add("repeated");
		EasyMock.expect(curClass.getMethodNames()).andReturn(emptyArrayList);
		EasyMock.expect(superClass.getMethodNames()).andReturn(superMethodNames);

		EasyMock.expect(curClass.getFieldNames()).andReturn(emptyArrayList);
		EasyMock.expect(superClass.getFieldNames()).andReturn(emptyArrayList);
		
		MyMethodNode m1 = EasyMock.createMock(MyMethodNode.class);
		ArrayList<MyMethodNode> methods = new ArrayList<MyMethodNode>();
		methods.add(m1);
		EasyMock.expect(curClass.getCleanName()).andReturn("SubClass");
		curClass.methods = methods;
		
		LinkedList<MyAbstractInsnNode> insns = new LinkedList<MyAbstractInsnNode>();
		MyMethodInsnNode n1a = EasyMock.createMock(MyMethodInsnNode.class);
		n1a.name = "repeated";
		MyAbstractInsnNode n1 = n1a;
		insns.add(n1);
		
		m1.instructions = insns;
		m1.name = "badMethod";
		EasyMock.expect(m1.getVarNames()).andReturn(new ArrayList<String>());

		EasyMock.expect(superClass.getCleanName()).andReturn("SuperClass");
		
		EasyMock.replay(curClass, superClass, m1, n1a);
		assertEquals("	Class SubClass calls method repeated from SuperClass in method badMethod\n",
				checker.checkHollywoodViolations(curClass, superClass));
		
		EasyMock.verify(curClass, superClass, m1, n1a);
	}
}
