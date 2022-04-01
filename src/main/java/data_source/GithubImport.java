package data_source;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import domain.CheckRunner;

public class GithubImport implements Testable{

	Grabber githubGrabber;
	PopulateJavaFile populator;
	Scanner in;
	ArrayList<String> githubClasses;
	ArrayList<File> fileList;
	
	public GithubImport()
	{
		in = new Scanner(System.in);
		githubClasses = new ArrayList<>();
		fileList = new ArrayList<>();
	}
	
	public ArrayList<File> getFileList()
	{
		return this.fileList;
	}
	
	
	@Override
	public ArrayList<String> generateClasses() {
		// TODO Auto-generated method stub
		
		System.out.println("Please Input a Github Link: ");
		githubGrabber = new Grabber(in.nextLine());
		
		populator = new PopulateJavaFile(githubGrabber.getDownloadURL(),
				githubGrabber.getFileName());
		
		fileList.add(populator.getPopulatedFile());
		
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
