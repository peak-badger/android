package com.kiodev.trailbadger.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class CommunityFragment extends Fragment {
	
	public static final String TAG = CommunityFragment.class.getSimpleName();

    LinearLayout mLeaderBoard;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_community,
				container, false);

        // Leaderboards
        mLeaderBoard = (LinearLayout) v.findViewById(R.id.LinearLayout_leaderboard);
        mLeaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), LeaderBoardActivity.class);
                startActivity(i);
            }
        });

		
		return v;
	}

}
