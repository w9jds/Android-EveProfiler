package com.w9jds.eveprofiler.Activities;

import android.annotation.TargetApi;
import android.app.AlertDialog;
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
        FillLayout();
    }

    private void FillLayout()
    {
        View[] Views = new View[ThisAccount.getCharacters().get(ThisAccount.getCurrentCharacter()).getMail().size()];

        for (int i = 0; i < ThisAccount.getCharacters().get(ThisAccount.getCurrentCharacter()).getMail().size(); i++)
        {
            try
            {
                LayoutInflater inflater = getLayoutInflater();
                View headerView = inflater.inflate(R.layout.mail_header, null);

                TextView titleview = (TextView)headerView.findViewById(R.id.titleView);
                titleview.setText(ThisAccount.getCharacters().get(ThisAccount.getCurrentCharacter()).getMail().get(i).getTitle());
                ImageView image = (ImageView)headerView.findViewById(R.id.SenderPortrait);
                Bitmap bMap = BitmapFactory.decodeByteArray(ThisAccount.getCharacters().get(ThisAccount.getCurrentCharacter()).getMail().get(i).getSenderPortrait(), 0, ThisAccount.getCharacters().get(ThisAccount.getCurrentCharacter()).getMail().get(i).getSenderPortrait().length);
                image.setImageBitmap(bMap);

                Views[i] = headerView;
            }
            catch(Exception e)
            { Log.d("Exception", e.toString()); }
        }

        try
        {
            ListView MailList = (ListView)this.findViewById(R.id.MailList);
            ArrayAdapter<View> modeAdapter = new ArrayAdapter<View>(this, android.R.layout.simple_selectable_list_item, android.R.id.button1, Views);
            MailList.setAdapter(modeAdapter);
        }
        catch (Exception e)
        { Log.d("Exception", e.toString()); }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
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

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
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
		// When the given dropdown item is selected, show its contents in the
		// container view.
//		Fragment fragment = new DummySectionFragment();
//		Bundle args = new Bundle();
//		args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
//		fragment.setArguments(args);
//		getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
		return true;
	}

//    public static class MailHeaderFragment extends Fragment
//    {
//
//        MailHeaderFragment()
//        {
//
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
//        {
//            View rootView = inflater.inflate(R.layout.mail_header_fragment, container, false);
//
//            return rootView;
//        }
//
//
//
//    }
}
