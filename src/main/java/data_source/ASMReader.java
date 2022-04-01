package data_source;

import java.io.IOException;
import java.util.ArrayList;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

public class ASMReader {

	public static ArrayList<MyClassNode> generateClassNodes(ArrayList<String> testableClasses) {
		ArrayList<ClassNode> classes = new ArrayList<>();
		for (String className : testableClasses) {
			ClassReader reader = null;
			try {
				reader = new ClassReader(className);
			} catch (IOException e) {
				e.printStackTrace();
			}
			ClassNode classNode = new ClassNode();
			reader.accept(classNode, ClassReader.EXPAND_FRAMES);

			classes.add(classNode);
		}

		return ASMParser.parseASM(classes);
	}

}
