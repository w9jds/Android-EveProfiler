package com.w9jds.eveprofiler.Classes;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.w9jds.eveprofiler.Activities.MailActivity;
import com.w9jds.eveprofiler.Activities.MainActivity;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import com.w9jds.eveprofiler.R;
import com.w9jds.eveprofiler.R.string;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

public class CallApi extends AsyncTask<ArrayList<Object>, Void, ArrayList<CharacterInfo>>{

    public ArrayList<CharacterInfo> Characters = new ArrayList<CharacterInfo>();
    public MainActivity Main = new MainActivity();
    public MailActivity Mail = new MailActivity();

    protected ArrayList<CharacterInfo> doInBackground(ArrayList<Object>... Info)
    {
        if(Info[0].get(1).getClass() == Main.getClass())
        {
            Main = (MainActivity)Info[0].get(1);
            Mail = null;
        }
        else if(Info[0].get(1).getClass() == Mail.getClass())
        {
            Mail = (MailActivity)Info[0].get(1);
            Main = null;
        }

        if (Info[0].get(0) == "getCharacters")
        {
            CallMethods Calls = new CallMethods();
            Calls.CharactersList();

            for (int i = 0; i < Characters.size(); i++)
            {
                Characters.get(i).CombineSheet(Calls.CharacterInfo(Characters.get(i).getCharacterID()));
                Characters.get(i).setCharacterPortrait(Calls.CharacterPortrait(Characters.get(i).getCharacterID(), "128"));
                Characters.get(i).setCorporationPortrait(Calls.CorporationPortrait(Characters.get(i).getCorporationID(), "64"));
                if(Characters.get(i).getAllianceID() != null)
                    Characters.get(i).setAlliancePortrait(Calls.AlliancePortrait(Characters.get(i).getAllianceID(), "64"));
            }
        }
        else if (Info[0].get(0) == "getMail")
        {
            Characters = (ArrayList<CharacterInfo>)Info[0].get(2);
            CallMethods Calls = new CallMethods();

            for (int i = 0; i < Characters.size(); i++)
            {
                Calls.CharacterMailHeaders(i);

                for (int j = 0; j < Characters.get(i).getMail().size(); j++)
                {
                    boolean duplicate = false;
                    for (int k = 0; k < j; k++)
                    {
                        if (Characters.get(i).getMail().get(k).getSenderID().equals(Characters.get(i).getMail().get(j).getSenderID()) == true)
                        {
                            Characters.get(i).getMail().get(j).setSenderName(Characters.get(i).getMail().get(k).getSenderName());
                            Characters.get(i).getMail().get(j).setSenderPortrait(Characters.get(i).getMail().get(k).getSenderPortrait());
                            duplicate = true;
                            break;
                        }
                    }
                    if (duplicate == false)
                    {
                        Characters.get(i).getMail().get(j).setSenderPortrait(Calls.CharacterPortrait(Characters.get(i).getMail().get(j).getSenderID(), "64"));
                        Characters.get(i).getMail().get(j).setSenderName(Calls.CharacterInfo(Characters.get(i).getMail().get(j).getSenderID()).getName());
                    }
                }
            }
        }
        return Characters;
    }

    class CallMethods
    {
        public void CharactersList()
        {
            String Response = null;
            ArrayList<KeysInfo> params = new ArrayList<KeysInfo>();
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(Main);
            params.add(new KeysInfo("keyid", settings.getString("keyid", null)));
            params.add(new KeysInfo("vCode", settings.getString("vCode", null)));

            Response = new CallApi.asyncClass().ApiGetCall(params, Main.getString(R.string.List_Characters));
            try
            {
                XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
                ParseCharacterList Handler = new ParseCharacterList();
                reader.setContentHandler(Handler);
                InputSource inputSource = new InputSource();
                inputSource.setEncoding("UTF-8");
                inputSource.setCharacterStream(new StringReader(Response));
                reader.parse(inputSource);
                Characters = Handler.data;
            }
            catch(Exception e){}
        }

