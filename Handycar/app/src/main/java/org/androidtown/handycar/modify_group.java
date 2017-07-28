package org.androidtown.handycar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GE62 on 2017-07-25.
 */

public class modify_group extends AppCompatActivity {
    Server server = new Server();
    Button b1, b2;
    TextView t1;
    int check = 0;
    String gname;
    ListView listview;
    CustomChoiceListViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifygroup);
        setup();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        gname = bundle.getString("name");
        t1.setText(gname);
        adapter = new CustomChoiceListViewAdapter() ;
        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter);
        new Thread() {
            @Override
            public void run() {
                HttpURLConnection con = server.getConnection("GET", "/car/" + gname);
                System.out.println("Connection done");
                try {
                    con.getResponseCode();
                    arrayToobject(server.readJson(con));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Integer> list = new HashMap<String, Integer>();
                ListViewItem listViewItem;
                for (int i = 0; i < adapter.getCount(); i++) {
                    listViewItem = adapter.listViewItemList.get(i);
                    if(listview.isItemChecked(i)==true)
                        list.put(listViewItem.getText(),1);
                    else
                        list.put(listViewItem.getText(),0);
                }
                server.updategrp(gname, list);
                adapter.notifyDataSetChanged();
                finish();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check == 0) {
                    for (int i = 0; i < adapter.getCount(); i++) {
                        listview.setItemChecked(i, true);
                    }
                    check = 1;
                } else {
                    for (int i = 0; i < adapter.getCount(); i++) {
                        listview.setItemChecked(i, false);
                    }
                    check = 0;
                }
            }
        });
    }

    public void setup() {
        b1 = (Button) findViewById(R.id.add);
        b2 = (Button) findViewById(R.id.selectAll);
        t1 = (TextView)findViewById(R.id.textView);
    }

    private void arrayToobject(JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject order = jsonArray.getJSONObject(i);
            if (order.getInt(gname) == 1)
                listview.setItemChecked(i, true);
            else
                listview.setItemChecked(i, false);
            adapter.addItem(ContextCompat.getDrawable(this, R.drawable.car), order.getString("name"));
        }
    }
}
