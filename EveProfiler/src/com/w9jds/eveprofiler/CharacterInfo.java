package com.w9jds.eveprofiler;

public class CharacterInfo 
{
	private String name;
	private int characterID;
	private String corporationName;
	private int corporationID;
	
	public void setName(String in){ this.name = in; }
	public String getName() { return this.name; }
	
	public void setCharacterID(int in) { this.characterID = in; }
	public int getCharacterID() { return this.characterID; }
	
	public void setCorporationName(String in) { this.corporationName = in; }
	public String getCorporationName() { return this.corporationName; }
	
	public void setCorporationID(int in) { this.corporationID = in; }
	public int getCorporationID() { return this.corporationID; }
	
	
}
