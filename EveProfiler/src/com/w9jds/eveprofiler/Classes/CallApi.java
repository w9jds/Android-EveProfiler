package com.w9jds.eveprofiler.Classes;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
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
import com.w9jds.eveprofiler.Activities.MainActivity;
import com.w9jds.eveprofiler.R.string;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

public class CallApi extends AsyncTask<ArrayList<Object>, Void, ArrayList<CharacterInfo>>{
		
	MainActivity Main = new MainActivity();
	ArrayList<CharacterInfo> Characters = new ArrayList<CharacterInfo>();
    ArrayList<String> params = new ArrayList<String>();
    ArrayList<String> keys = new ArrayList<String>();
    String Response = null;
	
	protected ArrayList<CharacterInfo> doInBackground(ArrayList<Object>... Info)
	{
		Main = (MainActivity)Info[0].get(1);
		
		if (Info[0].get(0) == "getCharacters")
		{
            CallMethods Calls = new CallMethods();
            Calls.CharactersList();

			for (int i = 0; i < Characters.size(); i++)
			{
                Calls.CharacterInfo(i);
                Calls.CharacterPortrait(i, "128");
                Calls.CorporationPortrait(i, "64");
                if(Characters.get(i).getAllianceID() != null)
                    Calls.AlliancePortrait(i, "64");

			}
		}

		return Characters;
	}

    class CallMethods
    {
        public void CharactersList()
        {
            params.clear();
            params.add(Main.getString(R.string.List_Characters));
            keys.clear();
            Response = new CallApi.asyncClass().ApiCall(params, keys, Main);

            try
            {
                SAXParserFactory spf = SAXParserFactory.newInstance();
                SAXParser sp = spf.newSAXParser();
                XMLReader xr = sp.getXMLReader();
                ParseCharacterList Handler = new ParseCharacterList();
                xr.setContentHandler(Handler);
                InputSource inputSource = new InputSource();
                inputSource.setEncoding("UTF-8");
                inputSource.setCharacterStream(new StringReader(Response));
                xr.parse(inputSource);
                Characters = Handler.data;
            }
            catch(Exception e){}
        }

        public void CharacterInfo(int i)
        {
            params.clear();
            params.add(Main.getString(R.string.Character_Info));
            keys.clear();
            keys.add(Characters.get(i).getCharacterID());
            Response = new CallApi.asyncClass().ApiCall(params, keys, Main);

            try
            {
                SAXParserFactory spf = SAXParserFactory.newInstance();
                SAXParser sp = spf.newSAXParser();
                XMLReader xr = sp.getXMLReader();
                ParseCharacterInfo Handler = new ParseCharacterInfo();
                xr.setContentHandler(Handler);
                InputSource inputSource = new InputSource();
                inputSource.setEncoding("UTF-8");
                inputSource.setCharacterStream(new StringReader(Response));
                xr.parse(inputSource);
                Characters.get(i).CombineSheet(Handler.data);
            }
            catch(Exception e){}
        }

        public void CharacterPortrait(int i, String size)
        {
            asyncClass portrait = new asyncClass();
            Characters.get(i).setCharacterPortrait(portrait.ApiImageCall(Main.getString(R.string.Character_Portrait), Characters.get(i).getCharacterID(), size));
        }

        public void AlliancePortrait(int i, String size)
        {
            asyncClass portrait = new asyncClass();
            Characters.get(i).setAlliancePortrait(portrait.ApiImageCall(Main.getString(R.string.Alliance_Logo), Characters.get(i).getAllianceID(), size));
        }

        public void CorporationPortrait(int i, String size)
        {
            asyncClass portrait = new asyncClass();
            Characters.get(i).setCorporationPortrait(portrait.ApiImageCall(Main.getString(R.string.Corp_Logo), Characters.get(i).getCorporationID(), size));
        }

        public void CharacterSheet(int i)
        {
            params.clear();
            params.add(Main.getString(R.string.Character_Sheet));
            keys.clear();
            keys.add(Characters.get(i).getCharacterID());
            Response = new CallApi.asyncClass().ApiCall(params, keys, Main);
            try
            {
                SAXParserFactory spf = SAXParserFactory.newInstance();
                SAXParser sp = spf.newSAXParser();
                XMLReader xr = sp.getXMLReader();
                ParseCharacterSheet Handler = new ParseCharacterSheet();
                xr.setContentHandler(Handler);
                InputSource inputSource = new InputSource();
                inputSource.setEncoding("UTF-8");
                inputSource.setCharacterStream(new StringReader(Response));
                xr.parse(inputSource);
                Characters.get(i).CombineSheet(Handler.data);
                Characters.get(i).setSkills(Handler.Skills);
                Characters.get(i).setSkillPoints(Double.toString(Handler.totalSP));
            }
            catch(Exception e){}
        }

