package org.androidtown.handycar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Created by GE62 on 2017-07-28.
 */

public class Carinfo extends AppCompatActivity {
    ListViewAdapter adapter;
    ListView listview;
    Server server = new Server();
    String gname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        gname = bundle.getString("name");

        adapter = new ListViewAdapter();
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
    }

    private void arrayToobject(JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject order = jsonArray.getJSONObject(i);
            if (order.getInt(gname) == 1)
                adapter.addItem(ContextCompat.getDrawable(this, R.drawable.car), order.getString("name"));
        }
    }
}