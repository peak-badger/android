package com.kiodev.trailbadger.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyStatsFragment extends Fragment {

	public static final String TAG = MyStatsFragment.class.getSimpleName();

    TextView mBadgesText;
    TextView mFeetText;
    TextView mGoalsText;

    private ArrayList<Peak> mPeaks;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        // Get singleton and then get list of crimes
        mPeaks = MyHistory.get(getActivity()).getPeaks();
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_my_stats,
				container, false);

        mBadgesText = (TextView) v.findViewById(R.id.TextView_badges);
        String numBadgesString = getResources().getString(R.string.badges);
        Integer numBadges = mPeaks.size();
        mBadgesText.setText(String.format(numBadgesString, numBadges.toString()));

        mFeetText = (TextView) v.findViewById(R.id.TextView_feet);
        String ftClimbedString = getResources().getString(R.string.feet_climbed);
        Double ftClimbed = getNumFeetClimbed();
        mFeetText.setText(String.format(ftClimbedString, ftClimbed.toString()));

        mGoalsText = (TextView) v.findViewById(R.id.TextView_goals);
        String goalsString = getResources().getString(R.string.goals_reached);
        Integer goals = 0;
        mGoalsText.setText(String.format(goalsString, goals.toString()));

        return v;
	}

    private Double getNumFeetClimbed() {
        Double totalFeet = 0.0;

        for(Peak p: mPeaks){
            //totalFeet += p.getHeightFeet();
        }

        return totalFeet;
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onresume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        MyHistory.get(getActivity()).savePeaks();
    }

    private class PeakAdapter extends ArrayAdapter<Peak> {

        public PeakAdapter(ArrayList<Peak> peaks) {
            super(getActivity(), 0, peaks);
        }

        // Overriding this method is what allows us to use the custom list. This
        // is what gets called
        // behind the scenes when ListView and adapter have their conversations
        // about what to display
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Log.d(TAG, "getView: " + position);

            // If we weren't given a view, then inflate one
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(
                        R.layout.list_item_peak, null);
            }

            // Configure the view for this specific peak
            Peak p = getItem(position);

            TextView titleTextView = (TextView) convertView.findViewById(R.id.peak_name);
            titleTextView.setText(p.getName());

            return convertView;
        }

    }

}