        public CharacterInfo CharacterInfo(String characterid)
        {
            CharacterInfo returnpeeps = new CharacterInfo();
            String Response = null;
            SharedPreferences settings = null;
            ArrayList<KeysInfo> params = new ArrayList<KeysInfo>();
            if (Main != null)
                settings = PreferenceManager.getDefaultSharedPreferences(Main);
            else if(Mail != null)
                settings = PreferenceManager.getDefaultSharedPreferences(Mail);
            params.add(new KeysInfo("keyid", settings.getString("keyid", null)));
            params.add(new KeysInfo("vCode", settings.getString("vCode", null)));
            params.add(new KeysInfo("characterID", characterid));

            if (Main != null)
                Response = new CallApi.asyncClass().ApiGetCall(params, Main.getString(R.string.Character_Info));
            else if(Mail != null)
                Response = new CallApi.asyncClass().ApiGetCall(params, Mail.getString(R.string.Character_Info));
            try
            {
                XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
                ParseCharacterInfo Handler = new ParseCharacterInfo();
                reader.setContentHandler(Handler);
                InputSource inputSource = new InputSource();
                inputSource.setEncoding("UTF-8");
                inputSource.setCharacterStream(new StringReader(Response));
                reader.parse(inputSource);
                returnpeeps = Handler.data;
            }
            catch(Exception e){}

            return returnpeeps;
        }

        public byte[] CharacterPortrait(String CharID, String size)
        {
            asyncClass portrait = new asyncClass();
            if (Main != null)
                return portrait.ApiImageCall(Main.getString(R.string.Character_Portrait), CharID, size);
            else if(Mail != null)
                return portrait.ApiImageCall(Mail.getString(R.string.Character_Portrait), CharID, size);
            else
                return null;
        }

        public byte[] AlliancePortrait(String AllyID, String size)
        {
            asyncClass portrait = new asyncClass();
            if (Main != null)
                return portrait.ApiImageCall(Main.getString(R.string.Alliance_Logo), AllyID, size);
            else if (Mail != null)
                return portrait.ApiImageCall(Mail.getString(R.string.Alliance_Logo), AllyID, size);
            else
                return null;
        }

        public byte[] CorporationPortrait(String CorpID, String size)
        {
            asyncClass portrait = new asyncClass();
            if (Main != null)
                return portrait.ApiImageCall(Main.getString(R.string.Corp_Logo), CorpID, size);
            else if (Mail != null)
                return portrait.ApiImageCall(Mail.getString(R.string.Corp_Logo), CorpID, size);
            else
                return null;
        }

        public void CharacterMailHeaders(int i)
        {
            String Response = null;
            ArrayList<KeysInfo> params = new ArrayList<KeysInfo>();
            SharedPreferences settings = settings =  PreferenceManager.getDefaultSharedPreferences(Mail);
            params.add(new KeysInfo("keyid", settings.getString("keyid", null)));
            params.add(new KeysInfo("vCode", settings.getString("vCode", null)));
            params.add(new KeysInfo("characterID", Characters.get(i).getCharacterID()));

            Response = new CallApi.asyncClass().ApiGetCall(params, Mail.getString(string.MailMessHeaders));
            try
            {
                XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
                ParseCharacterMailHeaders Handler = new ParseCharacterMailHeaders();
                reader.setContentHandler(Handler);
                InputSource inputSource = new InputSource();
                inputSource.setEncoding("UTF-8");
                inputSource.setCharacterStream(new StringReader(Response));
                reader.parse(inputSource);
                Characters.get(i).setMail(Handler.data);
            }
            catch(Exception e){}
        }

