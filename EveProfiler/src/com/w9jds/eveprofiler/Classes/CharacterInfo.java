package com.w9jds.eveprofiler.Classes;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;


import android.content.ClipData.Item;

public class CharacterInfo implements Serializable {
	
	private String name;
	private String characterID;
	private String corporationName;
    private String corporationDate;
	private String corporationID;
	private String allianceName;
    private String allianceDate;
	private String allianceID;
	private String DateOfBirth;
	private String Race;
	private String BloodLine;
	private String Ancestry;
	private String Gender;
	private String CloneName;
	private String CloneSkillPoints;
	private String WalletBalance;
	private String Intelligence;
	private String Memory;
	private String Charisma;
	private String Perception;
	private String Willpower;
    private String LastKnownLocation;
    private String shipName;
    private String shipTypeID;
    private String shipTypeName;
    private String SecStatus;
	private ArrayList<SkillInfo> Skills;
    private String SkillPoints;
    private ArrayList<StandingInfo> agentStandings;
    private ArrayList<StandingInfo> NPCStandings;
    private ArrayList<StandingInfo> factionStandings;
    private String nextTrainingEnds;
	private byte[] CharacterPortrait;
	private byte[] corporationPortrait;
	private byte[] alliancePortrait;
			
	public void CombineSheet(CharacterInfo Info2) {


		if((this.name == null || this.name != Info2.getName()) && Info2.name != null)
			this.setName(Info2.getName());
		if((this.characterID == null || this.characterID != Info2.getCharacterID()) && Info2.getCharacterID() != null)
			this.setCharacterID(Info2.getCharacterID());
		if((this.corporationName == null || this.corporationName != Info2.getCorporationName()) && Info2.getCorporationName() != null)
			this.setCorporationName(Info2.getCorporationName());
        if((this.corporationDate == null || this.corporationDate != Info2.getCorporationDate()) && Info2.getCorporationDate() != null)
            this.setCorporationDate(Info2.getCorporationDate());
		if((this.corporationID == null || this.corporationID != Info2.getCorporationID()) && Info2.getCorporationID() != null)
			this.setCorporationID(Info2.getCorporationID());
		if((this.allianceName == null || this.allianceName != Info2.getAllianceName()) && Info2.getAllianceName() != null)
			this.setAllianceName(Info2.getAllianceName());
        if((this.allianceDate == null || this.allianceDate != Info2.getAllianceDate()) && Info2.getAllianceDate() != null)
            this.setAllianceDate(Info2.getAllianceDate());
		if((this.allianceID == null || this.allianceID != Info2.getAllianceID()) && Info2.getAllianceID() != null)
			this.setAllianceID(Info2.getAllianceID());
		if((this.DateOfBirth == null || this.DateOfBirth != Info2.getDateOfBirth()) && Info2.getDateOfBirth() != null)
			this.setDateOfBirth(Info2.getDateOfBirth());
		if((this.Race == null || this.Race != Info2.getRace()) && Info2.getRace() != null)
			this.setRace(Info2.getRace());
		if((this.BloodLine == null || this.BloodLine != Info2.getBloodLine()) && Info2.getBloodLine() != null)
			this.setBloodLine(Info2.getBloodLine());
		if((this.Ancestry == null || this.Ancestry != Info2.getAncestry()) && Info2.getAncestry() != null)
			this.setAncestry(Info2.getAncestry());
		if((this.Gender == null || this.Gender != Info2.getGender()) && Info2.getGender() != null)
			this.setGender(Info2.getGender());
		if((this.CloneName == null || this.CloneName != Info2.getCloneName()) && Info2.getCloneName() != null)
			this.setCloneName(Info2.getCloneName());
		if((this.CloneSkillPoints == null || this.CloneSkillPoints != Info2.getCloneSkillPoints()) && Info2.getCloneSkillPoints() != null)
			this.setCloneSkillPoints(Info2.getCloneSkillPoints());
		if((this.WalletBalance == null || this.WalletBalance != Info2.getWalletBalance()) && Info2.getWalletBalance() != null)
			this.WalletBalance = Info2.getWalletBalance();
		if((this.Intelligence == null || this.Intelligence != Info2.getIntelligence()) && Info2.getIntelligence() != null)
			this.setIntelligence(Info2.getIntelligence());
		if((this.Memory == null || this.Memory != Info2.getMemory()) && Info2.getMemory() != null)
			this.setMemory(Info2.getMemory());
		if((this.Charisma == null || this.Charisma != Info2.getCharisma()) && Info2.getCharisma() != null)
			this.setCharisma(Info2.getCharisma());
		if((this.Perception == null || this.Perception != Info2.getPerception()) && Info2.getPerception() != null)
			this.setPerception(Info2.getPerception());
		if((this.Willpower == null || this.Willpower != Info2.getWillpower()) && Info2.getWillpower() != null)
			this.setWillpower(Info2.getWillpower());
		if((this.Skills == null || this.Skills != Info2.getSkills()) && Info2.getSkills() != null)
			this.setSkills(Info2.getSkills());
		if((this.CharacterPortrait == null || this.CharacterPortrait != Info2.getCharacterPortrait()) && Info2.getCharacterPortrait() != null)
			this.setCharacterPortrait(Info2.getCharacterPortrait());
		if((this.corporationPortrait == null || this.corporationPortrait != Info2.getCorporationPortrait()) && Info2.getCorporationPortrait() != null)
			this.setCorporationPortrait(Info2.getCorporationPortrait());
		if((this.alliancePortrait == null || this.alliancePortrait != Info2.getAlliancePortrait()) && Info2.getAlliancePortrait() != null)
			this.setAlliancePortrait(Info2.getAlliancePortrait());
        if((this.SecStatus == null || this.SecStatus != Info2.getSecStatus()) && Info2.getSecStatus() != null)
            this.SecStatus = Info2.getSecStatus();
        if((this.agentStandings == null || this.agentStandings != Info2.getagentStandings()) && Info2.getagentStandings() != null)
            this.setagentStandings(Info2.getagentStandings());
        if((this.SkillPoints == null || this.SkillPoints != Info2.getSkillPoints()) && Info2.getSkillPoints() != null)
            this.SkillPoints = Info2.getSkillPoints();
        if((this.factionStandings == null || this.factionStandings != Info2.getfactionStandings()) && Info2.getSkillPoints() != null)
            this.setfactionStandings(Info2.getfactionStandings());
        if((this.NPCStandings == null || this.NPCStandings != Info2.getNPCStandings()) && Info2.getNPCStandings() != null)
            this.setNPCStandings(Info2.getNPCStandings());
        if((this.shipTypeName == null || this.shipTypeName != Info2.getShipTypeName()) && Info2.getShipTypeName() != null)
            this.setShipTypeName(Info2.getShipTypeName());
        if((this.shipTypeID == null || this.shipTypeID != Info2.getshipTypeID()) && Info2.getshipTypeID() != null)
            this.setShipTypeID(Info2.getshipTypeID());
        if((this.shipName == null || this.shipName != Info2.getShipName()) && Info2.getShipName() != null)
            this.setShipName(Info2.getShipName());
        if((this.LastKnownLocation == null || this.LastKnownLocation != Info2.getLastKnownLocation()) && Info2.getLastKnownLocation() != null)
            this.setLastKnownLocation(Info2.getLastKnownLocation());
        if((this.nextTrainingEnds == null || this.nextTrainingEnds != Info2.getnextTrainingEnds()) || Info2.nextTrainingEnds != null)
            this.setnextTrainingEnds(Info2.getnextTrainingEnds());
	}

