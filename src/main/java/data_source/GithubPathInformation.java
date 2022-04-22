package data_source;

import java.util.ArrayList;

public class GithubPathInformation {

	private String userName;
	private String repoName;
	private String path;
	private String croppedURL;
	
	public GithubPathInformation(String url)
	{
		String newURL = url.substring(0, 19);
		
		if(!newURL.equals("https://github.com/"))
		{
			System.out.println("This is not a github URL");
		}
		
		this.croppedURL = url.substring(19);
		
		String[] splitURL = this.croppedURL.split("/");
		
		this.userName = splitURL[0];
		this.repoName = splitURL[1];
		
		ArrayList<String> pathList = new ArrayList<>();
		
		for(int i = 4; i < splitURL.length; i++)
		{
			pathList.add(splitURL[i]);
		}
		this.path = String.join("/", pathList);
		
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRepoName() {
		return repoName;
	}

	public void setRepoName(String repoName) {
		this.repoName = repoName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getCroppedURL() {
		return croppedURL;
	}

	public void setCroppedURL(String croppedURL) {
		this.croppedURL = croppedURL;
	}
	
}
