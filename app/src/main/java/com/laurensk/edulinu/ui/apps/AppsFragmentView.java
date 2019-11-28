package com.laurensk.edulinu.ui.apps;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.laurensk.edulinu.R;
import com.laurensk.edulinu.models.App;
import com.laurensk.edulinu.ui.apps.AppsListViewAdapter;

import java.util.ArrayList;

public class AppsFragmentView extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_apps, container, false);

        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        database.child("apps").orderByChild("id").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<App> apps = new ArrayList<>();

                for (DataSnapshot appsSnapshot : dataSnapshot.getChildren()) {
                    App app = appsSnapshot.getValue(App.class);
                    apps.add(app);
                }

                updateList(view, apps);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

    private void updateList(View view, final ArrayList<App> appsArrayList) {

        AppsListViewAdapter adapter = new AppsListViewAdapter(getActivity(), appsArrayList);

        ListView listView = view.findViewById(R.id.appsListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long l) {

                App app = appsArrayList.get(index);

                onClickList(app);
            }
        });
    }

    private void onClickList(App app) {

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(app.playStoreLink));
        startActivity(browserIntent);



    }

}
