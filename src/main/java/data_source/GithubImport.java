package data_source;

import java.io.IOException;
import static java.nio.file.StandardWatchEventKinds.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class GithubImport implements Testable{

	Grabber githubGrabber;
	PopulateJavaFile populator;
	Scanner in;

	public GithubImport(Scanner in)
	{
		this.in = in;
	}

	public Grabber getGrabber()
	{
		return this.githubGrabber;
	}
	
	@Override
	public ArrayList<String> generateClasses() {
		// TODO Auto-generated method stub
		
		System.out.println("Please Input a Github Link: ");
		return generateClassesHelper(in.nextLine());
	}
	
	public ArrayList<String> generateClassesHelper(String grabberLink) {
		githubGrabber = new Grabber(grabberLink);
		WatchService watcher = null;
		try {
			watcher = FileSystems.getDefault().newWatchService();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Path dir = Paths.get("src/main/java/data_source");
		try
		{
			WatchKey key = dir.register(watcher, ENTRY_CREATE);
		} catch (IOException x)
		{
			System.err.println(x);
		}
		
		
		populator = new PopulateJavaFile(githubGrabber.getJSONInfo());
		githubGrabber.setFileList(populator.getFileList());
		for (;;) {
		    // wait for key to be signaled
		    WatchKey key = null;
		    
	        try {
				key = watcher.take();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    if(key == null) continue;

		    for (WatchEvent<?> event: key.pollEvents()) {
		        WatchEvent.Kind<?> kind = event.kind();

//		        System.out.println(event.kind().name());
		        
		        if (kind == OVERFLOW) {
		            continue;
		        }
		        
		        try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        
				ArrayList<String> githubClasses = new ArrayList<String>();

				for(Map.Entry<String,String> entry : githubGrabber.getJSONInfo().entrySet())
				{
					String fileURL = "data_source." + entry.getKey().replace(".java", "");
					githubClasses.add(fileURL);
				}

				return githubClasses;
		  
		    }
		    boolean valid = key.reset();
		    if (!valid) {
		        break;
		    }
		}
		return null;
	}

}
