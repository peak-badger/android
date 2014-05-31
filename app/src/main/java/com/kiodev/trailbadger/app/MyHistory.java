package com.kiodev.trailbadger.app;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

public class MyHistory {

    private static final String TAG = "MyHistory";
    private static final String FILENAME = "peaks.json";

    private ArrayList<Peak> mPeaks;

    private static MyHistory sMyHistory;
    private Context mAppContext;
    MyHistoryJSONSerializer mSerializer;

    // Constructor
    private MyHistory(Context appContext) {
        mAppContext = appContext;
        mSerializer = new MyHistoryJSONSerializer(mAppContext, FILENAME);

        try {
            mPeaks = mSerializer.loadPeaks();
        } catch(Exception e) {
            mPeaks = new ArrayList<Peak>();
            Log.e(TAG, "Error loading peaks: ", e);
        }
    }

    // Create Singleton
    public static MyHistory get(Context c){
        // If the singleton doesn't exist yet, create it.
        if ( sMyHistory == null ) {
            // Don't assume that context 'c' will always be what you expect!  Be
            //    extra safe and call the method to make sure.
            sMyHistory = new MyHistory(c.getApplicationContext());
        }

        return sMyHistory;
    }

    // Add a peak to the list
    public void addPeak(Peak p){
        mPeaks.add(p);
    }

    // Delete a peak
    public void deletePeak(Peak p){
        Log.d(TAG, "Deleting peak: " + p.getName());
        mPeaks.remove(p);
    }

    // Return entire list of peaks
    public ArrayList<Peak> getPeaks() {
        return mPeaks;
    }

    // Return only a specific peak
    public Peak getPeak(UUID id) {
        for(Peak p : mPeaks) {
            if(p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    // Save all peaks to private app sandbox
    public boolean savePeaks(){
        try{
            mSerializer.savePeaks(mPeaks);
            Log.d(TAG, "Peaks saved to file");
            return true;
        } catch (Exception e) {
            Log.d(TAG, "Error saving peaks: ", e);
            return false;
        }
    }
}