        public void CharacterSheet(int i)
        {
            String Response = null;
            ArrayList<KeysInfo> params = new ArrayList<KeysInfo>();
            SharedPreferences settings = settings =  PreferenceManager.getDefaultSharedPreferences(Main);
            params.add(new KeysInfo("keyid", settings.getString("keyid", null)));
            params.add(new KeysInfo("vCode", settings.getString("vCode", null)));
            params.add(new KeysInfo("characterID", Characters.get(i).getCharacterID()));

            Response = new CallApi.asyncClass().ApiGetCall(params, Main.getString(R.string.Character_Sheet));
            try
            {
                XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
                ParseCharacterSheet Handler = new ParseCharacterSheet();
                reader.setContentHandler(Handler);
                InputSource inputSource = new InputSource();
                inputSource.setEncoding("UTF-8");
                inputSource.setCharacterStream(new StringReader(Response));
                reader.parse(inputSource);
                Characters.get(i).CombineSheet(Handler.data);
                Characters.get(i).setSkills(Handler.Skills);
            }
            catch(Exception e){}
        }

        public void CharacterStandings(int i)
        {
            String Response = null;
            ArrayList<KeysInfo> params = new ArrayList<KeysInfo>();
            SharedPreferences settings = settings =  PreferenceManager.getDefaultSharedPreferences(Main);
            params.add(new KeysInfo("keyid", settings.getString("keyid", null)));
            params.add(new KeysInfo("vCode", settings.getString("vCode", null)));
            params.add(new KeysInfo("characterID", Characters.get(i).getCharacterID()));

            Response = new CallApi.asyncClass().ApiGetCall(params, Main.getString(string.NPC_Standings));
            try
            {
                XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
                ParseCharacterStandings Handler = new ParseCharacterStandings();
                reader.setContentHandler(Handler);
                InputSource inputSource = new InputSource();
                inputSource.setEncoding("UTF-8");
                inputSource.setCharacterStream(new StringReader(Response));
                reader.parse(inputSource);
                Characters.get(i).setagentStandings(Handler.agent);
                Characters.get(i).setNPCStandings(Handler.NPC);
                Characters.get(i).setfactionStandings(Handler.faction);
            }
            catch(Exception e){}
        }
    }

    class ParseCharacterList extends DefaultHandler
    {
        public ArrayList<CharacterInfo> data = new ArrayList<CharacterInfo>();

        @Override
        public void startElement(String uri, String localName, String qName, org.xml.sax.Attributes attributes) throws SAXException
        {
            if (localName.equals("row"))
            {
                CharacterInfo charinfo = new CharacterInfo();
                charinfo.setName(attributes.getValue("name"));
                charinfo.setCharacterID(attributes.getValue("characterID"));
                charinfo.setCorporationName(attributes.getValue("corporationName"));
                charinfo.setCorporationID(attributes.getValue("corporationID"));
                data.add(charinfo);
            }
        }
    }

    class ParseCharacterSheet extends DefaultHandler
    {
        private boolean isDoB = false;
        private boolean isRace = false;
        private boolean isbloodLine = false;
        private boolean isancestry = false;
        private boolean isgender = false;
        private boolean isallianceName = false;
        private boolean isallianceID = false;
        private boolean iscloneName = false;
        private boolean iscloneSkillPoints = false;
        private boolean isbalance = false;
        private boolean isintelligence = false;
        private boolean ismemory = false;
        private boolean ischarisma = false;
        private boolean isperception = false;
        private boolean iswillpower = false;
        private CharacterInfo data = new CharacterInfo();
        private ArrayList<SkillInfo> Skills = new ArrayList<SkillInfo>();

