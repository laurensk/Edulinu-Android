package com.laurensk.edulinu.ui.news;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.laurensk.edulinu.R;
import com.laurensk.edulinu.models.App;
import com.laurensk.edulinu.models.News;
import com.laurensk.edulinu.ui.more.MoreOpenURLActivity;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);

        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        database.child("news").orderByChild("newsId").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<News> newsList = new ArrayList<>();

                for (DataSnapshot newsSnapshot : dataSnapshot.getChildren()) {
                    News news = newsSnapshot.getValue(News.class);
                    newsList.add(news);
                }

                updateList(reverseArrayList(newsList));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void updateList(final ArrayList<News> newsArrayList) {

        NewsListViewAdapter adapter = new NewsListViewAdapter(this, newsArrayList);

        ListView listView = findViewById(R.id.newsListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long l) {

                News news = newsArrayList.get(index);
                onClickList(news);

            }
        });
    }

    private void onClickList(News news) {

        NewsDetailActivity.news = news;
        startActivity(new Intent(this, NewsDetailActivity.class));

    }

    private ArrayList<News> reverseArrayList(ArrayList<News> alist) {

        ArrayList<News> revArrayList = new ArrayList<>();

        for (int i = alist.size() - 1; i >= 0; i--) {
            revArrayList.add(alist.get(i));
        }

        return revArrayList;
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
