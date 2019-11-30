package com.laurensk.edulinu.ui.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.laurensk.edulinu.R;
import com.laurensk.edulinu.ui.more.MoreAppSettingsActivity;

public class SplashscreenWelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen_welcome);

        getSupportActionBar().hide();

        Button getStartedButton = findViewById(R.id.splashWelcomeButton);
        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), SplashscreenFormActivity.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }
}