package com.kiodev.trailbadger.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class StartFragment extends Fragment {

    public static final String KIO = "KIO";
    public static final String TAG = StartFragment.class.getSimpleName();

    private ImageButton mCheckInButton;

    private Location mLocation;
    private LocationManager mLocMan;
    private String mLocProvider;

    private Double mMinTargetLat;
    private Double mMaxTargetLat;

    private Double mMinTargetLng;
    private Double mMaxTargetLng;

    private JSONObject mRealPeak;

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
        mCheckInButton = (ImageButton) rootView.findViewById(R.id.Button_CheckIn);
        mCheckInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(KIO, "Checkin clicked");

                getUserLocation();

                if( isOnPeak() ) {
                    addPeak();
                }
                else {
                    Log.d(TAG, "Sorry you are not on a peak");
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

                    // set title
                    alertDialogBuilder.setTitle(R.string.not_on_peak);

                    // set dialog message
                    alertDialogBuilder
                            .setMessage(R.string.not_on_peak_details)
                            .setCancelable(false)
                            .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // if this button is clicked, just close
                                    // the dialog box and do nothing
                                    dialog.cancel();
                                }
                            });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
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
            Toast.makeText(getActivity(), "You must turn on GPS to log a peak", Toast.LENGTH_SHORT).show();
        }
        else {
            Log.d(KIO, "GPS is enabled. You may proceed");

            // Make sure its the most recent location
            mLocation = mLocMan.getLastKnownLocation(mLocProvider);

            double lat = mLocation.getLatitude();
            double lng = mLocation.getLongitude();

            Log.d(TAG, String.format("Lat:%f Lng: %f", lat, lng));
        }
    }

    private boolean isOnPeak() {

        Boolean isOnPeak = false;

        Double lat;
        Double lng;

        // TODO - KIO Test only - Get random points
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(6);

        switch(randomInt) {
            case 0:
                Log.d(TAG, "Using current location");
                lat = mLocation.getLatitude();
                lng = mLocation.getLongitude();
                break;
            case 1:
                Log.d(TAG, "Using Longs Peak");
                lat = 40.2547;
                lng = -105.615;
                break;
            case 2:
                Log.d(TAG, "Using Maroon Bells");
                lat = 39.0708;
                lng = -106.989;
                break;
            case 3:
                Log.d(TAG, "Using Pikes Peak");
                lat = 38.8406;
                lng = -105.044;
                break;
            case 4:
                Log.d(TAG, "Using Crested Butte");
                lat = 38.8833;
                lng = -106.943;
                break;
            case 5:
                Log.d(TAG, "Using Pyramid Peak");
                lat = 39.0714;
                lng = -106.95;
                break;
            default:
                Log.d(TAG, "Using current location");
                lat = mLocation.getLatitude();
                lng = mLocation.getLongitude();
        }

        JSONObject jsonObject = MainActivity.PEAK_DATA;
        JSONArray jsonArray;
        JSONObject jsonFeatures;
        JSONObject mountain = null;

        Double thisLat = 0.0;
        Double thisLng = 0.0;

        setMyBox(lat, lng);

        if( jsonObject != null) {
            Log.d(TAG, "Yay we got some JSON");

            // Parse to array
            jsonArray = jsonObject.optJSONArray("features");

            // Loop through array
            for(int i = 0; i < jsonArray.length(); i++) {
                try {
                    jsonFeatures = (JSONObject) jsonArray.get(i);

                    if (jsonFeatures != null) {
                        mountain = jsonFeatures.optJSONObject("properties");
                        thisLat = mountain.optDouble("latitude");
                        thisLng = mountain.optDouble("longitude");

                        if ( isMountainInMyBox(thisLat, thisLng) ){
                            Log.d(TAG, "Our mountain is: " + mountain.optString("name"));
                            mRealPeak = mountain;
                            isOnPeak = true;
                            break;
                        }

                    }

                } catch (JSONException e) {
                    Log.e(TAG, "JSON Exception ", e);
                }
            }

        }

        return isOnPeak;
    }

    private void setMyBox(Double lat, Double lng) {

        mMinTargetLat = lat - 0.1;
        mMaxTargetLat = lat + 0.1;

        mMinTargetLng = lng - 0.1;
        mMaxTargetLng = lng + 0.1;

    }


    private boolean isMountainInMyBox(Double thisLat, Double thisLng){
        boolean isInMyBox = false;

        //Log.d(TAG, "isMountainInMyBox");

        if ( (thisLat > mMinTargetLat) &&
             (thisLat < mMaxTargetLat) &&
             (thisLng > mMinTargetLng) &&
             (thisLng < mMaxTargetLng) )  {

            Log.d(TAG, "ThisLat: " + thisLat);
            Log.d(TAG, "ThisLng: " + thisLng);

            isInMyBox = true;
        }
        return isInMyBox;
    }

    private void addPeak() {

        // Start new Activity to Add Peak
        Intent i = new Intent(getActivity(), SavePeakActivity.class);
        i.putExtra(MainActivity.EXTRA_PEAK, mRealPeak.toString());

        getActivity().startActivity(i);

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("KIO", "onPause");
        MyHistory.get(getActivity()).savePeaks();
    }
}
