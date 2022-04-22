package data_source;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PopulateJavaFile {
	
	private File populatedFile;
	private ArrayList<File> fileList;
	
	public PopulateJavaFile(HashMap<String,String> jsonInfo)
	{
		fileList = new ArrayList<>();
		try {

			for(Map.Entry<String,String> entry : jsonInfo.entrySet()) {
				String filename = entry.getKey();
				String url = entry.getValue();
				this.populate(url, filename);
				fileList.add(this.populatedFile);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public File getPopulatedFile()
	{
		return this.populatedFile;
	}
	
	public ArrayList<File> getFileList()
	{
		return this.fileList;
	}
	
	public void populate(String rawData, String fileName) throws IOException
	{	
			this.populatedFile = new File("src/main/java/data_source/" + fileName);
			
			if(this.populatedFile.createNewFile())
			{
				System.out.println("File: " + fileName + " has been created!");
			}
			else
			{
				System.out.println("File already exists");
			}
			Thread writeThread = new Thread()
			{
				public void run()
				{
					FileWriter myWriter = null;
					try {
						myWriter = new FileWriter("src/main/java/data_source/" + fileName);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		            URL url;
					try {
						url = new URL(rawData);
			            // read text returned by server
			            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			             
			            String line;
			            while ((line = in.readLine()) != null) {
			                myWriter.write(line + System.getProperty( "line.separator" ));
			            }
			            myWriter.close();
			            in.close();
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			
			writeThread.start();
			try {
				writeThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			

	}
	
}
