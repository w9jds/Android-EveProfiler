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

import android.content.SharedPreferences;
import android.os.AsyncTask;

public class CallApi extends AsyncTask<ArrayList<Object>, Void, ArrayList<CharacterInfo>>{
		
	MainActivity Main = new MainActivity();
	
	protected ArrayList<CharacterInfo> doInBackground(ArrayList<Object>... Info)
	{
		ArrayList<CharacterInfo> Characters = new ArrayList<CharacterInfo>();
		Main = (MainActivity)Info[0].get(1);
		
		if (Info[0].get(0) == "getCharacters")
		{
			ArrayList<String> params = new ArrayList<String>();
			params.add(Main.getString(R.string.List_Characters));
			ArrayList<String> keys = new ArrayList<String>();
			
			String Response = new CallApi.asyncClass().ApiCall(params, keys, Main);
			Characters = new CallApi.asyncParse().ParseCharacterList(Response);
			
//	        SharedPreferences settings = Main.getSharedPreferences("Characters", 0);
//	        SharedPreferences.Editor editor = settings.edit();
//	        editor.("CharacterList", Characters);
//	        editor.commit();
			
			for (int i = 0; i < Characters.size(); i++){
				params.clear();
				params.add(Main.getString(R.string.Character_Sheet));
				keys.clear();
				keys.add(Characters.get(0).getCharacterID());
				
				Response = new CallApi.asyncClass().ApiCall(params, keys, Main);
			}

		}
		
		
		
		
		
		
		
		
		
			
			
			
			
			
			
			


		
		
		return Characters;
	}

	class asyncParse {
		
		public ArrayList<CharacterInfo> ParseCharacterList(String response)
		{
			ArrayList<CharacterInfo> Characters = new ArrayList<CharacterInfo>();

			String[] split = response.split("<");
			ArrayList<String> characterLines = new ArrayList<String>();
			
			for (int i = 0; i < split.length; i++)
			{
				if (split[i].startsWith("row ") == true)
				{
					characterLines.add(split[i]);
				}
			}
			
			for (int i = 0; i < characterLines.size(); i++)
			{
				String[] character = characterLines.get(i).split("row name=\"|\" characterID=\"|\" corporationName=\"|\" corporationID=\"|\" />");
				Characters.add(new CharacterInfo());
				Characters.get(i).setName(character[1]);
				Characters.get(i).setCharacterID(character[2]);
				Characters.get(i).setCorporationName(character[3]);
				Characters.get(i).setCorporationID(character[4]);
			}

			return Characters;	
		}
		
		
		
		
		
		
		
	}
	
	class asyncClass {
		
		String responseString = "";
		public String ApiCall(ArrayList<String> Params, ArrayList<String> Keys, MainActivity Main){
			try {
					
				// Create a new HttpClient and Post Header
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost();
				
				switch(Params.size())
				{
	//				case 1:
	//					httppost = new HttpPost();
	//					break;
					case 1:
						httppost = new HttpPost(Main.getString(R.string.Api_Uri) + Params.get(0));
						break;
					case 2:
						httppost = new HttpPost(Main.getString(R.string.Api_Uri) + Params.get(0) + Params.get(0));
						break;
				}
				
			    // Add your data
			    List<NameValuePair> nameValuePairs;
			    //pull api keys
		        SharedPreferences settings = Main.getSharedPreferences("ApiKeys", 0);
		        String keyid = settings.getString("keyid", null);
		        String vCode = settings.getString("vCode", null);
			    
			    switch(Keys.size())
			    {
	//			    case 2:
	//		    		nameValuePairs = new ArrayList<NameValuePair>(1);
	//		    		nameValuePairs.add(new BasicNameValuePair(ApiInfo.get(1).get(0).toString(), ApiInfo.get(1).get(1).toString()));
	//		    		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	//		    		break;
			    
			    	case 0:
			    		nameValuePairs = new ArrayList<NameValuePair>(2);
			    		nameValuePairs.add(new BasicNameValuePair("keyID", keyid));
			    		nameValuePairs.add(new BasicNameValuePair("vCode", vCode));
			    		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			    		break;
			    		
			    	case 1:
			    		nameValuePairs = new ArrayList<NameValuePair>(3);
			    		nameValuePairs.add(new BasicNameValuePair("keyID", keyid));
			    		nameValuePairs.add(new BasicNameValuePair("vCode", vCode));
			    		nameValuePairs.add(new BasicNameValuePair("characterID", Keys.get(0)));
			    		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			    }
	
			    		    // Execute HTTP Post Request
			    HttpResponse response = httpclient.execute(httppost);
			    HttpEntity entity = response.getEntity();
			    responseString = EntityUtils.toString(entity);
			} 
			catch (Exception e){ System.out.println(e); }
	
		    return responseString;
		}
	}

	
	protected void onPostExecute(ArrayList<CharacterInfo> result)
	{
		//Main.ApiResponse(result, ApiCalled);
	}
}