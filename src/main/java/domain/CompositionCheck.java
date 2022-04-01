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
		if (!(classNode.isSuperBuiltIn()))
			return String.format(
					"	Class %s inherits from user created class %s. Could composition be used instead? \n",
					classNode.getCleanName(), classNode.getCleanSuperName());
		return "";
	}
	
	@Override
	public String getName() {
		return "Composition Over Inheritance";
	}

}
