package com.laurensk.edulinu.ui.news;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.laurensk.edulinu.R;
import com.laurensk.edulinu.models.News;

public class NewsPopUpActivity extends AppCompatActivity {

    public static News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_pop_up_activity);

        getSupportActionBar().hide();

        TextView newsPopUpTitleTextView = findViewById(R.id.newsPopUpTitleTextView);
        TextView newsPopUpAuthorTextView = findViewById(R.id.newsPopUpAuthorTextView);
        TextView newsPopUpMessageTextView = findViewById(R.id.newsPopUpMessageTextView);
        Button newsPopUpDismissButton = findViewById(R.id.newsPopUpDismissButton);

        newsPopUpTitleTextView.setText(news.title);
        newsPopUpAuthorTextView.setText(news.author);
        newsPopUpMessageTextView.setText(news.text);
        newsPopUpMessageTextView.setMovementMethod(new ScrollingMovementMethod());

        newsPopUpDismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
