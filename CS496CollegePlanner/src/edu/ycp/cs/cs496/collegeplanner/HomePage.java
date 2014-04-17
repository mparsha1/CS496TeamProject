package edu.ycp.cs.cs496.collegeplanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

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
		
	}
}
