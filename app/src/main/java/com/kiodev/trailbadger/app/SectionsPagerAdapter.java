package com.kiodev.trailbadger.app;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.Locale;

/**
 * A {@link android.support.v4.app.FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

    public static final int NUM_SCREENS = 3;
	protected Context mContext;
	
	public SectionsPagerAdapter(Context context, FragmentManager fm) {
		super(fm);
		mContext = context;
	}

	@Override
	public Fragment getItem(int position) {
        Log.d("TAG", "getItem: " + position);
		// getItem is called to instantiate the fragment for the given page.
		switch( position ){
			case 0:
				//Log.d("TAG", "Pos 0");
                return new StartFragment();
            case 1:
                //Log.d("TAG", "Pos 1");
                return new MyStatsFragment();
			case 2:
                //Log.d("TAG", "Pos 2");
				return new CommunityFragment();
			default:
				return null;
		}
	}

	@Override
	public int getCount() {
		return NUM_SCREENS;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		Locale l = Locale.getDefault();
		switch (position) {
		case 0:
			return mContext.getString(R.string.title_section1).toUpperCase(l);
        case 1:
            return mContext.getString(R.string.title_section2).toUpperCase(l);
		case 2:
			return mContext.getString(R.string.title_section3).toUpperCase(l);
		}
		return null;
	}
}