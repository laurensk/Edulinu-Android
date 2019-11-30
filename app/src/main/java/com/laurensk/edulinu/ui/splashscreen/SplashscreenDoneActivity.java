package com.laurensk.edulinu.ui.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.laurensk.edulinu.MainActivity;
import com.laurensk.edulinu.R;

public class SplashscreenDoneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen_done);

        getSupportActionBar().hide();

        TextView titleTextView = findViewById(R.id.splashDoneTitleTextView);
        Button doneButton = findViewById(R.id.splashDoneButton);

        SharedPreferences prefs = getApplication().getSharedPreferences("Elus", 0);
        String firstName = prefs.getString("ElusFirstName", "noName");

        titleTextView.setText("Hallo, " + firstName + "!");

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), MainActivity.class));
            }
        });




    }

    @Override
    public void onBackPressed() {
    }
}
