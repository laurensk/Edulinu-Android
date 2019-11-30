package com.laurensk.edulinu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.laurensk.edulinu.models.Teacher;
import com.laurensk.edulinu.ui.more.MoreOpenURLActivity;
import com.laurensk.edulinu.ui.splashscreen.SplashscreenWelcomeActivity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getApplication().getSharedPreferences("Elus", 0);

        boolean elusDidSplash = prefs.getBoolean("ElusDidSplash", false);

        if (!elusDidSplash) {

            startActivity(new Intent(this, SplashscreenWelcomeActivity.class));

        } else {

            setContentView(R.layout.activity_main);

            BottomNavigationView navView = findViewById(R.id.nav_view);
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_myedulinu, R.id.navigation_teachertable, R.id.navigation_mails, R.id.navigation_apps, R.id.navigation_more)
                    .build();

            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(navView, navController);

        }



    }

}
