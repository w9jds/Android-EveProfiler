package com.w9jds.eveprofiler.DataAccess;

import android.os.AsyncTask;
import java.util.ArrayList;
import com.w9jds.eveprofiler.Activities.MainActivity;
import com.w9jds.eveprofiler.Objects.Character.CharacterMain;

public class CallApi extends AsyncTask<ArrayList<Object>, Void, ArrayList<CharacterMain>> {

    private ArrayList<CharacterMain> Characters;
    Object Main;
    private String keyid;
    private String vCode;

    @Override
    protected ArrayList<CharacterMain> doInBackground(ArrayList<Object>... Info)
    {
        Main = Info[0].get(1);
        keyid = Info[0].get(2).toString();
        vCode = Info[0].get(3).toString();


        if (Info[0].get(0) == "getCharacters")
        {
            CallMethods Calls = new CallMethods();
            Characters = Calls.CharactersList(vCode, keyid);

            for (CharacterMain Character : Characters)
            {
                Character.setCharacterInfo(Calls.CharacterInfo(Character.getCharacterID(), vCode, keyid));
                Character.setCharacterPortrait(Calls.CharacterPortrait(Character.getCharacterID(), "1024"));
                Character.setCorporationPortrait(Calls.CorporationPortrait(Character.getCharacterInfo().getCorporationID(), "128"));
                if (Character.getCharacterInfo().getAllianceID() != null)
                    Character.setAlliancePortrait(Calls.AlliancePortrait(Character.getCharacterInfo().getAllianceID(), "128"));
            }
        }

        else if (Info[0].get(0) == "getMail")
        {
            Characters = (ArrayList<CharacterMain>)Info[0].get(2);
            CallMethods Calls = new CallMethods();

            for (int i = 0; i < Characters.size(); i++)
            {
                Calls.CharacterMailHeaders(Characters.get(i).getCharacterID(), vCode, keyid);

                for (int j = 0; j < Characters.get(i).getMail().size(); j++)
                {
                    boolean duplicate = false;
                    for (int k = 0; k < j; k++)
                    {
                        if (Characters.get(i).getMail().get(k).getSenderID().equals(Characters.get(i).getMail().get(j).getSenderID()))
                        {
                            Characters.get(i).getMail().get(j).setSenderName(Characters.get(i).getMail().get(k).getSenderName());
                            Characters.get(i).getMail().get(j).setSenderPortrait(Characters.get(i).getMail().get(k).getSenderPortrait());
                            duplicate = true;
                            break;
                        }
                    }
                    if (!duplicate)
                    {
                        Characters.get(i).getMail().get(j).setSenderPortrait(Calls.CharacterPortrait(Characters.get(i).getMail().get(j).getSenderID(), "128"));
                        Characters.get(i).getMail().get(j).setSenderName(Calls.CharacterInfo(Characters.get(i).getMail().get(j).getSenderID(), keyid, vCode).getName());
                    }
                }
            }
        }
        return Characters;
    }

    protected void onPostExecute(ArrayList<CharacterMain> result)
    {
        Object x = Main.getClass().getName();

        if(Main.getClass().getName().equals("com.w9jds.eveprofiler.Activities.MainActivity"))
        {
            MainActivity returncall = (MainActivity)Main;
            returncall.ApiResponse(result);
        }
    }
}