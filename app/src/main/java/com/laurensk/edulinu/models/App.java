package com.laurensk.edulinu.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class App {

    public String desc;
    public Integer id;
    public String imageURL;
    public String playStoreLink;
    public String title;


    public App() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public App(String desc, Integer id, String imageURL, String playStoreLink, String title) {
        this.desc = desc;
        this.id = id;
        this.imageURL = imageURL;
        this.playStoreLink = playStoreLink;
        this.title = title;
    }

}