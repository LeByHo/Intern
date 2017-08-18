package org.androidtown.handycar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GE62 on 2017-07-25.
 */

public class modify_group extends AppCompatActivity {
    Button b1, b2;
    TextView t1;
    String gname;
    ListView listview;
    CustomChoiceListViewAdapter adapter;
    DatabaseReference mDatebase = FirebaseDatabase.getInstance().getReference();
    ArrayList<ListViewItem> itemList = new ArrayList<ListViewItem>();
    Map<String, Integer> hashMap =  new HashMap<String, Integer>();
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifygroup);
        setup();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        gname = bundle.getString("name");
        t1.setText(gname);
        adapter = new CustomChoiceListViewAdapter(itemList) ;
        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Integer> list = new HashMap<String, Integer>();
                ListViewItem listViewItem;
                for (int i = 0; i < adapter.getCount(); i++) {
                    listViewItem = adapter.listViewItemList.get(i);
                    Log.d("check2",listViewItem.getText());
                    if (listview.isItemChecked(i) == true) {
                        list.put(listViewItem.getText(), 1);
                        Log.d("check3",listViewItem.getText());
                    }
                }
                Query applesQuery1 =  mDatebase.child("group").orderByChild("gname").equalTo(gname);
                final Groupinfo ginfo = new Groupinfo(gname,list);
                applesQuery1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().setValue(ginfo);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("TAG", "onCancelled", databaseError.toException());
                    }
                });
                adapter.notifyDataSetChanged();
                finish();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    for (int i = 0; i < adapter.getCount(); i++) {
                        listview.setItemChecked(i, true);
                    }
                }
        });
        mDatebase.child("cinform").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                FirebaseCar car1 = dataSnapshot.getValue(FirebaseCar.class);
                Log.d("check0",car1.getName());
                listview.setItemChecked(i, false);
                adapter.addItem(ContextCompat.getDrawable(modify_group.this, R.drawable.car), car1.getName());
                ++i;
                Comparator<ListViewItem> noDesc = new Comparator<ListViewItem>() {
                    @Override
                    public int compare(ListViewItem item1, ListViewItem item2) {
                        return (item1.getText().compareTo(item2.getText()));
                    }
                };
                Collections.sort(itemList, noDesc);
                adapter.notifyDataSetChanged();
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
        mDatebase.child("group").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                hashMap.clear();
                Groupinfo ginfo = dataSnapshot.getValue(Groupinfo.class);
                if(ginfo.getGname().equals(gname)){
                    hashMap = ginfo.getHashMap();
                }
                for ( String key : hashMap.keySet() ) {
                    Log.d("check2",key);
                    for (int a = 0; a < adapter.getCount(); a++) {
                        if (adapter.listViewItemList.get(a).getText().equals(key)) {
                            listview.setItemChecked(a, true);
                            break;
                        }
                    }
                }
                adapter.notifyDataSetChanged();
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
    public void setup() {
        b1 = (Button) findViewById(R.id.add);
        b2 = (Button) findViewById(R.id.selectAll);
        t1 = (TextView)findViewById(R.id.textView);
    }
}
