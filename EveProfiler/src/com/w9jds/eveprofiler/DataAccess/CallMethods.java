package com.w9jds.eveprofiler.DataAccess;

import com.w9jds.eveprofiler.Core.KeysInfo;
import com.w9jds.eveprofiler.Core.getXml;
import com.w9jds.eveprofiler.Objects.Character.CharacterMain;
import com.w9jds.eveprofiler.Objects.Character.Info;
import com.w9jds.eveprofiler.Objects.ReturnResult;
import com.w9jds.eveprofiler.Objects.Server.ServerStatus;

import org.apache.http.HttpStatus;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Jeremy on 6/6/13.
 */
public class CallMethods
{
    private XMLReader reader;

    public ReturnResult GetDateTime()
    {
        ReturnResult rrReturn = new ReturnResult();
        rrReturn = new getXml().ApiGetCall(null, "/server/ServerStatus.xml.aspx", rrReturn);
        if (rrReturn.getStatusCode() == HttpStatus.SC_OK)
        {
            try
            {
                reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
                ParseServerStatus Handler = new ParseServerStatus();
                reader.setContentHandler(Handler);
                InputSource inputSource = new InputSource();
                inputSource.setEncoding("UTF-8");
                inputSource.setCharacterStream(new StringReader(rrReturn.getoReturn().toString()));
                reader.parse(inputSource);
                rrReturn.setoReturn(Handler.data);
            }
            catch(Exception e)
            {
                rrReturn.setoReturn(null);
            }
        }

        return rrReturn;
    }

    public ReturnResult GetCharactersList(String vCode, String keyid)
    {
        ArrayList<KeysInfo> params = new ArrayList<KeysInfo>();
        params.add(new KeysInfo("keyid", keyid));
        params.add(new KeysInfo("vCode", vCode));

        ReturnResult rrReturn = new ReturnResult();
        rrReturn = new getXml().ApiGetCall(params, "/account/Characters.xml.aspx", rrReturn);
        if (rrReturn.getStatusCode() == HttpStatus.SC_OK)
        {
            try
            {
                reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
                ParseCharacterList Handler = new ParseCharacterList();
                reader.setContentHandler(Handler);
                InputSource inputSource = new InputSource();
                inputSource.setEncoding("UTF-8");
                inputSource.setCharacterStream(new StringReader(rrReturn.getoReturn().toString()));
                reader.parse(inputSource);
                rrReturn.setoReturn(Handler.data);
            }
            catch(Exception e)
            {
                rrReturn.setoReturn(null);
            }
        }

        return rrReturn;
    }

    public ReturnResult GetCharacterInfo(String characterid, String vCode, String keyid)
    {
        ArrayList<KeysInfo> params = new ArrayList<KeysInfo>();
        params.add(new KeysInfo("keyid", keyid));
        params.add(new KeysInfo("vCode", vCode));
        params.add(new KeysInfo("characterID", characterid));

        ReturnResult rrReturn = new ReturnResult();
        rrReturn = new getXml().ApiGetCall(params, "/eve/CharacterInfo.xml.aspx", rrReturn);
        if (rrReturn.getStatusCode() == HttpStatus.SC_OK)
        {
            try
            {
                reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
                ParseCharacterInfo Handler = new ParseCharacterInfo();
                reader.setContentHandler(Handler);
                InputSource inputSource = new InputSource();
                inputSource.setEncoding("UTF-8");
                inputSource.setCharacterStream(new StringReader(rrReturn.getoReturn().toString()));
                reader.parse(inputSource);
                rrReturn.setoReturn(Handler.data);
            }
            catch(Exception e)
            {
                rrReturn.setoReturn(null);
            }
        }

        return rrReturn;
    }

    public ReturnResult GetCharacterPortrait(String CharID, String size)
    {
        ReturnResult rrReturn = new ReturnResult();
        return new getXml().ApiImageCall("http://image.eveonline.com/Character/", CharID, size, rrReturn);
    }

    public ReturnResult GetAlliancePortrait(String AllyID, String size)
    {
        ReturnResult rrReturn = new ReturnResult();
        return new getXml().ApiImageCall("http://image.eveonline.com/Alliance/", AllyID, size, rrReturn);
    }

    public ReturnResult GetCorporationPortrait(String CorpID, String size)
    {
        ReturnResult rrReturn = new ReturnResult();
        return new getXml().ApiImageCall("http://image.eveonline.com/Corporation/", CorpID, size, rrReturn);
    }

    public ReturnResult CharacterMailHeaders(String CharacterID, String vCode, String keyid)
    {
        ArrayList<KeysInfo> params = new ArrayList<KeysInfo>();
        params.add(new KeysInfo("keyid", keyid));
        params.add(new KeysInfo("vCode", vCode));
        params.add(new KeysInfo("characterID", CharacterID));

        ReturnResult rrReturn = new ReturnResult();
        rrReturn = new getXml().ApiGetCall(params, "/char/MailMessages.xml.aspx", rrReturn);
        if (rrReturn.getStatusCode() == HttpStatus.SC_OK)
        {
            try
            {
                reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
                ParseCharacterMailHeaders Handler = new ParseCharacterMailHeaders();
                reader.setContentHandler(Handler);
                InputSource inputSource = new InputSource();
                inputSource.setEncoding("UTF-8");
                inputSource.setCharacterStream(new StringReader(rrReturn.getoReturn().toString()));
                reader.parse(inputSource);
                rrReturn.setoReturn(Handler.data);
            }
            catch(Exception e)
            {
                rrReturn.setoReturn(null);
            }
        }

        return rrReturn;
    }

//    public void CharacterSheet(CharacterInfo Character)
//    {
//        String Response;
//        ArrayList<KeysInfo> params = new ArrayList<KeysInfo>();
//        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
//        params.add(new KeysInfo("keyid", settings.getString("keyid", null)));
//        params.add(new KeysInfo("vCode", settings.getString("vCode", null)));
//        params.add(new KeysInfo("characterID", Character.getCharacterID()));
//
//        Response = new getXml().ApiGetCall(params, getString(R.string.Character_Sheet));
//        try
//        {
//            XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
//            ParseCharacterSheet Handler = new ParseCharacterSheet();
//            reader.setContentHandler(Handler);
//            InputSource inputSource = new InputSource();
//            inputSource.setEncoding("UTF-8");
//            inputSource.setCharacterStream(new StringReader(Response));
//            reader.parse(inputSource);
//        }
//        catch(Exception e){}
//    }

//    public CharacterInfo CharacterStandings(CharacterInfo Character)
//    {
//        String Response;
//        ArrayList<KeysInfo> params = new ArrayList<KeysInfo>();
//        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
//        params.add(new KeysInfo("keyid", settings.getString("keyid", null)));
//        params.add(new KeysInfo("vCode", settings.getString("vCode", null)));
//        params.add(new KeysInfo("characterID", Character.getCharacterID()));
//
//        Response = new getXml().ApiGetCall(params, getString(R.string.NPC_Standings));
//        try
//        {
//            XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
//            ParseCharacterStandings Handler = new ParseCharacterStandings();
//            reader.setContentHandler(Handler);
//            InputSource inputSource = new InputSource();
//            inputSource.setEncoding("UTF-8");
//            inputSource.setCharacterStream(new StringReader(Response));
//            reader.parse(inputSource);
//            Character.setagentStandings(Handler.agent);
//            Character.setNPCStandings(Handler.NPC);
//            Character.setfactionStandings(Handler.faction);
//            return Character;
//        }
//        catch(Exception e)
//        {
//            return null;
//        }
//    }
}