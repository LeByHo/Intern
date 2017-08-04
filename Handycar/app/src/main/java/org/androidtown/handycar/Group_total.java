package org.androidtown.handycar;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by GE62 on 2017-07-28.
 */

public class Group_total extends AppCompatActivity {
    FragmentManager fm;
    Group_fragment Frag;
    Group_fragment2 Frag2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFFFF));
        setup();
        fm = getFragmentManager();
        FragmentTransaction tr = fm.beginTransaction();
        tr.add(R.id.Linear,Frag,"fuel");
        tr.add(R.id.Linear2,Frag2,"socre");
        tr.commit();
    }
    void setup(){
        Frag = new Group_fragment();
        Frag2 = new Group_fragment2();
    }
}
