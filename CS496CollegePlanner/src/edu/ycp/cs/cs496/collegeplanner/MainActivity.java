package edu.ycp.cs.cs496.collegeplanner;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;

import edu.ycp.cs.cs496.collegeplanner.models.User;

import mobileControllers.GetLoginResult;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setDefaultView();
		//setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public boolean logIn(User user) throws ClientProtocolException, URISyntaxException, IOException {		
		GetLoginResult loginRequester = new GetLoginResult();
		return loginRequester.getLoginResult(user.getUsername(), user.getPassword());
	}	
	
	public void setDefaultView() {
		setContentView(R.layout.activity_main);
		
		//Obtain references to widgets
		Button signInButton = (Button) findViewById(R.id.Button_SignIn);		
		
		//Set listeners
		signInButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				User user = new User();
				
				EditText editText_username = (EditText) findViewById(R.id.editText_username);
				EditText editText_password = (EditText) findViewById(R.id.editText_Password);
				
				String username = editText_username.getText().toString();
				String password = editText_password.getText().toString();
				
				if(username.equals("") || password.equals("")) {
					Toast.makeText(MainActivity.this, "Missing Username or Password field", Toast.LENGTH_SHORT).show();
					return;
				}
					
				user.setUsername(username);
				user.setPassword(password);
				
				System.out.println(user.getUsername() + user.getPassword());
				
				boolean result = false;
				try {
					result = logIn(user);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(result == true) {
					Intent goToHome = new Intent(v.getContext(), HomePage.class);
					goToHome.putExtra("username", username);					
					startActivity(goToHome);
					
					
					Toast.makeText(MainActivity.this, "Welcome " + username, Toast.LENGTH_SHORT).show();
					
				} else {
					Toast.makeText(MainActivity.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
	}
	

}
