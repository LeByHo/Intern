package org.androidtown.handycar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.text.DecimalFormat;

public class TemActivity extends AppCompatActivity {
    TextView t1,t2,t3;
    Button b2;
    Server server = new Server();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tem_activity);
        setup();
        new Thread() {
            @Override
            public void run() {
                HttpURLConnection con = server.getConnection("GET", "/info");
                System.out.println("Connection done");
                try {
                    con.getResponseCode();
                    arrayToobject(server.readJson(con));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TemActivity.this,Group.class);
                startActivity(intent);
            }
        });
    }

    public void setup(){
        t1 = (TextView)findViewById(R.id.textView1);
        t2 = (TextView)findViewById(R.id.textView2);
        t3 = (TextView)findViewById(R.id.textView3);
        b2 = (Button)findViewById(R.id.button2);
    }
    private void arrayToobject(JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject order = jsonArray.getJSONObject(i);
            t1.setText(order.getString("name"));
            t2.setText(order.getString("date"));
            t3.setText(toNumFormat(Integer.parseInt(order.getString("price")))+"원");
        }
    }
    public static String toNumFormat(int num) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(num);
    }
}
