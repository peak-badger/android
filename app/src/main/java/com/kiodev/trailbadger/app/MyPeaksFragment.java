package com.kiodev.trailbadger.app;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyPeaksFragment extends ListFragment {
	
	public static final String TAG = MyPeaksFragment.class.getSimpleName();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_my_peaks,
				container, false);
		
		return rootView;
	}
	
	@Override
	public void onResume() {
		super.onResume();

	}
}
