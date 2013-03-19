package com.nanolabs.cuuenbici;

import android.app.Activity;
import com.facebook.*;
import com.facebook.model.*;

import android.view.Window;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class mainActivity extends Activity {
	
	TextView welcome; 
	TextView locationlabel;
	boolean sesion;
	
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);  // it points to activity_main.xml our second layout!
		
		welcome = (TextView) findViewById(R.id.welcome);
		locationlabel = (TextView) findViewById(R.id.txtlocation);
	//--------------------------------------------LOCATION------------
		
		// Acquire a reference to the system Location Manager
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		
		// Define a listener that responds to location updates
		LocationListener locationListener = new LocationListener() {
		    public void onLocationChanged(Location location) {
		      // Called when a new location is found by the network location provider.
		      locationlabel.setText("lat: " + location.getLatitude() + " \n long: " + location.getLongitude());
		    }

		    public void onStatusChanged(String provider, int status, Bundle extras) {}

		    public void onProviderEnabled(String provider) {}

		    public void onProviderDisabled(String provider) {}
		  };

		// Register the listener with the Location Manager to receive location updates
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
				
		
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
		                  
		                  welcome.setText("Hello " + user.getName() + "!!!");
		        
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
