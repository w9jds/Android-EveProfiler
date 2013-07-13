package com.w9jds.eveprofiler.DataAccess;

import com.w9jds.eveprofiler.Objects.Character.Info;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by Jeremy on 6/6/13.
 */
class ParseCharacterInfo extends DefaultHandler
{
    private boolean isname = false;
    private boolean israce = false;
    private boolean isbloodline = false;
    private boolean isaccountBalance = false;
    private boolean isskillPoints = false;
    private boolean isshipName = false;
    private boolean isshipTypeID = false;
    private boolean isshipTypeName = false;
    private boolean iscorporationID = false;
    private boolean iscorporation = false;
    private boolean iscorporationDate = false;
    private boolean isallianceID = false;
    private boolean isalliance = false;
    private boolean isallianceDate = false;
    private boolean islastKnownLocation = false;
    private boolean issecurityStatus = false;
    private boolean iscachedUntil = false;

    public final Info data = new Info();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        if (localName.equals("characterName"))
            isname = true;
        else if (localName.equals("race"))
            israce = true;
        else if (localName.equals("bloodline"))
            isbloodline = true;
        else if (localName.equals("accountBalance"))
            isaccountBalance = true;
        else if (localName.equals("skillPoints"))
            isskillPoints = true;
        else if (localName.equals("shipName"))
            isshipName = true;
        else if (localName.equals("shipTypeID"))
            isshipTypeID = true;
        else if (localName.equals("shipTypeName"))
            isshipTypeName = true;
        else if (localName.equals("corporationID"))
            iscorporationID = true;
        else if (localName.equals("corporation"))
            iscorporation = true;
        else if (localName.equals("corporationDate"))
            iscorporationDate = true;
        else if (localName.equals("allianceID"))
            isallianceID = true;
        else if (localName.equals("alliance"))
            isalliance = true;
        else if (localName.equals("allianceDate"))
            isallianceDate = true;
        else if (localName.equals("lastKnownLocation"))
            islastKnownLocation = true;
        else if (localName.equals("securityStatus"))
            issecurityStatus = true;
        else if (localName.equals("cachedUntil"))
            iscachedUntil = true;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        if (localName.equals("characterName"))
            isname = false;
        else if (localName.equals("race"))
            israce = false;
        else if (localName.equals("bloodline"))
            isbloodline = false;
        else if (localName.equals("accountBalance"))
            isaccountBalance = false;
        else if (localName.equals("skillPoints"))
            isskillPoints = false;
        else if (localName.equals("shipName"))
            isshipName = false;
        else if (localName.equals("shipTypeID"))
            isshipTypeID = false;
        else if (localName.equals("shipTypeName"))
            isshipTypeName = false;
        else if (localName.equals("corporationID"))
            iscorporationID = false;
        else if (localName.equals("corporation"))
            iscorporation = false;
        else if (localName.equals("corporationDate"))
            iscorporationDate = false;
        else if (localName.equals("allianceID"))
            isallianceID = false;
        else if (localName.equals("alliance"))
            isalliance = false;
        else if (localName.equals("allianceDate"))
            isallianceDate = false;
        else if (localName.equals("lastKnownLocation"))
            islastKnownLocation = false;
        else if (localName.equals("securityStatus"))
            issecurityStatus = false;
        else if (localName.equals("cachedUntil"))
            iscachedUntil = false;
    }

    @Override
    public void characters(char ch[], int start, int length)
    {
        String chars = new String(ch, start, length);
        if(isname) data.setName(chars);
        else if(israce) data.setRace(chars);
        else if(isbloodline) data.setBloodLine(chars);
        else if(isaccountBalance) data.setAccountBalance(Double.parseDouble(chars));
        else if(isskillPoints) data.setSkillPoints(Double.parseDouble(chars));
        else if(isshipName) data.setShipName(chars);
        else if(isshipTypeID) data.setShipTypeID(chars);
        else if(isshipTypeName) data.setShipTypeName(chars);
        else if(iscorporationID) data.setCorporationID(chars);
        else if(iscorporation) data.setCorporation(chars);
        else if(iscorporationDate) data.setCorporationDate(chars);
        else if(isallianceID) data.setAllianceID(chars);
        else if(isalliance) data.setAlliance(chars);
        else if(isallianceDate) data.setAllianceDate(chars);
        else if(islastKnownLocation) data.setLastKnownLocation(chars);
        else if(issecurityStatus) data.setSecurityStatus(Double.parseDouble(chars));
        else if(iscachedUntil) data.setCachedUntil(chars);
    }
}
