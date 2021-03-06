package com.handycar.b2c;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by GE62 on 2017-07-28.
 */

public class Carinfo extends AppCompatActivity {
    ListViewAdapter adapter;
    ListView listview;
    String gname;
    ArrayList<ListViewItem> itemList = new ArrayList<ListViewItem>();
    FirebaseOptions options = new FirebaseOptions.Builder()
            .setApplicationId("1:51453844849:android:51579fa183019e0b") // Required for Analytics.
            .setApiKey("AIzaSyBVMO8soca7w7qQ9vsQhhHTD-L6JQphA2E") // Required for Auth.
            .setDatabaseUrl("https://handycar-c8cf9.firebaseio.com") // Required for RTDB.
            .build();
    DatabaseReference mDatebase;
    Map<String, Integer> hashMap = new HashMap<String, Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFFFF));
        Random random = new Random();
        int x = random.nextInt(1000);
        FirebaseApp.initializeApp(this /* Context */, options, "cinfo"+x);
        FirebaseApp secondary = FirebaseApp.getInstance("cinfo"+x);
        FirebaseDatabase secondaryDatabase = FirebaseDatabase.getInstance(secondary);
        mDatebase = secondaryDatabase.getReference();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        gname = bundle.getString("name");

        adapter = new ListViewAdapter(itemList);
        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter);

        mDatebase.child("group").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                hashMap.clear();
                Groupinfo ginfo = dataSnapshot.getValue(Groupinfo.class);
                if (ginfo.getGname().equals(gname)) {
                    hashMap = ginfo.getHashMap();
                }
                for (final String key : hashMap.keySet()) {
                    mDatebase.child("cinform").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            FirebaseCar fire = dataSnapshot.getValue(FirebaseCar.class);
                            if (fire.getName().equals(key)) {
                                if (fire.getCate().equals("BMW"))
                                    adapter.addItem(ContextCompat.getDrawable(Carinfo.this, R.drawable.bmw), key);
                                else if (fire.getCate().equals("AUDI"))
                                    adapter.addItem(ContextCompat.getDrawable(Carinfo.this, R.drawable.audi), key);
                                else if (fire.getCate().equals("BENZ"))
                                    adapter.addItem(ContextCompat.getDrawable(Carinfo.this, R.drawable.benz), key);
                                else if (fire.getCate().equals("FERRARI"))
                                    adapter.addItem(ContextCompat.getDrawable(Carinfo.this, R.drawable.ferrari), key);
                                else if (fire.getCate().equals("JENESIS"))
                                    adapter.addItem(ContextCompat.getDrawable(Carinfo.this, R.drawable.jenesis), key);
                                else
                                    adapter.addItem(ContextCompat.getDrawable(Carinfo.this, R.drawable.car2), key);
                            }
                            Comparator<ListViewItem> noDesc = new Comparator<ListViewItem>() {
                                @Override
                                public int compare(ListViewItem item1, ListViewItem item2) {
                                    return (item1.getText().compareTo(item2.getText()));
                                }
                            };
                            Collections.sort(itemList, noDesc);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}