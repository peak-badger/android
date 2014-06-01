package com.kiodev.trailbadger.app;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.RelativeLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {
	
	public static final String TAG = MainActivity.class.getSimpleName();

    public static final String EXTRA_PEAK = "com.kiodev.trailbadger.app.peak";

    public static JSONObject PEAK_DATA = null;

	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;

    public static final int[] mTabImages = {
        R.drawable.mountain,
        R.drawable.feet_climbed,
        R.drawable.community
    };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_main);
		
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			actionBar.addTab(actionBar.newTab()
                    .setIcon(mTabImages[i])
                    .setTabListener(this));
		}

        // Load the JSON Object
        loadJSONFromAsset();
	}

    public JSONObject loadJSONFromAsset() {
        JSONObject jsonObject = PEAK_DATA;

        // Only load JSON once
        if (jsonObject == null) {
            String jsonString = null;
            Log.d(TAG, "loadJSONFromAsset()");

            try {

                InputStream is = getAssets().open("_index.geojson");

                int size = is.available();

                byte[] buffer = new byte[size];

                is.read(buffer);

                is.close();

                jsonString = new String(buffer, "UTF-8");
                try {
                    PEAK_DATA = new JSONObject(jsonString);
                } catch (JSONException e) {
                    Log.e(TAG, "JSON Error: ", e);
                }

            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
        }

        return jsonObject;
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int itemId = item.getItemId();

        switch(itemId) {
            case R.id.action_my_account:
                Intent i = new Intent(MainActivity.this, FacebookLoginActivity.class);
                startActivity(i);
                break;
        }

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.

        RelativeLayout tabLayout = (RelativeLayout) tab.getCustomView();
        tab.setCustomView(tabLayout);

		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}


}
