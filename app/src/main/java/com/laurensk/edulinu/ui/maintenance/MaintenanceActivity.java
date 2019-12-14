package com.laurensk.edulinu.ui.maintenance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.laurensk.edulinu.R;
import com.laurensk.edulinu.models.Maintenance;

public class MaintenanceActivity extends AppCompatActivity {

    public static Maintenance maintenance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintenance_activity);

        getSupportActionBar().hide();

        TextView maintenanceModeTitleTextView = findViewById(R.id.maintenanceModeTitleTextView);
        TextView maintenanceModeMessageTextView = findViewById(R.id.maintenanceModeMessageTextView);

        maintenanceModeTitleTextView.setText(maintenance.maintenanceTitle);
        maintenanceModeMessageTextView.setText(maintenance.maintenanceMessage);

        // Check for maintenance mode (and disable it)
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("maintenance").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Maintenance maintenance = dataSnapshot.getValue(Maintenance.class);
                if (maintenance != null && !maintenance.maintenanceMode) {
                    finish();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    @Override
    public void onBackPressed() {
    }
}
