package com.handycar.b2c;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yalantis.phoenix.PullToRefreshView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
    public static SpendAdapter fadapter, madapter, tadapter, f2adapter, m2adapter;
    public static ArrayList<ListViewItem> itemList = new ArrayList<ListViewItem>();
    public static ArrayList<ListViewItem> itemList1 = new ArrayList<ListViewItem>();
    public static ArrayList<ListViewItem> itemList2 = new ArrayList<ListViewItem>();
    public static Map<String, Integer> hashMap = new HashMap<String, Integer>();
    public static TreeMap tm;
    public static Map<String, Integer> hashMap2 = new HashMap<String, Integer>();
    public static TreeMap tm2;
    public static String cate;
    String str1,str2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        str1 = "https://api-uat.fs-dev.uk/handycar/account/v2/device";
        str2 = "https://api-uat.fs-dev.uk/firebase/authorise";
        showDialog(1);
        new Thread() {
            @Override
            public void run() {
                try {
                    getConnect(str2);
                    InputStream in = new BufferedInputStream(getConnectionurl(str1).getInputStream());
                    JSONObject json = new JSONObject(getStringFromInputStream(in));
                    parseJSON(json);
                    Log.d("JSON",json+"");
                    removeDialog(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

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
    }
    public HttpURLConnection getConnect(String str) {
        try {
            URL url = new URL(str);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            con.setDoInput(true);

            JSONObject json = new JSONObject();
            json.put("client_id", "IF7AaAjtTMWP5q8T4oYRjNlaGNUUClDP");
            json.put("access_token", Login.token);

            OutputStream out = con.getOutputStream();
            out.write(json.toString().getBytes());
            out.flush();
            Log.d("suc",con.getResponseCode()+"");
            return con;
        } catch (Exception e) {
            return null;
        }
    }

    public HttpURLConnection getConnectionurl(String str) {
        try {
            URL url = new URL(str);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Authorization", "Bearer "+Login.token);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            Log.d("suc",con.getResponseCode()+"");
            return con;
        } catch (Exception e) {
            return null;
        }
    }

    private void parseJSON(JSONObject json) throws JSONException {
        arrayToobject(json.getJSONObject("RESULT").getJSONArray("OIL"));
    }

    private void arrayToobject(JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject order = jsonArray.getJSONObject(i);
        }
    }

    private static String getStringFromInputStream(InputStream is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getinform();
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("정보를 가져오는 중...");
        //dialog.setCancelable(false);
        return dialog;
    }
    public void getinform(){
        itemList.clear();
        itemList1.clear();
        itemList2.clear();
        setup();
        mDatebase.child("cinform").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                FirebaseCar car1 = dataSnapshot.getValue(FirebaseCar.class);
                if (car1.getCheck() == 1) {
                    car = car1.getName();
                    cate = car1.getCate();
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
        mDatebase.child("inform").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fadapter = new SpendAdapter(itemList);
                f2adapter = new SpendAdapter(itemList);
                madapter = new SpendAdapter(itemList1);
                m2adapter = new SpendAdapter(itemList1);
                tadapter = new SpendAdapter(itemList2);
                Firebaseinfo fire = dataSnapshot.getValue(Firebaseinfo.class);
                if (fire.getName().equals(car)) {
                    if (fire.getCate().equals("fuel")) {
                        fadapter.addItem(ContextCompat.getDrawable(Mainactivity.this, R.drawable.break_oil), fire.getDate(), fire.getPlace(), toNumFormat(Integer.parseInt(fire.getPrice())) + "원", 0);
                        addf(fire.getDate(), fire.getPrice());
                        tadapter.addItem(ContextCompat.getDrawable(Mainactivity.this, R.drawable.break_oil), fire.getDate(), fire.getPlace(), toNumFormat(Integer.parseInt(fire.getPrice())) + "원", 0);
                    }

                    if (fire.getCate().equals("fix")) {
                        madapter.addItem(ContextCompat.getDrawable(Mainactivity.this, R.drawable.settings), fire.getDate(), fire.getPlace(), toNumFormat(Integer.parseInt(fire.getPrice())) + "원", 0);
                        addr(fire.getDate(), fire.getPrice());
                        tadapter.addItem(ContextCompat.getDrawable(Mainactivity.this, R.drawable.settings), fire.getDate(), fire.getPlace(), toNumFormat(Integer.parseInt(fire.getPrice())) + "원", 0);
                    }
                }
                Comparator<ListViewItem> noDesc = new Comparator<ListViewItem>() {
                    @Override
                    public int compare(ListViewItem item1, ListViewItem item2) {
                        return (item2.getText().compareTo(item1.getText()));
                    }
                };
                Collections.sort(itemList, noDesc);
                Collections.sort(itemList1, noDesc);
                Collections.sort(itemList2, noDesc);
                fadapter.notifyDataSetChanged();
                madapter.notifyDataSetChanged();
                tadapter.notifyDataSetChanged();
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
    public void addf(String date, String price) {
        String temp;
        temp = date.substring(0, 7);
        int i = hashMap.get(temp);
        i = i + Integer.parseInt(price);
        Log.d("price", price);
        hashMap.put(temp, i);
        tm = new TreeMap<String, Integer>(hashMap);
    }

    public void addr(String date, String price) {
        String temp;
        temp = date.substring(0, 7);
        int i = hashMap2.get(temp);
        i = i + Integer.parseInt(price);
        hashMap2.put(temp, i);
        tm2 = new TreeMap<String, Integer>(hashMap2);
    }

    public void setup() {
        //디비에서 여기서 불러오면 될듯
        hashMap.put("2017.01", 0);
        hashMap.put("2017.02", 0);
        hashMap.put("2017.03", 0);
        hashMap.put("2017.04", 0);
        hashMap.put("2017.05", 0);
        hashMap.put("2017.06", 0);
        hashMap.put("2017.07", 0);
        hashMap.put("2017.08", 0);
        hashMap.put("2017.09", 0);
        hashMap.put("2017.10", 0);
        tm = new TreeMap<String, Integer>(hashMap);
        hashMap2.put("2017.01", 0);
        hashMap2.put("2017.02", 0);
        hashMap2.put("2017.03", 0);
        hashMap2.put("2017.04", 0);
        hashMap2.put("2017.05", 0);
        hashMap2.put("2017.06", 0);
        hashMap2.put("2017.07", 0);
        hashMap2.put("2017.08", 0);
        hashMap2.put("2017.09", 0);
        hashMap2.put("2017.10", 0);
        tm2 = new TreeMap<String, Integer>(hashMap2);
    }

    public static String toNumFormat(int num) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(num);
    }
}
