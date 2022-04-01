package domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import data_source.MyAbstractInsnNode;
import data_source.MyClassNode;
import data_source.MyFieldNode;
import data_source.MyMethodInsnNode;
import data_source.MyMethodNode;

class FacadePatternTest {

	@Test
	public void testGetName() {
		FacadePatternCheck check = new FacadePatternCheck();
		assertEquals("Facade Pattern", check.getName());
	}
	
	@Test
	public void testCheckForFacadeTrue() {
		FacadePatternCheck check = new FacadePatternCheck();
		MyClassNode classNode = EasyMock.createMock(MyClassNode.class);
		MyMethodNode method1 = EasyMock.createMock(MyMethodNode.class);
		MyMethodInsnNode mInsn1 = EasyMock.createMock(MyMethodInsnNode.class);
		MyMethodInsnNode mInsn2 = EasyMock.createMock(MyMethodInsnNode.class);
		MyMethodInsnNode mInsn3 = EasyMock.createMock(MyMethodInsnNode.class);

		EasyMock.expect(classNode.getFullName()).andReturn("Java/Theater");
		EasyMock.expect(mInsn1.getFullOwner()).andReturn("Java/PopcornMaker");
		EasyMock.expect(mInsn2.getFullOwner()).andReturn("Java/ArrayList");
		EasyMock.expect(mInsn3.getFullOwner()).andReturn("Java/Projector");
		EasyMock.expect(mInsn1.getFullOwner()).andReturn("Java/PopcornMaker");
		EasyMock.expect(mInsn3.getFullOwner()).andReturn("Java/Projector");
		
		ArrayList<String> fieldUserClassNames = new ArrayList<>(Arrays.asList("Java/PopcornMaker", "Java/Projector"));
		method1.instructions = new LinkedList<>(Arrays.asList(mInsn1, mInsn2, mInsn3));
		
		EasyMock.expect(mInsn1.getType()).andReturn(MyAbstractInsnNode.METHOD_INSN);
		EasyMock.expect(mInsn1.isInvokeVirtual()).andReturn(true);
		EasyMock.expect(mInsn2.getType()).andReturn(MyAbstractInsnNode.METHOD_INSN);
		EasyMock.expect(mInsn3.getType()).andReturn(MyAbstractInsnNode.METHOD_INSN);
		EasyMock.expect(mInsn3.isInvokeVirtual()).andReturn(true);
		
		classNode.methods = new ArrayList<>(Arrays.asList(method1));
		
		EasyMock.replay(classNode, method1, mInsn1, mInsn2, mInsn3);
		assertEquals("	Java/Theater looks to be a facade for the following classes:\n" 
				+ "		Java/PopcornMaker\n" + "		Java/Projector\n", check.checkForFacade(classNode, fieldUserClassNames));
		
		EasyMock.verify(classNode, method1, mInsn1, mInsn2, mInsn3);
	}
	
