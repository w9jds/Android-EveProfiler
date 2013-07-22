package com.w9jds.eveprofiler.Objects;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.w9jds.eveprofiler.Objects.Character.CharacterMain;
import com.w9jds.eveprofiler.Objects.Character.Info;
import java.util.ArrayList;

/**
 * Created by Jeremy on 5/18/13.
 **/
public class Account implements Parcelable {

    public Account() { };

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
        try
        {
            parcel.writeInt(this.getCurrentCharacter());
            parcel.writeInt(this.getCharacters().size());
            for (int j = 0; j < this.Characters.size(); j++)
            {
                parcel.writeString(this.getCharacters().get(j).getCharacterID());
                parcel.writeString(this.getCharacters().get(j).getCharacterInfo().getName());
            }
        }
        catch(Exception e)
        {
            Log.d("Exception", e.toString());
        }
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
        try
        {
            this.setCurrentCharacter(in.readInt());
            int charactercount = in.readInt();
            for (int j = 0; j < charactercount; j++)
            {
                CharacterMain Character = new CharacterMain();
                Info CharacterInfo = new Info();
                Character.setCharacterID(in.readString());
                CharacterInfo.setName(in.readString());
                Character.setCharacterInfo(CharacterInfo);
                this.Characters.add(Character);
            }
        }
        catch(Exception e)
        {
            Log.d("Exception", e.toString());
        }
    }
}
