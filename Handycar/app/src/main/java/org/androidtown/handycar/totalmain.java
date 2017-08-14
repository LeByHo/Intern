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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;

public class totalmain extends AppCompatActivity {
    RelativeLayout relativeLayout;
    RadioButton 주유, 정비, 통합;
    RadioGroup radioGroup;
    fuel_Fragment Frag;
    maintenacne_fragment Frag2;
    total_Fragment Frag3;
    f_Record_fragment Frag4;
    m_Record_fragment Frag5;
    info_fragment Frag6;
    info_fragment2 Frag7;
    info_fragment3 Frag8;
    FragmentManager fm;
    ScrollView scrollView;
    Button btn1, btn2, btn3, btn4, btn5, btn6;
    Intent intent;
    int flag = 1;
    int b6 = 0;
    Bundle bundle = new Bundle();
    ArrayList fuel = new ArrayList();
    ArrayList maintenacne = new ArrayList();
    Server server = new Server();

   //SpendAdapter fadapter, madapter, tadapter;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.total_main);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFFFF));
        setup();

        new Thread() {
            @Override
            public void run() {
                HttpURLConnection con = server.getConnection("GET", "/info/" + Mainactivity.car + "/*");
                System.out.println("Connection donee");
                try {
                    con.getResponseCode();
                    infoarrayToobject(server.readJson(con));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

        intent = getIntent();
        flag = intent.getFlags();
        fm = getFragmentManager();
        Frag.setArguments(bundle);

        FragmentTransaction tr = fm.beginTransaction();
        tr.add(R.id.fragment, Frag, "fuel");
        tr.add(R.id.fragment, Frag2, "maint");
        tr.add(R.id.fragment, Frag3, "total");
        tr.add(R.id.relativelayout2, Frag6, "info");
        tr.add(R.id.relativelayout2, Frag7, "info");
        tr.add(R.id.relativelayout2, Frag8, "info");
        relativeLayout.setBackgroundColor(Color.rgb(246, 246, 246));
        if (flag == 1) {
            주유.setChecked(true);
            tr.hide(Frag2);
            tr.hide(Frag3);
            tr.hide(Frag7);
            tr.hide(Frag8);
            tr.commit();
            listview.setAdapter(Mainactivity.fadapter);
            //listview.setAdapter(fadapter);
        }
        if (flag == 2) {
            정비.setChecked(true);
            tr.hide(Frag);
            tr.hide(Frag3);
            tr.hide(Frag6);
            tr.hide(Frag8);
            tr.commit();
            listview.setAdapter(Mainactivity.madapter);
            //listview.setAdapter(madapter);
        }
        if (flag == 3) {
            통합.setChecked(true);
            tr.hide(Frag);
            tr.hide(Frag2);
            tr.hide(Frag6);
            tr.hide(Frag7);
            tr.commit();
            listview.setAdapter(Mainactivity.tadapter);
            //listview.setAdapter(tadapter);
        }

        주유.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction tr = fm.beginTransaction();
                tr.replace(R.id.fragment, Frag);
                tr.show(Frag);
                tr.hide(Frag2);
                tr.hide(Frag3);
                tr.replace(R.id.relativelayout2, Frag6);
                tr.show(Frag6);
                tr.hide(Frag7);
                tr.hide(Frag8);
                tr.commit();
                listview.setAdapter(Mainactivity.fadapter);
                //listview.setAdapter(fadapter);
            }
        });
        정비.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction tr = fm.beginTransaction();
                tr.replace(R.id.fragment, Frag2);
                tr.show(Frag2);
                tr.hide(Frag);
                tr.hide(Frag3);
                tr.replace(R.id.relativelayout2, Frag7);
                tr.show(Frag7);
                tr.hide(Frag6);
                tr.hide(Frag8);
                tr.commit();
                listview.setAdapter(Mainactivity.madapter);
               // listview.setAdapter(madapter);
            }
        });
        통합.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction tr = fm.beginTransaction();
                tr.replace(R.id.fragment, Frag3);
                tr.show(Frag3);
                tr.hide(Frag);
                tr.hide(Frag2);
                tr.show(Frag8);
                tr.hide(Frag6);
                tr.hide(Frag7);
                tr.commit();
                listview.setAdapter(Mainactivity.tadapter);
               // listview.setAdapter(tadapter);
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b6++;
                if (b6 % 2 == 1)
                    btn6.setBackgroundResource(R.drawable.btn_shape);
                else
                    btn6.setBackgroundColor(Color.rgb(25, 147, 168));
            }
        });
    }

    void setup() {

        btn3 = (Button) findViewById(R.id.button3);
        btn4 = (Button) findViewById(R.id.button4);
        btn5 = (Button) findViewById(R.id.button5);
        btn6 = (Button) findViewById(R.id.button6);
        btn6.setBackgroundColor(Color.rgb(25, 147, 168));
        Frag = new fuel_Fragment();
        Frag2 = new maintenacne_fragment();
        Frag3 = new total_Fragment();
        Frag6 = new info_fragment();
        Frag7 = new info_fragment2();
        Frag8 = new info_fragment3();
        // scrollView = (ScrollView)findViewById(R.id.scroll);
        listview = (ListView) findViewById(R.id.listview);
        relativeLayout = (RelativeLayout) findViewById(R.id.mainlayout);
        radioGroup = (RadioGroup) findViewById(R.id.radio);
        주유 = (RadioButton) findViewById(R.id.주유);
        정비 = (RadioButton) findViewById(R.id.정비);
        통합 = (RadioButton) findViewById(R.id.통합);
       // madapter = new SpendAdapter();
       // fadapter = new SpendAdapter();
       // tadapter = new SpendAdapter();
    }

    private void infoarrayToobject(JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject order = jsonArray.getJSONObject(i);
            if (order.getString("cate").equals("fuel")) {
              //  fadapter.addItem(ContextCompat.getDrawable(this, R.drawable.break_oil), order.getString("date"), order.getString("place"), f_main.toNumFormat(Integer.parseInt(order.getString("price"))) + "원");
               // tadapter.addItem(ContextCompat.getDrawable(this, R.drawable.break_oil), order.getString("date"), order.getString("place"), f_main.toNumFormat(Integer.parseInt(order.getString("price"))) + "원");
            }
            if (order.getString("cate").equals("fix")) {
              //  madapter.addItem(ContextCompat.getDrawable(this, R.drawable.settings), order.getString("date"), order.getString("place"), f_main.toNumFormat(Integer.parseInt(order.getString("price"))) + "원");
               // tadapter.addItem(ContextCompat.getDrawable(this, R.drawable.settings), order.getString("date"), order.getString("place"), f_main.toNumFormat(Integer.parseInt(order.getString("price"))) + "원");
            }
        }
    }
}