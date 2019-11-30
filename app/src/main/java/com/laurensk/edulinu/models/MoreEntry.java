package com.laurensk.edulinu.models;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.IgnoreExtraProperties;

import java.net.URL;

@IgnoreExtraProperties
public class MoreEntry {

    public String URL;
    public String desc;
    public Boolean enabledAndroid;
    public Integer id;
    public String onClickAction;
    public String section;
    public String title;


    public MoreEntry() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public MoreEntry(String URL, String desc, Boolean enabledAndroid, Integer id, String onClickAction, String section, String title) {
        this.URL = URL;
        this.desc = desc;
        this.enabledAndroid = enabledAndroid;
        this.id = id;
        this.onClickAction = onClickAction;
        this.section = section;
        this.title = title;
    }

}