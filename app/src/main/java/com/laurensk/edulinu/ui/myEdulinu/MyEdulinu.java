package com.laurensk.edulinu.ui.myEdulinu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.laurensk.edulinu.R;
import com.laurensk.edulinu.helpers.TeacherHelpers;
import com.laurensk.edulinu.helpers.PreferencesHelper;
import com.laurensk.edulinu.models.Teacher;
import com.laurensk.edulinu.ui.teacherTable.TeacherTableWebViewActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MyEdulinu extends Fragment {

    ImageView linuImageView = null;
    ImageView dayTimeIcon = null;
    TextView greetingTextView = null;
    TextView nameTextView = null;
    TextView dateTextView = null;
    ListView myEdulinuTeachersListView = null;

    ArrayList<Teacher> Teachers = new ArrayList<>();
    private View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_myedulinu, container, false);

        linuImageView = view.findViewById(R.id.linuImageView);
        dayTimeIcon = view.findViewById(R.id.dayTimeIcon);
        greetingTextView = view.findViewById(R.id.greetingTextView);
        nameTextView = view.findViewById(R.id.nameTextView);
        dateTextView = view.findViewById(R.id.dateTextView);
        myEdulinuTeachersListView = view.findViewById(R.id.myEdulinuTeachersListView);

        final DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("teachers");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<Teacher> teachers = new ArrayList<>();

                for (DataSnapshot teachersSnapshot : dataSnapshot.getChildren()) {
                    Teacher teacher = teachersSnapshot.getValue(Teacher.class);
                    teachers.add(teacher);
                }

                Teachers = teachers;

                prepareListUpdate();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dayTimeSetup();

        FloatingActionButton floatingActionButton = view.findViewById(R.id.addTeacherButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MyEdulinuAddTeacher.class));
            }
        });

        return view;
    }

    private void prepareListUpdate() {

        ArrayList<String> favTeachers = PreferencesHelper.getArrayPrefs("favTeachers", getContext());

        ArrayList<Teacher> matchedTeachers = new ArrayList<>();
        matchedTeachers = matchTeachers(favTeachers, Teachers);

        updateList(matchedTeachers);

    }

    private void updateList(final ArrayList<Teacher> teachersArrayList) {

        Log.i("teachersArrayList", teachersArrayList.toString());

        MyEdulinuTeacherListViewAdapter adapter = new MyEdulinuTeacherListViewAdapter(getActivity(), teachersArrayList);

        ListView listView = (ListView) view.findViewById(R.id.myEdulinuTeachersListView);

        listView.setEmptyView(view.findViewById(R.id.myEdulinuEmptyListItem));

        listView.setAdapter(adapter);

        listView.setLongClickable(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long l) {

                Teacher teacher = teachersArrayList.get(index);

                onClickList(teacher);
            }

        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int index, long l) {

                Teacher teacher = teachersArrayList.get(index);

                onLongClickList(teacher, index);

                return true;
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
            noPortalAlertBuilder.setMessage(TeacherHelpers.genderToText(teacher) + " " + teacher.lastName + " hat kein Portal.");
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

    private void onLongClickList(final Teacher teacher, final Integer index) {

        AlertDialog.Builder deleteTeacherAlertBuilder = new AlertDialog.Builder(getContext());
        deleteTeacherAlertBuilder.setTitle("Lehrer entfernen");
        deleteTeacherAlertBuilder.setMessage("Möchtest du " + teacher.firstName + " " + teacher.lastName + " wirklich entfernen?");
        deleteTeacherAlertBuilder.setCancelable(true);

        deleteTeacherAlertBuilder.setPositiveButton(
                "Abbrechen",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        deleteTeacherAlertBuilder.setNegativeButton(
                "Lehrer entfernen",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        deleteTeacher(index, teacher);
                    }
                });

        AlertDialog deleteTeacherAlert = deleteTeacherAlertBuilder.create();
        deleteTeacherAlert.show();

    }

    private void deleteTeacher(final Integer index, Teacher teacher) {

        ArrayList<String> favTeachers = PreferencesHelper.getArrayPrefs("favTeachers", getContext());
        favTeachers.remove(favTeachers.get(index));

        PreferencesHelper.setArrayPrefs("favTeachers", favTeachers, getContext());

        Snackbar.make(view, teacher.firstName + " " + teacher.lastName + " wurde von My Edulinu entfernt.", Snackbar.LENGTH_LONG).setAction("teacherDeleted", null).show();

        prepareListUpdate();



    }

    private void dayTimeSetup() {

        SharedPreferences prefs = getContext().getSharedPreferences("Elus", 0);
        String userRole = prefs.getString("ElusUserRole", "student");

        if (userRole.equals("student")) {
            String firstName = prefs.getString("ElusFirstName", "noName");
            nameTextView.setText(firstName);
        } else {
            String firstName = prefs.getString("ElusFirstName", "noName");
            String lastName = prefs.getString("ElusLastName", "noName");
            nameTextView.setText(firstName + " " + lastName);
        }


        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, d. MMMM yyyy", Locale.GERMAN);
        String formattedDate = formatter.format(date);

        dateTextView.setText(formattedDate);

        Calendar rightNow = Calendar.getInstance();
        int hour = rightNow.get(Calendar.HOUR_OF_DAY);

        if (hour >= 6 && hour <= 9) {
            greetingTextView.setText(R.string.greeting_morning);
            dayTimeIcon.setImageResource(R.mipmap.daytime_sunrise);
        } else if (hour >= 10 && hour <= 17) {
            greetingTextView.setText(R.string.greeting_day);
            dayTimeIcon.setImageResource(R.mipmap.daytime_sun);
        } else if (hour >= 18 && hour <= 20) {
            greetingTextView.setText(R.string.greeting_evening);
            dayTimeIcon.setImageResource(R.mipmap.daytime_sunset);
        } else if (hour >= 21 && hour <= 24) {
            greetingTextView.setText(R.string.greeting_night);
            dayTimeIcon.setImageResource(R.mipmap.daytime_moon);
        } else if (hour >= 0 && hour <= 5) {
            greetingTextView.setText(R.string.greeting_night);
            dayTimeIcon.setImageResource(R.mipmap.daytime_moon);
        } else {
            greetingTextView.setText(R.string.greeting_welcome);
            dayTimeIcon.setImageResource(R.mipmap.daytime_sun);
        }

    }

    private ArrayList<Teacher> matchTeachers(ArrayList<String> favTeachers, ArrayList<Teacher> allTeachers) {

        ArrayList<Teacher> matchedTeachers = new ArrayList<>();

        for (String teacherToMatch : favTeachers) {

            for (Teacher teacher : allTeachers) {

                if (teacher.teacherShort.equals(teacherToMatch)) {

                    matchedTeachers.add(teacher);

                }

            }

        }

        return matchedTeachers;

    }

    @Override
    public void onResume() {
        super.onResume();
        prepareListUpdate();
    }
}