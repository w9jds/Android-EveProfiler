package com.w9jds.eveprofiler.DataAccess;

import com.w9jds.eveprofiler.Objects.StandingInfo;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by Jeremy on 6/6/13.
 */
class ParseCharacterStandings extends DefaultHandler
{
    private boolean agentsdesc = false;
    private boolean npcdesc = false;
    private boolean factiondesc = false;
    public final ArrayList<StandingInfo> agent = new ArrayList<StandingInfo>();
    public final ArrayList<StandingInfo> NPC = new ArrayList<StandingInfo>();
    public final ArrayList<StandingInfo> faction = new ArrayList<StandingInfo>();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        if(localName.equals("rowset") && attributes.getValue("name").equals("agents"))
            agentsdesc = true;
        else if(localName.equals("rowset") && attributes.getValue("name").equals("NPCCorporations"))
            npcdesc = true;
        else if(localName.equals("rowset") && attributes.getValue("name").equals("factions"))
            factiondesc = true;
        else if(agentsdesc)
        {
            StandingInfo standing = new StandingInfo();
            standing.setfromID(attributes.getValue("fromID"));
            standing.setfromName(attributes.getValue("fromName"));
            standing.setStranding(attributes.getValue("standing"));
            agent.add(standing);
        }
        else if(npcdesc)
        {
            StandingInfo standing = new StandingInfo();
            standing.setfromID(attributes.getValue("fromID"));
            standing.setfromName(attributes.getValue("fromName"));
            standing.setStranding(attributes.getValue("standing"));
            NPC.add(standing);
        }
        else if(factiondesc)
        {
            StandingInfo standing = new StandingInfo();
            standing.setfromID(attributes.getValue("fromID"));
            standing.setfromName(attributes.getValue("fromName"));
            standing.setStranding(attributes.getValue("standing"));
            faction.add(standing);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        if (localName.equals("rowset"))
        {
            agentsdesc = false;
            npcdesc = false;
            factiondesc = false;
        }
    }
}
