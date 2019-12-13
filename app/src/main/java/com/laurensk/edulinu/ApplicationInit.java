package com.laurensk.edulinu;

import android.app.Application;
import android.content.SharedPreferences;

import com.google.firebase.database.FirebaseDatabase;
import com.onesignal.OneSignal;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ApplicationInit extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Enable disk persistence
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        // OneSignal Initialization
        OneSignal.startInit(this).inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification).unsubscribeWhenNotificationsAreDisabled(true).init();

        // Send OneSignal tags on app start
        SharedPreferences prefs = getSharedPreferences("Elus", 0);
        Map<String, String> oneSignalTags = new HashMap<>();
        oneSignalTags.put("firstName", prefs.getString("ElusFirstName", "noName"));
        oneSignalTags.put("lastName", prefs.getString("ElusLastName", "noName"));
        oneSignalTags.put("pmsClass", prefs.getString("ElusClass", "noName"));
        oneSignalTags.put("userRole", prefs.getString("ElusUserRole", "noName"));
        oneSignalTags.put("platform", "Android");
        OneSignal.sendTags(new JSONObject(oneSignalTags));

    }

}
