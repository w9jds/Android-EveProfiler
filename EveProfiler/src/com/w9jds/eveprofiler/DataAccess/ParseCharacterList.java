package com.w9jds.eveprofiler.DataAccess;

import com.w9jds.eveprofiler.Objects.Character.CharacterMain;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.ArrayList;

/**
 * Created by Jeremy on 6/6/13.
 */
class ParseCharacterList extends DefaultHandler
{
    public final ArrayList<CharacterMain> data = new ArrayList<CharacterMain>();

    @Override
    public void startElement(String uri, String localName, String qName, org.xml.sax.Attributes attributes) throws SAXException
    {
        if (localName.equals("row"))
        {
            CharacterMain charinfo = new CharacterMain();
            charinfo.setCharacterID(attributes.getValue("characterID"));
            data.add(charinfo);
        }
    }
}
