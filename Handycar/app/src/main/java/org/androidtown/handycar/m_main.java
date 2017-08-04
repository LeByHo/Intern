package org.androidtown.handycar;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.text.DecimalFormat;

/**
 * Created by LEE on 2017-07-27.
 */

public class m_main extends AppCompatActivity {
    FragmentManager fm;
    Button btn1,btn2,btn3,btn4;
    TextView text1,text2;
    m_repair_fragment Frag;
    m_Record_fragment Frag2;
    Intent intent;
    Server server = new Server();
    //SpendAdapter adapter;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintenance_main);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFFFF));
        setup();
        text1.setBackgroundColor(Color.rgb(25,147,168));
        text2.setBackgroundColor(Color.rgb(25,147,168));

        //adapter = new SpendAdapter();
        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(Mainactivity.madapter);
        new Thread() {
            @Override
            public void run() {
                HttpURLConnection con = server.getConnection("GET", "/info/"+ Mainactivity.car+"/fix");
                System.out.println("Connection donee");
                try {
                    con.getResponseCode();
                    infoarrayToobject(server.readJson(con));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        fm = getFragmentManager();

        FragmentTransaction tr = fm.beginTransaction();
        tr.add(R.id.Linear,Frag,"repair");
        tr.commit();
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(),totalmain.class);
                intent.setFlags(2);
                startActivity(intent);
            }
        });

    }
    public void setup(){
        Frag= new m_repair_fragment();
        btn1 = (Button)findViewById(R.id.button1);
            btn1.setBackgroundColor(Color.rgb(25,147,168));
        btn2 = (Button)findViewById(R.id.button2);
        btn3 =(Button)findViewById(R.id.button3);
        btn4 = (Button)findViewById(R.id.button4);
        text1 =(TextView)findViewById(R.id.text);
        text2=(TextView)findViewById(R.id.text2);
    }
    private void  infoarrayToobject(JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject order = jsonArray.getJSONObject(i);
           // adapter.addItem(ContextCompat.getDrawable(this, R.drawable.settings), order.getString("date"),order.getString("place"),toNumFormat(Integer.parseInt(order.getString("price")))+"원");
        }
    }
    public static String toNumFormat(int num) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(num);
    }
}
