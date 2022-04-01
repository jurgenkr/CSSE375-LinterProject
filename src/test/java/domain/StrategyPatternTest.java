package domain;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.List;
import java.util.ArrayList;
import java.util.LinkedList;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import data_source.MyAbstractInsnNode;
import data_source.MyClassNode;
import data_source.MyFieldInsnNode;
import data_source.MyFieldNode;
import data_source.MyMethodInsnNode;
import data_source.MyMethodNode;
import domain.StrategyPatternCheck;

class StrategyPatternTest {

	StrategyPatternCheck checker = new StrategyPatternCheck();
	
	@Test
	void failNoCall() {
		MyClassNode interfaceClass = EasyMock.createMock(MyClassNode.class);
		MyClassNode contextClass = EasyMock.createMock(MyClassNode.class);
		ArrayList<MyClassNode> classes = new ArrayList<>();
		classes.add(interfaceClass);
		classes.add(contextClass);
		
		MyMethodNode contextConstructor = EasyMock.createMock(MyMethodNode.class);
		EasyMock.expect(interfaceClass.getConstructor()).andReturn(null);
		EasyMock.expect(contextClass.getConstructor()).andReturn(contextConstructor);
		
		MyFieldNode interfaceField = EasyMock.createMock(MyFieldNode.class);
		ArrayList<MyFieldNode> contextFields = new ArrayList<>();
		contextFields.add(interfaceField);
		contextClass.fields = contextFields;
		
		ArrayList<String> argTypeNames = new ArrayList<>();
		argTypeNames.add("Pattern");
		EasyMock.expect(contextConstructor.getCleanArgTypes()).andReturn(argTypeNames);
		
		MyFieldInsnNode interfaceInit = EasyMock.createMock(MyFieldInsnNode.class);
		ArrayList<MyFieldInsnNode> constructorInsns = new ArrayList<>();
		constructorInsns.add(interfaceInit);
		EasyMock.expect(contextConstructor.getFieldInstructionNodes()).andReturn(constructorInsns);
		interfaceField.name = "strategyPattern";
		interfaceInit.name = "strategyPattern";
		EasyMock.expect(interfaceField.getCleanDesc()).andReturn("Pattern");
		EasyMock.expect(interfaceField.getCleanDesc()).andReturn("Pattern");
		
		EasyMock.expect(interfaceClass.getCleanName()).andReturn("Pattern");
		EasyMock.expect(interfaceClass.isInterface()).andReturn(true);
		
		MyMethodNode contextMethod = EasyMock.createMock(MyMethodNode.class);
		ArrayList<MyMethodNode> contextMethods = new ArrayList<>();
		contextMethods.add(contextConstructor);
		contextMethods.add(contextMethod);
		contextClass.methods = contextMethods;
		
		EasyMock.expect(contextConstructor.getMethodInstructions()).andReturn(new ArrayList<MyMethodInsnNode>());
		EasyMock.expect(contextMethod.getMethodInstructions()).andReturn(new ArrayList<MyMethodInsnNode>());
		EasyMock.expect(contextClass.getCleanName()).andReturn("AlmostStrategy");
		EasyMock.expect(contextClass.getCleanName()).andReturn("AlmostStrategy");
		
		EasyMock.replay(interfaceClass,
				contextClass,
				contextConstructor,
				interfaceField,
				interfaceInit,
				contextMethod);
		
		assertEquals(checker.runCheck(classes),
				"\nStrategy Pattern Implementations: \n"
				+ "	Strategy pattern is nearly implemented in AlmostStrategy"
				+ " using interface Pattern. To finish implementing strategy pattern, the function/functions "
						+ "called from the interface must be used\n");
	
		EasyMock.verify(interfaceClass,
				contextClass,
				contextConstructor,
				interfaceField,
				interfaceInit,
				contextMethod);
	}
	
	@Test
	void failNotConstructed() {
		MyClassNode interfaceClass = EasyMock.createMock(MyClassNode.class);
		MyClassNode contextClass = EasyMock.createMock(MyClassNode.class);
		ArrayList<MyClassNode> classes = new ArrayList<>();
		classes.add(interfaceClass);
		classes.add(contextClass);
		
		MyMethodNode contextConstructor = EasyMock.createMock(MyMethodNode.class);
		EasyMock.expect(interfaceClass.getConstructor()).andReturn(null);
		EasyMock.expect(contextClass.getConstructor()).andReturn(contextConstructor);
		
		MyFieldNode interfaceField = EasyMock.createMock(MyFieldNode.class);
		ArrayList<MyFieldNode> contextFields = new ArrayList<>();
		contextFields.add(interfaceField);
		contextClass.fields = contextFields;
		
		ArrayList<String> argTypeNames = new ArrayList<>();
		EasyMock.expect(contextConstructor.getCleanArgTypes()).andReturn(argTypeNames);
		
		MyFieldInsnNode interfaceInit = EasyMock.createMock(MyFieldInsnNode.class);
		ArrayList<MyFieldInsnNode> constructorInsns = new ArrayList<>();
		EasyMock.expect(contextConstructor.getFieldInstructionNodes()).andReturn(constructorInsns);
		
		EasyMock.replay(interfaceClass,
				contextClass,
				contextConstructor,
				interfaceField,
				interfaceInit);
		
		assertEquals(checker.runCheck(classes), "");
	
		EasyMock.verify(interfaceClass,
				contextClass,
				contextConstructor,
				interfaceField,
				interfaceInit);
	}
	
