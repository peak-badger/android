package com.kiodev.trailbadger.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MyStatsFragment extends Fragment {

	public static final String TAG = MyStatsFragment.class.getSimpleName();

    TextView mBadgesText;
    TextView mFeetText;
    TextView mGoalsText;

    LinearLayout mGoals;

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

        mGoals = (LinearLayout) v.findViewById(R.id.LinearLayout_badges);
        mGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MyPeaksActivity.class);
                startActivity(i);
            }
        });

        return v;
	}

    private Double getNumFeetClimbed() {
        Double totalFeet = 0.0;

        for(Peak p: mPeaks){
            //totalFeet += p.getHeightFeet();
        }

        return totalFeet;
    }

}