	@Test
	public void testCheckForFacadeMissingClasses() {
		FacadePatternCheck check = new FacadePatternCheck();
		MyClassNode classNode = EasyMock.createMock(MyClassNode.class);
		MyMethodNode method1 = EasyMock.createMock(MyMethodNode.class);
		MyMethodInsnNode mInsn1 = EasyMock.createMock(MyMethodInsnNode.class);
		MyMethodInsnNode mInsn2 = EasyMock.createMock(MyMethodInsnNode.class);
		MyMethodInsnNode mInsn3 = EasyMock.createMock(MyMethodInsnNode.class);
		
		EasyMock.expect(classNode.getFullName()).andReturn("Java/Theater");
		EasyMock.expect(mInsn1.getFullOwner()).andReturn("Java/PopcornMaker");
		EasyMock.expect(mInsn1.getFullOwner()).andReturn("Java/PopcornMaker");
		EasyMock.expect(mInsn2.getFullOwner()).andReturn("Java/ArrayList");
		EasyMock.expect(mInsn3.getFullOwner()).andReturn("Java/Projector");
		EasyMock.expect(mInsn3.getFullOwner()).andReturn("Java/Projector");
		
		ArrayList<String> fieldUserClassNames = new ArrayList<>(Arrays.asList("Java/PopcornMaker", "Java/Projector", "Java/Lights"));
		method1.instructions = new LinkedList<>(Arrays.asList(mInsn1, mInsn2, mInsn3));
		
		EasyMock.expect(mInsn1.getType()).andReturn(MyAbstractInsnNode.METHOD_INSN);
		EasyMock.expect(mInsn1.isInvokeVirtual()).andReturn(true);
		EasyMock.expect(mInsn2.getType()).andReturn(MyAbstractInsnNode.METHOD_INSN);
		EasyMock.expect(mInsn3.getType()).andReturn(MyAbstractInsnNode.METHOD_INSN);
		EasyMock.expect(mInsn3.isInvokeVirtual()).andReturn(true);
		
		classNode.methods = new ArrayList<>(Arrays.asList(method1));
		
		EasyMock.replay(classNode, method1, mInsn1, mInsn2, mInsn3);
		assertEquals("	Java/Theater might be an attempt at facade pattern. It is missing calls to methods in these classes:\n" 
				+ "		Java/Lights\n", check.checkForFacade(classNode, fieldUserClassNames));
		
		EasyMock.verify(classNode, method1, mInsn1, mInsn2, mInsn3);
	}
	
	@Test
	public void testRunCheckWithFacade() {
		FacadePatternCheck check = new FacadePatternCheck();
		MyClassNode classNode1 = EasyMock.createMock(MyClassNode.class);
		MyClassNode classNode2 = EasyMock.createMock(MyClassNode.class);
		MyClassNode classNode3 = EasyMock.createMock(MyClassNode.class);
		MyMethodNode method1 = EasyMock.createMock(MyMethodNode.class);
		MyMethodInsnNode mInsn1 = EasyMock.createMock(MyMethodInsnNode.class);
		MyMethodInsnNode mInsn2 = EasyMock.createMock(MyMethodInsnNode.class);
		MyMethodInsnNode mInsn3 = EasyMock.createMock(MyMethodInsnNode.class);
		MyFieldNode field1 = EasyMock.createMock(MyFieldNode.class);
		MyFieldNode field2 = EasyMock.createMock(MyFieldNode.class);
		MyFieldNode field3 = EasyMock.createMock(MyFieldNode.class);
		
		
		EasyMock.expect(classNode1.getFullName()).andReturn("Java/Theater");
		EasyMock.expect(classNode1.getCleanName()).andReturn("Theater");
		EasyMock.expect(classNode2.getFullName()).andReturn("Java/PopcornMaker");
		EasyMock.expect(classNode2.getCleanName()).andReturn("PopcornMaker");
		EasyMock.expect(classNode3.getFullName()).andReturn("Java/Projector");
		EasyMock.expect(classNode3.getCleanName()).andReturn("Projector");
		EasyMock.expect(classNode1.getFullName()).andReturn("Java/Theater");
		
		EasyMock.expect(mInsn1.getFullOwner()).andReturn("Java/PopcornMaker");
		EasyMock.expect(mInsn2.getFullOwner()).andReturn("Java/ArrayList");
		EasyMock.expect(mInsn3.getFullOwner()).andReturn("Java/Projector");
		EasyMock.expect(mInsn1.getFullOwner()).andReturn("Java/PopcornMaker");
		EasyMock.expect(mInsn3.getFullOwner()).andReturn("Java/Projector");
		EasyMock.expect(field1.getFullDesc()).andReturn("LJava/PopcornMaker;");
		EasyMock.expect(field2.getFullDesc()).andReturn("LJava/ArrayList;");
		EasyMock.expect(field3.getFullDesc()).andReturn("LJava/Projector;");
	
		
		method1.instructions = new LinkedList<>(Arrays.asList(mInsn1, mInsn2, mInsn3));
		
		EasyMock.expect(mInsn1.getType()).andReturn(MyAbstractInsnNode.METHOD_INSN);
		EasyMock.expect(mInsn1.isInvokeVirtual()).andReturn(true);
		EasyMock.expect(mInsn2.getType()).andReturn(MyAbstractInsnNode.METHOD_INSN);
		EasyMock.expect(mInsn3.getType()).andReturn(MyAbstractInsnNode.METHOD_INSN);
		EasyMock.expect(mInsn3.isInvokeVirtual()).andReturn(true);
		
		classNode1.methods = new ArrayList<>(Arrays.asList(method1));
		classNode1.fields = new ArrayList<>(Arrays.asList(field1, field2, field3));
		classNode2.fields = new ArrayList<>();
		classNode3.fields = new ArrayList<>();
		
		ArrayList<MyClassNode> classes = new ArrayList<>(Arrays.asList(classNode1, classNode2, classNode3));
		
		EasyMock.replay(classNode1, classNode2, classNode3, method1, mInsn1, mInsn2, mInsn3, field1, field2, field3);
		assertEquals("\nFacade Pattern Check:\n" + "	Java/Theater looks to be a facade for the following classes:\n" 
				+ "		Java/PopcornMaker\n" + "		Java/Projector\n", check.runCheck(classes));
		
		EasyMock.verify(classNode1, classNode2, classNode3, method1, mInsn1, mInsn2, mInsn3, field1, field2, field3);
	}
	
