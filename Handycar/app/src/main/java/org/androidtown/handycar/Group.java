package org.androidtown.handycar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by GE62 on 2017-07-24.
 */

public class Group extends AppCompatActivity {
    ListViewAdapter adapter;
    ListView listview;
    ImageButton i1;
    Button b1,b2;
    DatabaseReference mDatebase = FirebaseDatabase.getInstance().getReference();
    ArrayList<ListViewItem> itemList = new ArrayList<ListViewItem>();
    Map<String, Integer> hashMap =  new HashMap<String, Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFFFF));
        setup();
        adapter = new ListViewAdapter(itemList);
        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(listener);
        registerForContextMenu(listview);

        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Group.this);
                final EditText name = new EditText(Group.this);
                alert.setTitle("Input your Group name");
                alert.setMessage("No Space, Special character!");
                alert.setView(name);
                alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String str = name.getText().toString();
                        Groupinfo ginfo;
                        int check=0;
                        if(str.length()>0) {
                            for (int a = 0; a < adapter.getCount(); a++) {
                                if (adapter.listViewItemList.get(a).getText().equals(str))
                                    break;
                                else {
                                    check++;
                                }
                            }
                        }
                        if(check==adapter.getCount()){
                            ginfo = new Groupinfo(str,hashMap);
                            mDatebase.child("group").push().setValue(ginfo);
                        }
                    }
                });
                alert.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                alert.show();
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mDatebase.child("group").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Groupinfo ginfo = dataSnapshot.getValue(Groupinfo.class);
                adapter.addItem(ContextCompat.getDrawable(Group.this, R.drawable.groupcar), ginfo.getGname());
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
        b1 = (Button)findViewById(R.id.car_management);
        b2 = (Button)findViewById(R.id.group);
        b2.setBackgroundColor(Color.rgb(30, 154, 207));
        i1 = (ImageButton) findViewById(R.id.imageButton);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        getMenuInflater().inflate(R.menu.menu_list, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        String tem  = adapter.listViewItemList.get(index).getText();
        switch (item.getItemId()) {
            case R.id.modify:
                Intent intent = new Intent(Group.this, modify_group.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", tem);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.delete:
                Query applesQuery = mDatebase.child("group").orderByChild("gname").equalTo(tem);
                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("TAG", "onCancelled", databaseError.toException());
                    }
                });
                adapter.listViewItemList.remove(index);
                adapter.notifyDataSetChanged();
                break;
            case R.id.info:
                Intent intent2 = new Intent(Group.this, Carinfo.class);
                Bundle bundle2= new Bundle();
                bundle2.putString("name", tem);
                intent2.putExtras(bundle2);
                startActivity(intent2);
                break;
        }
        return true;
    }

    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // TODO Auto-generated method stub
            Intent intent = new Intent(Group.this, Group_total.class);
            final String str = adapter.listViewItemList.get(position).getText();
            mDatebase.child("group").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Groupinfo ginfo = dataSnapshot.getValue(Groupinfo.class);
                    if(ginfo.getGname().equals(str)){
                        Group_total.totalMap = ginfo.getHashMap();

                    }
                    Groupinfo sinfo = dataSnapshot.getValue(Groupinfo.class);
                    if(sinfo.getGname().equals(str)){
                        Group_total.scoreMap= sinfo.getHashMap();

                    }

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
            startActivity(intent);
        }
    };
}

class Groupinfo{
    String gname;
    Map<String, Integer> hashMap =  new HashMap<String, Integer>();

    public Groupinfo() {
    }

    public Groupinfo(String gname, Map<String, Integer> hashMap) {
        this.gname = gname;
        this.hashMap = hashMap;
    }

    public Map<String, Integer> getHashMap() {
        return hashMap;
    }

    public void setHashMap(Map<String, Integer> hashMap) {
        this.hashMap = hashMap;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }
}
