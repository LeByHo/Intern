package org.androidtown.handycar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by GE62 on 2017-08-03.
 */

public class Firebase extends AppCompatActivity{
    DatabaseReference mDatebase = FirebaseDatabase.getInstance().getReference();
    TextView t1,t2,t3,t4,t5;
    EditText e1;
    Button b1;
    String s1,s2,s3,s4,s5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firebase);
        t1 = (TextView)findViewById(R.id.textView1);
        t2 = (TextView)findViewById(R.id.textView2);
        t3 = (TextView)findViewById(R.id.textView3);
        t4 = (TextView)findViewById(R.id.textView4);
        e1 = (EditText) findViewById(R.id.editText);
        b1 = (Button)findViewById(R.id.button1);
        s1 = t1.getText().toString();
        s2 = t2.getText().toString();
        s3 = t3.getText().toString();
        s4 = t4.getText().toString();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s5 = e1.getText().toString();
                Firebaseinfo fire = new Firebaseinfo(s1,s2,s3,s4,s5);
                mDatebase.child("inform").child(s1).setValue(fire);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
    }
}
