package domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import data_source.MyClassNode;

class CompositionCheckIntegrationTest {
	
	CompositionCheck checker = new CompositionCheck();

	@Test
	void testCompositionBad() {
		MyClassNode curClass = new MyClassNode("BadComposition", "/user/package/SuperBadComposition", null, null, null, 0);
		
		String expected = "	Class BadComposition inherits from user created class SuperBadComposition. "
				+ "Could composition be used instead? \n";
		
		assertEquals(expected, checker.checkClassComposition(curClass));
	}
	
	@Test
	void testCompositionGood() {
		MyClassNode curClass = new MyClassNode("goodComposition", "java/lang/object/good", null, null, null, 0);
		
		String expected = "";
		
		assertEquals(expected, checker.checkClassComposition(curClass));
	}

}
