package com.laurensk.edulinu;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.laurensk.edulinu.models.MoreEntry;
import com.laurensk.edulinu.models.News;
import com.laurensk.edulinu.ui.appInfo.AppInfoActivity;
import com.laurensk.edulinu.ui.more.MoreOpenURLActivity;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;
import com.onesignal.OneSignalNotificationManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ApplicationInit extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Enable disk persistence
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        // OneSignal Initialization
        OneSignal.startInit(this).inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification).unsubscribeWhenNotificationsAreDisabled(true).setNotificationOpenedHandler(new NotificationHandler()).init();

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

    public void getNewsFromDatabase(final Integer newsId) {

        final DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("news");

        database.orderByChild("newsId").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<News> newsList = new ArrayList<>();

                for (DataSnapshot newsSnapshot : dataSnapshot.getChildren()) {
                    News news = newsSnapshot.getValue(News.class);
                    newsList.add(news);
                }

                for (News news : newsList) {
                    if (news.newsId.equals(newsId)) {
                        startNewsActivity(news);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void startNewsActivity(News news) {
        // TODO: Create NewsPopUpActivity and start it here
        //NewsPopUpActivity.news = news;
        //Intent newsOpenedIntent = new Intent(this, NewsPopUpActivity.class);
        //newsOpenedIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //startActivity(newsOpenedIntent);
    }

    class NotificationHandler implements OneSignal.NotificationOpenedHandler {

        @Override
        public void notificationOpened(OSNotificationOpenResult result) {

            JSONObject data = result.notification.payload.additionalData;

            if (data != null && data.has("openNewsWithId")) {

                Integer newsId = data.optInt("openNewsWithId");
                getNewsFromDatabase(newsId);


            }



        }

    }

}
