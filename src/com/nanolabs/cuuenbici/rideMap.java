package com.nanolabs.cuuenbici;



import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class rideMap extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.map_ride);  // it points to map_ride.xml our map layout!
		
		
	}
	
	

}
