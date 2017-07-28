package org.androidtown.handycar;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by GE62 on 2017-07-28.
 */

public class StartActivity extends FragmentActivity {
    fragment_a Frag;
    Button Group;
    ImageView carimg;
    TextView distance;
    TextView point;
    TextView carname;
    public static Intent intent;

    ImageButton plus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        distance=(TextView)findViewById(R.id.distance_driven);
        point=(TextView)findViewById(R.id.my_point);
        carimg=(ImageView)findViewById(R.id.carkind);
        carname=(TextView) findViewById(R.id.carname);
        Group=(Button)findViewById(R.id.group);
        Group.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Group.class);
                startActivity(intent);
            }
        });
        //String name= intent.getExtras().getString("ddd");
        //carname.setText(name);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction tr = fm.beginTransaction();
        for(int i =0 ; i<5;i++){
            Frag = new fragment_a();
            tr.add(R.id.linear,Frag,"line");
        }
        tr.commit();
    }
}