	@Test
	void passSingleStrategy() {
		MyClassNode interfaceClass = EasyMock.createMock(MyClassNode.class);
		MyClassNode contextClass = EasyMock.createMock(MyClassNode.class);
		ArrayList<MyClassNode> classes = new ArrayList<>();
		classes.add(interfaceClass);
		classes.add(contextClass);
		
		MyMethodNode contextConstructor = EasyMock.createMock(MyMethodNode.class);
		EasyMock.expect(interfaceClass.getConstructor()).andReturn(null);
		EasyMock.expect(contextClass.getConstructor()).andReturn(contextConstructor);
		
		MyFieldNode interfaceField = EasyMock.createMock(MyFieldNode.class);
		ArrayList<MyFieldNode> contextFields = new ArrayList<>();
		contextFields.add(interfaceField);
		contextClass.fields = contextFields;
		
		ArrayList<String> argTypeNames = new ArrayList<>();
		argTypeNames.add("Pattern");
		EasyMock.expect(contextConstructor.getCleanArgTypes()).andReturn(argTypeNames);
		
		MyFieldInsnNode interfaceInit = EasyMock.createMock(MyFieldInsnNode.class);
		ArrayList<MyFieldInsnNode> constructorInsns = new ArrayList<>();
		constructorInsns.add(interfaceInit);
		EasyMock.expect(contextConstructor.getFieldInstructionNodes()).andReturn(constructorInsns);
		interfaceField.name = "strategyPattern";
		interfaceInit.name = "strategyPattern";
		EasyMock.expect(interfaceField.getCleanDesc()).andReturn("Pattern");
		EasyMock.expect(interfaceField.getCleanDesc()).andReturn("Pattern");
		
		EasyMock.expect(interfaceClass.getCleanName()).andReturn("Pattern");
		EasyMock.expect(interfaceClass.isInterface()).andReturn(true);
		
		MyMethodNode contextMethod = EasyMock.createMock(MyMethodNode.class);
		ArrayList<MyMethodNode> contextMethods = new ArrayList<>();
		contextMethods.add(contextConstructor);
		contextMethods.add(contextMethod);
		contextClass.methods = contextMethods;
		
		MyMethodInsnNode methodInsn = EasyMock.createMock(MyMethodInsnNode.class);
		ArrayList<MyMethodInsnNode> methodInsns = new ArrayList<MyMethodInsnNode>();
		methodInsns.add(methodInsn);
		EasyMock.expect(contextConstructor.getMethodInstructions()).andReturn(new ArrayList<MyMethodInsnNode>());
		
		EasyMock.expect(contextMethod.getMethodInstructions()).andReturn(methodInsns);
		EasyMock.expect(methodInsn.getCleanOwner()).andReturn("Pattern");
		EasyMock.expect(methodInsn.isInvokeVirtual()).andReturn(true);
		
		EasyMock.expect(contextClass.getCleanName()).andReturn("Strategy1");
		EasyMock.expect(contextClass.getCleanName()).andReturn("Strategy1");
		contextMethod.name = "contextMethod";
		
		EasyMock.replay(interfaceClass,
				contextClass,
				contextConstructor,
				interfaceField,
				interfaceInit,
				contextMethod,
				methodInsn);
		
		assertEquals(checker.runCheck(classes),
				"\nStrategy Pattern Implementations: \n"
				+ "	Implemented in Strategy1"
				+ " class using interface Pattern"
				+ ", and invoked for the first time in method contextMethod\n");
	
		EasyMock.verify(interfaceClass,
				contextClass,
				contextConstructor,
				interfaceField,
				interfaceInit,
				contextMethod,
				methodInsn);
	}
	
