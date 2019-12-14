package com.laurensk.edulinu.ui.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.laurensk.edulinu.R;
import com.laurensk.edulinu.models.App;
import com.laurensk.edulinu.models.News;

import java.util.ArrayList;

class NewsListViewAdapter extends ArrayAdapter<News> {

    Context context;
    ArrayList<News> newsArrayList;

    NewsListViewAdapter(Context context, ArrayList<News> newsArrayList) {
        super(context, 0, newsArrayList);
        this.context = context;
        this.newsArrayList = newsArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        News news = newsArrayList.get(position);

        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View row = layoutInflater.inflate(R.layout.news_row, parent, false);
        TextView newsListTitleTextView = row.findViewById(R.id.newsListTitleTextView);
        TextView newsListDescTextView = row.findViewById(R.id.newsListDescTextView);
        ImageView newsListImageView = row.findViewById(R.id.newsListImageView);

        newsListTitleTextView.setText(news.title);
        newsListDescTextView.setText(news.desc);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(30));
        Glide.with(context).asBitmap().load(news.imageUrl).apply(requestOptions).into(newsListImageView);

        return row;

    }
}