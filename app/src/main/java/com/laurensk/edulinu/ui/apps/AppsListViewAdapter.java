package com.laurensk.edulinu.ui.apps;

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
import com.laurensk.edulinu.R;
import com.laurensk.edulinu.models.App;
import com.laurensk.edulinu.models.Teacher;

import java.util.ArrayList;

class AppsListViewAdapter extends ArrayAdapter<App> {

    Context context;
    ArrayList<App> appsArrayList;

    AppsListViewAdapter(Context context, ArrayList<App> appsArrayList) {
        super(context, 0, appsArrayList);
        this.context = context;
        this.appsArrayList = appsArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        App app = appsArrayList.get(position);

        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (app.desc.isEmpty()) {

            View row = layoutInflater.inflate(R.layout.apps_row_onlytitle, parent, false);
            TextView appNameTextView = row.findViewById(R.id.appNameTextView);
            ImageView appImageView = row.findViewById(R.id.appImageView);

            appNameTextView.setText(app.title);

            Glide.with(context).asBitmap().load(app.imageURL).into(appImageView);

            return row;

        } else {

            View row = layoutInflater.inflate(R.layout.apps_row_titledesc, parent, false);
            TextView appNameTextView = row.findViewById(R.id.appNameTextView);
            TextView appDescTextView = row.findViewById(R.id.appDescTextView);
            ImageView appImageView = row.findViewById(R.id.appImageView);

            appNameTextView.setText(app.title);
            appDescTextView.setText(app.desc);

            Glide.with(context).asBitmap().load(app.imageURL).into(appImageView);

            return row;

        }


    }
}