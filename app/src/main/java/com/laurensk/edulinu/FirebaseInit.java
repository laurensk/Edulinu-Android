package com.laurensk.edulinu;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class FirebaseInit extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        /* Enable disk persistence  */
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

}
