package data_source;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class Grabber {
	
	private String userName;
	private String repoName;
	private String path;
	private String jsonString;
	private String downloadURL;
	private String fileName;
	
	public Grabber(String url)
	{
		this.grabCreds(url);
		try {
			this.apiGrabber();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.parseJSON();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getDownloadURL()
	{
		return this.downloadURL;
	}
	
	public String getFileName()
	{
		return this.fileName;
	}
	
	public void grabCreds(String URL)
	{	
		String githubURL = URL.substring(0, 19);
		
		if(!githubURL.equals("https://github.com/"))
		{
			System.out.println("This is not a github URL");
		}
		
		String stringWithoutGithub = URL.substring(19);
		
		System.out.println(stringWithoutGithub);
	
		String[] spilt = stringWithoutGithub.split("/");
		
		this.userName = spilt[0];
		this.repoName = spilt[1];
		//3 = blob
		//4 = main
		//5+ = path
		
		ArrayList<String> pathList = new ArrayList<>();
		for(int i = 4; i < spilt.length; i++)
		{
			pathList.add(spilt[i]);
			if(i == spilt.length-1) this.fileName = spilt[i];
		}	
		this.path = String.join("/", pathList);
	}
	
	public void apiGrabber() throws IOException
	{
		
		String urlString = "https://api.github.com/repos/" + this.userName+ "/" + this.repoName + "/contents/" + this.path;
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
	
	public void parseJSON() throws JSONException
	{
		JSONObject obj = new JSONObject(this.jsonString);
		
		this.downloadURL = obj.getString("download_url");
		
	}
	
	
}
