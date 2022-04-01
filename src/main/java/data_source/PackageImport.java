package data_source;

import java.util.ArrayList;
import java.util.Scanner;

import domain.CheckRunner;

public class PackageImport implements Testable{

	Directory directory;
	Scanner in;
	
	public PackageImport()
	{
		in = new Scanner(System.in);
	}
	
	
	@Override
	public ArrayList<String> generateClasses() {
		// TODO Auto-generated method stub
		
		System.out.println("Please Input a Package Name: ");
		directory = new Directory(in.nextLine());
		
		return directory.getDirectoryString();
	}

}
