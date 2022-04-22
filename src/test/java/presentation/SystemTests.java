package presentation;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SystemTests {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	
	@BeforeEach
	void setUp() throws Exception {
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void restoreStreams() {
	    System.setOut(originalOut);
	}


	@Test
	void testNameCheck() {
		ByteArrayInputStream in = new ByteArrayInputStream("Console\nPackage\nexample\nname\nrun\ndone".getBytes());
		System.setIn(in);

		InputManager.main(new String[0]);
		
		assertEquals("Would you like to input with GUI or Console? [GUI, Console]\r\n" + 
				"What type of Import would you like to do: \r\n" + 
				"Please Input a Package Name: \r\n" + 
				"Classes inputted: \n" + 
				"	badClass$\n" + 
				"	superBadClass$\n" + 
				"\r\n" + 
				"Input the name of the check you would like to run:Input the name of the check you would like to run:Running checks: \n" + 
				"	Name Style\n" + 
				"\n" + 
				"On classes: \n" + 
				"	badClass$\n" + 
				"	superBadClass$\n" + 
				"\n" + 
				"Names Check:\n" + 
				"	Class: badClass$\n" + 
				"		Name Style Violations: \n" + 
				"			Class Name checks: \n" + 
				"				Class Name does not start with a capital letter \n" + 
				"				Class Name contains the non-alphanumeric character: $\n" + 
				"			Field Name checks: \n" + 
				"				Field string has the same name as its type \n" +
				"				Field NotGood has an uppercase first letter, and is not static and final \n" + 
				"				Field j has too short of a name (1 character) \n" + 
				"			Method & Method Variable Name checks: \n" + 
				"				Variable Ok has an uppercase first letter in method <init>\n" + 
				"				Variable badclass$ has the same name as its type in method BadMethodName\n" +
				"				Variable Integer has an uppercase first letter in method BadMethodName\n" + 
				"				Variable i has the same name as its type in method BadMethodName\n" + 
				"				Method BadMethodName has an uppercase first letter \n" + 
				"				Variable myfirstLinter has the same name as its type in method m\n"+
				"				Method m has too short of a name (1 character) \n" + 
				"				Variable i has the same name as its type in method methodWithUnusedVariables\n" + 
				"				Variable i has the same name as its type in method longMethod\n" + 
				"	Class: superBadClass$\n" + 
				"		Name Style Violations: \n" + 
				"			Class Name checks: \n" + 
				"				Class Name does not start with a capital letter \n" + 
				"				Class Name contains the non-alphanumeric character: $\n" + 
				"\r\n" + 
				"Input the name of the check you would like to run:", outContent.toString().trim());
	}
	
	@Test
	void testUnusedInstantiationCheck() {
		ByteArrayInputStream in = new ByteArrayInputStream("Console\nPackage\nexample\ninstantiation\nrun\ndone".getBytes());
		System.setIn(in);

		InputManager.main(new String[0]);
		
		assertEquals("Would you like to input with GUI or Console? [GUI, Console]\r\n" + 
				"What type of Import would you like to do: \r\n" + 
				"Please Input a Package Name: \r\n" + 
				"Classes inputted: \n" + 
				"	badClass$\n" + 
				"	superBadClass$\n" + 
				"\r\n" + 
				"Input the name of the check you would like to run:Input the name of the check you would like to run:Running checks: \n" + 
				"	Unused Instantiation\n" + 
				"\n" + 
				"On classes: \n" + 
				"	badClass$\n" + 
				"	superBadClass$\n" + 
				"\n" + 
				"Unused Instantiation Check:\n" + 
				"	Class: badClass$\n" + 
				"		Unused Variables: \n" + 
				"			Line 8: Unused field named string\n" + 
				"			Line 9: Unused field named okay\n" + 
				"			Line 10: Unused field named NotGood\n" + 
				"			Line 12: Unused field named j\n" + 
				"			Line 13: Unused field named unusedString\n" + 
				"			Line 24: Unused field named noString\n" + 
				"			Line 26: Unused field named okay\n" + 
				"			Line 20: Unused variable named Integer in method BadMethodName\n" + 
				"			Line 30: Unused variable named myfirstLinter in method m\n" + 
				"			Line 34: Unused variable named name in method methodWithUnusedVariables\n" + 
				"			Line 35: Unused variable named number in method methodWithUnusedVariables\n" + 
				"			Line 36: Unused variable named newNumber in method methodWithUnusedVariables\n" + 
				"			Line 41: Unused variable named newNumber in method methodWithUnusedVariables\n" + 
				"			Line 50: Unused variable named newNumber in method methodWithUnusedVariables\n" + 
				"			Line 51: Unused variable in method methodWithUnusedVariables\n" + 
				"	Class: superBadClass$\n" + 
				"		Unused Variables: \n" + 
				"			Line 4: Unused field named noString\n" + 
				"			Line 5: Unused field named okay\n" + 
				"\r\n" + 
				"Input the name of the check you would like to run:", outContent.toString().trim());
	}
	
	@Test
	void testMethodLengthCheck() {
		ByteArrayInputStream in = new ByteArrayInputStream("Console\nPackage\nexample\nlength\nrun\ndone".getBytes());
		System.setIn(in);

		InputManager.main(new String[0]);
		
		assertEquals("Would you like to input with GUI or Console? [GUI, Console]\r\n" + 
				"What type of Import would you like to do: \r\n" + 
				"Please Input a Package Name: \r\n" + 
				"Classes inputted: \n" + 
				"	badClass$\n" + 
				"	superBadClass$\n" + 
				"\r\n" + 
				"Input the name of the check you would like to run:Input the name of the check you would like to run:Running checks: \n" + 
				"	Method Length\n" + 
				"\n" + 
				"On classes: \n" + 
				"	badClass$\n" + 
				"	superBadClass$\n" + 
				"\n" + 
				"Method Length Check:\n" + 
				"	Class: badClass$\n" + 
				"		Method: longMethod\n" + 
				"			Method too long: (56 lines) Shorten it to 35 lines or less. \n" + 
				"\r\n" + 
				"Input the name of the check you would like to run:", outContent.toString().trim());
	}
	
	@Test
	void testRedundantInterfaces() {
		ByteArrayInputStream in = new ByteArrayInputStream("Console\nPackage\nexample.interfaces\ninterface\nrun\ndone".getBytes());
		System.setIn(in);
		InputManager.main(new String[0]);
		
		assertEquals("Would you like to input with GUI or Console? [GUI, Console]\r\n" + 
				"What type of Import would you like to do: \r\n" + 
				"Please Input a Package Name: \r\n" + 
				"Classes inputted: \n" + 
				"	Animal\n" + 
				"	Bird\n" + 
				"	Cat\n" + 
				"	ClassRunner\n" + 
				"	Dog\n" + 
				"	FlyingBirds\n" + 
				"	FlyingCats\n" + 
				"	MyList\n" + 
				"	NonRedundantInterface\n" + 
				"	RedundantInterface\n" + 
				"\r\n" + 
				"Input the name of the check you would like to run:Input the name of the check you would like to run:Running checks: \n" + 
				"	Redundant Interfaces\n" + 
				"\n" + 
				"On classes: \n" + 
				"	Animal\n" + 
				"	Bird\n" + 
				"	Cat\n" + 
				"	ClassRunner\n" + 
				"	Dog\n" + 
				"	FlyingBirds\n" + 
				"	FlyingCats\n" + 
				"	MyList\n" + 
				"	NonRedundantInterface\n" + 
				"	RedundantInterface\n" + 
				"\n" + 
				"\n" + 
				"Redundant Interfaces: \n" + 
				"	1 class uses RedundantInterface\n" + 
				"	1 class uses FlyingBirds\n" + 
				"	0 classes use FlyingCats\n" + 
				"\r\n" + 
				"Input the name of the check you would like to run:", outContent.toString().trim());
	}
	
	@Test
	void testCompositionCheck() {
		ByteArrayInputStream in = new ByteArrayInputStream("Console\nPackage\nexample\ncomposition\nrun\ndone".getBytes());
		System.setIn(in);

		InputManager.main(new String[0]);
		
		assertEquals("Would you like to input with GUI or Console? [GUI, Console]\r\n" + 
				"What type of Import would you like to do: \r\n" + 
				"Please Input a Package Name: \r\n" + 
				"Classes inputted: \n" + 
				"	badClass$\n" + 
				"	superBadClass$\n" + 
				"\r\n" + 
				"Input the name of the check you would like to run:Input the name of the check you would like to run:Running checks: \n" + 
				"	Composition Over Inheritance\n" + 
				"\n" + 
				"On classes: \n" + 
				"	badClass$\n" + 
				"	superBadClass$\n" + 
				"\n" + 
				"\n" + 
				"Composition Over Inheritance: \n" + 
				"	Class badClass$ inherits from user created class superBadClass$. Could composition be used instead? \n" + 
				"\r\n" + 
				"Input the name of the check you would like to run:", outContent.toString().trim());
	}
	
	@Test
	void testHollywoodCheck() {
		ByteArrayInputStream in = new ByteArrayInputStream("Console\nPackage\nexample\nhollywood\nrun\ndone".getBytes());
		System.setIn(in);

		InputManager.main(new String[0]);
		
		assertEquals("Would you like to input with GUI or Console? [GUI, Console]\r\n" + 
				"What type of Import would you like to do: \r\n" + 
				"Please Input a Package Name: \r\n" + 
				"Classes inputted: \n" + 
				"	badClass$\n" + 
				"	superBadClass$\n" + 
				"\r\n" + 
				"Input the name of the check you would like to run:Input the name of the check you would like to run:Running checks: \n" + 
				"	Hollywood Principle\n" + 
				"\n" + 
				"On classes: \n" + 
				"	badClass$\n" + 
				"	superBadClass$\n" + 
				"\n" + 
				"\n" + 
				"Hollywood Principle Violations: \n" + 
				"	Class badClass$ uses field noString from superBadClass$ in method BadMethodName\n" + 
				"	Class badClass$ calls method doNothing from superBadClass$ in method BadMethodName\n" + 
				"\r\n" + 
				"Input the name of the check you would like to run:", outContent.toString().trim());
	}
	
	@Test
	void testGoodFacadePattern() {
		ByteArrayInputStream in = new ByteArrayInputStream("Console\nPackage\nexample.goodFacade\npattern\nrun\ndone".getBytes());
		System.setIn(in);
		InputManager.main(new String[0]);
		
		assertEquals("Would you like to input with GUI or Console? [GUI, Console]\r\n" + 
				"What type of Import would you like to do: \r\n" + 
				"Please Input a Package Name: \r\n" + 
				"Classes inputted: \n" + 
				"	CdPlayer\n" + 
				"	DvdPlayer\n" + 
				"	Lights\n" + 
				"	Main\n" + 
				"	MovieTheaterFacade\n" + 
				"	PopcornMaker\n" + 
				"	Projector\n" + 
				"	Screen\n" + 
				"	Speakers\n" + 
				"\r\n" + 
				"Input the name of the check you would like to run:Input the name of the check you would like to run:Running checks: \n" + 
				"	Strategy Pattern\n" + 
				"	Facade Pattern\n" + 
				"	Adapter Pattern\n" + 
				"\n" + 
				"On classes: \n" + 
				"	CdPlayer\n" + 
				"	DvdPlayer\n" + 
				"	Lights\n" + 
				"	Main\n" + 
				"	MovieTheaterFacade\n" + 
				"	PopcornMaker\n" + 
				"	Projector\n" + 
				"	Screen\n" + 
				"	Speakers\n" + 
				"\n" + 
				"\n" + 
				"Facade Pattern Check:\n" + 
				"	example/goodFacade/MovieTheaterFacade looks to be a facade for the following classes:\n" + 
				"		example/goodFacade/PopcornMaker\n" + 
				"		example/goodFacade/DvdPlayer\n" + 
				"		example/goodFacade/CdPlayer\n" + 
				"		example/goodFacade/Lights\n" + 
				"		example/goodFacade/Projector\n" + 
				"		example/goodFacade/Screen\n" + 
				"		example/goodFacade/Speakers\n" + 
				"	example/goodFacade/Projector might be an attempt at facade pattern. It is missing calls to methods in these classes:\n" + 
				"		example/goodFacade/Speakers\n" + 
				"		example/goodFacade/Screen\n" + 
				"		example/goodFacade/DvdPlayer\n" + 
				"		example/goodFacade/CdPlayer\n" + 
				"\r\n" + 
				"Input the name of the check you would like to run:", outContent.toString().trim());
	}
	
	@Test
	void testBadFacadePattern() {
		ByteArrayInputStream in = new ByteArrayInputStream("Console\nPackage\nexample.badFacade\npattern\nrun\ndone".getBytes());
		System.setIn(in);
		InputManager.main(new String[0]);
		
		assertEquals("Would you like to input with GUI or Console? [GUI, Console]\r\n" + 
				"What type of Import would you like to do: \r\n" + 
				"Please Input a Package Name: \r\n" + 
				"Classes inputted: \n" + 
				"	CatFacade\n" + 
				"\r\n" + 
				"Input the name of the check you would like to run:Input the name of the check you would like to run:Running checks: \n" + 
				"	Strategy Pattern\n" + 
				"	Facade Pattern\n" + 
				"	Adapter Pattern\n" + 
				"\n" + 
				"On classes: \n" + 
				"	CatFacade\n" + 
				"\n" + 
				"\n" + 
				"Facade Pattern Check:\n" + 
				"	example/badFacade/CatFacade contains the word 'facade' but is not a facade pattern\n" + 
				"\r\n" + 
				"Input the name of the check you would like to run:", outContent.toString().trim());
	}
	
	@Test
	void testGoodStrategyPattern() {
		ByteArrayInputStream in = new ByteArrayInputStream("Console\nPackage\nexample.goodStrategy\npattern\nrun\ndone".getBytes());
		System.setIn(in);
		InputManager.main(new String[0]);
		
		assertEquals("Would you like to input with GUI or Console? [GUI, Console]\r\n" + 
				"What type of Import would you like to do: \r\n" + 
				"Please Input a Package Name: \r\n" + 
				"Classes inputted: \n" + 
				"	BlueFish\n" + 
				"	Fish\n" + 
				"	NoSwim\n" + 
				"	OneFish\n" + 
				"	OtherSwimBehavior\n" + 
				"	RedFish\n" + 
				"	RedRedFish\n" + 
				"	SwimBehavior\n" + 
				"	SwimFast\n" + 
				"	TwoFish\n" + 
				"\r\n" + 
				"Input the name of the check you would like to run:Input the name of the check you would like to run:Running checks: \n" + 
				"	Strategy Pattern\n" + 
				"	Facade Pattern\n" + 
				"	Adapter Pattern\n" + 
				"\n" + 
				"On classes: \n" + 
				"	BlueFish\n" + 
				"	Fish\n" + 
				"	NoSwim\n" + 
				"	OneFish\n" + 
				"	OtherSwimBehavior\n" + 
				"	RedFish\n" + 
				"	RedRedFish\n" + 
				"	SwimBehavior\n" + 
				"	SwimFast\n" + 
				"	TwoFish\n" + 
				"\n\n" + 
				"Strategy Pattern Implementations: \n" + 
				"	Implemented in Fish class using interface SwimBehavior, and invoked for the first time in method doSwim\n" +
				"	Implemented in Fish class using interface OtherSwimBehavior, and invoked for the first time in method doSwim\n" +
				"\n" + 
				"Facade Pattern Check:\n" + 
				"	example/goodStrategy/Fish looks to be a facade for the following classes:\n" + 
				"		example/goodStrategy/SwimBehavior\n" + 
				"		example/goodStrategy/OtherSwimBehavior\n" + 
				"\r\n" + 
				"Input the name of the check you would like to run:", outContent.toString().trim());
	}
	
	@Test
	void testGoodAdapterPattern() {
		ByteArrayInputStream in = new ByteArrayInputStream("Console\nPackage\nexample.goodAdapter\npattern\nrun\ndone".getBytes());
		System.setIn(in);
		InputManager.main(new String[0]);
		
		assertEquals("Would you like to input with GUI or Console? [GUI, Console]\r\n" + 
				"What type of Import would you like to do: \r\n" + 
				"Please Input a Package Name: \r\n" + 
				"Classes inputted: \n" + 
				"	Cat\n" + 
				"	CatToFishAdapter\n" + 
				"	Fish\n" + 
				"\r\n" + 
				"Input the name of the check you would like to run:Input the name of the check you would like to run:Running checks: \n" + 
				"	Strategy Pattern\n" + 
				"	Facade Pattern\n" + 
				"	Adapter Pattern\n" + 
				"\n" + 
				"On classes: \n" + 
				"	Cat\n" + 
				"	CatToFishAdapter\n" + 
				"	Fish\n" + 
				"\n" + 
				"\n" + 
				"Facade Pattern Check:\n" + 
				"	example/goodAdapter/CatToFishAdapter looks to be a facade for the following classes:\n" + 
				"		example/goodAdapter/Cat\n" + 
				"\n" + 
				"Adapter Pattern Checker: \n" + 
				"	Class CatToFishAdapter uses Adapter Pattern to adapt Class Cat to Interface Fish \n" + 
				"\r\n" + 
				"Input the name of the check you would like to run:", outContent.toString().trim());
	}
	
	@Test
	void testBadAdapterPattern() {
		ByteArrayInputStream in = new ByteArrayInputStream("Console\nPackage\nexample.badAdapter\npattern\nrun\ndone".getBytes());
		System.setIn(in);
		InputManager.main(new String[0]);
		
		assertEquals("Would you like to input with GUI or Console? [GUI, Console]\r\n" + 
				"What type of Import would you like to do: \r\n" + 
				"Please Input a Package Name: \r\n" + 
				"Classes inputted: \n" + 
				"	Cat\n" + 
				"	Dog\n" + 
				"	FifthBadAdapter\n" + 
				"	FirstBadAdapter\n" + 
				"	Fish\n" + 
				"	FourthBadAdapter\n" + 
				"	SecondBadAdapter\n" + 
				"	ThirdBadAdapter\n" + 
				"\r\n" + 
				"Input the name of the check you would like to run:Input the name of the check you would like to run:Running checks: \n" + 
				"	Strategy Pattern\n" + 
				"	Facade Pattern\n" + 
				"	Adapter Pattern\n" + 
				"\n" + 
				"On classes: \n" + 
				"	Cat\n" + 
				"	Dog\n" + 
				"	FifthBadAdapter\n" + 
				"	FirstBadAdapter\n" + 
				"	Fish\n" + 
				"	FourthBadAdapter\n" + 
				"	SecondBadAdapter\n" + 
				"	ThirdBadAdapter\n" + 
				"\n" + 
				"\n" + 
				"Facade Pattern Check:\n" + 
				"	example/badAdapter/FourthBadAdapter might be an attempt at facade pattern. It is missing calls to methods in these classes:\n" + 
				"		example/badAdapter/Fish\n" + 
				"	example/badAdapter/ThirdBadAdapter might be an attempt at facade pattern. It is missing calls to methods in these classes:\n" + 
				"		example/badAdapter/Fish\n" + 
				"\n" + 
				"Adapter Pattern Checker: \n" + 
				"	Class FifthBadAdapter has \"adapter\" in name, but does not implement an interface and have a field of a user defined class to adapt. \n" + 
				"	Class FirstBadAdapter has \"adapter\" in name, but does not implement an interface and have a field of a user defined class to adapt. \n" + 
				"	Class FourthBadAdapter has \"adapter\" in name, but has methods that are not empty or calling methods of Class Fish \n" + 
				"	Class SecondBadAdapter has \"adapter\" in name, but does not implement an interface and have a field of a user defined class to adapt. \n" + 
				"	Class ThirdBadAdapter has \"adapter\" in name, but does not implement an interface and have a field of a user defined class to adapt. \n" + 
				"\r\n" + 
				"Input the name of the check you would like to run:", outContent.toString().trim());
	}
	
	@Test
	void testCheckRunnerHelpMethod() {
		ByteArrayInputStream in = new ByteArrayInputStream("Console\nPackage\nexample\nhelp\ndone".getBytes());
		System.setIn(in);
		InputManager.main(new String[0]);
		
		assertEquals("Would you like to input with GUI or Console? [GUI, Console]\r\n" + 
				"What type of Import would you like to do: \r\n" + 
				"Please Input a Package Name: \r\n" + 
				"Classes inputted: \n" + 
				"	badClass$\n" + 
				"	superBadClass$\n" + 
				"\r\n" + 
				"Input the name of the check you would like to run:Possible commands:\n"
				+ "'all' : Runs all of the checks listed below\n"
				+ "'name' : Checks for name convention violations in classes, methods, and fields\n"
				+ "'instantiation' : Checks for variables and fields that are instantiated but never used\n"
				+ "'length' : Checks for methods that are too long\n"
				+ "'hollywood' : Checks for violations of the Hollywood Principle\n"
				+ "'composition' : Checks for places where composition could be used instead of inheritance\n"
				+ "'interface' : Checks for interfaces that might be redundant\n"
				+ "'pattern' : Detects use of Strategy, Adapter, and Facade patterns\n"
				+ "'run' : Runs the checks that have been added\n"
				+ "'remove' : Select classes added to list of classes to be tested to be removed from list\n"
				+ "'readd' : Select classes previously removed from list of classes to be tested to be added back to list\n"
				+ "'done' : Exits the program"
				+ "\r\n" + 
				"Input the name of the check you would like to run:", outContent.toString().trim());
	}
	
	@Test
	void testAllChecks() {
		ByteArrayInputStream in = new ByteArrayInputStream("Console\nPackage\nexample\nall\nrun\ndone".getBytes());
		System.setIn(in);
		InputManager.main(new String[0]);
		
		assertEquals("Would you like to input with GUI or Console? [GUI, Console]\r\n" + 
				"What type of Import would you like to do: \r\n" + 
				"Please Input a Package Name: \r\n" + 
				"Classes inputted: \n" + 
				"	badClass$\n" + 
				"	superBadClass$\n" + 
				"\r\n" + 
				"Input the name of the check you would like to run:Input the name of the check you would like to run:Running checks: \n" + 
				"	Name Style\n" + 
				"	Unused Instantiation\n" + 
				"	Method Length\n" + 
				"	Hollywood Principle\n" + 
				"	Composition Over Inheritance\n" + 
				"	Redundant Interfaces\n" + 
				"	Strategy Pattern\n" + 
				"	Facade Pattern\n" + 
				"	Adapter Pattern\n" + 
				"\n" + 
				"On classes: \n" + 
				"	badClass$\n" + 
				"	superBadClass$\n" + 
				"\n" + 
				"Names Check:\n" + 
				"	Class: badClass$\n" + 
				"		Name Style Violations: \n" + 
				"			Class Name checks: \n" + 
				"				Class Name does not start with a capital letter \n" + 
				"				Class Name contains the non-alphanumeric character: $\n" + 
				"			Field Name checks: \n" +
				"				Field string has the same name as its type \n" +
				"				Field NotGood has an uppercase first letter, and is not static and final \n" + 
				"				Field j has too short of a name (1 character) \n" + 
				"			Method & Method Variable Name checks: \n" + 
				"				Variable Ok has an uppercase first letter in method <init>\n" + 
				"				Variable badclass$ has the same name as its type in method BadMethodName\n" +
				"				Variable Integer has an uppercase first letter in method BadMethodName\n" + 
				"				Variable i has the same name as its type in method BadMethodName\n" + 
				"				Method BadMethodName has an uppercase first letter \n" + 
				"				Variable myfirstLinter has the same name as its type in method m\n"+
				"				Method m has too short of a name (1 character) \n" + 
				"				Variable i has the same name as its type in method methodWithUnusedVariables\n" + 
				"				Variable i has the same name as its type in method longMethod\n" + 
				"	Class: superBadClass$\n" + 
				"		Name Style Violations: \n" + 
				"			Class Name checks: \n" + 
				"				Class Name does not start with a capital letter \n" + 
				"				Class Name contains the non-alphanumeric character: $\n" + 
				"Unused Instantiation Check:\n" + 
				"	Class: badClass$\n" + 
				"		Unused Variables: \n" + 
				"			Line 8: Unused field named string\n" + 
				"			Line 9: Unused field named okay\n" + 
				"			Line 10: Unused field named NotGood\n" + 
				"			Line 12: Unused field named j\n" + 
				"			Line 13: Unused field named unusedString\n" + 
				"			Line 24: Unused field named noString\n" + 
				"			Line 26: Unused field named okay\n" + 
				"			Line 20: Unused variable named Integer in method BadMethodName\n" + 
				"			Line 30: Unused variable named myfirstLinter in method m\n" + 
				"			Line 34: Unused variable named name in method methodWithUnusedVariables\n" + 
				"			Line 35: Unused variable named number in method methodWithUnusedVariables\n" + 
				"			Line 36: Unused variable named newNumber in method methodWithUnusedVariables\n" + 
				"			Line 41: Unused variable named newNumber in method methodWithUnusedVariables\n" + 
				"			Line 50: Unused variable named newNumber in method methodWithUnusedVariables\n" + 
				"			Line 51: Unused variable in method methodWithUnusedVariables\n" + 
				"	Class: superBadClass$\n" + 
				"		Unused Variables: \n" + 
				"			Line 4: Unused field named noString\n" + 
				"			Line 5: Unused field named okay\n" + 
				"Method Length Check:\n" + 
				"	Class: badClass$\n" + 
				"		Method: longMethod\n" + 
				"			Method too long: (56 lines) Shorten it to 35 lines or less. \n" + 
				"\n" + 
				"Hollywood Principle Violations: \n" + 
				"	Class badClass$ uses field noString from superBadClass$ in method BadMethodName\n" + 
				"	Class badClass$ calls method doNothing from superBadClass$ in method BadMethodName\n" + 
				"\n" + 
				"Composition Over Inheritance: \n" + 
				"	Class badClass$ inherits from user created class superBadClass$. Could composition be used instead? \n" + 
				"\r\n" + 
				"Input the name of the check you would like to run:", outContent.toString().trim());
	}
	
	@Test
	void testRemoveSuperBadClassCheck() {
		ByteArrayInputStream in = new ByteArrayInputStream("Console\nPackage\nexample\nremove\nsuperBadClass$\nname\nrun\ndone".getBytes());
		System.setIn(in);

		InputManager.main(new String[0]);
		
		assertEquals("Would you like to input with GUI or Console? [GUI, Console]\r\n" + 
				"What type of Import would you like to do: \r\n" + 
				"Please Input a Package Name: \r\n" + 
				"Classes inputted: \n" + 
				"	badClass$\n" + 
				"	superBadClass$\n" + 
				"\r\n" + 
				"Input the name of the check you would like to run:Current classes being linted:\r\n" + 
				"	badClass$\n" + 
				"	superBadClass$\n" + 
				"\r\n" + 
				"Input a class name to remove from the list:\r\n" + 
				"Input the name of the check you would like to run:Input the name of the check you would like to run:Running checks: \n" + 
				"	Name Style\n" + 
				"\n" + 
				"On classes: \n" + 
				"	badClass$\n" + 
				"\n" + 
				"Names Check:\n" + 
				"	Class: badClass$\n" + 
				"		Name Style Violations: \n" + 
				"			Class Name checks: \n" + 
				"				Class Name does not start with a capital letter \n" + 
				"				Class Name contains the non-alphanumeric character: $\n" + 
				"			Field Name checks: \n" + 
				"				Field string has the same name as its type \n" +
				"				Field NotGood has an uppercase first letter, and is not static and final \n" + 
				"				Field j has too short of a name (1 character) \n" + 
				"			Method & Method Variable Name checks: \n" + 
				"				Variable Ok has an uppercase first letter in method <init>\n" + 
				"				Variable badclass$ has the same name as its type in method BadMethodName\n" +
				"				Variable Integer has an uppercase first letter in method BadMethodName\n" + 
				"				Variable i has the same name as its type in method BadMethodName\n" + 
				"				Method BadMethodName has an uppercase first letter \n" + 
				"				Variable myfirstLinter has the same name as its type in method m\n"+
				"				Method m has too short of a name (1 character) \n" + 
				"				Variable i has the same name as its type in method methodWithUnusedVariables\n" + 
				"				Variable i has the same name as its type in method longMethod\n" + 
				"\r\n" + 
				"Input the name of the check you would like to run:", outContent.toString().trim());
	}
	
	@Test
	void testRemoveReaddSuperBadClassCheck() {
		ByteArrayInputStream in = new ByteArrayInputStream("Console\nPackage\nexample\nremove\nsuperBadClass$\nreadd\nsuperBadClass$\nname\nrun\ndone".getBytes());
		System.setIn(in);

		InputManager.main(new String[0]);
		
		assertEquals("Would you like to input with GUI or Console? [GUI, Console]\r\n" + 
				"What type of Import would you like to do: \r\n" + 
				"Please Input a Package Name: \r\n" + 
				"Classes inputted: \n" + 
				"	badClass$\n" + 
				"	superBadClass$\n" + 
				"\r\n" + 
				"Input the name of the check you would like to run:Current classes being linted:\r\n" + 
				"	badClass$\n" + 
				"	superBadClass$\n" + 
				"\r\n" + 
				"Input a class name to remove from the list:\r\n" + 
				"Input the name of the check you would like to run:Classes removed from linting:\r\n" + 
				"	superBadClass$\n" + 
				"\r\n" + 
				"Input a class name to re-add to the list:\r\n" + 
				"Input the name of the check you would like to run:Input the name of the check you would like to run:Running checks: \n" + 
				"	Name Style\n" + 
				"\n" + 
				"On classes: \n" + 
				"	badClass$\n" + 
				"	superBadClass$\n" + 
				"\n" + 
				"Names Check:\n" + 
				"	Class: badClass$\n" + 
				"		Name Style Violations: \n" + 
				"			Class Name checks: \n" + 
				"				Class Name does not start with a capital letter \n" + 
				"				Class Name contains the non-alphanumeric character: $\n" + 
				"			Field Name checks: \n" + 
				"				Field string has the same name as its type \n" +
				"				Field NotGood has an uppercase first letter, and is not static and final \n" + 
				"				Field j has too short of a name (1 character) \n" + 
				"			Method & Method Variable Name checks: \n" + 
				"				Variable Ok has an uppercase first letter in method <init>\n" + 
				"				Variable badclass$ has the same name as its type in method BadMethodName\n" +
				"				Variable Integer has an uppercase first letter in method BadMethodName\n" + 
				"				Variable i has the same name as its type in method BadMethodName\n" + 
				"				Method BadMethodName has an uppercase first letter \n" + 
				"				Variable myfirstLinter has the same name as its type in method m\n"+
				"				Method m has too short of a name (1 character) \n" + 
				"				Variable i has the same name as its type in method methodWithUnusedVariables\n" + 
				"				Variable i has the same name as its type in method longMethod\n" + 
				"	Class: superBadClass$\n" + 
				"		Name Style Violations: \n" + 
				"			Class Name checks: \n" + 
				"				Class Name does not start with a capital letter \n" + 
				"				Class Name contains the non-alphanumeric character: $\n" + 
				"\r\n" + 
				"Input the name of the check you would like to run:", outContent.toString().trim());
	}
	
	@Test
	void testGithubGrabber() {
		//requires internet and that the link provided it valid
		//Testing on https://github.com/TheAlgorithms/Java/blob/master/src/main/java/com/thealgorithms/searches/BinarySearch.java
		ByteArrayInputStream in = new ByteArrayInputStream("Console\nGithub\nhttps://github.com/TheAlgorithms/Java/blob/master/src/main/java/com/thealgorithms/searches/BinarySearch.java\nall\nrun\ndone".getBytes());
		System.setIn(in);
		InputManager.main(new String[0]);
		
		assertEquals("Would you like to input with GUI or Console? [GUI, Console]\r\n" + 
				"What type of Import would you like to do: \r\n" + 
				"Please Input a Github Link: \r\n" + 
				"File: BinarySearch.java has been created!\r\n" + 
				"Classes inputted: \n" + 
				"	BinarySearch\n" + 
				"\r\n" + 
				"Input the name of the check you would like to run:Input the name of the check you would like to run:Running checks: \n" + 
				"	Name Style\n" + 
				"	Unused Instantiation\n" + 
				"	Method Length\n" + 
				"	Hollywood Principle\n" + 
				"	Composition Over Inheritance\n" + 
				"	Redundant Interfaces\n" + 
				"	Strategy Pattern\n" + 
				"	Facade Pattern\n" + 
				"	Adapter Pattern\n" + 
				"\n" + 
				"On classes: \n" + 
				"	BinarySearch\n" + 
				"\n" + 
				"\n" + 
				"No principle violations detected!\n" +
				"\r\n" + 
				"Input the name of the check you would like to run:", outContent.toString().trim());
	}

}
