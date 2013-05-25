package com.w9jds.eveprofiler.Activities;

import java.util.ArrayList;
import android.graphics.drawable.Drawable;
import android.support.v4.app.*;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.widget.*;
import com.w9jds.eveprofiler.Classes.*;
import com.w9jds.eveprofiler.R;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener 
{
	private SectionsPagerAdapter mSectionsPagerAdapter;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

	private ViewPager mViewPager;
	private PrevSettings prevSettings = new PrevSettings();
	private static Account ThisAccount = new Account();
	private static final int RESULT_SETTINGS = 1;

    @Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getActionBar().setDisplayHomeAsUpEnabled(true);

        LoadDrawer();
        getCharacters();
	}
	
	private void LoadDrawer()
    {
        ArrayList<Drawable> images = new ArrayList<Drawable>();
        images.add(getResources().getDrawable(R.drawable.drawer_mail));
        images.add(getResources().getDrawable(R.drawable.drawer_wallet));

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new DrawerListAdapter(this, images));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view)
            {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView)
            {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

	}

	private void getCharacters()
	{
//		ProgressBar pb = (ProgressBar)getWindow().getDecorView().findViewById(R.id.progressBar);
//		pb.setVisibility(View.VISIBLE);
		ArrayList<Object> get = new ArrayList<Object>();
		get.add("getCharacters");
		get.add(this);
		new CallApi().execute(get);
	}

	public void ApiResponse(ArrayList<CharacterInfo> apiCharacters)
	{
//		ProgressBar pb = (ProgressBar)getWindow().getDecorView().findViewById(R.id.progressBar);
        ThisAccount.setCharacters(apiCharacters);
		CreateTabItems();
//		pb.setVisibility(View.GONE);
	}

    public void CreateTabItems()
	{
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() 
		{
			@Override
			public void onPageSelected(int position) { actionBar.setSelectedNavigationItem(position); }
		});

		// For each character create a tab and use the characters name as the title
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++)
			actionBar.addTab(actionBar.newTab().setText(mSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
	}

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
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
	        case android.R.id.home:
	        	//menu.toggle();
	        	break;
	        case R.id.action_settings:
	        	SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
	        	prevSettings.setsmallDrawer(sharedPrefs.getBoolean("drawerSize", true));
	        	prevSettings.setapiKey(sharedPrefs.getString("keyid", null));
	        	prevSettings.setvCode(sharedPrefs.getString("vCode", null));
	        	startActivityForResult(new Intent(this, SettingsActivity.class), RESULT_SETTINGS);
	        	break;
	        case R.id.action_refresh:
	        	ActionBar actionBar = getActionBar();
	        	actionBar.removeAllTabs();
	        	getCharacters();
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
        	if (prevSettings.getapiKey() != sharedPrefs.getString("keyid", null) || prevSettings.getvCode() != sharedPrefs.getString("vCode", null)){
	        	ActionBar actionBar = getActionBar();
	        	actionBar.removeAllTabs();
	        	getCharacters();
        	}
        	if (prevSettings.getsmallDrawer() != sharedPrefs.getBoolean("drawerSize", true))
        		LoadDrawer();
            break;
        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {

//            selectItem(position);
        }
    }

	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) 
	{
		int Position = tab.getPosition();

		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
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

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) 
		{
			super(fm);
		}

		@Override
		public Fragment getItem(int position) 
		{
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
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
			return ThisAccount.getCharacters().get(position).getName();
		}
	}

	public static class DummySectionFragment extends Fragment {

		public static final String ARG_SECTION_NUMBER = "section_number";
		
		public DummySectionFragment() 
		{
			
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
		{
			View rootView = inflater.inflate(R.layout.fragment_main_dummy, container, false);

			ImageView image = (ImageView) rootView.findViewById(R.id.CapsuleerPortrait);
			Bitmap bMap = BitmapFactory.decodeByteArray(ThisAccount.getCharacters().get(container.getChildCount()).getCharacterPortrait(), 0, ThisAccount.getCharacters().get(container.getChildCount()).getCharacterPortrait().length);
			image.setImageBitmap(bMap);
			
			image = (ImageView) rootView.findViewById(R.id.corpPic);
			bMap = BitmapFactory.decodeByteArray(ThisAccount.getCharacters().get(container.getChildCount()).getCorporationPortrait(), 0, ThisAccount.getCharacters().get(container.getChildCount()).getCorporationPortrait().length);
			image.setImageBitmap(bMap);

            TextView ActiveShipType = (TextView) rootView.findViewById(R.id.ActiveShipType);
            ActiveShipType.setText(ThisAccount.getCharacters().get(container.getChildCount()).getShipTypeName());

            TextView ActiveShipName = (TextView) rootView.findViewById(R.id.ActiveShipName);
            ActiveShipName.setText("[" + ThisAccount.getCharacters().get(container.getChildCount()).getShipName() + "]");

            TextView LastKnownLocation = (TextView) rootView.findViewById(R.id.Location);
            LastKnownLocation.setText(ThisAccount.getCharacters().get(container.getChildCount()).getLastKnownLocation());

            TextView SkillPoints = (TextView) rootView.findViewById(R.id.SkillPoints);
            SkillPoints.setText(ThisAccount.getCharacters().get(container.getChildCount()).getSkillPoints());

            TextView SecStatus = (TextView) rootView.findViewById(R.id.SecStatus);
            SecStatus.setText(ThisAccount.getCharacters().get(container.getChildCount()).getSecStatus());

			TextView Corporation = (TextView) rootView.findViewById(R.id.CorpName);
			Corporation.setText(ThisAccount.getCharacters().get(container.getChildCount()).getCorporationName());

			TextView IskWealth = (TextView) rootView.findViewById(R.id.WealthIsk);
			IskWealth.setText(ThisAccount.getCharacters().get(container.getChildCount()).getWalletBalance() + " ISK");

			if (ThisAccount.getCharacters().get(container.getChildCount()).getAlliancePortrait() != null){
				TextView Alliance = (TextView) rootView.findViewById(R.id.allianceName);
				Alliance.setText(ThisAccount.getCharacters().get(container.getChildCount()).getAllianceName());
				image = (ImageView) rootView.findViewById(R.id.alliancePic);
				bMap = BitmapFactory.decodeByteArray(ThisAccount.getCharacters().get(container.getChildCount()).getAlliancePortrait(), 0, ThisAccount.getCharacters().get(container.getChildCount()).getAlliancePortrait().length);
				image.setImageBitmap(bMap);	
			}
			
			return rootView;
		}
	}
}
