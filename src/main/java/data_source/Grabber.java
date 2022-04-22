package data_source;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class Grabber {
	
//	private String userName;
//	private String repoName;
//	private String path;
	private String jsonString;
	
	private GithubPathInformation GPI;
	
	ArrayList<File> fileList;
	HashMap<String, String> jsonInformation;

	private Parsable parsingMethod;

	private boolean controlFlagJavaFile;
	
	public Grabber(String url)
	{
		jsonInformation = new HashMap<>();
		fileList = new ArrayList<>();
		controlFlagJavaFile = false;
		this.GPI = new GithubPathInformation(url);
		try {
			this.apiGrabber();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(controlFlagJavaFile)
			{
				parsingMethod = new javaClassParse();
			}
			else
			{
				parsingMethod = new javaFolderParse();
			}

			this.jsonInformation = parsingMethod.parseJSON(this.jsonString);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public HashMap<String,String> getJSONInfo()
	{
		return this.jsonInformation;
	}
	
	public void setFileList(ArrayList<File> fileList)
	{
		this.fileList = fileList;
	}
	
	public void apiGrabber() throws IOException
	{
		String userName = this.GPI.getUserName();
		
		
		String urlString = "https://api.github.com/repos/" 
		+ GPI.getUserName() 
		+ "/" 
		+ GPI.getRepoName() 
		+ "/contents/" 
		+ GPI.getPath();

		if(GPI.getPath().endsWith(".java"))
		{
			controlFlagJavaFile = true;
		}

		String contentType = null;
		int status = 0;
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", "application/json");
			contentType = connection.getHeaderField("Content-Type");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			status = connection.getResponseCode();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Reader streamReader = null;
		
		if (status > 299) {
		    streamReader = new InputStreamReader(connection.getErrorStream());
		} else {
		    streamReader = new InputStreamReader(connection.getInputStream());
		}
		
		BufferedReader in = new BufferedReader(
				new InputStreamReader(connection.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		
		while((inputLine = in.readLine()) != null)
		{
			content.append(inputLine);
		}
		in.close();
		
		
		this.jsonString = content.toString();
		
		connection.disconnect();
	}

	public void deleteFiles() {
		// TODO Auto-generated method stub
		for(File file : this.fileList)
		{
			file.delete();
		}
		
	}
	
	
}
