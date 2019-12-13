package com.laurensk.edulinu.ui.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.laurensk.edulinu.R;
import com.onesignal.OneSignal;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SplashscreenFormActivity extends AppCompatActivity {

    EditText firstNameEditText;
    EditText lastNameEditText;
    Button selectClassButton;
    Button nextButton;
    String selectedClassPicker = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen_form);

        getSupportActionBar().hide();

        firstNameEditText = findViewById(R.id.splashFormFirstNameEditText);
        lastNameEditText = findViewById(R.id.splashFormLastNameEditText);
        selectClassButton = findViewById(R.id.splashFormSelectClassButton);
        nextButton = findViewById(R.id.splashFormButton);

        selectClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClass();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateForm(firstNameEditText.getText().toString(), lastNameEditText.getText().toString(), selectedClassPicker);
            }
        });




    }

    private void validateForm(String firstName, String lastName, String selectedClass) {

        String validFirstName;
        String validLastName;
        String validClass;
        String validUserRole;

        if(firstName.isEmpty() || lastName.isEmpty() || selectedClass.isEmpty()) {
            invalidForm();
        } else {
            validFirstName = firstName;
            validLastName = lastName;

            if (selectedClass.equals("Ich bin ein Elternteil")) {
                validClass = "noClass";
                validUserRole = "parent";
            } else if (selectedClass.equals("Ich bin Lehrer/in")) {
                validClass = "noClass";
                validUserRole = "teacher";
            } else {
                validClass = selectedClass;
                validUserRole = "student";
            }

            saveValidatedFormEntries(validFirstName, validLastName, validClass, validUserRole);
        }

    }

    private void saveValidatedFormEntries(String firstName, String lastName, String validClass, String userRole) {

        SharedPreferences prefs = getApplication().getSharedPreferences("Elus", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("ElusFirstName", firstName);
        editor.putString("ElusLastName", lastName);
        editor.putString("ElusClass", validClass);
        editor.putString("ElusUserRole", userRole);
        editor.putBoolean("ElusDidSplash", true);
        editor.apply();

        Map<String, String> oneSignalTags = new HashMap<>();
        oneSignalTags.put("firstName", firstName);
        oneSignalTags.put("lastName", lastName);
        oneSignalTags.put("pmsClass", validClass);
        oneSignalTags.put("userRole", userRole);
        oneSignalTags.put("platform", "Android");

        sendFormEntriesToOneSignal(oneSignalTags);


    }

    private void sendFormEntriesToOneSignal(Map<String, String> oneSignalTags) {

        OneSignal.sendTags(new JSONObject(oneSignalTags));

        formFinished();

    }

    private void formFinished() {
        startActivity(new Intent(this, SplashscreenDoneActivity.class));
    }

    private void invalidForm() {

        AlertDialog.Builder invalidFormAlertBuilder = new AlertDialog.Builder(this);
        invalidFormAlertBuilder.setTitle("Hinweis");
        invalidFormAlertBuilder.setMessage("Bitte fülle alle Felder korrekt aus.");
        invalidFormAlertBuilder.setCancelable(true);

        invalidFormAlertBuilder.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog invalidFormAlert = invalidFormAlertBuilder.create();
        invalidFormAlert.show();

    }

    private void setClass() {

        final String[] allClassrooms = getResources().getStringArray(R.array.all_classrooms_and_roles);

        final NumberPicker numberPicker = new NumberPicker(this);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(allClassrooms.length-1);

        numberPicker.setDisplayedValues(allClassrooms);
        numberPicker.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(numberPicker);
        builder.setTitle("Klasse auswählen");
        builder.setMessage("Bitte wähle deine aktuelle Klasse aus.");

        builder.setPositiveButton("Speichern", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                int pos = numberPicker.getValue();
                String selectedClass = allClassrooms[pos];

                if (pos == 0) {

                    dialog.dismiss();
                    selectedInavlidClass();


                } else {

                    selectedClassPicker = selectedClass;
                    selectClassButton.setText("Klasse auswählen (" + selectedClass + ")");
                    dialog.dismiss();

                }

            }
        });

        builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.create();
        builder.show();

    }

    private void selectedInavlidClass() {

        AlertDialog.Builder updateClassErrorAlertBuilder = new AlertDialog.Builder(this);
        updateClassErrorAlertBuilder.setTitle("Klasse auswählen");
        updateClassErrorAlertBuilder.setMessage("Bitte wähle eine Klasse aus.");
        updateClassErrorAlertBuilder.setCancelable(true);

        updateClassErrorAlertBuilder.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        setClass();
                    }
                });

        AlertDialog updateClassErrorAlert = updateClassErrorAlertBuilder.create();
        updateClassErrorAlert.show();

    }
}