	@Test
	public void testRunCheckWithFailedFacade() {
		FacadePatternCheck check = new FacadePatternCheck();
		MyClassNode classNode1 = EasyMock.createMock(MyClassNode.class);
		MyClassNode classNode2 = EasyMock.createMock(MyClassNode.class);
		MyClassNode classNode3 = EasyMock.createMock(MyClassNode.class);
		MyClassNode classNode4 = EasyMock.createMock(MyClassNode.class);
		MyMethodNode method1 = EasyMock.createMock(MyMethodNode.class);
		MyMethodInsnNode mInsn1 = EasyMock.createMock(MyMethodInsnNode.class);
		MyMethodInsnNode mInsn2 = EasyMock.createMock(MyMethodInsnNode.class);
		MyMethodInsnNode mInsn3 = EasyMock.createMock(MyMethodInsnNode.class);
		MyFieldNode field1 = EasyMock.createMock(MyFieldNode.class);
		MyFieldNode field2 = EasyMock.createMock(MyFieldNode.class);
		MyFieldNode field3 = EasyMock.createMock(MyFieldNode.class);
		
		EasyMock.expect(classNode1.getFullName()).andReturn("Java/Theater");
		EasyMock.expect(classNode1.getCleanName()).andReturn("Theater");
		EasyMock.expect(classNode2.getFullName()).andReturn("Java/PopcornMaker");
		EasyMock.expect(classNode2.getCleanName()).andReturn("PopcornMaker");
		EasyMock.expect(classNode3.getFullName()).andReturn("Java/Projector");
		EasyMock.expect(classNode3.getCleanName()).andReturn("Projector");
		EasyMock.expect(classNode4.getFullName()).andReturn("Java/Lights");
		EasyMock.expect(classNode4.getCleanName()).andReturn("Lights");
		EasyMock.expect(classNode1.getFullName()).andReturn("Java/TheaterFacade");
		
		
		EasyMock.expect(mInsn1.getFullOwner()).andReturn("Java/PopcornMaker");
		EasyMock.expect(mInsn2.getFullOwner()).andReturn("Java/ArrayList");
		EasyMock.expect(mInsn3.getFullOwner()).andReturn("Java/Projector");
		EasyMock.expect(mInsn1.getFullOwner()).andReturn("Java/PopcornMaker");
		EasyMock.expect(mInsn3.getFullOwner()).andReturn("Java/Projector");
		EasyMock.expect(field1.getFullDesc()).andReturn("LJava/PopcornMaker;");
		EasyMock.expect(field2.getFullDesc()).andReturn("LJava/Lights;");
		EasyMock.expect(field3.getFullDesc()).andReturn("LJava/Projector;");
		
		method1.instructions = new LinkedList<>(Arrays.asList(mInsn1, mInsn2, mInsn3));
		
		EasyMock.expect(mInsn1.getType()).andReturn(MyAbstractInsnNode.METHOD_INSN);
		EasyMock.expect(mInsn1.isInvokeVirtual()).andReturn(true);
		EasyMock.expect(mInsn2.getType()).andReturn(MyAbstractInsnNode.METHOD_INSN);
		EasyMock.expect(mInsn3.getType()).andReturn(MyAbstractInsnNode.METHOD_INSN);
		EasyMock.expect(mInsn3.isInvokeVirtual()).andReturn(true);
		
		classNode1.methods = new ArrayList<>(Arrays.asList(method1));
		classNode1.fields = new ArrayList<>(Arrays.asList(field1, field2, field3));
		classNode2.fields = new ArrayList<>();
		classNode3.fields = new ArrayList<>();
		classNode4.fields = new ArrayList<>();
		
		ArrayList<MyClassNode> classes = new ArrayList<>(Arrays.asList(classNode1, classNode2, classNode3, classNode4));
		
		EasyMock.replay(classNode1, classNode2, classNode3, classNode4, method1, mInsn1, mInsn2, mInsn3, field1, field2, field3);
		assertEquals("\nFacade Pattern Check:\n" + "	Java/TheaterFacade might be an attempt at facade pattern. It is missing calls to methods in these classes:\n" 
				+ "		Java/Lights\n", check.runCheck(classes));
		
		EasyMock.verify(classNode1, classNode2, classNode3, classNode4, method1, mInsn1, mInsn2, mInsn3, field1, field2, field3);
	}
	
