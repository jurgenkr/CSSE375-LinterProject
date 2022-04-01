package data_source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Directory {
	
	private ArrayList<String> directoryString = new ArrayList<String>();
	
	public Directory (String packageName)
	{
		this.directoryString = this.getAllClasses(packageName);
	}

	public ArrayList<String> getAllClasses(String packageName)
	{
		InputStream stream = ClassLoader.getSystemClassLoader()
				.getResourceAsStream(packageName.replaceAll("[.]", "/"));
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		
		List<String> newString = reader.lines()
				.filter(line -> line.endsWith(".class"))
				.map(line -> line.replace(".class", ""))
				.map(line -> packageName + "." + line)
				.collect(Collectors.toList());

		ArrayList returnList = new ArrayList<String>(newString);
		
		InputStream stream1 = ClassLoader.getSystemClassLoader()
				.getResourceAsStream(packageName.replaceAll("[.]", "/"));
		BufferedReader reader1 = new BufferedReader(new InputStreamReader(stream1));
		
		List<String> testString = reader1.lines()
				.filter(line -> !line.endsWith(".class"))
				.map(line -> packageName + "." + line)
				.collect(Collectors.toList());
		
		
		if(testString.isEmpty())
		{
			return returnList;
		}
		
		//List<String> newList = null;
		List<String> newList = new ArrayList<>();
		
		for(String packageString : testString)
		{
			newList = Stream.concat(newList.stream(), Stream.concat(newString.stream(), this.getAllClasses(packageString).stream()))
                    .collect(Collectors.toList());
		}

		return returnList;
		
	}
	
	public ArrayList<String> getDirectoryString() {
		return directoryString;
	}
	
}