package com.kiodev.trailbadger.app;

import android.content.Context;
import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StartFragment extends Fragment {

    public static final String KIO = "KIO";
    public static final String TAG = StartFragment.class.getSimpleName();

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

        Boolean isOnPeak = false;

        Double lat = mLocation.getLatitude();
        Double lng = mLocation.getLongitude();


        JSONObject jsonObject = MainActivity.PEAK_DATA;
        JSONArray jsonArray = null;
        JSONObject mountain = null;
        JSONObject jsonFeatures = null;

        String name = null;
        String continent = null;

//        Lat: 39.7334722400
//        Lng: -104.99265809

        Double minTargetLat = 39.0;
        Double maxTargetLat = 40.0;

        Double minTargetLng = -105.0;
        Double maxTargetLng = -104.0;


        Double thisLat = 0.0;
        Double thisLng = 0.0;

        ArrayList<JSONObject> potentialMountains = new ArrayList<JSONObject>();


        if( jsonObject != null) {
            Log.d(TAG, "Yay we got some JSON");
            name = jsonObject.optString("type");
            jsonArray = jsonObject.optJSONArray("features");

            for(int i = 0; i < jsonArray.length(); i++) {
                try {
                    jsonFeatures = (JSONObject) jsonArray.get(i);

                    if (jsonFeatures != null) {
                        mountain = jsonFeatures.optJSONObject("properties");
                        thisLat = mountain.optDouble("latitude");
                        //Log.d(TAG, String.format("Iteration %d, Lat: %f", i, thisLat));
                        if ( (thisLat > minTargetLat) && (thisLat < maxTargetLat) ){
                            Log.d(TAG, "ThisLat: " + thisLat);
                            Log.d(TAG, "This mountain: " + mountain.optString("name"));
                            potentialMountains.add(mountain);
                        }
                    }

                } catch (JSONException e) {
                    Log.e(TAG, "JSON Exception ", e);
                }
            }

            // Now loop through potential mountains on Lng, if there are any
            if (potentialMountains.size() > 0){
                for(int i = 0; i < potentialMountains.size(); i++){
                    thisLng = potentialMountains.get(i).optDouble("longitude");

                    if ( (thisLng > minTargetLng) && (thisLng < maxTargetLng) ){
                        Log.d(TAG, "ThisLng: " + thisLng);
                        Log.d(TAG, "This mountain: " + potentialMountains.get(i).optString("name"));
                    }
                }
            }

            if ( mountain != null ) {
                // this is our mountain
                Log.d(TAG, "Our mountain is: " + mountain.optString("name"));
            }
        }

        isOnPeak = true;

        return isOnPeak;
    }



    private void addPeak() {

        // Start new Activity to Add Peak
        Intent i = new Intent(getActivity(), SavePeakActivity.class);
        i.putExtra(MainActivity.EXTRA_LAT, mLocation.getLatitude());
        i.putExtra(MainActivity.EXTRA_LNG, mLocation.getLongitude());
        getActivity().startActivity(i);

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("KIO", "onPause");
        MyHistory.get(getActivity()).savePeaks();
    }
}
