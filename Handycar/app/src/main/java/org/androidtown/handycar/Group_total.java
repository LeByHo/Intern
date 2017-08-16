package org.androidtown.handycar;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import static org.androidtown.handycar.R.drawable.car1;

/**
 * Created by GE62 on 2017-07-28.
 */

public class Group_total extends AppCompatActivity {
    FragmentManager   fm = getFragmentManager();
    Group_fragment Frag;
    Group_fragment2 Frag2;
    public static Map<String, Integer> scoreMap = new HashMap<String, Integer>();
    public static Map<String, Integer> totalMap = new HashMap<String, Integer>();
    DatabaseReference mDatebase = FirebaseDatabase.getInstance().getReference();
      int cnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFFFF));

        mDatebase.child("inform").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Firebaseinfo info = dataSnapshot.getValue(Firebaseinfo.class);
                for (final String key : totalMap.keySet()) {
                    if (info.getName().equals(key)) {
                        totalMap.put(key, totalMap.get(key) + Integer.parseInt(info.getPrice()));

                    }
                }
                setup();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatebase.child("cinform").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                FirebaseCar car1 = dataSnapshot.getValue(FirebaseCar.class);
                for (final String key : scoreMap.keySet()) {
                    if (car1.getName().equals(key)) {
                        scoreMap.put(key, car1.getScore());
                        Log.d("!@#$", key + " " + totalMap.get(key));
                    }
                }
                setUP();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    void setup() {
        Frag = new Group_fragment();
        FragmentTransaction tr = fm.beginTransaction();
        tr.add(R.id.Linear, Frag, "fuel");
        tr.commit();
    }
    void  setUP(){
        Frag2 = new Group_fragment2();
        FragmentTransaction tr = fm.beginTransaction();
        tr.add(R.id.Linear2, Frag2, "socre");
        tr.commit();
    }
}
