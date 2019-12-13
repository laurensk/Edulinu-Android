package com.laurensk.edulinu.models;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.IgnoreExtraProperties;

import java.net.URL;

@IgnoreExtraProperties
public class AppInfoDatabase {

    public String appInfoDatabaseRoot;
    public String appInfoDatabaseVersion;
    public String appInfoDeveloper;
    public Integer appInfoDevelopmentTimeHours;
    public Integer appInfoDevelopmentTimeMinutes;


    public AppInfoDatabase() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public AppInfoDatabase(String appInfoDatabaseRoot, String appInfoDatabaseVersion, String appInfoDeveloper, Integer appInfoDevelopmentTimeHours, Integer appInfoDevelopmentTimeMinutes) {
        this.appInfoDatabaseRoot = appInfoDatabaseRoot;
        this.appInfoDatabaseVersion = appInfoDatabaseVersion;
        this.appInfoDeveloper = appInfoDeveloper;
        this.appInfoDevelopmentTimeHours = appInfoDevelopmentTimeHours;
        this.appInfoDevelopmentTimeMinutes = appInfoDevelopmentTimeMinutes;
    }

}