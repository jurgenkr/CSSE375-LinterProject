package domain;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import data_source.MyClassNode;

public class RedundantInterfaceCheckTest {
	RedundantInterfaceCheck checker = new RedundantInterfaceCheck();

	@Test
	public void testUnimplementedInterface() {
		MyClassNode class1 = EasyMock.createMock(MyClassNode.class);
		MyClassNode class2 = EasyMock.createMock(MyClassNode.class);
		MyClassNode class3 = EasyMock.createMock(MyClassNode.class);
		ArrayList<MyClassNode> classes = new ArrayList<>();
		classes.add(class1);
		classes.add(class2);
		classes.add(class3);
		List<String> interfaces = new ArrayList<>();
		class1.interfaces = interfaces;
		class2.interfaces = interfaces;
		class3.interfaces = interfaces;
		
		EasyMock.expect(class1.isInterface()).andReturn(true);
		EasyMock.expect(class1.getCleanName()).andReturn("unimplemented");
		EasyMock.expect(class2.isInterface()).andReturn(false);
		EasyMock.expect(class3.isInterface()).andReturn(false);
		
		EasyMock.replay(class1, class2, class3);
		
		assertEquals("\nRedundant Interfaces: \n	0 classes use unimplemented\n", checker.runCheck(classes));
		
		EasyMock.verify(class1, class2, class3);
	}
	
	@Test
	public void testSingleImplementationOfInterface() {
		MyClassNode class1 = EasyMock.createMock(MyClassNode.class);
		MyClassNode class2 = EasyMock.createMock(MyClassNode.class);
		MyClassNode class3 = EasyMock.createMock(MyClassNode.class);
		ArrayList<MyClassNode> classes = new ArrayList<>();
		classes.add(class1);
		classes.add(class2);
		classes.add(class3);
		List<String> interfaces1 = new ArrayList<>();
		List<String> interfaces2 = new ArrayList<>();
		class1.interfaces = interfaces1;
		class2.interfaces = interfaces1;
		interfaces2.add("single");
		class3.interfaces = interfaces2;
		
		EasyMock.expect(class1.isInterface()).andReturn(true);
		EasyMock.expect(class1.getCleanName()).andReturn("single");
		EasyMock.expect(class2.isInterface()).andReturn(false);
		EasyMock.expect(class3.isInterface()).andReturn(false);
		
		EasyMock.replay(class1, class2, class3);
		
		assertEquals("\nRedundant Interfaces: \n	1 class uses single\n", checker.runCheck(classes));
		
		EasyMock.verify(class1, class2, class3);
	}
	
	@Test
	public void testMultipleImplementationOfInterface() {
		MyClassNode class1 = EasyMock.createMock(MyClassNode.class);
		MyClassNode class2 = EasyMock.createMock(MyClassNode.class);
		MyClassNode class3 = EasyMock.createMock(MyClassNode.class);
		ArrayList<MyClassNode> classes = new ArrayList<>();
		classes.add(class1);
		classes.add(class2);
		classes.add(class3);
		List<String> interfaces1 = new ArrayList<>();
		List<String> interfaces2 = new ArrayList<>();
		class1.interfaces = interfaces1;
		interfaces2.add("multiple");
		class2.interfaces = interfaces2;
		class3.interfaces = interfaces2;
		
		EasyMock.expect(class1.isInterface()).andReturn(true);
		EasyMock.expect(class1.getCleanName()).andReturn("multiple");
		EasyMock.expect(class2.isInterface()).andReturn(false);
		EasyMock.expect(class3.isInterface()).andReturn(false);
		
		EasyMock.replay(class1, class2, class3);
		
		assertEquals("", checker.runCheck(classes));
		
		EasyMock.verify(class1, class2, class3);
	}
	
}