    public String getnextTrainingEnds() { return nextTrainingEnds; }
    public void setnextTrainingEnds(String nextTrainingEnds) { this.nextTrainingEnds = nextTrainingEnds; }

    public String getAllianceDate() { return allianceDate; }
    public void setAllianceDate(String allianceDate) { this.allianceDate = allianceDate; }

    public String getCorporationDate() { return corporationDate; }
    public void setCorporationDate(String corporationDate) { this.corporationDate = corporationDate; }

    public String getSkillPoints() { return SkillPoints; }
    public void setSkillPoints(double SkillPoints)
    {
        DecimalFormat formatter = new DecimalFormat("#,###");
        this.SkillPoints = formatter.format(SkillPoints);
    }

    public String getLastKnownLocation() { return this.LastKnownLocation; }
    public void setLastKnownLocation(String LastKnownLocation) { this.LastKnownLocation = LastKnownLocation; }

    public String getShipName() { return this.shipName; }
    public void setShipName(String shipName) { this.shipName = shipName; }

    public String getshipTypeID() { return this.shipTypeID; }
    public void setShipTypeID(String shipTypeID) { this.shipTypeID = shipTypeID; }

    public String getShipTypeName() { return this.shipTypeName; }
    public void setShipTypeName(String shipTypeName) { this.shipTypeName = shipTypeName; }