        public void CharacterStandings(int i)
        {
            params.clear();
            params.add(Main.getString(string.NPC_Standings));
            keys.clear();
            keys.add(Characters.get(i).getCharacterID());
            Response = new CallApi.asyncClass().ApiCall(params, keys, Main);
            try
            {
                SAXParserFactory spf = SAXParserFactory.newInstance();
                SAXParser sp = spf.newSAXParser();
                XMLReader xr = sp.getXMLReader();
                ParseCharacterStandings Handler = new ParseCharacterStandings();
                xr.setContentHandler(Handler);
                InputSource inputSource = new InputSource();
                inputSource.setEncoding("UTF-8");
                inputSource.setCharacterStream(new StringReader(Response));
                xr.parse(inputSource);
                Characters.get(i).setagentStandings(Handler.agent);
                Characters.get(i).setNPCStandings(Handler.NPC);
                Characters.get(i).setfactionStandings(Handler.faction);
                Characters.get(i).setSecStatus(Handler.SecurityStatus);
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
	        	data.add(new CharacterInfo());
	        	String attributeValue = attributes.getValue("name");
	        	data.get(data.size()-1).setName(attributeValue);
	        	attributeValue = attributes.getValue("characterID");
	        	data.get(data.size()-1).setCharacterID(attributeValue);
	        	attributeValue = attributes.getValue("corporationName");
	        	data.get(data.size()-1).setCorporationName(attributeValue);
	        	attributeValue = attributes.getValue("corporationID");
	        	data.get(data.size()-1).setCorporationID(attributeValue);
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
        private double totalSP = 0;
	    
		public CharacterInfo getParse(){ return data; }
		
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
                    totalSP += Double.parseDouble(attribute2);
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
        public String SecurityStatus = null;

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
                agent.add(new StandingInfo());
                String attributeValue = attributes.getValue("fromID");
                agent.get(agent.size()-1).setfromID(attributeValue);
                attributeValue = attributes.getValue("fromName");
                agent.get(agent.size()-1).setfromName(attributeValue);
                attributeValue = attributes.getValue("standing");
                agent.get(agent.size()-1).setStranding(attributeValue);
            }
            else if(npcdesc == true)
            {
                NPC.add(new StandingInfo());
                String attributeValue = attributes.getValue("fromID");
                NPC.get(NPC.size()-1).setfromID(attributeValue);
                attributeValue = attributes.getValue("fromName");
                NPC.get(NPC.size()-1).setfromName(attributeValue);
                attributeValue = attributes.getValue("standing");
                if(attributes.getValue("fromName").equals("CONCORD"))
                    SecurityStatus = attributeValue;
                NPC.get(NPC.size()-1).setStranding(attributeValue);
            }
            else if(factiondesc == true)
            {
                faction.add(new StandingInfo());
                String attributeValue = attributes.getValue("fromID");
                faction.get(faction.size()-1).setfromID(attributeValue);
                attributeValue = attributes.getValue("fromName");
                faction.get(faction.size()-1).setfromName(attributeValue);
                attributeValue = attributes.getValue("standing");
                faction.get(faction.size()-1).setStranding(attributeValue);
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
        private CharacterInfo data = new CharacterInfo();

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
        {
            if (localName.equals("race"))
                isRace = true;
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

	class asyncClass
    {
		public byte[] ApiImageCall(String uri, String toonID, String size)
		{
			byte[] responseArray = new byte[Integer.parseInt(size)];
			
			try {
				// Create a new HttpClient and Get Header
				HttpClient httpclient = new DefaultHttpClient();
				HttpGet httpget = new HttpGet();
				
				if (uri.equals("http://image.eveonline.com/Character/") == true){
					httpget = new HttpGet(uri + toonID + "_" + size + ".jpg");
				}
				else{
					httpget = new HttpGet(uri + toonID + "_" + size + ".png");
				}
				
			    // Execute HTTP Get Request
			    HttpResponse response = httpclient.execute(httpget);
			    HttpEntity entity = response.getEntity();
			    responseArray = EntityUtils.toByteArray(entity);

			} 
			catch (Exception e){ System.out.println(e); }
				
			return responseArray;
		
		}

		public String ApiCall(ArrayList<String> Params, ArrayList<String> Keys, MainActivity Main)
        {
			String responseString = "";
			
			try {
					
				// Create a new HttpClient and Post Header
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost();
				
				switch(Params.size())
				{
	//				case 1:
	//					httppost = new HttpPost();
	//					break;
					case 1:
						httppost = new HttpPost(Main.getString(R.string.Api_Uri) + Params.get(0));
						break;
					case 2:
						httppost = new HttpPost(Main.getString(R.string.Api_Uri) + Params.get(0) + Params.get(0));
						break;
				}
				
			    // Add your data
			    List<NameValuePair> nameValuePairs;
			    //pull api keys
			    SharedPreferences settings =  PreferenceManager.getDefaultSharedPreferences(Main);
		        String keyid = settings.getString("keyid", null);
		        String vCode = settings.getString("vCode", null);
			    
			    switch(Keys.size())
			    {
	//			    case 2:
	//		    		nameValuePairs = new ArrayList<NameValuePair>(1);
	//		    		nameValuePairs.add(new BasicNameValuePair(ApiInfo.get(1).get(0).toString(), ApiInfo.get(1).get(1).toString()));
	//		    		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	//		    		break;
			    
			    	case 0:
			    		nameValuePairs = new ArrayList<NameValuePair>(2);
			    		nameValuePairs.add(new BasicNameValuePair("keyID", keyid));
			    		nameValuePairs.add(new BasicNameValuePair("vCode", vCode));
			    		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			    		break;
			    		
			    	case 1:
			    		nameValuePairs = new ArrayList<NameValuePair>(3);
			    		nameValuePairs.add(new BasicNameValuePair("keyID", keyid));
			    		nameValuePairs.add(new BasicNameValuePair("vCode", vCode));
			    		nameValuePairs.add(new BasicNameValuePair("characterID", Keys.get(0)));
			    		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			    }
	
			    		    // Execute HTTP Post Request
			    HttpResponse response = httpclient.execute(httppost);
			    HttpEntity entity = response.getEntity();
			    responseString = EntityUtils.toString(entity);
			} 
			catch (Exception e){ System.out.println(e); }
	
		    return responseString;
		}
	}
	
	protected void onPostExecute(ArrayList<CharacterInfo> result)
	{
		Main.ApiResponse(result);
	}
}