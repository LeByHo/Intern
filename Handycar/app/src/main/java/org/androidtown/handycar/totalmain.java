package org.androidtown.handycar;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class totalmain extends AppCompatActivity {

    RelativeLayout relativeLayout;
    RadioButton 주유,정비,통합;
    RadioGroup radioGroup;
    fuel_Fragment Frag;
    maintenacne_fragment Frag2;
    total_Fragment Frag3;
    f_Record_fragment Frag4;
    m_Record_fragment Frag5;
    info_fragment Frag6;
    info_fragment2 Frag7;
    FragmentManager fm;
    ScrollView scrollView;
    Button btn1,btn2,btn3,btn4,btn5,btn6;
    Intent intent;
    int flag = 1;
    int b6=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.total_main);
        setup();
        intent= getIntent();
        flag=intent.getFlags();
        fm = getFragmentManager();

        FragmentTransaction tr = fm.beginTransaction();
        tr.add(R.id.fragment,Frag,"fuel");
        tr.add(R.id.fragment,Frag2,"maint");
        tr.add(R.id.fragment,Frag3,"total");
        tr.add(R.id.relativelayout2,Frag6,"info");
        tr.add(R.id.relativelayout2,Frag7,"info");
        relativeLayout.setBackgroundColor(Color.rgb(246,246,246));
        int radioId = radioGroup.getCheckedRadioButtonId();
        if(flag==1){
            주유.setChecked(true);
            tr.hide(Frag2);
            tr.hide(Frag3);
            tr.hide(Frag7);
            for(int i =0; i<3;i++){
                Frag4 = new f_Record_fragment();
                tr.add(R.id.frame,Frag4,"fue1");
            }
            tr.commit();
        }
        if(flag==2){
            정비.setChecked(true);
            tr.hide(Frag);
            tr.hide(Frag3);
            tr.hide(Frag6);
            for(int i =0; i<3;i++){
                Frag5 = new m_Record_fragment();
                tr.add(R.id.frame,Frag5,"maint2");
            }
            tr.commit();
        }
        if(flag==3)
        {
            통합.setChecked(true);
            tr.hide(Frag);
            tr.hide(Frag2);
            tr.commit();
        }

        주유.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction tr = fm.beginTransaction();
                tr.replace(R.id.fragment,Frag);
                tr.show(Frag);
                tr.hide(Frag2);
                tr.hide(Frag3);
                tr.replace(R.id.relativelayout2,Frag6);
                tr.show(Frag6);
                tr.hide(Frag7);
                for(int i =0; i<3;i++){
                    Frag4 = new f_Record_fragment();
                    if(i==0)
                        tr.replace(R.id.frame,Frag4);
                    else
                        tr.add(R.id.frame,Frag4,"fue1");
                }
                tr.commit();
            }
        });
        정비.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction tr = fm.beginTransaction();
                tr.replace(R.id.fragment,Frag2);
                tr.show(Frag2);
                tr.hide(Frag);
                tr.hide(Frag3);
                tr.replace(R.id.relativelayout2,Frag7);
                tr.show(Frag7);
                tr.hide(Frag6);
                for(int i =0; i<3;i++){
                    Frag5 = new m_Record_fragment();
                    if(i==0)
                        tr.replace(R.id.frame,Frag5);
                    else
                        tr.add(R.id.frame,Frag5,"fue1");
                }
                tr.commit();
            }
        });
        통합.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction tr = fm.beginTransaction();
                tr.replace(R.id.fragment,Frag3);
               tr.show(Frag3);
                tr.hide(Frag);
                tr.hide(Frag2);
                tr.commit();
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b6++;
                if(b6%2==0)
                    btn6.setBackgroundResource(R.drawable.btn_shape);
                else
                    btn6.setBackgroundColor(Color.rgb(25,147,168));
            }
        });
    }
    void setup(){
        btn1 = (Button) findViewById(R.id.btn);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.button3);
        btn4 = (Button) findViewById(R.id.button4);
        btn5 = (Button) findViewById(R.id.button5);
        btn6 = (Button) findViewById(R.id.button6);
        Frag = new fuel_Fragment();
        Frag2= new maintenacne_fragment();
        Frag3= new total_Fragment();
        Frag6= new info_fragment();
        Frag7= new info_fragment2();
        scrollView = (ScrollView)findViewById(R.id.scroll);
        relativeLayout =(RelativeLayout)findViewById(R.id.mainlayout);
        radioGroup = (RadioGroup)findViewById(R.id.radio);
        주유 = (RadioButton)findViewById(R.id.주유);
        정비 = (RadioButton)findViewById(R.id.정비);
        통합 = (RadioButton)findViewById(R.id.통합);
    }
}