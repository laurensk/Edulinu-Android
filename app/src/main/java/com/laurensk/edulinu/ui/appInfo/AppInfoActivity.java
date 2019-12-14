package com.laurensk.edulinu.ui.appInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.laurensk.edulinu.R;
import com.laurensk.edulinu.models.App;
import com.laurensk.edulinu.models.AppInfo;
import com.laurensk.edulinu.models.AppInfoDatabase;

import java.util.ArrayList;

public class AppInfoActivity extends AppCompatActivity {

    ListView appInfoListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_info);

        appInfoListView = findViewById(R.id.appInfoListView);

        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        database.child("appInfo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<AppInfo> appInfoList = new ArrayList<>();

                AppInfoDatabase appInfoDatabase = dataSnapshot.getValue(AppInfoDatabase.class);

                String versionName = "";
                String versionCode = "";

                try {
                    PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
                    versionName = pInfo.versionName;
                    versionCode = String.valueOf(pInfo.versionCode);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                appInfoList.add(new AppInfo(0, "App-Version", versionName));
                appInfoList.add(new AppInfo(1, "App-Build", versionCode));
                appInfoList.add(new AppInfo(2, "Datenbank-Version", getValueOrDefault(appInfoDatabase.appInfoDatabaseVersion, "Fehler beim Laden.")));
                appInfoList.add(new AppInfo(3, "Datenbank-Root", getValueOrDefault(appInfoDatabase.appInfoDatabaseRoot, "Fehler beim Laden.")));
                appInfoList.add(new AppInfo(4, "Entwickler", getValueOrDefault(appInfoDatabase.appInfoDeveloper, "Fehler beim Laden.")));
                appInfoList.add(new AppInfo(5, "Entwicklungszeit", getValueOrDefault(appInfoDatabase.appInfoDevelopmentTimeHours + " h " + appInfoDatabase.appInfoDevelopmentTimeMinutes + " min", "Fehler beim Laden.")));


                updateList(appInfoList);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void updateList(ArrayList<AppInfo> appInfoList) {

        AppInfoListViewAdapter adapter = new AppInfoListViewAdapter(this, appInfoList);

        ListView listView = findViewById(R.id.appInfoListView);
        listView.setAdapter(adapter);

    }

    public static <T> T getValueOrDefault(T value, T defaultValue) {
        return value == null ? defaultValue : value;
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
