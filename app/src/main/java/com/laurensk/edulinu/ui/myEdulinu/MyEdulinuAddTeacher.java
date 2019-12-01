package com.laurensk.edulinu.ui.myEdulinu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.laurensk.edulinu.R;
import com.laurensk.edulinu.helpers.PreferencesHelper;
import com.laurensk.edulinu.helpers.TeacherHelpers;
import com.laurensk.edulinu.models.Teacher;

import java.util.ArrayList;

public class MyEdulinuAddTeacher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myedulinu_add_teacher);

        final DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("teachers");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<Teacher> teachers = new ArrayList<>();

                for (DataSnapshot teachersSnapshot : dataSnapshot.getChildren()) {
                    Teacher teacher = teachersSnapshot.getValue(Teacher.class);
                    teachers.add(teacher);
                }

                updateList(teachers);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void updateList(final ArrayList<Teacher> teachersArrayList) {

        MyEdulinuTeacherListViewAdapter adapter = new MyEdulinuTeacherListViewAdapter(this, teachersArrayList);

        ListView listView = findViewById(R.id.myEdulinuAddTeacherListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long l) {

                Teacher teacher = teachersArrayList.get(index);

                onClickList(teacher);
            }
        });
    }

    private void onClickList(final Teacher teacher) {

        if (teacher.hasPortal) {

            addTeacher(teacher);

            finish();

        } else {

            AlertDialog.Builder noPanelWarningAlertBuilder = new AlertDialog.Builder(this);
            noPanelWarningAlertBuilder.setTitle("Kein Portal");
            noPanelWarningAlertBuilder.setMessage(TeacherHelpers.genderToText(teacher) + " " + teacher.lastName + " hat kein Portal.");
            noPanelWarningAlertBuilder.setCancelable(true);

            noPanelWarningAlertBuilder.setPositiveButton(
                    "Abbrechen",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            noPanelWarningAlertBuilder.setNegativeButton(
                    "Trotzdem hinzufügen",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            addTeacher(teacher);

                            finish();
                        }
                    });

            AlertDialog deleteTeacherAlert = noPanelWarningAlertBuilder.create();
            deleteTeacherAlert.show();

        }




    }

    private void addTeacher(Teacher teacher) {

        ArrayList<String> favTeachers = PreferencesHelper.getArrayPrefs("favTeachers", this);
        favTeachers.add(teacher.teacherShort);

        PreferencesHelper.setArrayPrefs("favTeachers", favTeachers, this);

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
