package com.laurensk.edulinu.ui.more;

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
import com.laurensk.edulinu.models.MoreEntry;
import com.laurensk.edulinu.models.Teacher;

import java.util.ArrayList;

public class MoreListViewAdapter extends ArrayAdapter<MoreEntry> {

    Context context;
    ArrayList<MoreEntry> moreEntries;

    MoreListViewAdapter(Context context, ArrayList<MoreEntry> moreEntries) {
        super(context, 0, moreEntries);
        this.context = context;
        this.moreEntries = moreEntries;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        MoreEntry moreEntry = moreEntries.get(position);

        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (moreEntry.desc.isEmpty()) {

            View row = layoutInflater.inflate(R.layout.more_row_onlytitle, parent, false);
            TextView moreTitleTextView = row.findViewById(R.id.moreTitleTextView);

            moreTitleTextView.setText(moreEntry.title);

            return row;

        } else {

            View row = layoutInflater.inflate(R.layout.more_row_titledesc, parent, false);
            TextView moreTitleTextView = row.findViewById(R.id.moreTitleTextView);
            TextView moreDescTextView = row.findViewById(R.id.moreDescTextView);

            moreTitleTextView.setText(moreEntry.title);
            moreDescTextView.setText(moreEntry.desc);

            return row;

        }
    }

}