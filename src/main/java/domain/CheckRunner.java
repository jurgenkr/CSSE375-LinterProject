package domain;

import java.util.ArrayList;

import data_source.MyClassNode;

import data_source.ASMReader;
import data_source.FileWriter;

public class CheckRunner {

	private ArrayList<MyClassNode> classes;
	private ArrayList<MyClassNode> deleted;
	private ArrayList<ClassCheck> checks;
	private FileWriter fileWriter;
	public CheckRunner(ArrayList<String> testableClasses) {
		this.classes = ASMReader.generateClassNodes(testableClasses);
		this.checks = new ArrayList<>();
		this.deleted = new ArrayList<>();
		this.fileWriter = new FileWriter("LintOutput.txt");
	}
	
	public String classNames() {
		String printString = "";
		for (int i = 0; i < classes.size(); i++) {
			printString += "	" + classes.get(i).getCleanName() + "\n";
		}
		return printString;
	}
	
	public String deletedClassNames() {
		String printString = "";
		for (int i = 0; i < deleted.size(); i++) {
			printString += "	" + deleted.get(i).getCleanName() + "\n";
		}
		return printString;
	}
	
	public String runChecks() {
		String printString = "";

		for (ClassCheck check : checks) {
			printString += check.runCheck(this.classes);
		}
		if (printString.equals("")) {
			printString += "\nNo principle violations detected!\n";
		}
		printString = getChecks() + "\nOn classes: \n" + classNames() + "\n" + printString ;
		fileWriter.writeToFile(printString);
		return printString;
	}
	
	public String getChecks() {
		String printString = "";
		printString += "Running checks: \n";
		for (ClassCheck check : checks) {
			printString += "	" + check.getName() + "\n";
		}
		return printString;
	}
	
	
	public void addCheck(ClassCheck check) {
		checks.add(check);
	}
	
	public void resetChecks() {
		checks.removeAll(checks);
	}
	
	public boolean removeClass(String toRemove) {
		for (int i = 0; i < classes.size(); i++) {
			if (classes.get(i).getCleanName().equals(toRemove)) {
				deleted.add(classes.remove(i));
				return true;
			}
		}
		return false;
	}
	
	public boolean reAddClass(String toAdd) {
		for (int i = 0; i < deleted.size(); i++) {
			if (deleted.get(i).getCleanName().equals(toAdd)) {
				classes.add(deleted.remove(i));
				return true;
			}
		}
		return false;
	}
	
}