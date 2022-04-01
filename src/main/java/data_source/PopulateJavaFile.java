package data_source;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class PopulateJavaFile {
	
	private File populatedFile;
	
	public PopulateJavaFile(String url, String fileName)
	{
		
		try {
			this.populate(url, fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public File getPopulatedFile()
	{
		return this.populatedFile;
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
			
			this.populatedFile.createNewFile();
			FileWriter myWriter = new FileWriter("src/main/java/data_source/" + fileName);
			
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
			}
	}
	
}
