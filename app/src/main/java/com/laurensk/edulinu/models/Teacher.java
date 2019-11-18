package com.laurensk.edulinu.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Teacher {

    public String desc;
    public String firstName;
    public String gender;
    public Boolean hasPortal;
    public Integer id;
    public String imageURL;
    public String lastName;
    public String portalURL;
    public String teacherShort;


    public Teacher() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Teacher(String desc, String firstName, String gender, Boolean hasPortal, Integer id, String imageURL, String lastName, String portalURL, String teacherShort) {
        this.desc = desc;
        this.firstName = firstName;
        this.gender = gender;
        this.hasPortal = hasPortal;
        this.id = id;
        this.imageURL = imageURL;
        this.lastName = lastName;
        this.portalURL = portalURL;
        this.teacherShort = teacherShort;
    }

}