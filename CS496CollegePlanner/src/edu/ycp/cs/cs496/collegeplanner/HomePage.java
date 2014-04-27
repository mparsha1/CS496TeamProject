package edu.ycp.cs.cs496.collegeplanner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomePage extends Activity {
	
	String username;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent i = getIntent();
		username = i.getStringExtra("username");
		
		setDefaultView();
		//setContentView(R.layout.activity_main);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}	
	
	public void setDefaultView() {
		setContentView(R.layout.home_screen);
		
		Button logOutBtn = (Button) findViewById(R.id.button_logOut);
		
		logOutBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(final View v) {				
				
				AlertDialog.Builder builder =  new AlertDialog.Builder(HomePage.this);
				builder.setMessage("Are you sure you want to log out?");
				builder.setPositiveButton("Log out", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent goToSignIn = new Intent(v.getContext(), MainActivity.class);	
							Toast.makeText(HomePage.this, "Bye " + username, Toast.LENGTH_SHORT).show();
							startActivity(goToSignIn);							
						}
					});
				builder.setNegativeButton("Cancel", null);
				builder.show();					
			}
		});
		
		Button settingsBtn = (Button) findViewById(R.id.SettingsBtn);
		
		settingsBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent goToSettings = new Intent(v.getContext(), SettingsPage.class);
				goToSettings.putExtra("username", username);
				startActivity(goToSettings);
				
			}
			
			
			
		});
		
		Button accountButton = (Button) findViewById(R.id.button_myAccount);
		
		accountButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent goToAccount = new Intent(v.getContext(), AccountPage.class);
				goToAccount.putExtra("username", username);
				startActivity(goToAccount);				
			}
		});
		
		Button viewCoursesButton = (Button) findViewById(R.id.button_viewNextSemester);
		
		viewCoursesButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent goToCourses = new Intent(v.getContext(), CourseSequencePage.class);
				goToCourses.putExtra("username", username);
				startActivity(goToCourses);				
			}
		});
		
		
	}
}
