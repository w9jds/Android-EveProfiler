package com.w9jds.eveprofiler.Activities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.*;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.widget.*;
import com.w9jds.eveprofiler.Classes.*;
import com.w9jds.eveprofiler.DataAccess.CallMethods;
import com.w9jds.eveprofiler.ListAdapters.DrawerListAdapter;
import com.w9jds.eveprofiler.Objects.Account;
import com.w9jds.eveprofiler.Objects.Character.CharacterMain;
import com.w9jds.eveprofiler.Objects.ReturnResult;
import com.w9jds.eveprofiler.R;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.apache.http.HttpStatus;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener 
{
    private SharedPreferences settings;
    private Context ThisContext;
    private ActionBarDrawerToggle mDrawerToggle;
	private ViewPager mViewPager;
	private final PrevSettings prevSettings = new PrevSettings();
	private static final Account ThisAccount = new Account();
	private static final int RESULT_SETTINGS = 1;

    @Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getActionBar().setDisplayHomeAsUpEnabled(true);

        settings = PreferenceManager.getDefaultSharedPreferences(this);
        ThisContext = this.getBaseContext();

        LoadDrawer();
        LoadInfo();
	}

    private void LoadInfo()
    {
        if (settings.contains("keyid") == false || settings.contains("vCode") == false)
            startActivityForResult(new Intent(this, SettingsActivity.class), RESULT_SETTINGS);
        else
        {
            try
            {
                FileInputStream fis = this.getBaseContext().openFileInput("MainPage");
                ObjectInputStream is = new ObjectInputStream(fis);
                ThisAccount.setCharacters((ArrayList<CharacterMain>)is.readObject());
                is.close();

                CreateTabItems();
            }
            catch (Exception x)
            {
                new getCharacters().execute();
            }
        }
    }
	
	private void LoadDrawer()
    {
        ArrayList<Integer> images = new ArrayList<Integer>();
        images.add(R.drawable.drawer_mail);
        images.add(R.drawable.drawer_wallet);
        images.add(R.drawable.drawer_assets);
        images.add(R.drawable.drawer_server);

        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ListView mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerList.setAdapter(new DrawerListAdapter(this, images));

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle( this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close )
        {
            public void onDrawerClosed(View view)
            {
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView)
            {
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

    private class DrawerItemClickListener implements ListView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id)
        {
            switch(position)
            {
                case 0:
                    Intent i = new Intent(MainActivity.this, MailActivity.class);
                    i.putExtra("Characters", ThisAccount);
                    startActivity(i);
                    break;
                case 1:
                    break;
                case 2:
                    break;
            }
        }
    }

    private class getCharacters extends AsyncTask<Void, Void, Void>
    {
        protected Void doInBackground(Void... info)
        {
            CallMethods Calls = new CallMethods();
            ReturnResult rrReturn;
            rrReturn = Calls.GetCharactersList(settings.getString("vCode", null), settings.getString("keyid", null));

            if (rrReturn.getStatusCode() == HttpStatus.SC_OK)
                ThisAccount.setCharacters((ArrayList<CharacterMain>)rrReturn.getoReturn());

            for (CharacterMain Character : ThisAccount.getCharacters())
            {
                rrReturn = Calls.GetCharacterInfo(Character.getCharacterID(), settings.getString("vCode", null), settings.getString("keyid", null));
                if (rrReturn.getStatusCode() == HttpStatus.SC_OK)
                    Character.setCharacterInfo((com.w9jds.eveprofiler.Objects.Character.Info)rrReturn.getoReturn());

                rrReturn = Calls.GetCharacterPortrait(Character.getCharacterID(), "1024");
                if (rrReturn.getStatusCode() == HttpStatus.SC_OK)
                    Character.setCharacterPortrait((byte[])rrReturn.getoReturn());

                rrReturn = Calls.GetCorporationPortrait(Character.getCharacterInfo().getCorporationID(), "128");
                if (rrReturn.getStatusCode() == HttpStatus.SC_OK)
                    Character.setCorporationPortrait((byte[])rrReturn.getoReturn());

                if (Character.getCharacterInfo().getAllianceID() != null)
                {
                    rrReturn = Calls.GetAlliancePortrait(Character.getCharacterInfo().getAllianceID(), "128");
                    if (rrReturn.getStatusCode() == HttpStatus.SC_OK)
                        Character.setAlliancePortrait((byte[])rrReturn.getoReturn());
                }
            }

            try
            {
                FileOutputStream fos = ThisContext.openFileOutput("MainPage", Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.writeObject(ThisAccount.getCharacters());
                os.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            CreateTabItems();

            return info[0];
        }

    }

    void CreateTabItems()
	{
		final ActionBar actionBar = getActionBar();

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
            {
                @Override
                public void onPageSelected(int position) { actionBar.setSelectedNavigationItem(position); }
            });

		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++)
			actionBar.addTab(actionBar.newTab().setText(mSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
	}

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
    {
        if (mDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }

	    switch (item.getItemId()) 
	    {
	        case R.id.action_settings:
	        	SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
	        	prevSettings.setsmallDrawer(sharedPrefs.getBoolean("drawerSize", true));
	        	prevSettings.setapiKey(sharedPrefs.getString("keyid", null));
	        	prevSettings.setvCode(sharedPrefs.getString("vCode", null));
	        	startActivityForResult(new Intent(this, SettingsActivity.class), RESULT_SETTINGS);
	        	break;
	        case R.id.action_refresh:
                mViewPager.removeAllViews();
	        	ActionBar actionBar = getActionBar();
	        	actionBar.removeAllTabs();
                new getCharacters().execute();
	        	break;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	    return true;
    }
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
        case RESULT_SETTINGS:
        	SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        	if (prevSettings.getapiKey().equals(sharedPrefs.getString("keyid", null)) == false || prevSettings.getvCode().equals(sharedPrefs.getString("vCode", null)) == false){
	        	mViewPager.removeAllViews();
                ActionBar actionBar = getActionBar();
	        	actionBar.removeAllTabs();
                new getCharacters().execute();
        	}
        	if (prevSettings.getsmallDrawer() != sharedPrefs.getBoolean("drawerSize", true))
        		LoadDrawer();
            break;
        }
    }

	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) 
	{
		int Position = tab.getPosition();

		mViewPager.setCurrentItem(Position);
        ThisAccount.setCurrentCharacter(Position);
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) 
	{
		
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) 
	{
		
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter
    {

		public SectionsPagerAdapter(FragmentManager fm) 
		{
			super(fm);
		}

		@Override
		public Fragment getItem(int position) 
		{
			Fragment fragment = new DummySectionFragment();
			
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			
			return fragment;
		}

		@Override
		public int getCount() 
		{
			return ThisAccount.getCharacters().size();
		}
		
		@Override
		public CharSequence getPageTitle(int position) 
		{
			return ThisAccount.getCharacters().get(position).getCharacterInfo().getName();
		}
	}

	public static class DummySectionFragment extends Fragment
    {
		public static final String ARG_SECTION_NUMBER = "section_number";
		
		public DummySectionFragment() 
		{
			
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
		{
			View rootView = inflater.inflate(R.layout.fragment_main_dummy, container, false);

			ImageView image = (ImageView) rootView.findViewById(R.id.CapsuleerPortrait);
			image.setImageBitmap(BitmapFactory.decodeByteArray(ThisAccount.getCharacters().get(container.getChildCount()).getCharacterPortrait(), 0, ThisAccount.getCharacters().get(container.getChildCount()).getCharacterPortrait().length));
			
			image = (ImageView) rootView.findViewById(R.id.corpPic);
			image.setImageBitmap(BitmapFactory.decodeByteArray(ThisAccount.getCharacters().get(container.getChildCount()).getCorporationPortrait(), 0, ThisAccount.getCharacters().get(container.getChildCount()).getCorporationPortrait().length));

            TextView ActiveShipType = (TextView) rootView.findViewById(R.id.ActiveShipType);
            ActiveShipType.setText(ThisAccount.getCharacters().get(container.getChildCount()).getCharacterInfo().getShipTypeName());

            TextView ActiveShipName = (TextView) rootView.findViewById(R.id.ActiveShipName);
            ActiveShipName.setText("[" + ThisAccount.getCharacters().get(container.getChildCount()).getCharacterInfo().getShipName() + "]");

            TextView LastKnownLocation = (TextView) rootView.findViewById(R.id.Location);
            LastKnownLocation.setText(ThisAccount.getCharacters().get(container.getChildCount()).getCharacterInfo().getLastKnownLocation());

            TextView SkillPoints = (TextView) rootView.findViewById(R.id.SkillPoints);
            SkillPoints.setText(new DecimalFormat("#,###").format(ThisAccount.getCharacters().get(container.getChildCount()).getCharacterInfo().getSkillPoints()));

            TextView SecStatus = (TextView) rootView.findViewById(R.id.SecStatus);
            SecStatus.setText(new DecimalFormat("#.###").format(ThisAccount.getCharacters().get(container.getChildCount()).getCharacterInfo().getSecurityStatus()));

			TextView Corporation = (TextView) rootView.findViewById(R.id.CorpName);
			Corporation.setText(ThisAccount.getCharacters().get(container.getChildCount()).getCharacterInfo().getCorporation());

			TextView IskWealth = (TextView) rootView.findViewById(R.id.WealthIsk);
			IskWealth.setText(new DecimalFormat("#,###.00").format(ThisAccount.getCharacters().get(container.getChildCount()).getCharacterInfo().getAccountBalance()) + " ISK");

			if (ThisAccount.getCharacters().get(container.getChildCount()).getAlliancePortrait() != null){
				TextView Alliance = (TextView) rootView.findViewById(R.id.allianceName);
				Alliance.setText(ThisAccount.getCharacters().get(container.getChildCount()).getCharacterInfo().getAlliance());

				image = (ImageView) rootView.findViewById(R.id.alliancePic);
				image.setImageBitmap(BitmapFactory.decodeByteArray(ThisAccount.getCharacters().get(container.getChildCount()).getAlliancePortrait(), 0, ThisAccount.getCharacters().get(container.getChildCount()).getAlliancePortrait().length));
			}
			
			return rootView;
		}
	}
}
