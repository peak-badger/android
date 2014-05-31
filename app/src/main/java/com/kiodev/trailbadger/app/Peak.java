package com.kiodev.trailbadger.app;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class Peak {

    private static final String JSON_ID = "id";
    private static final String JSON_LAT="title";
    private static final String JSON_LNG = "solved";
    private static final String JSON_NAME = "date";

    private UUID mId;

    private Double mLat;
    private Double mLng;
    private String mName;
//
//    private Double mHeightFeet;
//    private Double mHeightMeter;
//
//    private String mRegion;
//    private String mCountry;
//    private String mContinent;

    public Peak(Double lat, Double lng) {
        // Generate a unique identifier
        mId = UUID.randomUUID();
        mLat = lat;
        mLng = lng;
    }

    public Peak( JSONObject json ) throws JSONException {
        mId = UUID.fromString(json.getString(JSON_ID));

        if (json.has(JSON_NAME)) {
            mName = json.getString(JSON_NAME);
        }

        if (json.has(JSON_LAT)) {
            mLat = json.getDouble(JSON_LAT);
        }

        if (json.has(JSON_LNG)) {
            mLng = json.getDouble(JSON_LNG);
        }

    }

    public JSONObject toJSON() throws JSONException {

        JSONObject json = new JSONObject();

        json.put(JSON_ID, mId.toString());
        json.put(JSON_NAME, mName);
        json.put(JSON_LAT, mLat);
        json.put(JSON_LNG, mLng);

        return json;
    }

    public UUID getId(){
        return mId;
    }

    public String getName(){
        return mName;
    }

    public void setName(String peakName){
        mName = peakName;
    }
}
