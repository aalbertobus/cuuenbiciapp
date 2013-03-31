package com.nanolabs.cuuenbici;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class httpHandler {

//post url dice a que direccion se va a postear
	
	public String post(String posturl,String lat_,String long_){
		
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(posturl);
			
			//add params
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("lat", long_));
			params.add(new BasicNameValuePair("long", lat_));
			httppost.setEntity(new UrlEncodedFormEntity(params));
			
			httpclient.execute(httppost); //This line send the http request to server
			
			
			HttpResponse resp = httpclient.execute(httppost); //gets response from server
			HttpEntity ent = resp.getEntity();
			String text = EntityUtils.toString(ent);  	
			return text;
		} catch (Exception e) {
			String text = "http POST fail";
			return text;
		}
	}

}


