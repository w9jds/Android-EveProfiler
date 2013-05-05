package com.w9jds.eveprofiler;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;

public class CallApi extends AsyncTask<ArrayList<ArrayList<String>>, Void, String>{
		
	protected String doInBackground(ArrayList<ArrayList<String>>... Info)
	{
		ArrayList<ArrayList<String>> ApiInfo = Info[0];
		String responseString = "";
		
		try {

			// Create a new HttpClient and Post Header
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost();
			
			switch(ApiInfo.get(0).size())
			{
				case 1:
					httppost = new HttpPost(ApiInfo.get(0).get(0));
					break;
				case 2:
					httppost = new HttpPost(ApiInfo.get(0).get(0) + ApiInfo.get(0).get(1));
					break;
				case 3:
					httppost = new HttpPost(ApiInfo.get(0).get(0) + ApiInfo.get(0).get(1) + ApiInfo.get(0).get(2));
					break;
			}

		    // Add your data
		    List<NameValuePair> nameValuePairs;
		    
		    switch(ApiInfo.get(1).size())
		    {
			    case 2:
		    		nameValuePairs = new ArrayList<NameValuePair>(1);
		    		nameValuePairs.add(new BasicNameValuePair(ApiInfo.get(1).get(0), ApiInfo.get(1).get(1)));
		    		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		    		break;
		    
		    	case 4:
		    		nameValuePairs = new ArrayList<NameValuePair>(2);
		    		nameValuePairs.add(new BasicNameValuePair(ApiInfo.get(1).get(0), ApiInfo.get(1).get(1)));
		    		nameValuePairs.add(new BasicNameValuePair(ApiInfo.get(1).get(2), ApiInfo.get(1).get(3)));
		    		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		    		break;
		    		
		    	case 6:
		    		nameValuePairs = new ArrayList<NameValuePair>(3);
		    		nameValuePairs.add(new BasicNameValuePair(ApiInfo.get(1).get(0), ApiInfo.get(1).get(1)));
		    		nameValuePairs.add(new BasicNameValuePair(ApiInfo.get(1).get(2), ApiInfo.get(1).get(3)));
		    		nameValuePairs.add(new BasicNameValuePair(ApiInfo.get(1).get(4), ApiInfo.get(1).get(5)));
		    		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		    }

		    // Execute HTTP Post Request
		    HttpResponse response = httpclient.execute(httppost);
		    HttpEntity entity = response.getEntity();
		    responseString = EntityUtils.toString(entity);

		} 
		catch (Exception e){ 
			System.out.println(e);
			}
		
		return responseString;
	}
	
	protected void onPostExecute(String result)
	{
		new ParseXml().parse(result);
	
	}
}
