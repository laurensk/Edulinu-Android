package com.laurensk.edulinu.ui.teacherTable;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.laurensk.edulinu.R;
import com.laurensk.edulinu.models.Teacher;

import java.util.ArrayList;


public class TeacherTableFragmentView extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_teachertable, container, false);

        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        database.child("teachers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<Teacher> teachers = new ArrayList<>();

                for (DataSnapshot teachersSnapshot : dataSnapshot.getChildren()) {
                    Teacher teacher = teachersSnapshot.getValue(Teacher.class);
                    teachers.add(teacher);
                }

                updateList(view, teachers);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        return view;
    }


    private void updateList(View view, final ArrayList<Teacher> teachersArrayList) {


        //SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(),itemDataList,R.layout.teachertable_row, new String[]{"teacherImage","teacherName","teacherDesc"},new int[]{R.id.teacherImageView, R.id.teacherNameTextView, R.id.teacherDescTextView});

        TeacherTableListViewAdapter adapter = new TeacherTableListViewAdapter(getActivity(), teachersArrayList);

        ListView listView = view.findViewById(R.id.teacherListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long l) {

                Teacher teacher = teachersArrayList.get(index);

                onClickList(teacher);


            }
        });
    }

    private void onClickList(Teacher teacher) {

        TeacherTableWebViewActivity.teacherName = teacher.firstName + " " + teacher.lastName;

        if (teacher.hasPortal) {
            TeacherTableWebViewActivity.teacherUrl = teacher.portalURL;
            startActivity(new Intent(getActivity(), TeacherTableWebViewActivity.class));
        } else {

            AlertDialog.Builder noPortalAlertBuilder = new AlertDialog.Builder(getContext());
            noPortalAlertBuilder.setTitle("Kein Portal");
            noPortalAlertBuilder.setMessage(genderToText(teacher) + " " + teacher.lastName + " hat kein Portal.");
            noPortalAlertBuilder.setCancelable(true);

            noPortalAlertBuilder.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog noPortalAlert = noPortalAlertBuilder.create();
            noPortalAlert.show();

        }

    }

    private String genderToText(Teacher teacher) {

        if (teacher.gender == "w") {
            return "Frau";
        } else if (teacher.gender == "m") {
            return "Herr";
        }

        return teacher.firstName;
    }
}