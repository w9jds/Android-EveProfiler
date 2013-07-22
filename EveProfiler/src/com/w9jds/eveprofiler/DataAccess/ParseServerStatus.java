package com.w9jds.eveprofiler.DataAccess;

import com.w9jds.eveprofiler.Objects.Server.ServerStatus;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by Jeremy on 7/21/13.
 */
class ParseServerStatus extends DefaultHandler
{
    private boolean iscurrentTime = false;
    private boolean isserverOpen = false;
    private boolean isonlinePlayers = false;
    private boolean iscachedUntil = false;

    public final ServerStatus data = new ServerStatus();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        if (localName.equals("currentTime"))
            iscurrentTime = true;
        else if (localName.equals("serverOpen"))
            isserverOpen = true;
        else if (localName.equals("onlinePlayers"))
            isonlinePlayers = true;
        else if (localName.equals("cachedUntil"))
            iscachedUntil = true;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        if (localName.equals("currentTime"))
            iscurrentTime = false;
        else if (localName.equals("serverOpen"))
            isserverOpen = false;
        else if (localName.equals("onlinePlayers"))
            isonlinePlayers = false;
        else if (localName.equals("cachedUntil"))
            iscachedUntil = false;
    }

    @Override
    public void characters(char ch[], int start, int length)
    {
        String chars = new String(ch, start, length);
        if (iscurrentTime) data.setCurrentTime(chars);
        else if(isonlinePlayers) data.setonlinePlayers(chars);
        else if(isserverOpen) data.setserverOpen(chars);
        else if(iscachedUntil) data.setCachedUntil(chars);
    }

}
