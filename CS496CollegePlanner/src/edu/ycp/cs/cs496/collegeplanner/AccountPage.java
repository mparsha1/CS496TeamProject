package edu.ycp.cs.cs496.collegeplanner;

import java.io.IOException;
import java.net.URISyntaxException;

import mobileControllers.GetAdvisorForUser;
import mobileControllers.GetMajor;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AccountPage extends Activity{
	String username;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent i = getIntent();
		
		username = i.getStringExtra("username");

		try {
			setDefaultView();
		} catch (Exception e) {
			e.printStackTrace();
		} 

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void setDefaultView() throws ClientProtocolException, URISyntaxException, IOException {
		setContentView(R.layout.account_view);
		
		TextView majorText = (TextView) findViewById(R.id.MajorTextView_2);
		TextView usernameText = (TextView) findViewById(R.id.UsernameTextView_2);
		TextView advisorText = (TextView) findViewById(R.id.advisorTextView);
		
		Button signOutButton = (Button) findViewById(R.id.signOutButtonAccountPg);
		Button backButton = (Button) findViewById(R.id.backButtonAccountPg);
		
		Button editUsernameButton = (Button) findViewById(R.id.editUsernameAcntPgBtn);
		Button editPasswordButton = (Button) findViewById(R.id.editPasswordAcntPgBtn);
		
		usernameText.setText("Username: " + username);
		
		GetMajor gm = new GetMajor();
		majorText.setText("Major: " + gm.getMajor(username));
		
		GetAdvisorForUser gafu = new GetAdvisorForUser();
		advisorText.setText("Advisor: " + gafu.getAdvisorForUser(username).getName());
		
		editUsernameButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		editPasswordButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		backButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent backToHome = new Intent(v.getContext(), HomePage.class);
				backToHome.putExtra("username", username);
				startActivity(backToHome);

			}	
		});

		signOutButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent signOut = new Intent(v.getContext(), MainActivity.class);
				startActivity(signOut);

			}	
		});
	}
}
