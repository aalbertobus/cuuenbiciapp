package com.nanolabs.cuuenbici;

import android.app.Activity;
import com.facebook.*;
import com.facebook.model.*;

import android.view.Window;
import android.widget.TextView;
import android.content.Intent;
import android.os.Bundle;

public class mainActivity extends Activity {
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);  // it points to activity_main.xml our second layout!
		
		// start Facebook Login
		  Session.openActiveSession(this, true, new Session.StatusCallback() {

		    // callback when session changes state
		    @Override
		    public void call(Session session, SessionState state, Exception exception) {
		    	if (session.isOpened()) {

		            // make request to the /me API
		            Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

		              // callback after Graph API response with user object
		              @Override
		              public void onCompleted(GraphUser user, Response response) {
		                if (user != null) {
		                  TextView welcome = (TextView) findViewById(R.id.welcome);
		                  welcome.setText("Hello " + user.getName() + "! Your are in!!!");
		                } 
		              }
		            });
		        }
		    }
		  });	
	}

	 @Override
	  public void onActivityResult(int requestCode, int resultCode, Intent data) {
	      super.onActivityResult(requestCode, resultCode, data);
	      Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	  }
	
}
