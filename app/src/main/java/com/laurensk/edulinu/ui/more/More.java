package com.laurensk.edulinu.ui.more;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.laurensk.edulinu.R;
import com.laurensk.edulinu.helpers.PreferencesHelper;
import com.laurensk.edulinu.models.MoreEntry;
import com.laurensk.edulinu.ui.splashscreen.SplashscreenDoneActivity;
import com.laurensk.edulinu.ui.splashscreen.SplashscreenWelcomeActivity;

import java.util.ArrayList;

public class More extends Fragment {

    private View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_more, container, false);

        final DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("more/moreEntries");

        database.orderByChild("id").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<MoreEntry> moreEntries = new ArrayList<>();

                for (DataSnapshot moreEntriesSnapshot : dataSnapshot.getChildren()) {
                    MoreEntry moreEntry = moreEntriesSnapshot.getValue(MoreEntry.class);
                    moreEntries.add(moreEntry);
                }

                prepareUpdateList(moreEntries);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;
    }

    private void prepareUpdateList(ArrayList<MoreEntry> moreEntries) {

        ArrayList<MoreEntry> matchedMoreEntries = matchEntries(moreEntries);

        updateList(matchedMoreEntries);

    }

    private void updateList(final ArrayList<MoreEntry> moreEntries) {

        MoreListViewAdapter adapter = new MoreListViewAdapter(getActivity(), moreEntries);

        ListView listView = view.findViewById(R.id.moreListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long l) {

                MoreEntry moreEntry = moreEntries.get(index);

                onClickAction(moreEntry);



            }
        });
    }

    private void onClickAction(MoreEntry moreEntry) {

        switch(moreEntry.onClickAction){
            case "openURL":
                openURL(moreEntry.URL, moreEntry.title);
                break;
            case "bugReport":
                bugReport(moreEntry);
                break;
            case "updateClass":
                updateClass();
                break;
            case "resetApp":
                resetApp();
                break;
            case "appInfo":
                appInfo();
                break;
            case "openMail":
                openMail(moreEntry.URL, moreEntry.title);
                break;
            case "openSettings":
                openSettings();
                break;
            case "openNews":
                openNews();
                break;
            default:
                onClickActionNotAvailable();
        }

    }

    private void openURL(String url, String title) {

        MoreOpenURLActivity.moreEntryTitle = title;
        MoreOpenURLActivity.moreEntryUrl = url;
        startActivity(new Intent(getActivity(), MoreOpenURLActivity.class));

    }

    private void bugReport(MoreEntry moreEntry) {

        openURL(moreEntry.URL, moreEntry.title);

    }

    private void updateClass() {

        final String[] allClassrooms = getResources().getStringArray(R.array.all_classrooms);

        final NumberPicker numberPicker = new NumberPicker(getActivity());
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(allClassrooms.length-1);

        numberPicker.setDisplayedValues(allClassrooms);
        numberPicker.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(numberPicker);
        builder.setTitle("Klasse ändern");
        builder.setMessage("Bitte aktualisiere deine Klasse.");

        builder.setPositiveButton("Klasse ändern", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                int pos = numberPicker.getValue();
                String selectedClass = allClassrooms[pos];

                if (pos == 0) {

                    dialog.dismiss();

                    AlertDialog.Builder updateClassErrorAlertBuilder = new AlertDialog.Builder(getContext());
                    updateClassErrorAlertBuilder.setTitle("Klasse auswählen");
                    updateClassErrorAlertBuilder.setMessage("Bitte wähle eine Klasse aus.");
                    updateClassErrorAlertBuilder.setCancelable(true);

                    updateClassErrorAlertBuilder.setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    updateClass();
                                }
                            });

                    AlertDialog updateClassErrorAlert = updateClassErrorAlertBuilder.create();
                    updateClassErrorAlert.show();


                } else {

                    SharedPreferences prefs = getContext().getSharedPreferences("Elus", 0);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("ElusClass", selectedClass);
                    editor.apply();

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

    private void resetApp() {

        AlertDialog.Builder resetAppAlertBuilder = new AlertDialog.Builder(getContext());
        resetAppAlertBuilder.setTitle("Bist du sicher?");
        resetAppAlertBuilder.setMessage("Die Edulinu-App wird zurückgesetzt und kann dann neu eingerichtet werden.");
        resetAppAlertBuilder.setCancelable(true);

        resetAppAlertBuilder.setPositiveButton(
                "Abbrechen",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        resetAppAlertBuilder.setNegativeButton(
                "App zurücksetzen",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                        SharedPreferences prefs = getContext().getSharedPreferences("Elus", 0);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("ElusFirstName", "");
                        editor.putString("ElusLastName", "");
                        editor.putString("ElusClass", "");
                        editor.putString("ElusUserRole", "");
                        editor.putBoolean("ElusDidSplash", false);

                        ArrayList<String> favTeachers = new ArrayList<>();
                        PreferencesHelper.setArrayPrefs("favTeachers", favTeachers, getContext());

                        editor.apply();

                        startActivity(new Intent(getContext(), SplashscreenWelcomeActivity.class));
                    }
                });

        AlertDialog resetAppAlert = resetAppAlertBuilder.create();
        resetAppAlert.show();

    }

    private void appInfo() {
        // TODO: Build appInfo activity and start it here
    }

    private void openMail(String mail, String title) {

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse(mail));
        startActivity(Intent.createChooser(emailIntent, title));

    }

    private void openSettings() {
        startActivity(new Intent(getActivity(), MoreAppSettingsActivity.class));
    }

    private void openNews() {
        // TODO: Build openNews activity and start it here
    }


    private void onClickActionNotAvailable() {

        AlertDialog.Builder onClickActionNotAvailableAlertBuilder = new AlertDialog.Builder(getContext());
        onClickActionNotAvailableAlertBuilder.setTitle("App-Version veraltet");
        onClickActionNotAvailableAlertBuilder.setMessage("Bitte aktualisiere die Edulinu-App im Play Store, um diese Funktion nutzen zu können.");
        onClickActionNotAvailableAlertBuilder.setCancelable(true);

        onClickActionNotAvailableAlertBuilder.setPositiveButton(
                "Abbrechen",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        onClickActionNotAvailableAlertBuilder.setNegativeButton(
                "Zum Play Store",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        // TODO: Open Play Store
                    }
                });

        AlertDialog onClickActionNotAvailableAlert = onClickActionNotAvailableAlertBuilder.create();
        onClickActionNotAvailableAlert.show();

    }

    private ArrayList<MoreEntry> matchEntries(ArrayList<MoreEntry> allMoreEntries) {

        ArrayList<MoreEntry> matchedMoreEntries = new ArrayList<>();

        for (MoreEntry moreEntry : allMoreEntries) {

            if (moreEntry.enabledAndroid) {

                matchedMoreEntries.add(moreEntry);

            }

        }

        return matchedMoreEntries;
    }

    @Override
    public void onResume() {
        super.onResume();


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String signature = prefs.getString("signature", "lol");
        Log.i("settings signature", signature);

    }
}
