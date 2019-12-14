package com.laurensk.edulinu.models;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.IgnoreExtraProperties;

import java.net.URL;

@IgnoreExtraProperties
public class Maintenance {

    public Boolean maintenanceMode;
    public String maintenanceTitle;
    public String maintenanceMessage;


    public Maintenance() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Maintenance(Boolean maintenanceMode, String maintenanceTitle, String maintenanceMessage) {
        this.maintenanceMode = maintenanceMode;
        this.maintenanceTitle = maintenanceTitle;
        this.maintenanceMessage = maintenanceMessage;
    }

}