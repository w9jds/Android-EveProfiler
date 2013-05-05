package com.w9jds.eveprofiler;

import java.util.ArrayList;
import java.util.Locale;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener 
{

	SectionsPagerAdapter mSectionsPagerAdapter;

	ViewPager mViewPager;
	ArrayList<CharacterInfo> Characters = new ArrayList<CharacterInfo>();

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		GetCharacterList();
	}

	private void GetCharacterList()
	{
		ArrayList<ArrayList<Object>> Info = new ArrayList<ArrayList<Object>>(3);
		Info.add(new ArrayList<Object>());
		Info.get(0).add(getString(R.string.Api_Uri));
		Info.get(0).add(getString(R.string.List_Characters));
		Info.add(new ArrayList<Object>());
		Info.get(1).add("keyID");
		Info.get(1).add("1996957");
		Info.get(1).add("vCode");
		Info.get(1).add("I6YLp1vVB0KYAir2B3Z4mDIPtZrFHlpeysYYSaxGkjV4rO820NpTOBustmNsoEA4");
		Info.add(new ArrayList<Object>());
		Info.get(2).add(this);
		new CallApi().execute(Info);
	}
	
	private void GetCharacterSheet(int Position)
	{
		ArrayList<ArrayList<Object>> Info = new ArrayList<ArrayList<Object>>(3);
		Info.add(new ArrayList<Object>());
		Info.get(0).add(getString(R.string.Api_Uri));
		Info.get(0).add(getString(R.string.Character_Sheet));
		Info.add(new ArrayList<Object>());
		Info.get(1).add("keyID");
		Info.get(1).add("1996957");
		Info.get(1).add("vCode");
		Info.get(1).add("I6YLp1vVB0KYAir2B3Z4mDIPtZrFHlpeysYYSaxGkjV4rO820NpTOBustmNsoEA4");
		Info.get(1).add("characterID");
		Info.get(1).add(Characters.get(Position).getCharacterID());
		Info.add(new ArrayList<Object>());
		Info.get(2).add(this);
		new CallApi().execute(Info);
	}
	
	public void ApiResponse(String XMLResponse, String ApiCalled)
	{
		if (ApiCalled == getString(R.string.List_Characters))
		{
			ArrayList<ArrayList<String>> ParsedResponse = new ParseXml().ParseCharacterList(XMLResponse);
			for (int i = 0; i < ParsedResponse.size(); i++)
			{
				Characters.add(new CharacterInfo());
				Characters.get(i).setName(ParsedResponse.get(i).get(0));
				Characters.get(i).setCharacterID(ParsedResponse.get(i).get(1));
				Characters.get(i).setCorporationName(ParsedResponse.get(i).get(2));
				Characters.get(i).setCorporationID(ParsedResponse.get(i).get(3));
			}
			CreateTabItems();
		}
		else if (ApiCalled == getString(R.string.Character_Sheet))
		{
			
			
			
		}
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

		// For each character create a tab and use the characters name as the f
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) 
		{
			actionBar.addTab(actionBar.newTab().setText(mSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) 
	{
		GetCharacterSheet(tab.getPosition());
		
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
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
			return Characters.size();
		}
		
		@Override
		public CharSequence getPageTitle(int position) 
		{
			return Characters.get(position).getName();
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
			
			return rootView;
		}
	}
}
