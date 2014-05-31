package com.kiodev.trailbadger.app;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class StartFragment extends Fragment {

    public static final String KIO = "KIO";

    private Button mCheckInButton;

    private Location mLocation;
    private LocationManager mLocMan;
    private String mLocProvider;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_start,
				container, false);

        // Setup Location management
        mLocMan = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        mLocProvider = mLocMan.getBestProvider(criteria, false);
        mLocation = mLocMan.getLastKnownLocation(mLocProvider);

        // Setup Checkin button
        mCheckInButton = (Button) rootView.findViewById(R.id.Button_CheckIn);
        mCheckInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(KIO, "Checkin clicked");

                getUserLocation();

                if( isOnPeak() ) {
                    addPeak();
                }
                else {
                    Log.d(KIO, "Sorry you are not on a peak");
                }

                // button becomes camera button
            }
        });
		
		return rootView;
	}

    private void getUserLocation() {
        LocationManager service = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!enabled) {
            Log.d(KIO, "GPS is not enabled. Sad face :(");
            Toast.makeText(getActivity(), "Turn on GPS to log a peak", Toast.LENGTH_SHORT).show();
        }
        else {
            Log.d(KIO, "GPS is enabled. You may proceed");

            double lat = mLocation.getLatitude();
            double lng = mLocation.getLongitude();

            Toast.makeText(getActivity(), String.format("Lat:%f Lng: %f", lat, lng),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isOnPeak() {
        Double lat = mLocation.getLatitude();
        Double lng = mLocation.getLongitude();

        // Check Lat

        // Check Lng

        return true;
    }


    private void addPeak() {
        Peak thisPeak = new Peak(mLocation.getLatitude(), mLocation.getLongitude());
        thisPeak.setName("Fake Peak");
        MyHistory.get(getActivity()).addPeak(thisPeak);
        Log.d(KIO, "Added Peak: " + MyHistory.get(getActivity()).getPeak(thisPeak.getId()).toString());
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("KIO", "onPause");
        MyHistory.get(getActivity()).savePeaks();
    }
}
