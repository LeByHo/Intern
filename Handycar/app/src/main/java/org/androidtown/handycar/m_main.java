package org.androidtown.handycar;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintenance_main);
        setup();
        text1.setBackgroundColor(Color.rgb(25,147,168));
        text2.setBackgroundColor(Color.rgb(25,147,168));
        fm = getFragmentManager();

        FragmentTransaction tr = fm.beginTransaction();
        tr.add(R.id.Linear,Frag,"repair");

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(),totalmain.class);
                intent.setFlags(2);
                startActivity(intent);
            }
        });
        for(int i =0 ; i<2;i++){
            Frag2 = new m_Record_fragment();
            tr.add(R.id.Linear2,Frag2,"RECENT");
        }
        tr.commit();
    }
    public void setup(){
        Frag= new m_repair_fragment();
        btn1 = (Button)findViewById(R.id.button1);
        btn2 = (Button)findViewById(R.id.button2);
        btn3 =(Button)findViewById(R.id.button3);
        btn4 = (Button)findViewById(R.id.button4);
        text1 =(TextView)findViewById(R.id.text);
        text2=(TextView)findViewById(R.id.text2);
    }
}
