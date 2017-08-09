package org.androidtown.handycar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GE62 on 2017-07-28.
 */

public class Carinfo extends AppCompatActivity {
    ListViewAdapter adapter;
    ListView listview;
    String gname;
    ArrayList<ListViewItem> itemList = new ArrayList<ListViewItem>();
    DatabaseReference mDatebase = FirebaseDatabase.getInstance().getReference();
    Map<String, Integer> hashMap =  new HashMap<String, Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        gname = bundle.getString("name");

        adapter = new ListViewAdapter(itemList);
        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter);

        mDatebase.child("group").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Groupinfo ginfo = dataSnapshot.getValue(Groupinfo.class);
                if(ginfo.getGname().equals(gname)){
                    hashMap = ginfo.getHashMap();
                }
                for ( String key : hashMap.keySet() ) {
                    if (hashMap.get(key) == 1)
                     adapter.addItem(ContextCompat.getDrawable(Carinfo.this, R.drawable.car), key);
                }
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