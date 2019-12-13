package com.laurensk.edulinu.ui.appInfo;

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
import com.laurensk.edulinu.models.AppInfo;
import com.laurensk.edulinu.models.Teacher;

import java.util.ArrayList;

class AppInfoListViewAdapter extends ArrayAdapter<AppInfo> {

    Context context;
    ArrayList<AppInfo> appInfoArrayList;

    AppInfoListViewAdapter(Context context, ArrayList<AppInfo> appInfoArrayList) {
        super(context, 0, appInfoArrayList);
        this.context = context;
        this.appInfoArrayList = appInfoArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        AppInfo appInfo = appInfoArrayList.get(position);

        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View row = layoutInflater.inflate(R.layout.app_info_row, parent, false);
        TextView appInfoTitleTextView = row.findViewById(R.id.appInfoTitleTextView);
        TextView appInfoDescTextView = row.findViewById(R.id.appInfoDescTextView);

        appInfoTitleTextView.setText(appInfo.title);
        appInfoDescTextView.setText(appInfo.desc);

        return row;


    }
}