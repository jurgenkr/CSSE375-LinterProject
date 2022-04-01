package domain;

import java.util.ArrayList;
import java.util.HashMap;

import data_source.MyClassNode;


public class RedundantInterfaceCheck implements ClassCheck {
	
	@Override
	public String runCheck(ArrayList<MyClassNode> classes) {
		String printString = "";
		HashMap<String, Integer> interfaces = new HashMap<>();
		for (MyClassNode node : classes) {
			if (node.isInterface()) {
				interfaces.put(node.getCleanName(), 0);
			}
		}
		for (MyClassNode node : classes) {
			for (String name : node.interfaces) {
				if (interfaces.containsKey(name)) {
					int value = interfaces.get(name);
					interfaces.replace(name, value + 1);
				}
			}
		}
		for (String name : interfaces.keySet()) {
			if (interfaces.get(name) == 0) {
				printString += "	" + interfaces.get(name) + " classes use " + name + "\n";
			} else if (interfaces.get(name) == 1) {
				printString += "	" + interfaces.get(name) + " class uses " + name + "\n";
			}
		}
		
		if (printString != "") {
			printString = "\nRedundant Interfaces: \n" + printString;
		}
		return printString;
	}
	
	@Override
	public String getName() {
		return "Redundant Interfaces";
	}
	
}
