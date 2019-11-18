package com.laurensk.edulinu.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Student {

    public String name;


    public Student() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Student(String name) {
        this.name = name;
    }

}