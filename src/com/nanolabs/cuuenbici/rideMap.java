package com.nanolabs.cuuenbici;


import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.widget.Toast;
import android.provider.Settings;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class rideMap extends FragmentActivity {
	//variables
	GoogleMap map;
	double lat_;
	double long_;
	LatLng mylocation;
	Marker mylocationstart;
	Marker mylocationcurrent;
	String url = "http://10.0.1.39:8080/track"; 
	httpHandler handler;
	Toast toastAlert ;
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.map_ride);
		
		toastAlert = Toast.makeText( this  , "" , Toast.LENGTH_LONG );
		
		if(CheckEnableGPS()){  
			//check gps , if gps enabled load map;
			toastAlert.setText("GPS Habilitado");
			toastAlert.show();
			loadrideMap(); 	
		} else {      
			gpsAlert();  //show alert to enable gps and kill current activity
		}
	}
	
	//EXTRA FUNCTIONS
	//to check if gps is enabled and ask for it
	private boolean CheckEnableGPS(){
		String provider = Settings.Secure.getString(getContentResolver(),
		Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		
		if(provider.contains("gps")){
			System.out.println("GPS DETECTED");
			return true;
		}else{
			//if gps is disabled show alert and send to network settings
			System.out.println("NO GPS");
			return false;
		}
	}
	
	private void gpsAlert(){
		// if no gps send alert and go to network settings
		final AlertDialog.Builder nogpsAlert = new AlertDialog.Builder(this);
		nogpsAlert.setTitle("Alerta");
		nogpsAlert.setMessage("Necesitas Habilitar el GPS de tu movil");
		
		nogpsAlert.setPositiveButton("Aceptar",new OnClickListener() {	
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);  //intent to network settings
				startActivity(intent);
				finish();
			} 
		});	
		nogpsAlert.show();					
	}
	
	//Load Map Function
	private void loadrideMap(){
		
		
		handler =  new httpHandler();  //new http handler
		map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap(); //referencing map
		
		//setting normal type map
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		
		//setting default camera zoom
		CameraUpdate zoom=CameraUpdateFactory.zoomTo(18);
		map.animateCamera(zoom);
		
		// Add a marker at start point.	it have random lat and long values but it doesn´t affect	  
		mylocationcurrent = map.addMarker(new MarkerOptions()
		 .position(new LatLng(28.641787,-106.076431))  //chihuahua mexico as the default location
		 .title("Punto Actual")
		 .snippet("Este es tu punto actual"));
		  	
		// Acquire a reference to the system Location Manager
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

		//LOCATION LISTENERS
		LocationListener locationlistener = new LocationListener() {
			public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
				
			}
			
			@Override
			public void onProviderEnabled(String arg0) {
				
			}
			
			@Override
			public void onProviderDisabled(String arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLocationChanged(Location arg0) {
				//getting current lat & long values
				lat_ = arg0.getLatitude();
		    	long_ = arg0.getLongitude();
		    	
		    	//setting lat & long values to a my location object and displaying the position on the map
		    	 mylocation = new LatLng(lat_, long_);
		    	 mylocationcurrent.setPosition(mylocation);	
		    	 
		    	 //to move the camera to current location
		    	 CameraUpdate center= CameraUpdateFactory.newLatLng(new LatLng(lat_,long_));
		         map.moveCamera(center);
		    		   
		    	//sending http request + lat & long params to server and getting response to checkHttp string
			     String checkHttp = handler.post(url,Double.toString(lat_),Double.toString(long_));
			     
			     //check if http was received for the server
			     //if server responds with OK, server receive http , else server Fails receiving 
			     if(checkHttp.equals("OK")){
			    	 System.out.println("Received by SERVER");
			    	 toastAlert.cancel();
			     } else {
			    	 toastAlert.setText("Problemas de Conectividad");
					 toastAlert.show();
			    	 System.out.println("Can't Find Server");
			     }    
			}
		};
		
		// Register the listener with the Location Manager to receive location updates
		//locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationlistener);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationlistener);		
	}
}
