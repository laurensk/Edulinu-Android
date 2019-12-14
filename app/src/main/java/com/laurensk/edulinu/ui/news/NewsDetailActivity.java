package com.laurensk.edulinu.ui.news;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.laurensk.edulinu.R;
import com.laurensk.edulinu.models.News;

public class NewsDetailActivity extends AppCompatActivity {

    public static News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail_view);

        TextView newsDetailTitleTextView = findViewById(R.id.newsDetailTitleTextView);
        TextView newsDetailAuthorTextView = findViewById(R.id.newsDetailAuthorTextView);
        TextView newsDetailMessageTextView = findViewById(R.id.newsDetailMessageTextView);

        newsDetailTitleTextView.setText(news.title);
        newsDetailAuthorTextView.setText(news.author);
        newsDetailMessageTextView.setText(news.text);
        newsDetailMessageTextView.setMovementMethod(new ScrollingMovementMethod());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
