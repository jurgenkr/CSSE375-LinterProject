package presentation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import data_source.Directory;
import data_source.GithubImport;
import data_source.Grabber;
import data_source.PackageImport;
import data_source.PopulateJavaFile;
import data_source.Testable;
import domain.AdapterPatternCheck;
import domain.CheckRunner;
import domain.CompositionCheck;
import domain.FacadePatternCheck;
import domain.HollywoodCheck;
import domain.MethodLengthCheck;
import domain.NamesCheck;
import domain.RedundantInterfaceCheck;
import domain.StrategyPatternCheck;
import domain.UnusedInstantiationCheck;

public class ConsoleManager {
	static Grabber githubGrabber;
	static PopulateJavaFile populator;
	static Directory directory;
	private Scanner in;
	
	public ConsoleManager(Scanner in) {
		this.in = in;
	}
	
	public void userInterfaceLoop() {
		System.out.println("What type of Import would you like to do: ");
		
		CheckRunner runner = null;
		Testable testingMethod = null;
		Grabber gitGrabber = null;
		boolean github = false;

		
		boolean successful = false;
		while(!successful) {
		switch(in.nextLine().toLowerCase())
		{
        
			case "github":
				testingMethod = new GithubImport(in);
				github = true;
				successful = true;
				break;
			case "package":
				testingMethod = new PackageImport(in);
				successful = true;
				break;
			default:
				break;
			}
		}
		
		runner = new CheckRunner(testingMethod.generateClasses());
		
		
		if(github) {
			gitGrabber = ((GithubImport) testingMethod).getGrabber();
			gitGrabber.deleteFiles();
		}
		
		System.out.println("Classes inputted: \n" + runner.classNames());
	
		
		ArrayList<String> added = new ArrayList<>();
		boolean complete = false;
		while (!complete) {
			System.out.print("Input the name of the check you would like to run:");
			String checkName = in.next().toLowerCase();
			switch (checkName) {
			case "help":
				System.out.println("Possible commands:\n"
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
						+ "'done' : Exits the program");
				break;
				
			case "all":
				runner.resetChecks();
				runner.addCheck(new NamesCheck());
				runner.addCheck(new UnusedInstantiationCheck());
				runner.addCheck(new MethodLengthCheck());
				runner.addCheck(new HollywoodCheck());
				runner.addCheck(new CompositionCheck());
				runner.addCheck(new RedundantInterfaceCheck());
				runner.addCheck(new StrategyPatternCheck());
				runner.addCheck(new FacadePatternCheck());
				runner.addCheck(new AdapterPatternCheck());
				added.add("all");
				break;
				
			case "name":
				if (!added.contains("name") && !added.contains("all")) {
					runner.addCheck(new NamesCheck());
					added.add("name");
				} else {
					System.out.println("This check has already been added.");
				}
				break;
				
			case "instantiation":
				if (!added.contains("instantiation") && !added.contains("all")) {
					runner.addCheck(new UnusedInstantiationCheck());
					added.add("instantiation");
				} else {
					System.out.println("This check has already been added.");
				}
				break;
				
			case "length":
				if (!added.contains("length") && !added.contains("all")) {
					runner.addCheck(new MethodLengthCheck());
					added.add("length");
				} else {
					System.out.println("This check has already been added.");
				}
				break;
				
			case "hollywood":
				if (!added.contains("hollywood") && !added.contains("all")) {
					runner.addCheck(new HollywoodCheck());
					added.add("hollywood");
				} else {
					System.out.println("This check has already been added.");
				}
				break;
				
			case "composition":
				if (!added.contains("composition") && !added.contains("all")) {
					runner.addCheck(new CompositionCheck());
					added.add("composition");
				} else {
					System.out.println("This check has already been added.");
				}
				break;
				
			case "interface":
				if (!added.contains("interface") && !added.contains("all")) {
					runner.addCheck(new RedundantInterfaceCheck());
					added.add("interface");
				} else {
					System.out.println("This check has already been added.");
				}
				break;
				
			case "pattern":
				if (!added.contains("pattern") && !added.contains("all")) {
					runner.addCheck(new StrategyPatternCheck());
					runner.addCheck(new FacadePatternCheck());
					runner.addCheck(new AdapterPatternCheck());
					added.add("pattern");
				} else {
					System.out.println("This check has already been added.");
				}
				break;
				
			case "remove":
				System.out.println("Current classes being linted:");
				System.out.println(runner.classNames());
				System.out.println("Input a class name to remove from the list:");
				String toRemove = in.next();
				if (!runner.removeClass(toRemove)) {
					System.out.println("This is not a valid check name\n");
				}
				break;
				
			case "readd":
				System.out.println("Classes removed from linting:");
				System.out.println(runner.deletedClassNames());
				System.out.println("Input a class name to re-add to the list:");
				String toAdd = in.next();
				if (!runner.reAddClass(toAdd)) {
					System.out.println("This is not a valid check name\n");
				}
				break;
				
			case "run":
				System.out.println(runner.runChecks());
				runner.resetChecks();
				added.removeAll(added);
				break;
				
			case "done":
				complete = true;
				break;
			
			default:
				System.out.println("Not a valid input, type 'help' for valid inputs: ");
			}
		}
	}
	
	
	
}