    public ArrayList<StandingInfo> getNPCStandings() { return NPCStandings; }
    public void setNPCStandings(ArrayList<StandingInfo> NPCStandings) { this.NPCStandings = NPCStandings; }

    public ArrayList<StandingInfo> getfactionStandings() { return factionStandings; }
    public void setfactionStandings(ArrayList<StandingInfo> factionStandings) { this.factionStandings = factionStandings; }

    public String getSecStatus() { return SecStatus; }
    public void setSecStatus(String SecStatus)
    {
        DecimalFormat Formatter = new DecimalFormat("#.###");
        this.SecStatus = Formatter.format(Double.parseDouble(SecStatus));
    }

    public ArrayList<StandingInfo> getagentStandings() { return agentStandings; }
    public void setagentStandings(ArrayList<StandingInfo> agentStandings) { this.agentStandings = agentStandings; }

	public byte[] getAlliancePortrait() { return alliancePortrait; }
	public void setAlliancePortrait(byte[] alliancePortrait) { this.alliancePortrait = alliancePortrait; }
	
	public byte[] getCorporationPortrait() { return corporationPortrait; }
	public void setCorporationPortrait(byte[] corporationPortrait) { this.corporationPortrait = corporationPortrait; }
	
	public byte[] getCharacterPortrait() { return CharacterPortrait; }
	public void setCharacterPortrait(byte[] CharacterPortrait) { this.CharacterPortrait = CharacterPortrait; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public String getCharacterID() { return characterID; }
	public void setCharacterID(String characterID) { this.characterID = characterID; }
	
	public String getCorporationName() { return corporationName; }
	public void setCorporationName(String corporationName) { this.corporationName = corporationName; } 
	
	public String getCorporationID() { return corporationID; }
	public void setCorporationID(String corporationID) { this.corporationID = corporationID; }
	
	public String getDateOfBirth() { return DateOfBirth; }
	public void setDateOfBirth(String DateOfBirth) { this.DateOfBirth = DateOfBirth; }
	
	public String getRace() { return Race; }
	public void setRace(String Race) { this.Race = Race; }
	
	public String getBloodLine() { return BloodLine; }
	public void setBloodLine(String BloodLine) { this.BloodLine = BloodLine; }
	
	public String getAncestry() { return Ancestry; }
	public void setAncestry(String Ancestry) { this.Ancestry = Ancestry; }
	
	public String getGender() { return Gender; }
	public void setGender(String Gender) { this.Gender = Gender; }
	
	public String getCloneName() { return CloneName; }
	public void setCloneName(String CloneName) { this.CloneName = CloneName; }
	
	public String getCloneSkillPoints() { return CloneSkillPoints; }
	public void setCloneSkillPoints(String CloneSkillPoints) 
	{ 
		DecimalFormat formatter = new DecimalFormat("#,###");
		this.CloneSkillPoints = formatter.format(Double.parseDouble(CloneSkillPoints)); 
	}
	
	public String getWalletBalance() { return WalletBalance; }
	public void setWalletBalance(String WalletBalance) 
	{ 
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		this.WalletBalance = formatter.format(Double.parseDouble(WalletBalance)); 
	}
	
	public String getIntelligence() { return Intelligence; }
	public void setIntelligence(String Intelligence) { this.Intelligence = Intelligence; }
	
	public String getMemory() { return Memory; }
	public void setMemory(String Memory) { this.Memory = Memory; }
	
	public String getCharisma() { return Charisma; }
	public void setCharisma(String Charisma) { this.Charisma = Charisma; }
	
	public String getPerception() { return Perception; }
	public void setPerception(String Perception) { this.Perception = Perception; }
	
	public String getWillpower() { return Willpower; }
	public void setWillpower(String Willpower) { this.Willpower = Willpower; }
	
	public String getAllianceName() { return allianceName; }
	public void setAllianceName(String allianceName) { this.allianceName = allianceName; }
	
	public String getAllianceID() { return allianceID; }
	public void setAllianceID(String allianceID) { this.allianceID = allianceID; }
	
	public ArrayList<SkillInfo> getSkills() { return Skills; }
	public void setSkills(ArrayList<SkillInfo> Skills) { this.Skills = Skills; }
	
}
