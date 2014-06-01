package com.kiodev.trailbadger.app;

import java.util.Random;

public class User {
    private String mName;
    private Double mFeet;
    private Integer mImgResId;

    private static final int[] mLeaderImages = {
            R.drawable.john,
            R.drawable.kelly,
            R.drawable.ryan,
            R.drawable.wally
    };

    public User(String name, Double ft) {
        mName = name;
        mFeet = ft;

        Random randomGenerator = new Random();
        mImgResId =mLeaderImages[randomGenerator.nextInt(4)];
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public Double getFeet() {
        return mFeet;
    }

    public void setFeet(Double ft) {
        this.mFeet = ft;
    }

    public Integer getImgResId(){
        return mImgResId;
    }

    public void setImgResId(Integer id){
        mImgResId = id;
    }
}
