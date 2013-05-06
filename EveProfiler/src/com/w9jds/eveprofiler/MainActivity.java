package com.w9jds.eveprofiler;

import java.util.ArrayList;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.TextView;

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
	
	private void GetCharacterImage(int Position)
	{
		ArrayList<Object> Info = new ArrayList<Object>();
		Info.add(getString(R.string.Character_Portrait));
		Info.add(Characters.get(Position).getCharacterID());
		Info.add("128");
		Info.add(this);
		new CallImageApi().execute(Info);
	}
	
	public void ApiResponse(String XMLResponse, String ApiCalled)
	{
		if (ApiCalled.equals(getString(R.string.List_Characters)) == true)
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
		else if (ApiCalled.equals(getString(R.string.Character_Sheet)) == true)
		{
			ArrayList<ArrayList<String>> ParseResponse = new ParseXml().ParseCharacterSheet(XMLResponse);
			for(int i = 0; i < ParseResponse.size(); i++)
			{
				if (ParseResponse.get(i).get(0).equals("DoB") == true)
					Characters.get(mViewPager.getCurrentItem()).setDateOfBirth(ParseResponse.get(i).get(1));
				else if (ParseResponse.get(i).get(0).equals("race") == true)
					Characters.get(mViewPager.getCurrentItem()).setRace(ParseResponse.get(i).get(1));
				else if (ParseResponse.get(i).get(0).equals("bloodLine") == true)
					Characters.get(mViewPager.getCurrentItem()).setBloodLine(ParseResponse.get(i).get(1));
				else if (ParseResponse.get(i).get(0).equals("ancestry") == true)
					Characters.get(mViewPager.getCurrentItem()).setAncestry(ParseResponse.get(i).get(1));
				else if (ParseResponse.get(i).get(0).equals("allianceName") == true)
					Characters.get(mViewPager.getCurrentItem()).setAllianceName(ParseResponse.get(i).get(1));
				else if (ParseResponse.get(i).get(0).equals("allianceID") == true)
					Characters.get(mViewPager.getCurrentItem()).setAllianceID(ParseResponse.get(i).get(1));
				else if (ParseResponse.get(i).get(0).equals("cloneName") == true)
					Characters.get(mViewPager.getCurrentItem()).setCloneName(ParseResponse.get(i).get(1));
				else if (ParseResponse.get(i).get(0).equals("cloneSkillPoints") == true)
					Characters.get(mViewPager.getCurrentItem()).setCloneSkillPoints(ParseResponse.get(i).get(1));
				else if (ParseResponse.get(i).get(0).equals("balance") == true)
					Characters.get(mViewPager.getCurrentItem()).setWalletBalance(ParseResponse.get(i).get(1));
				else if (ParseResponse.get(i).get(0).equals("intelligence") == true)
					Characters.get(mViewPager.getCurrentItem()).setIntelligence(ParseResponse.get(i).get(1));
				else if (ParseResponse.get(i).get(0).equals("memory") == true)
					Characters.get(mViewPager.getCurrentItem()).setMemory(ParseResponse.get(i).get(1));
				else if (ParseResponse.get(i).get(0).equals("charisma") == true)
					Characters.get(mViewPager.getCurrentItem()).setCharisma(ParseResponse.get(i).get(1));
				else if (ParseResponse.get(i).get(0).equals("perception") == true)
					Characters.get(mViewPager.getCurrentItem()).setPerception(ParseResponse.get(i).get(1));
				else if (ParseResponse.get(i).get(0).equals("willpower") == true)
					Characters.get(mViewPager.getCurrentItem()).setWillpower(ParseResponse.get(i).get(1));
				
				
				
			}
			
			
			UpdateCharacterView();
		}
	}
	
	private void UpdateCharacterView()
	{
		TextView Clone = (TextView) mViewPager.getChildAt(mViewPager.getCurrentItem()).findViewById(R.id.Clone);
		Clone.setText(Characters.get(mViewPager.getCurrentItem()).getCloneName() + " (" + Characters.get(mViewPager.getCurrentItem()).getCloneSkillPoints() + ")");
		TextView DOB = (TextView) mViewPager.getChildAt(mViewPager.getCurrentItem()).findViewById(R.id.DateOBirth);
		DOB.setText(Characters.get(mViewPager.getCurrentItem()).getDateOfBirth());
		TextView Background = (TextView) mViewPager.getChildAt(mViewPager.getCurrentItem()).findViewById(R.id.Background);
		Background.setText(Characters.get(mViewPager.getCurrentItem()).getRace() + " - " + Characters.get(mViewPager.getCurrentItem()).getBloodLine() + " - " + Characters.get(mViewPager.getCurrentItem()).getAncestry());
		TextView Corporation = (TextView) mViewPager.getChildAt(mViewPager.getCurrentItem()).findViewById(R.id.Corporation);
		Corporation.setText(Characters.get(mViewPager.getCurrentItem()).getCorporationName());
		TextView Alliance = (TextView) mViewPager.getChildAt(mViewPager.getCurrentItem()).findViewById(R.id.Alliance);
		Alliance.setText(Characters.get(mViewPager.getCurrentItem()).getAllianceName());
		
		
		
		
	}
	
	
	
	public void ApiImageResponse(byte[] response, String ApiCalled)
	{
		if (ApiCalled.equals(getString(R.string.Character_Portrait)) == true)
		{
			Characters.get(mViewPager.getCurrentItem()).setCharacterPortrait(response);
			ImageView image = (ImageView) mViewPager.getChildAt(mViewPager.getCurrentItem()).findViewById(R.id.CapsuleerPortrait);
			Bitmap bMap = BitmapFactory.decodeByteArray(response, 0, response.length);
			image.setImageBitmap(bMap);
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
		int Position = tab.getPosition();

		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(Position);
		
		if (Characters.get(Position).getCharacterPortrait() == null)
			GetCharacterImage(Position);
		
		if (Characters.get(Position).getCloneSkillPoints() == null)
			GetCharacterSheet(Position);
		
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
