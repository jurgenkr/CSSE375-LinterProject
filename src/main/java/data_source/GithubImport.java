package data_source;

import java.util.ArrayList;
import java.util.Scanner;

import domain.CheckRunner;

public class GithubImport implements Testable{

	Grabber githubGrabber;
	PopulateJavaFile populator;
	Scanner in;
	ArrayList<String> githubClasses;
	
	public GithubImport()
	{
		in = new Scanner(System.in);
		githubClasses = new ArrayList<>();
	}
	
	
	@Override
	public ArrayList<String> generateClasses() {
		// TODO Auto-generated method stub
		
		System.out.println("Please Input a Github Link: ");
		githubGrabber = new Grabber(in.nextLine());
		
		populator = new PopulateJavaFile(githubGrabber.getDownloadURL(),
				githubGrabber.getFileName());
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String fileURL = "data_source." + githubGrabber.getFileName().replace(".java", "");
		
		githubClasses.add(fileURL);
		
		System.out.println("Would you like to Import More?(Y/N)");
		if(in.nextLine().equals("Y")) this.generateClasses();
		
		return githubClasses;
	}

}
