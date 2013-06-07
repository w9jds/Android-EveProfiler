package com.w9jds.eveprofiler.DataAccess;

import android.os.AsyncTask;
import java.util.ArrayList;
import com.w9jds.eveprofiler.Activities.MailActivity;
import com.w9jds.eveprofiler.Activities.MainActivity;
import com.w9jds.eveprofiler.Objects.Character.CharacterMain;

public class CallApi extends AsyncTask<ArrayList<Object>, Void, ArrayList<CharacterMain>> {

    private ArrayList<CharacterMain> Characters = new ArrayList<CharacterMain>();
    private MainActivity Main = new MainActivity();
    private MailActivity Mail = new MailActivity();

    @Override
    protected ArrayList<CharacterMain> doInBackground(ArrayList<Object>... Info)
    {
        if(Info[0].get(1).getClass() == Main.getClass())
        {
            Main = (MainActivity)Info[0].get(1);
            Mail = null;
        }
        else if(Info[0].get(1).getClass() == Mail.getClass())
        {
            Mail = (MailActivity)Info[0].get(1);
            Main = null;
        }

        if (Info[0].get(0) == "getCharacters")
        {
            CallMethods Calls = new CallMethods();
            Calls.CharactersList();

            for (CharacterMain Character : Characters) {
                Character.setCharacterInfo(Calls.CharacterInfo(Character.getCharacterID()));
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
                Calls.CharacterMailHeaders(Characters.get(i).getCharacterID());

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
                        Characters.get(i).getMail().get(j).setSenderName(Calls.CharacterInfo(Characters.get(i).getMail().get(j).getSenderID()).getName());
                    }
                }
            }
        }
        return Characters;
    }

    protected void onPostExecute(ArrayList<CharacterMain> result)
    {
        if(Main != null)
            Main.ApiResponse(result);
//        else if(Mail != null)
//            Mail.ApiResponse(result);
    }
}