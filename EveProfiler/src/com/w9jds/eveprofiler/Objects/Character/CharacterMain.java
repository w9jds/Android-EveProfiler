package com.w9jds.eveprofiler.Objects.Character;

import java.io.Serializable;
import java.util.ArrayList;

public class CharacterMain implements Serializable
{

    private static final long serialVersionUID = 0L;

	private String characterID;
    private Info characterInfo;
	private byte[] CharacterPortrait;
	private byte[] corporationPortrait;
	private byte[] alliancePortrait;
    private ArrayList<MailInfo> Mail;

    public Info getCharacterInfo() { return characterInfo; }
    public void setCharacterInfo(Info CharacterInfo) { this.characterInfo = CharacterInfo; }

    public ArrayList<MailInfo> getMail() { return Mail; }
    public void setMail(ArrayList<MailInfo> Mail) { this.Mail = Mail; }

	public byte[] getAlliancePortrait() { return alliancePortrait; }
	public void setAlliancePortrait(byte[] alliancePortrait) { this.alliancePortrait = alliancePortrait; }
	
	public byte[] getCorporationPortrait() { return corporationPortrait; }
	public void setCorporationPortrait(byte[] corporationPortrait) { this.corporationPortrait = corporationPortrait; }
	
	public byte[] getCharacterPortrait() { return CharacterPortrait; }
	public void setCharacterPortrait(byte[] CharacterPortrait) { this.CharacterPortrait = CharacterPortrait; }

	public String getCharacterID() { return characterID; }
	public void setCharacterID(String characterID) { this.characterID = characterID; }
	
}
