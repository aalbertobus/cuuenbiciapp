package com.nanolabs.cuuenbici;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;


public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
      //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
      	setContentView(R.layout.activity_splash);
        
        Thread timer = new Thread(){
        	public void run(){
        		try {
					sleep(2000);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					Intent mainWindow = new Intent(getApplicationContext(),mainActivity.class);
					startActivity(mainWindow);	
				}
        		
        	}
        };
        
        timer.start();
    }

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	

    	

    
}
