package com.w9jds.eveprofiler.DataAccess;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.w9jds.eveprofiler.Activities.MainActivity;
import com.w9jds.eveprofiler.Core.KeysInfo;
import com.w9jds.eveprofiler.Core.getXml;
import com.w9jds.eveprofiler.Objects.Character.CharacterMain;
import com.w9jds.eveprofiler.Objects.Character.Info;
import com.w9jds.eveprofiler.R;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import java.io.StringReader;
import java.util.ArrayList;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Jeremy on 6/6/13.
 */
class CallMethods
{
    public ArrayList<CharacterMain> CharactersList()
    {
        String Response;
        ArrayList<KeysInfo> params = new ArrayList<KeysInfo>();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(Main);
        params.add(new KeysInfo("keyid", settings.getString("keyid", null)));
        params.add(new KeysInfo("vCode", settings.getString("vCode", null)));

        Response = new getXml().ApiGetCall(params, Main.getString(R.string.List_Characters));
        try
        {
            XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            ParseCharacterList Handler = new ParseCharacterList();
            reader.setContentHandler(Handler);
            InputSource inputSource = new InputSource();
            inputSource.setEncoding("UTF-8");
            inputSource.setCharacterStream(new StringReader(Response));
            reader.parse(inputSource);
            return Handler.data;
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public Info CharacterInfo(String characterid)
    {
        String Response;
        ArrayList<KeysInfo> params = new ArrayList<KeysInfo>();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(Main);
        params.add(new KeysInfo("keyid", settings.getString("keyid", null)));
        params.add(new KeysInfo("vCode", settings.getString("vCode", null)));
        params.add(new KeysInfo("characterID", characterid));

        Response = new getXml().ApiGetCall(params, Main.getString(R.string.Character_Info));
        try
        {
            XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            ParseCharacterInfo Handler = new ParseCharacterInfo();
            reader.setContentHandler(Handler);
            InputSource inputSource = new InputSource();
            inputSource.setEncoding("UTF-8");
            inputSource.setCharacterStream(new StringReader(Response));
            reader.parse(inputSource);
            return Handler.data;
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public byte[] CharacterPortrait(String CharID, String size)
    {
        return new getXml().ApiImageCall(Main.getString(R.string.Character_Portrait), CharID, size);
    }

    public byte[] AlliancePortrait(String AllyID, String size)
    {
        return new getXml().ApiImageCall(Main.getString(R.string.Alliance_Logo), AllyID, size);
    }

    public byte[] CorporationPortrait(String CorpID, String size)
    {
        return new getXml().ApiImageCall(Main.getString(R.string.Corp_Logo), CorpID, size);
    }

    public ArrayList<com.w9jds.eveprofiler.Objects.MailInfo> CharacterMailHeaders(String CharacterID)
    {
        String Response;
        ArrayList<KeysInfo> params = new ArrayList<KeysInfo>();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(Main);
        params.add(new KeysInfo("keyid", settings.getString("keyid", null)));
        params.add(new KeysInfo("vCode", settings.getString("vCode", null)));
        params.add(new KeysInfo("characterID", CharacterID));

        Response = new getXml().ApiGetCall(params, Main.getString(R.string.MailMessHeaders));
        try
        {
            XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            ParseCharacterMailHeaders Handler = new ParseCharacterMailHeaders();
            reader.setContentHandler(Handler);
            InputSource inputSource = new InputSource();
            inputSource.setEncoding("UTF-8");
            inputSource.setCharacterStream(new StringReader(Response));
            reader.parse(inputSource);
            return Handler.data;
        }
        catch(Exception e)
        {
            return null;
        }
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