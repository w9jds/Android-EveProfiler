package com.w9jds.eveprofiler;

import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;

public class CallImageApi extends AsyncTask<ArrayList<Object>, Void, byte[]>
{
	MainActivity Main = new MainActivity();
	String ApiCalled = "";
	
	protected byte[] doInBackground(ArrayList<Object>... Info)
	{
		ArrayList<Object> ApiInfo = Info[0];
		byte[] responseArray = new byte[Integer.parseInt(ApiInfo.get(2).toString())];
		Main = (MainActivity)ApiInfo.get(3);
		
		try {

			// Create a new HttpClient and Get Header
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet();
			
			ApiCalled = ApiInfo.get(0).toString();
			
			if (ApiCalled.equals("http://image.eveonline.com/Character/") == true){
				httpget = new HttpGet(ApiInfo.get(0).toString() + ApiInfo.get(1).toString() + "_" + ApiInfo.get(2).toString() + ".jpg");
			}
			else{
				httpget = new HttpGet(ApiInfo.get(0).toString() + ApiInfo.get(1).toString() + "_" + ApiInfo.get(2).toString() + ".png");
			}
			
		    // Execute HTTP Get Request
		    HttpResponse response = httpclient.execute(httpget);
		    HttpEntity entity = response.getEntity();
		    responseArray = EntityUtils.toByteArray(entity);

		} 
		catch (Exception e){ System.out.println(e); }
		
		return responseArray;
	}
	
	protected void onPostExecute(byte[] result)
	{
		Main.ApiImageResponse(result, ApiCalled);
	}

}
