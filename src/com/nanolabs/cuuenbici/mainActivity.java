package com.nanolabs.cuuenbici;

import android.app.Activity;
import com.facebook.*;
import com.facebook.model.*;
import com.facebook.widget.ProfilePictureView;


import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

import android.os.Bundle;

public class mainActivity extends Activity {
	
	 TextView welcome; 
	Button ridebutton,searchridesbutton,logoutbutton;
	 ProfilePictureView profilePictureView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);  // it points to activity_main.xml our second layout!
		
		
		welcome = (TextView) findViewById(R.id.userLabel);
		profilePictureView = (ProfilePictureView) findViewById(R.id.selection_profile_pic);
		ridebutton = (Button) findViewById(R.id.paseobutton);
		searchridesbutton = (Button) findViewById(R.id.searchrideButton);
		logoutbutton = (Button)findViewById(R.id.logoutbutton);
		
		
		
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
		                  
		                	welcome.setText("Hello\n " + user.getName() + "!");
		                  		                 
		                	// view that in turn displays the profile picture.
		                	profilePictureView.setProfileId(user.getId());
		                	profilePictureView.setPresetSize(profilePictureView.CUSTOM);
		                    
		                    //BUTTON LISTENERS
		                	ridebutton.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View arg0) {
									Intent nextLayout = new Intent(getApplicationContext(),rideMap.class);
									startActivity(nextLayout);
									
								}
							});
		                	
		                	
		                	searchridesbutton.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									Intent nextLayout = new Intent(getApplicationContext(),searchRides.class);
									startActivity(nextLayout);
									
								}
							});
		                	
		                	logoutbutton.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									// log out form facebook
									
								}
							});
		                	
		                	
		                	
		                    
		        
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