	@Test
	void passMultiStrategy() {
		MyClassNode interfaceClass1 = EasyMock.createMock(MyClassNode.class);
		MyClassNode interfaceClass2 = EasyMock.createMock(MyClassNode.class);
		MyClassNode contextClass = EasyMock.createMock(MyClassNode.class);
		ArrayList<MyClassNode> classes = new ArrayList<>();
		classes.add(interfaceClass1);
		classes.add(interfaceClass2);
		classes.add(contextClass);
		
		MyMethodNode contextConstructor = EasyMock.createMock(MyMethodNode.class);
		EasyMock.expect(interfaceClass1.getConstructor()).andReturn(null);
		EasyMock.expect(interfaceClass2.getConstructor()).andReturn(null);
		EasyMock.expect(contextClass.getConstructor()).andReturn(contextConstructor);
		
		MyFieldNode interfaceField1 = EasyMock.createMock(MyFieldNode.class);
		MyFieldNode interfaceField2 = EasyMock.createMock(MyFieldNode.class);
		ArrayList<MyFieldNode> contextFields = new ArrayList<>();
		contextFields.add(interfaceField1);
		contextFields.add(interfaceField2);
		contextClass.fields = contextFields;
		
		ArrayList<String> argTypeNames = new ArrayList<>();
		argTypeNames.add("Pattern1");
		argTypeNames.add("Pattern2");
		EasyMock.expect(contextConstructor.getCleanArgTypes()).andReturn(argTypeNames);

		MyFieldInsnNode interfaceInit1 = EasyMock.createMock(MyFieldInsnNode.class);
		MyFieldInsnNode interfaceInit2 = EasyMock.createMock(MyFieldInsnNode.class);
		ArrayList<MyFieldInsnNode> constructorInsns = new ArrayList<>();
		constructorInsns.add(interfaceInit1);
		constructorInsns.add(interfaceInit2);
		EasyMock.expect(contextConstructor.getFieldInstructionNodes()).andReturn(constructorInsns);
		interfaceField1.name = "strategyPattern1";
		interfaceInit1.name = "strategyPattern1";
		interfaceField2.name = "strategyPattern2";
		interfaceInit2.name = "strategyPattern2";
		EasyMock.expect(interfaceField1.getCleanDesc()).andReturn("Pattern1");
		EasyMock.expect(interfaceField1.getCleanDesc()).andReturn("Pattern1");
		EasyMock.expect(interfaceField2.getCleanDesc()).andReturn("Pattern2");
		EasyMock.expect(interfaceField2.getCleanDesc()).andReturn("Pattern2");
		
		EasyMock.expect(interfaceClass1.getCleanName()).andReturn("Pattern1");
		EasyMock.expect(interfaceClass1.isInterface()).andReturn(true);
		EasyMock.expect(interfaceClass1.getCleanName()).andReturn("Pattern1");
		EasyMock.expect(interfaceClass2.getCleanName()).andReturn("Pattern2");
		EasyMock.expect(interfaceClass2.isInterface()).andReturn(true);
		EasyMock.expect(interfaceClass2.getCleanName()).andReturn("Pattern2");
		
		MyMethodNode contextMethod = EasyMock.createMock(MyMethodNode.class);
		ArrayList<MyMethodNode> contextMethods = new ArrayList<>();
		contextMethods.add(contextConstructor);
		contextMethods.add(contextMethod);
		contextClass.methods = contextMethods;
		
		MyMethodInsnNode methodInsn1 = EasyMock.createMock(MyMethodInsnNode.class);
		MyMethodInsnNode methodInsn2 = EasyMock.createMock(MyMethodInsnNode.class);
		ArrayList<MyMethodInsnNode> methodInsns = new ArrayList<MyMethodInsnNode>();
		methodInsns.add(methodInsn1);
		methodInsns.add(methodInsn2);
		EasyMock.expect(contextConstructor.getMethodInstructions()).andReturn(new ArrayList<MyMethodInsnNode>());
		EasyMock.expect(contextMethod.getMethodInstructions()).andReturn(methodInsns);
		EasyMock.expect(contextConstructor.getMethodInstructions()).andReturn(new ArrayList<MyMethodInsnNode>());
		
		EasyMock.expect(contextMethod.getMethodInstructions()).andReturn(methodInsns);
		EasyMock.expect(methodInsn1.getCleanOwner()).andReturn("Pattern1");
		EasyMock.expect(methodInsn1.isInvokeVirtual()).andReturn(true);
		
		EasyMock.expect(contextClass.getCleanName()).andReturn("Strategy2");
		EasyMock.expect(contextClass.getCleanName()).andReturn("Strategy2");
		contextMethod.name = "contextMethod";
		
		EasyMock.expect(methodInsn1.getCleanOwner()).andReturn("Pattern1");
		EasyMock.expect(methodInsn2.getCleanOwner()).andReturn("Pattern2");
		EasyMock.expect(methodInsn2.isInvokeVirtual()).andReturn(true);
		
		EasyMock.expect(contextClass.getCleanName()).andReturn("Strategy2");
		EasyMock.expect(contextClass.getCleanName()).andReturn("Strategy2");
		
		EasyMock.replay(interfaceClass1,
				interfaceClass2,
				contextClass,
				contextConstructor,
				interfaceField1,
				interfaceField2,
				interfaceInit1,
				interfaceInit2,
				contextMethod,
				methodInsn1,
				methodInsn2);
		
		assertEquals(checker.runCheck(classes),
				"\nStrategy Pattern Implementations: \n"
				+ "	Implemented in Strategy2"
				+ " class using interface Pattern1"
				+ ", and invoked for the first time in method contextMethod\n"
				+ "	Implemented in Strategy2"
				+ " class using interface Pattern2"
				+ ", and invoked for the first time in method contextMethod\n");
	
		EasyMock.verify(interfaceClass1,
				interfaceClass2,
				contextClass,
				contextConstructor,
				interfaceField1,
				interfaceField2,
				interfaceInit1,
				interfaceInit2,
				contextMethod,
				methodInsn1,
				methodInsn2);
	}
}