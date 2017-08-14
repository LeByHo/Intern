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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
 * Created by GE62 on 2017-07-28.
 */

public class StartActivity extends AppCompatActivity {
    Button Group;
    Button management;
    ImageView carimg;
    TextView distance;
    TextView point;
    TextView carname;

    ListViewAdapter adapter;
    ListView listview;
    ArrayList<ListViewItem> itemList = new ArrayList<ListViewItem>();
    DatabaseReference mDatebase = FirebaseDatabase.getInstance().getReference();
    ImageButton plus;
    int cnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFFFF));
        setup();

        Group.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Group.class);
                startActivity(intent);
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(StartActivity.this);
                final EditText name = new EditText(StartActivity.this);
                alert.setTitle("Input your Car name");
                alert.setMessage("No Space, Special character!");
<<<<<<< HEAD
                alert.setView(name);
=======
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.customdialog, null);
                final EditText editname = (EditText) dialogView.findViewById(R.id.dialog_edit);
                final EditText score = (EditText) dialogView.findViewById(R.id.distance_driven);
                final RadioGroup rg = (RadioGroup)dialogView.findViewById(R.id.dialog_rg);
                //alert.setView(name);
                alert.setView(dialogView);
>>>>>>> origin/master
                alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String str = editname.getText().toString();
                        int id = rg.getCheckedRadioButtonId();
                        Log.d("zxc",id+"");
                        RadioButton rb = (RadioButton)dialogView.findViewById(id);
                        //Toast.makeText(getApplication(),rb.getText().toString(),Toast.LENGTH_SHORT).show();
                        FirebaseCar car;
                        int check = 0;
                        if (str.length() > 0) {
                            for (int a = 0; a < adapter.getCount(); a++) {
                                if (adapter.listViewItemList.get(a).getText().equals(str))
                                    break;
                                else {
                                    check++;
                                }
                            }
                        }
                        if (check == adapter.getCount()) {
                            if (adapter.getCount() == 0 && cnt == 0) {
                                carname.setText(str);
<<<<<<< HEAD
                                car = new FirebaseCar(str, 1);
                            } else {
                                car = new FirebaseCar(str, 0);
=======
                                car = new FirebaseCar(str, 1, Integer.parseInt(score.getText().toString()));
                            } else {
                                car = new FirebaseCar(str, 0, Integer.parseInt(score.getText().toString()));
>>>>>>> origin/master
                            }
                            mDatebase.child("cinform").push().setValue(car);
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

        mDatebase.child("cinform").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                FirebaseCar fire = dataSnapshot.getValue(FirebaseCar.class);
                if (fire.getCheck() == 1) {
                    carname.setText(fire.getName());
                    ++cnt;
                }
                if (fire.getCheck() == 0) {
                    adapter.addItem(ContextCompat.getDrawable(StartActivity.this, R.drawable.car2), fire.getName());
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
        management = (Button) findViewById(R.id.car_management);
        management.setBackgroundColor(Color.rgb(25, 147, 168));
        distance = (TextView) findViewById(R.id.distance_driven);
        point = (TextView) findViewById(R.id.my_point);
        carimg = (ImageView) findViewById(R.id.carkind);
        carname = (TextView) findViewById(R.id.carname);
        Group = (Button) findViewById(R.id.group);
        plus = (ImageButton) findViewById(R.id.addCar1);
        adapter = new ListViewAdapter(itemList);
        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter);
        registerForContextMenu(listview);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        getMenuInflater().inflate(R.menu.menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        String tem = adapter.listViewItemList.get(index).getText();
        switch (item.getItemId()) {
            case R.id.select:
                adapter.addItem(ContextCompat.getDrawable(StartActivity.this, R.drawable.car2), carname.getText().toString());
<<<<<<< HEAD
                Query applesQuery1 =  mDatebase.child("cinform").orderByChild("name").equalTo(carname.getText().toString());
                final FirebaseCar car = new FirebaseCar(carname.getText().toString(),0);
=======
                Query applesQuery1 = mDatebase.child("cinform").orderByChild("name").equalTo(carname.getText().toString());
                final FirebaseCar car = new FirebaseCar(carname.getText().toString(), 0, 0);
>>>>>>> origin/master
                applesQuery1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().setValue(car);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("TAG", "onCancelled", databaseError.toException());
                    }
                });
                carname.setText(tem);
                org.androidtown.handycar.Group.carchk++;
<<<<<<< HEAD
                org.androidtown.handycar.Group.scar=tem;
                Query applesQuery2 =  mDatebase.child("cinform").orderByChild("name").equalTo(carname.getText().toString());
                final FirebaseCar car1 = new FirebaseCar(tem,1);
=======
                org.androidtown.handycar.Group.scar = tem;
                Query applesQuery2 = mDatebase.child("cinform").orderByChild("name").equalTo(carname.getText().toString());
                final FirebaseCar car1 = new FirebaseCar(tem, 1, 0);
>>>>>>> origin/master
                applesQuery2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().setValue(car1);
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
            case R.id.delete:
                Query applesQuery = mDatebase.child("cinform").orderByChild("name").equalTo(tem);
                Query informQuery = mDatebase.child("inform").orderByChild("name").equalTo(tem);
                Query groupQuery = mDatebase.child("group").orderByChild("cname").equalTo(tem);
                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("TAG", "onCancelled", databaseError.toException());
                    }
                });
                informQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("TAG", "onCancelled", databaseError.toException());
                    }
                });
                groupQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
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
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}

class FirebaseCar {
    String name;
    int check = 0;
<<<<<<< HEAD
=======
    int score = 0;
>>>>>>> origin/master

    public FirebaseCar() {
    }

    public FirebaseCar(String name, int check) {
        this.name = name;
        this.check = check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }
}
