package domain;

import java.util.ArrayList;

import data_source.MyClassNode;

public class CompositionCheck implements ClassCheck {

	@Override
	public String runCheck(ArrayList<MyClassNode> classes) {
		String problems = "";
		for (MyClassNode classNode : classes) {
			problems += checkClassComposition(classNode);
			}
		String printString = (problems.equals("")) ? "" : "\nComposition Over Inheritance: \n" + problems;
		return printString;
	}

	String checkClassComposition(MyClassNode classNode) {
		String[] className = classNode.getFullName().split("/");
		String[] superName = classNode.getFullSuperName().split("/");
		if (!superName[0].equals("java"))
			return String.format(
					"	Class %s inherits from user created class %s. Could composition be used instead? \n",
					className[className.length - 1], superName[superName.length - 1]);
		return "";
	}
	
	@Override
	public String getName() {
		return "Composition Over Inheritance";
	}

}
