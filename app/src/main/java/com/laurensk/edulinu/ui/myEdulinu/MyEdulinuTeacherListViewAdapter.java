package com.laurensk.edulinu.ui.myEdulinu;

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
import com.laurensk.edulinu.models.Teacher;

import java.util.ArrayList;

public class MyEdulinuTeacherListViewAdapter extends ArrayAdapter<Teacher> {

    Context context;
    ArrayList<Teacher> teacherArrayList;

    MyEdulinuTeacherListViewAdapter(Context context, ArrayList<Teacher> teacherArrayList) {
        super(context, 0, teacherArrayList);
        this.context = context;
        this.teacherArrayList = teacherArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Teacher teacher = teacherArrayList.get(position);

        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.teachertable_row, parent, false);
        TextView teacherNameTextView = row.findViewById(R.id.teacherNameTextView);
        TextView teacherDescTextView = row.findViewById(R.id.teacherDescTextView);
        ImageView teacherImageView = row.findViewById(R.id.teacherImageView);
        // now set our resources on views
        teacherNameTextView.setText(teacher.firstName + " " + teacher.lastName);

        if(teacher.desc.isEmpty()) {
            teacherDescTextView.setText(teacher.teacherShort.toUpperCase());
        } else {
            teacherDescTextView.setText(teacher.teacherShort.toUpperCase() + " - " + teacher.desc);
        }

        Glide.with(context).asBitmap().load(teacher.imageURL).into(teacherImageView);

        return row;
    }

}
