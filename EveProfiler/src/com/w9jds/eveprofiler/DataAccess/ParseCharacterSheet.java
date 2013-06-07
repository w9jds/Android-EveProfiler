package com.w9jds.eveprofiler.DataAccess;

import com.w9jds.eveprofiler.Objects.SkillInfo;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.ArrayList;

/**
 * Created by Jeremy on 6/6/13.
 */
class ParseCharacterSheet extends DefaultHandler
{
//    private boolean isDoB = false;
//    private boolean isRace = false;
//    private boolean isbloodLine = false;
//    private boolean isancestry = false;
//    private boolean isgender = false;
//    private boolean isallianceName = false;
//    private boolean isallianceID = false;
//    private boolean iscloneName = false;
//    private boolean iscloneSkillPoints = false;
//    private boolean isbalance = false;
//    private boolean isintelligence = false;
//    private boolean ismemory = false;
//    private boolean ischarisma = false;
//    private boolean isperception = false;
//    private boolean iswillpower = false;
//    private final ArrayList<SkillInfo> Skills = new ArrayList<SkillInfo>();
//
//    @Override
//    public void startElement(String uri, String localName, String qName, org.xml.sax.Attributes attributes) throws SAXException {
//        if (localName.equals("DoB"))
//            isDoB = true;
//        else if (localName.equals("race"))
//            isRace = true;
//        else if (localName.equals("bloodLine"))
//            isbloodLine = true;
//        else if (localName.equals("ancestry"))
//            isancestry = true;
//        else if (localName.equals("gender"))
//            isgender = true;
//        else if (localName.equals("allianceName"))
//            isallianceName = true;
//        else if (localName.equals("allianceID"))
//            isallianceID = true;
//        else if (localName.equals("cloneName"))
//            iscloneName = true;
//        else if (localName.equals("cloneSkillPoints"))
//            iscloneSkillPoints = true;
//        else if (localName.equals("balance"))
//            isbalance = true;
//        else if (localName.equals("intelligence"))
//            isintelligence = true;
//        else if (localName.equals("memory"))
//            ismemory = true;
//        else if (localName.equals("charisma"))
//            ischarisma = true;
//        else if (localName.equals("perception"))
//            isperception = true;
//        else if (localName.equals("willpower"))
//            iswillpower = true;
//        else if (localName.equals("row")){
//            String attribute1 = attributes.getValue("typeID");
//            String attribute2 = attributes.getValue("skillpoints");
//            String attribute3 = attributes.getValue("level");
//            String attribute4 = attributes.getValue("published");
//            if (attribute1 != null && attribute2 != null && attribute3 != null && attribute4 != null)
//            {
//                SkillInfo Skill = new SkillInfo();
//                Skill.setTypeID(attribute1);
//                Skill.setSkillPoints(attribute2);
//                Skill.setLevel(attribute3);
//                Skill.setPublished(attribute4);
//                Skills.add(Skill);
//            }
//        }
//    }
//
//    @Override
//    public void endElement(String uri, String localName, String qName) throws SAXException
//    {
//        if (localName.equals("DoB"))
//            isDoB = false;
//        else if (localName.equals("race"))
//            isRace = false;
//        else if (localName.equals("bloodLine"))
//            isbloodLine = false;
//        else if (localName.equals("ancestry"))
//            isancestry = false;
//        else if (localName.equals("gender"))
//            isgender = false;
//        else if (localName.equals("allianceName"))
//            isallianceName = false;
//        else if (localName.equals("allianceID"))
//            isallianceID = false;
//        else if (localName.equals("cloneName"))
//            iscloneName = false;
//        else if (localName.equals("cloneSkillPoints"))
//            iscloneSkillPoints = false;
//        else if (localName.equals("balance"))
//            isbalance = false;
//        else if (localName.equals("intelligence"))
//            isintelligence = false;
//        else if (localName.equals("memory"))
//            ismemory = false;
//        else if (localName.equals("charisma"))
//            ischarisma = false;
//        else if (localName.equals("perception"))
//            isperception = false;
//        else if (localName.equals("willpower"))
//            iswillpower = false;
//    }
//
//    @Override
//    public void characters(char ch[], int start, int length)
//    {
//        String chars = new String(ch, start, length);
//        if (isDoB) data.setDateOfBirth(chars);
//        else if(isRace) data.setRace(chars);
//        else if(isbloodLine) data.setBloodLine(chars);
//        else if(isancestry) data.setAncestry(chars);
//        else if(isgender) data.setGender(chars);
//        else if(isallianceName) data.setAllianceName(chars);
//        else if(isallianceID) data.setAllianceID(chars);
//        else if(iscloneName) data.setCloneName(chars);
//        else if(iscloneSkillPoints) data.setCloneSkillPoints(chars);
//        else if(isbalance) data.setWalletBalance(chars);
//        else if(isintelligence) data.setIntelligence(chars);
//        else if(ismemory) data.setMemory(chars);
//        else if(ischarisma) data.setCharisma(chars);
//        else if(isperception) data.setPerception(chars);
//        else if(iswillpower) data.setWillpower(chars);
//    }
}
