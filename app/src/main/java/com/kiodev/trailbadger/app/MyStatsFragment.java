package com.kiodev.trailbadger.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MyStatsFragment extends Fragment {

	public static final String TAG = MyStatsFragment.class.getSimpleName();

    TextView mBadgesText;
    TextView mFeetText;
    TextView mGoalsText;

    LinearLayout mBadges;
    LinearLayout mGoals;
    LinearLayout mFeet;

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

        // Badges
        mBadgesText = (TextView) v.findViewById(R.id.TextView_badges);
        String numBadgesString = getResources().getString(R.string.badges);
        Integer numBadges = mPeaks.size();
        mBadgesText.setText(String.format(numBadgesString, numBadges.toString()));

        mBadges = (LinearLayout) v.findViewById(R.id.LinearLayout_badges);
        mBadges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MyBadgesActivity.class);
                startActivity(i);
            }
        });

        // Feet
        mFeetText = (TextView) v.findViewById(R.id.TextView_feet);
        String ftClimbedString = getResources().getString(R.string.feet_climbed);
        Double ftClimbed = getNumFeetClimbed();
        DecimalFormat formatter = new DecimalFormat("#,###");
        mFeetText.setText(String.format(ftClimbedString, formatter.format(ftClimbed)));

        mFeet = (LinearLayout) v.findViewById(R.id.LinearLayout_feet);
        mFeet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MyPeaksActivity.class);
                startActivity(i);
            }
        });

        // Goals
        mGoalsText = (TextView) v.findViewById(R.id.TextView_goals);
        String goalsString = getResources().getString(R.string.goals_reached);
        Integer goals = 0;
        mGoalsText.setText(String.format(goalsString, goals.toString()));

        return v;
	}

    private Double getNumFeetClimbed() {
        Double totalFeet = 0.0;

        for(Peak p: mPeaks){
            Log.d(TAG, p.getName());

            if(p.getLat() != null){
                Log.d(TAG, p.getLat().toString());
            }

            if(p.getHeightFeet() != null){
                Log.d(TAG, p.getHeightFeet().toString());
            }

            totalFeet += p.getHeightFeet();
        }

        return totalFeet;
    }

}
