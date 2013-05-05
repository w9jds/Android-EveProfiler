package com.w9jds.eveprofiler;

import java.util.ArrayList;

public class CharacterInfo {
	private String name;
	private String characterID;
	private String corporationName;
	private String corporationID;
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
	private ArrayList<ArrayList<String>> Skills;
	private byte[] CharacterPortrait;
	
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
	public void setCloneSkillPoints(String CloneSkillPoints) { this.CloneSkillPoints = CloneSkillPoints; }
	
	public String getWalletBalance() { return WalletBalance; }
	public void setWalletBalance(String WalletBalance) { this.WalletBalance = WalletBalance; }
	
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
	
	public ArrayList<ArrayList<String>> getSkills() { return Skills; }
	public void setSkills(ArrayList<ArrayList<String>> Skills) { this.Skills = Skills; }
	
}
