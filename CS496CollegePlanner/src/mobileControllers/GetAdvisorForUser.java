package mobileControllers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.DefaultHttpClient;

import edu.ycp.cs.cs496.collegeplanner.json.JSON;
import edu.ycp.cs.cs496.collegeplanner.models.Advisor;

public class GetAdvisorForUser {
	public Advisor getAdvisorForUser(String username) throws URISyntaxException, ClientProtocolException, IOException {
		return getAdvisorForUserRequest(username);
	}

	private Advisor getAdvisorForUserRequest(String username) throws URISyntaxException, ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		
		URI uri;
		
		String location = "/advisor/" + username;
		uri = URIUtils.createURI("http", "10.0.2.2", 8081, location, null, null);
		
		HttpGet req = new HttpGet(uri);
		
		HttpResponse response = client.execute(req);
		
		
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			
			HttpEntity entity = response.getEntity();
			Advisor advisor = JSON.getObjectMapper().readValue(entity.getContent(), Advisor.class);
			return advisor;
			
		} 
		
		return null;
	}
}