        @Override
        public void startElement(String uri, String localName, String qName, org.xml.sax.Attributes attributes) throws SAXException {
            if (localName.equals("DoB"))
                isDoB = true;
            else if (localName.equals("race"))
                isRace = true;
            else if (localName.equals("bloodLine"))
                isbloodLine = true;
            else if (localName.equals("ancestry"))
                isancestry = true;
            else if (localName.equals("gender"))
                isgender = true;
            else if (localName.equals("allianceName"))
                isallianceName = true;
            else if (localName.equals("allianceID"))
                isallianceID = true;
            else if (localName.equals("cloneName"))
                iscloneName = true;
            else if (localName.equals("cloneSkillPoints"))
                iscloneSkillPoints = true;
            else if (localName.equals("balance"))
                isbalance = true;
            else if (localName.equals("intelligence"))
                isintelligence = true;
            else if (localName.equals("memory"))
                ismemory = true;
            else if (localName.equals("charisma"))
                ischarisma = true;
            else if (localName.equals("perception"))
                isperception = true;
            else if (localName.equals("willpower"))
                iswillpower = true;
            else if (localName.equals("row")){
                String attribute1 = attributes.getValue("typeID");
                String attribute2 = attributes.getValue("skillpoints");
                String attribute3 = attributes.getValue("level");
                String attribute4 = attributes.getValue("published");
                if (attribute1 != null && attribute2 != null && attribute3 != null && attribute4 != null)
                {
                    SkillInfo Skill = new SkillInfo();
                    Skill.setTypeID(attribute1);
                    Skill.setSkillPoints(attribute2);
                    Skill.setLevel(attribute3);
                    Skill.setPublished(attribute4);
                    Skills.add(Skill);
                }
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException
        {
            if (localName.equals("DoB"))
                isDoB = false;
            else if (localName.equals("race"))
                isRace = false;
            else if (localName.equals("bloodLine"))
                isbloodLine = false;
            else if (localName.equals("ancestry"))
                isancestry = false;
            else if (localName.equals("gender"))
                isgender = false;
            else if (localName.equals("allianceName"))
                isallianceName = false;
            else if (localName.equals("allianceID"))
                isallianceID = false;
            else if (localName.equals("cloneName"))
                iscloneName = false;
            else if (localName.equals("cloneSkillPoints"))
                iscloneSkillPoints = false;
            else if (localName.equals("balance"))
                isbalance = false;
            else if (localName.equals("intelligence"))
                isintelligence = false;
            else if (localName.equals("memory"))
                ismemory = false;
            else if (localName.equals("charisma"))
                ischarisma = false;
            else if (localName.equals("perception"))
                isperception = false;
            else if (localName.equals("willpower"))
                iswillpower = false;
        }

        @Override
        public void characters(char ch[], int start, int length)
        {
            String chars = new String(ch, start, length);
            if (isDoB) data.setDateOfBirth(chars);
            else if(isRace) data.setRace(chars);
            else if(isbloodLine) data.setBloodLine(chars);
            else if(isancestry) data.setAncestry(chars);
            else if(isgender) data.setGender(chars);
            else if(isallianceName) data.setAllianceName(chars);
            else if(isallianceID) data.setAllianceID(chars);
            else if(iscloneName) data.setCloneName(chars);
            else if(iscloneSkillPoints) data.setCloneSkillPoints(chars);
            else if(isbalance) data.setWalletBalance(chars);
            else if(isintelligence) data.setIntelligence(chars);
            else if(ismemory) data.setMemory(chars);
            else if(ischarisma) data.setCharisma(chars);
            else if(isperception) data.setPerception(chars);
            else if(iswillpower) data.setWillpower(chars);
        }
    }

    class ParseCharacterStandings extends DefaultHandler
    {
        private boolean agentsdesc = false;
        private boolean npcdesc = false;
        private boolean factiondesc = false;
        public ArrayList<StandingInfo> agent = new ArrayList<StandingInfo>();
        public ArrayList<StandingInfo> NPC = new ArrayList<StandingInfo>();
        public ArrayList<StandingInfo> faction = new ArrayList<StandingInfo>();

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
        {
            if(localName.equals("rowset") && attributes.getValue("name").equals("agents"))
                agentsdesc = true;
            else if(localName.equals("rowset") && attributes.getValue("name").equals("NPCCorporations"))
                npcdesc = true;
            else if(localName.equals("rowset") && attributes.getValue("name").equals("factions"))
                factiondesc = true;
            else if(agentsdesc == true)
            {
                StandingInfo standing = new StandingInfo();
                standing.setfromID(attributes.getValue("fromID"));
                standing.setfromName(attributes.getValue("fromName"));
                standing.setStranding(attributes.getValue("standing"));
                agent.add(standing);
            }
            else if(npcdesc == true)
            {
                StandingInfo standing = new StandingInfo();
                standing.setfromID(attributes.getValue("fromID"));
                standing.setfromName(attributes.getValue("fromName"));
                standing.setStranding(attributes.getValue("standing"));
                NPC.add(standing);
            }
            else if(factiondesc == true)
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

    class ParseCharacterInfo extends DefaultHandler
    {
        private boolean isRace = false;
        private boolean isbloodLine = false;
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
        private boolean isnextTrainingEnds = false;
        private boolean isCharacterName = false;
        private CharacterInfo data = new CharacterInfo();

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
        {
            if (localName.equals("race"))
                isRace = true;
            else if (localName.equals("characterName"))
                isCharacterName = true;
            else if (localName.equals("bloodLine"))
                isbloodLine = true;
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
            else if (localName.equals("nextTrainingEnds"))
                isnextTrainingEnds = true;
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException
        {
            if (localName.equals("race"))
                isRace = false;
            else if (localName.equals("characterName"))
                isCharacterName = false;
            else if (localName.equals("bloodLine"))
                isbloodLine = false;
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
            else if (localName.equals("nextTrainingEnds"))
                isnextTrainingEnds = false;
        }

        @Override
        public void characters(char ch[], int start, int length)
        {
            String chars = new String(ch, start, length);
            if(isRace) data.setRace(chars);
            else if(isCharacterName) data.setName(chars);
            else if(isbloodLine) data.setBloodLine(chars);
            else if(isaccountBalance) data.setWalletBalance(chars);
            else if(isskillPoints) data.setSkillPoints(chars);
            else if(isalliance) data.setAllianceName(chars);
            else if(isallianceID) data.setAllianceID(chars);
            else if(isallianceDate) data.setAllianceDate(chars);
            else if(islastKnownLocation) data.setLastKnownLocation(chars);
            else if(issecurityStatus) data.setSecStatus(chars);
            else if(isshipName) data.setShipName(chars);
            else if(isshipTypeID) data.setShipTypeID(chars);
            else if(isshipTypeName) data.setShipTypeName(chars);
            else if(iscorporationID) data.setCorporationID(chars);
            else if(iscorporation) data.setCorporationName(chars);
            else if(iscorporationDate) data.setCorporationDate(chars);
            else if(isnextTrainingEnds) data.setnextTrainingEnds(chars);
        }
    }

    class ParseCharacterMailHeaders extends DefaultHandler
    {
        public ArrayList<MailInfo> data = new ArrayList<MailInfo>();

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

    class asyncClass
    {
        public byte[] ApiImageCall(String uri, String toonID, String size)
        {
            byte[] responseArray = new byte[Integer.parseInt(size)];

            try
            {
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet httpget;

                if (uri.equals("http://image.eveonline.com/Character/") == true)
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

        public String ApiPostCall(ArrayList<KeysInfo> Params, String pathurl)
        {
            String responseString = null;

            try
            {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("https://api.eveonline.com" + pathurl);

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(Params.size());
                for (int i = 0; i < Params.size(); i++)
                    nameValuePairs.add(new BasicNameValuePair(Params.get(i).getKeyName(), Params.get(i).getKeyValue()));

                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                responseString = EntityUtils.toString(entity);
            }
            catch(Exception e){}

            return responseString;
        }

        public String ApiGetCall(ArrayList<KeysInfo> Params, String pathurl)
        {
            String responseString = null;

            try
            {
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet httpget = new HttpGet(CreateUri("https://api.eveonline.com" + pathurl, Params));

                HttpResponse response = httpclient.execute(httpget);
                HttpEntity entity = response.getEntity();
                if (entity.getContentType().getValue().equals("text/html; charset=us-ascii") != true)
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

    protected void onPostExecute(ArrayList<CharacterInfo> result)
    {
        if(Main != null)
            Main.ApiResponse(result);
        else if(Mail != null)
            Mail.ApiResponse(result);
    }
}