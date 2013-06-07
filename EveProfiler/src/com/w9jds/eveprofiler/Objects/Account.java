package com.w9jds.eveprofiler.Objects;

import android.os.Parcel;
import android.os.Parcelable;
import com.w9jds.eveprofiler.Objects.Character.CharacterMain;
import java.util.ArrayList;

/**
 * Created by Jeremy on 5/18/13.
 */
public class Account implements Parcelable {

    public Account()
    {

    }

    private ArrayList<CharacterMain> Characters = new ArrayList<CharacterMain>();
    private int CurrentCharcter;

    public ArrayList<CharacterMain> getCharacters() { return this.Characters; }
    public void setCharacters(ArrayList<CharacterMain> Characters) { this.Characters = Characters; }

    public int getCurrentCharacter() { return this.CurrentCharcter; }
    public void setCurrentCharacter(int CurrentCharacter) { this.CurrentCharcter = CurrentCharacter; }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
//        try
//        {
//            parcel.writeInt(this.getCurrentCharacter());
//
//            parcel.writeInt(this.getCharacters().size());
//            for (int j = 0; j < this.Characters.size(); j++)
//            {
//                parcel.writeString(this.getCharacters().get(j).getName());
//                parcel.writeString(this.getCharacters().get(j).getCharacterID());
//                parcel.writeString(this.getCharacters().get(j).getCorporationName());
//                parcel.writeString(this.getCharacters().get(j).getCorporationDate());
//                parcel.writeString(this.getCharacters().get(j).getCorporationID());
//                parcel.writeString(this.getCharacters().get(j).getAllianceName());
//                parcel.writeString(this.getCharacters().get(j).getAllianceDate());
//                parcel.writeString(this.getCharacters().get(j).getAllianceID());
//                parcel.writeString(this.getCharacters().get(j).getDateOfBirth());
//                parcel.writeString(this.getCharacters().get(j).getRace());
//                parcel.writeString(this.getCharacters().get(j).getBloodLine());
//                parcel.writeString(this.getCharacters().get(j).getAncestry());
//                parcel.writeString(this.getCharacters().get(j).getGender());
//                parcel.writeString(this.getCharacters().get(j).getCloneName());
//                parcel.writeString(this.getCharacters().get(j).getCloneSkillPoints());
//                parcel.writeString(this.getCharacters().get(j).getWalletBalance());
//                parcel.writeString(this.getCharacters().get(j).getIntelligence());
//                parcel.writeString(this.getCharacters().get(j).getMemory());
//                parcel.writeString(this.getCharacters().get(j).getCharisma());
//                parcel.writeString(this.getCharacters().get(j).getPerception());
//                parcel.writeString(this.getCharacters().get(j).getWillpower());
//                parcel.writeString(this.getCharacters().get(j).getLastKnownLocation());
//                parcel.writeString(this.getCharacters().get(j).getShipName());
//                parcel.writeString(this.getCharacters().get(j).getshipTypeID());
//                parcel.writeString(this.getCharacters().get(j).getShipTypeName());
//                parcel.writeString(this.getCharacters().get(j).getSecStatus());
//
//
//                if(this.getCharacters().get(j).getSkills() != null)
//                {
//                    parcel.writeInt(this.getCharacters().get(j).getSkills().size());
//                    for(int k = 0; k < this.getCharacters().get(j).getSkills().size(); k++)
//                    {
//                        parcel.writeString(this.getCharacters().get(j).getSkills().get(k).getTypeID());
//                        parcel.writeString(this.getCharacters().get(j).getSkills().get(k).getSkillPoints());
//                        parcel.writeString(this.getCharacters().get(j).getSkills().get(k).getLevel());
//                        parcel.writeString(this.getCharacters().get(j).getSkills().get(k).getPublished());
//                    }
//                }
//                else
//                    parcel.writeInt(0);
//
//                parcel.writeString(this.getCharacters().get(j).getSkillPoints());
//
//
//                if (this.getCharacters().get(j).getagentStandings() != null)
//                {
//                    parcel.writeInt(this.getCharacters().get(j).getagentStandings().size());
//                    for (int k = 0; k < this.getCharacters().get(j).getagentStandings().size(); k++)
//                    {
//                        parcel.writeString(this.getCharacters().get(j).getagentStandings().get(k).getfromID());
//                        parcel.writeString(this.getCharacters().get(j).getagentStandings().get(k).getfromName());
//                        parcel.writeString(this.getCharacters().get(j).getagentStandings().get(k).getStanding());
//                    }
//                }
//                else
//                    parcel.writeInt(0);
//
//                if(this.getCharacters().get(j).getNPCStandings() != null)
//                {
//                    parcel.writeInt(this.getCharacters().get(j).getNPCStandings().size());
//                    for (int k = 0; k < this.getCharacters().get(j).getNPCStandings().size(); k++)
//                    {
//                        parcel.writeString(this.getCharacters().get(j).getNPCStandings().get(k).getfromID());
//                        parcel.writeString(this.getCharacters().get(j).getNPCStandings().get(k).getfromName());
//                        parcel.writeString(this.getCharacters().get(j).getNPCStandings().get(k).getStanding());
//                    }
//                }
//                else
//                    parcel.writeInt(0);
//
//                if(this.getCharacters().get(j).getfactionStandings() != null)
//                {
//                    parcel.writeInt(this.getCharacters().get(j).getfactionStandings().size());
//                    for (int k = 0; k < this.getCharacters().get(j).getfactionStandings().size(); k++)
//                    {
//                        parcel.writeString(this.getCharacters().get(j).getfactionStandings().get(k).getfromID());
//                        parcel.writeString(this.getCharacters().get(j).getfactionStandings().get(k).getfromName());
//                        parcel.writeString(this.getCharacters().get(j).getfactionStandings().get(k).getStanding());
//                    }
//                }
//                else
//                    parcel.writeInt(0);
//
//                parcel.writeString(this.getCharacters().get(j).getnextTrainingEnds());
//    //            parcel.writeByteArray(this.getCharacters().get(j).getCharacterPortrait());
//    //            parcel.writeByteArray(this.getCharacters().get(j).getCorporationPortrait());
//    //            parcel.writeByteArray(this.getCharacters().get(j).getAlliancePortrait());
//            }
//        }
//        catch(Exception e)
//        {
//            Log.d("Exception", e.toString());
//        }
    }

    public static final Parcelable.Creator<Account> CREATOR = new Parcelable.Creator<Account>()
    {
        public Account createFromParcel(Parcel in)
        {
            return new Account(in);
        }

        public Account[] newArray(int size)
        {
            return new Account[size];
        }
    };

    private Account(Parcel in)
    {
//        try
//        {
//            this.setCurrentCharacter(in.readInt());
//
//            int charactercount = in.readInt();
//            for (int j = 0; j < charactercount; j++)
//            {
//                CharacterInfo Character = new CharacterInfo();
//                Character.setName(in.readString());
//                Character.setCharacterID(in.readString());
//                Character.setCorporationName(in.readString());
//                Character.setCorporationDate(in.readString());
//                Character.setCorporationID(in.readString());
//                Character.setAllianceName(in.readString());
//                Character.setAllianceDate(in.readString());
//                Character.setAllianceID(in.readString());
//                Character.setDateOfBirth(in.readString());
//                Character.setRace(in.readString());
//                Character.setBloodLine(in.readString());
//                Character.setAncestry(in.readString());
//                Character.setGender(in.readString());
//                Character.setCloneName(in.readString());
//                Character.setCloneSkillPoints(in.readString());
//                Character.setWalletBalance(in.readString());
//                Character.setIntelligence(in.readString());
//                Character.setMemory(in.readString());
//                Character.setCharisma(in.readString());
//                Character.setPerception(in.readString());
//                Character.setWillpower(in.readString());
//                Character.setLastKnownLocation(in.readString());
//                Character.setShipName(in.readString());
//                Character.setShipTypeID(in.readString());
//                Character.setShipTypeName(in.readString());
//                Character.setSecStatus(in.readString());
//
//                int skillSize = in.readInt();
//                for(int k = 0; k < skillSize; k++)
//                {
//                    SkillInfo skill = new SkillInfo();
//                    skill.setTypeID(in.readString());
//                    skill.setSkillPoints(in.readString());
//                    skill.setLevel(in.readString());
//                    skill.setPublished(in.readString());
//                    Character.getSkills().add(skill);
//                }
//
//                Character.setSkillPoints(in.readString());
//
//                int agentSize = in.readInt();
//                for (int k = 0; k < agentSize; k++)
//                {
//                    StandingInfo standing = new StandingInfo();
//                    standing.setfromID(in.readString());
//                    standing.setfromName(in.readString());
//                    standing.setStranding(in.readString());
//                    Character.getagentStandings().add(standing);
//                }
//
//                int NPCSize = in.readInt();
//                for (int k = 0; k < NPCSize; k++)
//                {
//                    StandingInfo standing = new StandingInfo();
//                    standing.setfromID(in.readString());
//                    standing.setfromName(in.readString());
//                    standing.setStranding(in.readString());
//                    Character.getNPCStandings().add(standing);
//                }
//
//                int factionSize = in.readInt();
//                for (int k = 0; k < factionSize; k++)
//                {
//                    StandingInfo standing = new StandingInfo();
//                    standing.setfromID(in.readString());
//                    standing.setfromName(in.readString());
//                    standing.setStranding(in.readString());
//                    Character.getfactionStandings().add(standing);
//                }
//
//                Character.setnextTrainingEnds(in.readString());
//    //            parcel.writeByteArray(this.getCharacters().get(j).getCharacterPortrait());
//    //            parcel.writeByteArray(this.getCharacters().get(j).getCorporationPortrait());
//    //            parcel.writeByteArray(this.getCharacters().get(j).getAlliancePortrait());
//                this.Characters.add(Character);
//            }
//        }
//        catch(Exception e)
//        {
//            Log.d("Exception", e.toString());
//        }
    }
}
