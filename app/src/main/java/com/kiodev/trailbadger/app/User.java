package com.kiodev.trailbadger.app;

public class User {
    private String mName;
    private Double mFeet;

    public User(String name, Double ft) {
        mName = name;
        mFeet = ft;
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
}