	@Test
	public void testRunCheckWithClassNameFacade() {
		FacadePatternCheck check = new FacadePatternCheck();
		MyClassNode classNode1 = EasyMock.createMock(MyClassNode.class);
		MyClassNode classNode2 = EasyMock.createMock(MyClassNode.class);
		MyClassNode classNode3 = EasyMock.createMock(MyClassNode.class);
		MyClassNode classNode4 = EasyMock.createMock(MyClassNode.class);
		
		
		EasyMock.expect(classNode1.getFullName()).andReturn("Java/TheaterFacade");
		EasyMock.expect(classNode1.getCleanName()).andReturn("TheaterFacade");
		EasyMock.expect(classNode2.getFullName()).andReturn("Java/PopcornMaker");
		EasyMock.expect(classNode2.getCleanName()).andReturn("PopcornMaker");
		EasyMock.expect(classNode3.getFullName()).andReturn("Java/Projector");
		EasyMock.expect(classNode3.getCleanName()).andReturn("Projector");
		EasyMock.expect(classNode4.getFullName()).andReturn("Java/Lights");
		EasyMock.expect(classNode4.getCleanName()).andReturn("Lights");
		EasyMock.expect(classNode1.getFullName()).andReturn("Java/TheaterFacade");
		
		classNode1.fields = new ArrayList<>();
		classNode2.fields = new ArrayList<>();
		classNode3.fields = new ArrayList<>();
		classNode4.fields = new ArrayList<>();
		
		ArrayList<MyClassNode> classes = new ArrayList<>(Arrays.asList(classNode1, classNode2, classNode3, classNode4));
		
		EasyMock.replay(classNode1, classNode2, classNode3, classNode4);
		assertEquals("\nFacade Pattern Check:\n" + "	Java/TheaterFacade contains the word 'facade' but is not a facade pattern\n"
				, check.runCheck(classes));
		
		EasyMock.verify(classNode1, classNode2, classNode3, classNode4);
	}

}
