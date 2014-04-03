package edu.ycp.cs.cs496.collegeplanner;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class HomePage extends Activity {
	
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
	
	public void setDefaultView() {
		setContentView(R.layout.home_screen);
	}
}
