package com.w9jds.eveprofiler.Core;

import android.os.AsyncTask;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeremy on 6/6/13.
 */
public class getXml
{
    public byte[] ApiImageCall(String uri, String toonID, String size)
    {
        byte[] responseArray = new byte[Integer.parseInt(size)];

        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget;

            if (uri.equals("http://image.eveonline.com/Character/"))
                httpget = new HttpGet(uri + toonID + "_" + size + ".jpg");
            else
                httpget = new HttpGet(uri + toonID + "_" + size + ".png");

            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            responseArray = EntityUtils.toByteArray(entity);
        }
        catch(Exception e){}

        return responseArray;
    }

//    public String ApiPostCall(ArrayList<KeysInfo> Params, String pathurl)
//    {
//        String responseString = null;
//
//        try
//        {
//            HttpClient httpclient = new DefaultHttpClient();
//            HttpPost httppost = new HttpPost("https://api.eveonline.com" + pathurl);
//
//            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(Params.size());
//            for (int i = 0; i < Params.size(); i++)
//                nameValuePairs.add(new BasicNameValuePair(Params.get(i).getKeyName(), Params.get(i).getKeyValue()));
//
//            HttpResponse response = httpclient.execute(httppost);
//            HttpEntity entity = response.getEntity();
//            responseString = EntityUtils.toString(entity);
//        }
//        catch(Exception e){}
//
//        return responseString;
//    }

    public String ApiGetCall(ArrayList<KeysInfo> Params, String pathurl)
    {
        String responseString = null;

        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(CreateUri("https://api.eveonline.com" + pathurl, Params));

            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (!entity.getContentType().getValue().equals("text/html; charset=us-ascii"))
                responseString = EntityUtils.toString(entity);
        }
        catch(Exception e){}

        return responseString;

    }

    private String CreateUri(String baseurl, ArrayList<KeysInfo> Parms)
    {
        String url = "";

        for(int i = 0; i < Parms.size(); i++)
        {
            if (i == 0)
                url += baseurl + "?" + Parms.get(i).getKeyName() + "=" + Parms.get(i).getKeyValue();
            else
                url += Parms.get(i).getKeyName() + "=" + Parms.get(i).getKeyValue();
            if(i != Parms.size()-1)
                url += "&";
        }

        return url;
    }
}


