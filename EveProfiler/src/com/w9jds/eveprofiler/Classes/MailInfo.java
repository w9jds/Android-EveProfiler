package com.w9jds.eveprofiler.Classes;

/**
 * Created by Jeremy on 5/19/13.
 */
public class MailInfo
{

    private String messageID;
    private String senderID;
    private String senderName;
    private byte[] senderPortrait;
    private String sentDate;
    private String title;
    private String toCorpOrAllianceID;
    private String toCharacterIDs;
    private String toListID;

    public String getSenderName() { return senderName; }
    public void setSenderName(String senderName) { this.senderName = senderName; }

    public String getMessageID() { return messageID; }
    public void setMessageID(String messageID) { this.messageID = messageID; }

    public String getSenderID() { return senderID; }
    public void setSenderID(String senderID) { this.senderID = senderID; }

    public byte[] getSenderPortrait() { return senderPortrait; }
    public void setSenderPortrait(byte[] senderPortrait) { this.senderPortrait = senderPortrait; }

    public String getSentDate() { return sentDate; }
    public void setSentDate(String sentDate) { this.sentDate = sentDate; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getToCorpOrAllianceID() { return toCorpOrAllianceID; }
    public void setToCorpOrAllianceID(String toCorpOrAllianceID) { this.toCorpOrAllianceID = toCorpOrAllianceID; }

    public String getToCharacterIDs() { return toCharacterIDs; }
    public void setToCharacterIDs(String toCharacterIDs) { this.toCharacterIDs = toCharacterIDs; }

    public String getToListID() { return toListID; }
    public void setToListID(String toListID) { this.toListID = toListID; }

}
