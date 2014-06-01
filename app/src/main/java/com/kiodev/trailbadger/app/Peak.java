package com.kiodev.trailbadger.app;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

public class Peak {

    private static final String JSON_ID = "id";
    private static final String JSON_LAT="title";
    private static final String JSON_LNG = "solved";
    private static final String JSON_NAME = "date";

    public static final String JSON_HEIGHT_FEET = "height_feet";
    public static final String JSON_HEIGHT_METER = "height_feet";


    private UUID mId;

    private Double mLat;
    private Double mLng;
    private String mName;

    private Double mHeightFeet;
    private Double mHeightMeter;

    private ArrayList<String> mRegions;
    private ArrayList<String> mCountries;
    private String mContinent;

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

        if (json.has(JSON_HEIGHT_FEET)){
            mHeightFeet = json.getDouble(JSON_HEIGHT_FEET);
        }

        if (json.has(JSON_HEIGHT_METER)){
            mHeightMeter = json.getDouble(JSON_HEIGHT_METER);
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

    public Double getLat() { return mLat; }

    public void setLat(Double lat) { mLat = lat; }

    public Double getLng() { return mLng; }

    public void setLng(Double lng) { mLng = lng; }

    public Double getHeightFeet() { return mHeightFeet; }

    public void setHeightFeet(Double heightFeet) { mHeightFeet = heightFeet; }

    public Double getHeightMeter() { return mHeightMeter; }

    public void setHeightMeter(Double heightMeter) { mHeightMeter = heightMeter; }

    public ArrayList<String> getRegions() { return mRegions; }

    public void setRegion(String region) { mRegions.add(region); }

    public ArrayList<String> getCountries() { return mCountries; }

    public void setCountry(String country) { mCountries.add(country); }

    public String getContinent() { return mContinent; }

    public void setContinent(String continent) { mContinent = continent; }

}
