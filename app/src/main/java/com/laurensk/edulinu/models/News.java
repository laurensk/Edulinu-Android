package com.laurensk.edulinu.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class News {

    public String author;
    public String desc;
    public String imageURL;
    public Integer newsId;
    public String text;
    public String title;


    public News() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public News(String author, String desc, String imageURL, Integer newsId, String text, String title) {
        this.author = author;
        this.desc = desc;
        this.imageURL = imageURL;
        this.newsId = newsId;
        this.text = text;
        this.title = title;
    }

}