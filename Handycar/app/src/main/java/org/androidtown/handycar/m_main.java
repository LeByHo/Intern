package org.androidtown.handycar;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by LEE on 2017-07-27.
 */

public class m_main extends AppCompatActivity {
    FragmentManager fm;
    Button btn1,btn2,btn3,btn4;
    TextView text1,text2;
    m_repair_fragment Frag;
    Intent intent;
    ListView listview;
    ArrayList<ListViewItem> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintenance_main);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFFFF));
        setup();
        text1.setBackgroundColor(Color.rgb(30, 154, 207));
        text2.setBackgroundColor(Color.rgb(30, 154, 207));

        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(Mainactivity.m2adapter);
        Log.d("AAA",Mainactivity.itemList1.size()+"");
        for (int i = 0; i < Mainactivity.itemList1.size(); i ++){
            if(i>1)
                break;
            list.add(Mainactivity.itemList1.get(i));
        }
        if(Mainactivity.itemList1.size()==0)
            Mainactivity.m2adapter.addItem(null,"기록이없습니다",null,null,0);
        else
            Mainactivity.m2adapter.change(list);
        Mainactivity.m2adapter.notifyDataSetChanged();
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
        btn1.setBackgroundColor(Color.rgb(30, 154, 207));
        btn2 = (Button)findViewById(R.id.button2);
        btn3 =(Button)findViewById(R.id.button3);
        btn4 = (Button)findViewById(R.id.button4);
        text1 =(TextView)findViewById(R.id.text);
        text2=(TextView)findViewById(R.id.text2);
    }
}