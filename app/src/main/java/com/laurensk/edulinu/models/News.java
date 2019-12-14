package com.laurensk.edulinu.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class News {

    public String author;
    public String desc;
    public String imageUrl;
    public Integer newsId;
    public String text;
    public String title;


    public News() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public News(String author, String desc, String imageUrl, Integer newsId, String text, String title) {
        this.author = author;
        this.desc = desc;
        this.imageUrl = imageUrl;
        this.newsId = newsId;
        this.text = text;
        this.title = title;
    }

}