package edu.ycp.cs.cs496.collegeplanner;

import edu.ycp.cs.cs496.collegeplanner.controllers.LoginController;
import edu.ycp.cs.cs496.collegeplanner.models.User;
import android.os.Bundle;
import android.app.Activity;
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
	
	public boolean logIn(User user) {
		LoginController lc = new LoginController();
		return lc.login(user);		
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
				
				user.setUsername(editText_username.getText().toString());
				user.setPassword(editText_password.getText().toString());
				
				System.out.println(user.getUsername() + user.getPassword());
				
				boolean result = logIn(user);
				Toast.makeText(MainActivity.this, "Log in:" + result, Toast.LENGTH_SHORT).show();
				
			}
		});
		
	}
	
	//TODO: Display homepage
	public void showHomePageView() {
		
	}

}
