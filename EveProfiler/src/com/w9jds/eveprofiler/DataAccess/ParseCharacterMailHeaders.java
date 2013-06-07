package com.w9jds.eveprofiler.DataAccess;

import com.w9jds.eveprofiler.Objects.MailInfo;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.ArrayList;

/**
 * Created by Jeremy on 6/6/13.
 */
class ParseCharacterMailHeaders extends DefaultHandler
{
    public final ArrayList<MailInfo> data = new ArrayList<MailInfo>();

    @Override
    public void startElement(String uri, String localName, String qName, org.xml.sax.Attributes attributes) throws SAXException
    {
        if (localName.equals("row"))
        {
            MailInfo newmail = new MailInfo();
            newmail.setMessageID(attributes.getValue("messageID"));
            newmail.setSenderID(attributes.getValue("senderID"));
            newmail.setSentDate(attributes.getValue("sentDate"));
            newmail.setTitle(attributes.getValue("title"));
            newmail.setToCorpOrAllianceID(attributes.getValue("toCorpOrAllianceID"));
            newmail.setToCharacterIDs(attributes.getValue("toCharacterIDs"));
            newmail.setToListID(attributes.getValue("toListID"));
            data.add(newmail);
        }
    }
}
