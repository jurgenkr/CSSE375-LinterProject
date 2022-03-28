package domain;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Test;
import data_source.MyFieldNode;
import data_source.MyLocalVariableNode;
import data_source.MyMethodNode;

public class NamesCheckTest {
	NamesCheck checker = new NamesCheck();
	
	@Test
	public void testShortClassName() {
		assertEquals("				Class Name is too short (1 character) \n", checker.checkClass("A"));
	}
	
	@Test
	public void testNonCapitalClass() {
		assertEquals("				Class Name does not start with a capital letter \n", checker.checkClass("incorrect"));
	}
	
	@Test
	public void testClassNameSymbol() {
		assertEquals("				Class Name contains the non-alphanumeric character: ^\n", checker.checkClass("Hello^"));
	}
	
	@Test
	public void validClassName() {
		assertEquals("", checker.checkClass("GoodClass"));
	}
	
	@Test
	public void testFieldUppercaseInvalid() {
		MyFieldNode node = EasyMock.createMock(MyFieldNode.class);
		node.name = "Badfield";
		EasyMock.expect(node.isStatic()).andReturn(true);
		EasyMock.expect(node.isFinal()).andReturn(false);
		node.desc = "I";
		
		EasyMock.replay(node);
		assertEquals("				Field Badfield has an uppercase first letter, and is not static and final \n", checker.checkField(node));
		EasyMock.verify(node);
	}
	
	@Test
	public void testFieldUppercaseValid() {
		MyFieldNode node = EasyMock.createMock(MyFieldNode.class);
		node.name = "GOODFIELD";
		EasyMock.expect(node.isStatic()).andReturn(true);
		EasyMock.expect(node.isFinal()).andReturn(true);
		node.desc = "I";
		
		EasyMock.replay(node);
		assertEquals("", checker.checkField(node));
		EasyMock.verify(node);
	}
	
	@Test
	public void testFieldTypeName() {
		MyFieldNode node = EasyMock.createMock(MyFieldNode.class);
		node.name = "badfield";
		node.desc = "Ljava/lang/Badfield;";
		
		EasyMock.replay(node);
		assertEquals("				Field badfield has the same name as its type \n", checker.checkField(node));
		EasyMock.verify(node);
	}
	
	@Test
	public void testFieldNameLength() {
		MyFieldNode node = EasyMock.createMock(MyFieldNode.class);
		node.name = "j";
		node.desc = "I";
		
		EasyMock.replay(node);
		assertEquals("				Field j has too short of a name (1 character) \n", checker.checkField(node));
		EasyMock.verify(node);
	}
	
	@Test
	public void testValidField() {
		MyFieldNode node = EasyMock.createMock(MyFieldNode.class);
		node.name = "goodField";
		node.desc = "I";
		
		EasyMock.replay(node);
		assertEquals("", checker.checkField(node));
		EasyMock.verify(node);
	}
	
	@Test
	public void testMethodLocalVarUppercase() {
		MyLocalVariableNode lv1 = EasyMock.createMock(MyLocalVariableNode.class);
		List<MyLocalVariableNode> list = Arrays.asList(lv1);
		MyMethodNode node = EasyMock.createMock(MyMethodNode.class);
		node.localVariables = list;
		node.name = "goodMethod";
		lv1.name = "Bad";
		lv1.desc = "I";

		EasyMock.replay(node);
		assertEquals("				Variable Bad has an uppercase first letter in method goodMethod\n", checker.checkMethod(node));
		EasyMock.verify(node);
	}
	
	@Test
	public void testMethodLocalVarTypeName() {
		MyLocalVariableNode lv1 = EasyMock.createMock(MyLocalVariableNode.class);
		List<MyLocalVariableNode> list = Arrays.asList(lv1);
		MyMethodNode node = EasyMock.createMock(MyMethodNode.class);
		node.localVariables = list;
		node.name = "goodMethod";
		lv1.name = "bad";
		lv1.desc = "Ljava/lang/Bad";

		EasyMock.replay(node);
		assertEquals("				Variable bad has the same name as its type in method goodMethod\n", checker.checkMethod(node));
		EasyMock.verify(node);
	}
	
	@Test
	public void testMethodUppercase() {
		MyLocalVariableNode lv1 = EasyMock.createMock(MyLocalVariableNode.class);
		List<MyLocalVariableNode> list = Arrays.asList(lv1);
		MyMethodNode node = EasyMock.createMock(MyMethodNode.class);
		node.localVariables = list;
		node.name = "BadMethod";
		lv1.name = "good";
		lv1.desc = "I";

		EasyMock.replay(node);
		assertEquals("				Method BadMethod has an uppercase first letter \n", checker.checkMethod(node));
		EasyMock.verify(node);
	}
	
	@Test
	public void testMethodNameLength() {
		MyLocalVariableNode lv1 = EasyMock.createMock(MyLocalVariableNode.class);
		List<MyLocalVariableNode> list = Arrays.asList(lv1);
		MyMethodNode node = EasyMock.createMock(MyMethodNode.class);
		node.localVariables = list;
		node.name = "b";
		lv1.name = "good";
		lv1.desc = "I";

		EasyMock.replay(node);
		assertEquals("				Method b has too short of a name (1 character) \n", checker.checkMethod(node));
		EasyMock.verify(node);
	}
	
	@Test
	public void testMethodValid() {
		MyLocalVariableNode lv1 = EasyMock.createMock(MyLocalVariableNode.class);
		List<MyLocalVariableNode> list = Arrays.asList(lv1);
		MyMethodNode node = EasyMock.createMock(MyMethodNode.class);
		node.localVariables = list;
		node.name = "goodMethod";
		lv1.name = "good";
		lv1.desc = "I";

		EasyMock.replay(node);
		assertEquals("", checker.checkMethod(node));
		EasyMock.verify(node);
	}
}
