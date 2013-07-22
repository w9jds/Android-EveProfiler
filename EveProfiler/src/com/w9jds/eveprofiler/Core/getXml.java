package com.w9jds.eveprofiler.Core;

import com.w9jds.eveprofiler.Objects.ReturnResult;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import java.util.ArrayList;

/**
 * Created by Jeremy on 6/6/13.
 **/
public class getXml
{
    public ReturnResult ApiImageCall(String uri, String toonID, String size, ReturnResult rrReturn)
    {
        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget;

            if (uri.equals("http://image.eveonline.com/Character/"))
                httpget = new HttpGet(uri + toonID + "_" + size + ".jpg");
            else
                httpget = new HttpGet(uri + toonID + "_" + size + ".png");

            HttpResponse response = httpclient.execute(httpget);
            rrReturn.setStatusCode(response.getStatusLine().getStatusCode());
            if (rrReturn.getStatusCode() == HttpStatus.SC_OK)
            {
                HttpEntity entity = response.getEntity();
                rrReturn.setoReturn(EntityUtils.toByteArray(entity));
            }
        }
        catch(Exception e)
        {
            rrReturn.setoReturn(false);
        }

        return rrReturn;
    }

    public ReturnResult ApiGetCall(ArrayList<KeysInfo> Params, String pathurl, ReturnResult rrReturn)
    {
        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget;

            if (Params != null)
                httpget = new HttpGet(CreateUri("https://api.eveonline.com" + pathurl, Params));
            else
                httpget = new HttpGet("https://api.eveonline.com/" + pathurl);

            HttpResponse response = httpclient.execute(httpget);
            rrReturn.setStatusCode(response.getStatusLine().getStatusCode());
            if (rrReturn.getStatusCode() == HttpStatus.SC_OK)
            {
                HttpEntity entity = response.getEntity();
                rrReturn.setoReturn(EntityUtils.toString(entity));
            }
        }
        catch(Exception e)
        {
            rrReturn.setoReturn(false);
        }

        return rrReturn;

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


