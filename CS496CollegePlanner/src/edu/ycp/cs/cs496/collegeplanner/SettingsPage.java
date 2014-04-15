package edu.ycp.cs.cs496.collegeplanner;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class SettingsPage extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.print("made it to settingsPage");
		setDefaultView();
		//setContentView(R.layout.activity_main);
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void setDefaultView() {
		// TODO Auto-generated method stub
		System.out.print("made it to settingsPage");
		setContentView(R.layout.settings_view);
		
		
		
	}
	
	
	
}
