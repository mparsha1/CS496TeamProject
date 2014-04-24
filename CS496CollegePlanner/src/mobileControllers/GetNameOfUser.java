package mobileControllers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.DefaultHttpClient;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import edu.ycp.cs.cs496.collegeplanner.json.JSON;

public class GetNameOfUser {
	public String getNameOfUser(String username) throws JsonParseException, JsonMappingException, IllegalStateException, IOException, URISyntaxException {
		return makeGetNameOfUserRequest(username);
	}

	private String makeGetNameOfUserRequest(String username) throws JsonParseException, JsonMappingException, IllegalStateException, IOException, URISyntaxException {
		HttpClient client = new DefaultHttpClient();
		
		URI uri;
		
		String location = "/name/" + username;
		uri = URIUtils.createURI("http", "10.0.2.2", 8081, location, null, null);
		
		HttpGet req = new HttpGet(uri);
		
		HttpResponse response = client.execute(req);
		
		if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			HttpEntity entity = response.getEntity();
			String name = JSON.getObjectMapper().readValue(entity.getContent(), String.class);
			return name;
		}
		return null;
	}
}
