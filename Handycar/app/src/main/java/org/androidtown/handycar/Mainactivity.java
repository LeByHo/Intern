package org.androidtown.handycar;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.github.mikephil.charting.data.BarEntry;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yalantis.phoenix.PullToRefreshView;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by LEE on 2017-07-27.
 */

public class Mainactivity extends AppCompatActivity {
    Intent intent;
    PullToRefreshView mPullToRefreshView;
    public static int chk = 0;
    MainViewAdapter adapter;
    DatabaseReference mDatebase = FirebaseDatabase.getInstance().getReference();
    public static String car;
    public static SpendAdapter fadapter,madapter,tadapter;
    public static Map<String, String> hashMap3 =  new HashMap<String, String>();
    public static TreeMap<String, String> tm3= new TreeMap<String, String>(Collections.reverseOrder());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fadapter = new SpendAdapter();
        madapter = new SpendAdapter();
        tadapter = new SpendAdapter();
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFFFF));
        ListView listview = (ListView) findViewById(R.id.list_view);
        adapter = new MainViewAdapter();
        listview.setAdapter(adapter);
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.m_car));
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.break_oil));
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.settings));
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.graph));
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0)
                    intent = new Intent(getApplicationContext(), StartActivity.class);
                else if (position == 1)
                    intent = new Intent(getApplicationContext(), f_main.class);
                else if (position == 2)
                    intent = new Intent(getApplicationContext(), m_main.class);
                else if (position == 3) {
                    intent = new Intent(getApplicationContext(), totalmain.class);
                    intent.setFlags(1);
                }
                startActivity(intent);
            }
        });
        mPullToRefreshView = (PullToRefreshView) findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            public static final long REFRESH_DELAY = 100;

            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                    }
                }, REFRESH_DELAY);
            }
        });
        mDatebase.child("inform").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Firebaseinfo fire = dataSnapshot.getValue(Firebaseinfo.class);
                String k = fire.getCate() + "/" + fire.getPlace() + "/" + fire.getPrice();
                hashMap3.put(fire.getDate(), k);
                tm3.putAll(hashMap3);

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

    @Override
    protected void onStart() {
        super.onStart();
        mDatebase.child("car").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                car = dataSnapshot.getValue(String.class);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        Iterator<String> iterator = tm3.keySet().iterator();
        while (iterator.hasNext()) {
            String info = (String) iterator.next();
            String[] split = tm3.get(info).split("/");
            int ct = 0;
            if (split[0].equals("fuel")) {
                for (int a = 0; a < fadapter.getCount(); a++) {
                    if (fadapter.listViewItemList.get(a).getText().equals(info) && fadapter.listViewItemList.get(a).getPlace().equals(split[1])&&fadapter.listViewItemList.get(a).getPrice().equals(toNumFormat(Integer.parseInt(split[2])) + "원"))
                        ct = 1;
                }
                if (ct == 0) {
                    fadapter.addf(info, split[2]);
                    fadapter.addItem(ContextCompat.getDrawable(Mainactivity.this, R.drawable.break_oil), info, split[1], toNumFormat(Integer.parseInt(split[2])) + "원");
                    tadapter.addItem(ContextCompat.getDrawable(Mainactivity.this, R.drawable.break_oil), info, split[1], toNumFormat(Integer.parseInt(split[2])) + "원");
                }
            }
            if (split[0].equals("fix")) {
                for (int a = 0; a < madapter.getCount(); a++) {
                    if (madapter.listViewItemList.get(a).getText().equals(info) && madapter.listViewItemList.get(a).getPlace().equals(split[1]) && madapter.listViewItemList.get(a).getPrice().equals(toNumFormat(Integer.parseInt(split[2])) + "원"))
                        ct = 1;
                }
                if (ct == 0) {
                    madapter.addf(info, split[2]);
                    madapter.addItem(ContextCompat.getDrawable(Mainactivity.this, R.drawable.settings), info, split[1], toNumFormat(Integer.parseInt(split[2])) + "원");
                    tadapter.addItem(ContextCompat.getDrawable(Mainactivity.this, R.drawable.settings), info, split[1], toNumFormat(Integer.parseInt(split[2])) + "원");
                }
            }
        }
    }
    public static String toNumFormat(int num) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(num);
    }
}
