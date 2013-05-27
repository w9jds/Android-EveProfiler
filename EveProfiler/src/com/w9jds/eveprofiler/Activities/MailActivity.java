package com.w9jds.eveprofiler.Activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.w9jds.eveprofiler.Classes.Account;
import com.w9jds.eveprofiler.Classes.CallApi;
import com.w9jds.eveprofiler.Classes.CharacterInfo;
import com.w9jds.eveprofiler.Classes.MailHeaderListAdapter;
import com.w9jds.eveprofiler.R;
import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import java.util.ArrayList;

public class MailActivity extends FragmentActivity implements ActionBar.OnNavigationListener
{

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * current dropdown position.
	 */
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
    private Account ThisAccount = new Account();

    @Override
	protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mail_activity);

        FillDropDown();
        getMail();
	}

    private void getMail()
    {
        ArrayList<Object> get = new ArrayList<Object>();
        get.add("getMail");
        get.add(this);
        get.add(ThisAccount.getCharacters());
        new CallApi().execute(get);
    }

    public void ApiResponse(ArrayList<CharacterInfo> CharactersIn)
    {
        ThisAccount.setCharacters(CharactersIn);
        FillList();
    }

    private void FillList()
    {
        ListView headerList = (ListView) this.findViewById(R.id.mail_headers);
        headerList.setAdapter(new MailHeaderListAdapter(this, ThisAccount.getCharacters().get(getActionBar().getSelectedNavigationIndex()).getMail()));
    }

    private void FillDropDown()
    {
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        Intent i = getIntent();
        if(i != null && i.hasExtra("Characters"))
            ThisAccount = i.getParcelableExtra("Characters");

        String[] characterList = new String[ThisAccount.getCharacters().size()];

        for (int j = 0; j < ThisAccount.getCharacters().size(); j++)
            characterList[j] = ThisAccount.getCharacters().get(j).getName();

        SpinnerAdapter CharacterAdapter = new ArrayAdapter<String>(actionBar.getThemedContext(), android.R.layout.simple_spinner_dropdown_item, characterList);

        actionBar.setListNavigationCallbacks(CharacterAdapter, this);
        actionBar.setSelectedNavigationItem(ThisAccount.getCurrentCharacter());
    }

    @Override
	public void onRestoreInstanceState(Bundle savedInstanceState)
    {
		// Restore the previously serialized current dropdown position.
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM))
			getActionBar().setSelectedNavigationItem(savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
	}

	@Override
	public void onSaveInstanceState(Bundle outState)
    {
		// Serialize the current dropdown position.
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar().getSelectedNavigationIndex());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
    {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mail, menu);
		return true;
	}

	@Override
	public boolean onNavigationItemSelected(int position, long id)
    {
//        ThisAccount.setCurrentCharacter(getActionBar().getSelectedNavigationIndex());
//        fileList();
		return true;
	}

}
