package com.laurensk.edulinu.models;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.IgnoreExtraProperties;

import java.net.URL;

@IgnoreExtraProperties
public class AppInfo {

    public Integer id;
    public String title;
    public String desc;


    public AppInfo() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public AppInfo(Integer id, String title, String desc) {
        this.id = id;
        this.title = title;
        this.desc = desc;
    }

}