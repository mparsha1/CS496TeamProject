package mobileControllers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

public class SetMajor {
	
	public boolean setMajor(String username, String major) throws ClientProtocolException, URISyntaxException, IOException {
		
		return makeSetMajorRequest(username, major);
	}
	
	
	private boolean makeSetMajorRequest(String username, String major) throws ClientProtocolException, URISyntaxException, IOException {
		
		 HttpClient client = new DefaultHttpClient();
		 
		 URI uri;
			
			String location = "/majors";
			uri = URIUtils.createURI("http", "10.0.2.2", 8081, location, 
					    null, null);
			
		HttpPost req = new HttpPost(uri);
		
		HttpParams params = new BasicHttpParams();
		params.setParameter("major", major);
		params.setParameter("username", username);
		req.setParams(params);
		
		
		HttpResponse resp = client.execute(req);
		
		if(resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			return true;
		}
		
		
		return false;
		
			
		
	}

}
