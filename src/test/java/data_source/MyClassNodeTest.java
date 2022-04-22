package data_source;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

class MyClassNodeTest {

	@Test
	void testGetCleanName() {
		MyClassNode node = new MyClassNode("L;main/java/package/Class1", null, null, null, null, 0);
		assertEquals("Class1", node.getCleanName());
	}
	
	@Test
	void testGetCleanSuperName() {
		MyClassNode node = new MyClassNode(null, "L;main/java/package/Class2", null, null, null, 0);
		assertEquals("Class2", node.getCleanSuperName());
	}

	@Test
	void testIsSuperBuiltInTrue() {
		MyClassNode node = new MyClassNode(null, "L;java/package/Class2", null, null, null, 0);
		assertTrue(node.isSuperBuiltIn());
	}
	
	@Test
	void testIsSuperBuiltInFalse() {
		MyClassNode node = new MyClassNode(null, "L;main/blava/package/Class2", null, null, null, 0);
		assertFalse(node.isSuperBuiltIn());
	}
	
	@Test
	void testGetInterfaces() {
		ArrayList<String> interfaces = new ArrayList<>();
		interfaces.add("L;main/blava/package/Class3");
		interfaces.add( "L;main/java/package/Class4");
		MyClassNode node = new MyClassNode(null, null, interfaces, null, null, 0);
		
		ArrayList<String> interfacesExpected = new ArrayList<>();
		interfacesExpected.add("Class3");
		interfacesExpected.add("Class4");
		
		assertEquals(interfacesExpected, node.getInterfaces());
	}
	
	@Test
	void testGetNonBuiltInFieldTypes() {
		MyFieldNode field1 = EasyMock.createMock(MyFieldNode.class);
		MyFieldNode field2 = EasyMock.createMock(MyFieldNode.class);
		ArrayList<MyFieldNode> fields = new ArrayList<>();
		fields.add(field1);
		fields.add(field2);

		MyClassNode node = new MyClassNode(null, null, null, fields, null, 0);
		
		EasyMock.expect(field1.isBuiltIn()).andReturn(true);
		EasyMock.expect(field2.isBuiltIn()).andReturn(false);
		EasyMock.expect(field2.getCleanDesc()).andReturn("Class4");
		
		
		ArrayList<String> expectedOutput = new ArrayList<>();
		expectedOutput.add("Class4");
		
		EasyMock.replay(field1, field2);
		
		assertEquals(expectedOutput, node.getNonBuiltInFieldTypes());
		
		EasyMock.verify(field1, field2);
	}
	
	@Test
	void testGetMethodNames() {
		MyMethodNode method1 = EasyMock.createMock(MyMethodNode.class);
		MyMethodNode method2 = EasyMock.createMock(MyMethodNode.class);
		ArrayList<MyMethodNode> methods = new ArrayList<>();
		methods.add(method1);
		methods.add(method2);

		MyClassNode node = new MyClassNode(null, null, null, null, methods, 0);
		method1.name = "funMethod";
		method2.name = "unfunMethod";
		
		ArrayList<String> expectedOutput = new ArrayList<>();
		expectedOutput.add("funMethod");
		expectedOutput.add("unfunMethod");
		
		assertEquals(expectedOutput, node.getMethodNames());
	}
	
	@Test
	void testGetFieldNames() {
		MyFieldNode field1 = EasyMock.createMock(MyFieldNode.class);
		MyFieldNode field2 = EasyMock.createMock(MyFieldNode.class);
		ArrayList<MyFieldNode> fields = new ArrayList<>();
		fields.add(field1);
		fields.add(field2);

		MyClassNode node = new MyClassNode(null, null, null, fields, null, 0);
		field1.name = "funField";
		field2.name = "unfunField";
		
		ArrayList<String> expectedOutput = new ArrayList<>();
		expectedOutput.add("funField");
		expectedOutput.add("unfunField");
		
		assertEquals(expectedOutput, node.getFieldNames());
	}
	
	@Test
	void testGetConstructor() {
		MyMethodNode method1 = EasyMock.createMock(MyMethodNode.class);
		MyMethodNode method2 = EasyMock.createMock(MyMethodNode.class);
		MyMethodNode method3 = EasyMock.createMock(MyMethodNode.class);
		ArrayList<MyMethodNode> methods = new ArrayList<>();
		methods.add(method1);
		methods.add(method2);
		methods.add(method3);

		MyClassNode node = new MyClassNode(null, null, null, null, methods, 0);
		EasyMock.expect(method1.isConstructor()).andReturn(false);
		EasyMock.expect(method2.isConstructor()).andReturn(true);
		
		EasyMock.replay(method1, method2);
		assertEquals(method2, node.getConstructor());
		EasyMock.verify(method1, method2);
	}
}