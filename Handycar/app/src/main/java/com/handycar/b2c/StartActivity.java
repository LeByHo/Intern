package com.handycar.b2c;

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
import android.view.LayoutInflater;
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

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

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

    InformAdapter adapter;
    ListView listview;
    ArrayList<ListViewItem> itemList = new ArrayList<ListViewItem>();
    FirebaseOptions options = new FirebaseOptions.Builder()
            .setApplicationId("1:51453844849:android:51579fa183019e0b") // Required for Analytics.
            .setApiKey("AIzaSyBVMO8soca7w7qQ9vsQhhHTD-L6JQphA2E") // Required for Auth.
            .setDatabaseUrl("https://handycar-c8cf9.firebaseio.com") // Required for RTDB.
            .build();
    DatabaseReference mDatebase;
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
        Random random = new Random();
        int x = random.nextInt(1000);
        FirebaseApp.initializeApp(this /* Context */, options, "second"+x);
        FirebaseApp secondary = FirebaseApp.getInstance("second"+x);
        FirebaseDatabase secondaryDatabase = FirebaseDatabase.getInstance(secondary);
        mDatebase = secondaryDatabase.getReference();

        Group.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.handycar.b2c.Group.class);
                startActivity(intent);
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(StartActivity.this);
                alert.setTitle("Input your Car name");
                alert.setMessage("No Space, Special character!");
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.customdialog, null);
                final EditText editname = (EditText) dialogView.findViewById(R.id.dialog_edit);
                final EditText score = (EditText) dialogView.findViewById(R.id.distance_driven);
                final RadioGroup rg = (RadioGroup) dialogView.findViewById(R.id.dialog_rg);
                alert.setView(dialogView);
                alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String str = editname.getText().toString();
                        int id = rg.getCheckedRadioButtonId();
                        RadioButton rb = (RadioButton) dialogView.findViewById(id);
                        String tem = rb.getText().toString();
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
                                distance.setText(tem);
                                point.setText(score.getText().toString());
                                car = new FirebaseCar(str, 1, tem, Integer.parseInt(score.getText().toString()));
                            } else {
                                car = new FirebaseCar(str, 0, tem, Integer.parseInt(score.getText().toString()));
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
                    distance.setText(fire.getCate());
                    point.setText(fire.getScore() + "");
                    insertitem(fire.getCate());
                    ++cnt;
                }
                if (fire.getCheck() == 0) {
                    if (fire.getCate().equals("BMW"))
                        adapter.addItem(ContextCompat.getDrawable(StartActivity.this, R.drawable.bmw), fire.getName(), fire.getCate(), fire.getScore() + "");
                    else if (fire.getCate().equals("AUDI"))
                        adapter.addItem(ContextCompat.getDrawable(StartActivity.this, R.drawable.audi), fire.getName(), fire.getCate(), fire.getScore() + "");
                    else if (fire.getCate().equals("BENZ"))
                        adapter.addItem(ContextCompat.getDrawable(StartActivity.this, R.drawable.benz), fire.getName(), fire.getCate(), fire.getScore() + "");
                    else if (fire.getCate().equals("FERRARI"))
                        adapter.addItem(ContextCompat.getDrawable(StartActivity.this, R.drawable.ferrari), fire.getName(), fire.getCate(), fire.getScore() + "");
                    else if (fire.getCate().equals("JENESIS"))
                        adapter.addItem(ContextCompat.getDrawable(StartActivity.this, R.drawable.jenesis), fire.getName(), fire.getCate(), fire.getScore() + "");
                    else
                        adapter.addItem(ContextCompat.getDrawable(StartActivity.this, R.drawable.car2), fire.getName(), fire.getCate(), fire.getScore() + "");
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

    public void insertitem(String str) {
        if (str.equals("BMW"))
            carimg.setImageResource(R.drawable.bmw);
        else if (str.equals("AUDI"))
            carimg.setImageResource(R.drawable.audi);
        else if (str.equals("BENZ"))
            carimg.setImageResource(R.drawable.benz);
        else if (str.equals("FERRARI"))
            carimg.setImageResource(R.drawable.ferrari);
        else if (str.equals("JENESIS"))
            carimg.setImageResource(R.drawable.jenesis);
        else
            carimg.setImageResource(R.drawable.car);
    }

    public void setup() {
        management = (Button) findViewById(R.id.car_management);
        management.setBackgroundColor(Color.rgb(30, 154, 207));
        distance = (TextView) findViewById(R.id.distance_driven);
        point = (TextView) findViewById(R.id.my_point);
        carimg = (ImageView) findViewById(R.id.carkind);
        carname = (TextView) findViewById(R.id.carname);
        Group = (Button) findViewById(R.id.group);
        plus = (ImageButton) findViewById(R.id.addCar1);
        adapter = new InformAdapter(itemList);
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
        final String tem = adapter.listViewItemList.get(index).getText();
        switch (item.getItemId()) {
            case R.id.select:
                if (distance.getText().toString().equals("BMW"))
                    adapter.addItem(ContextCompat.getDrawable(StartActivity.this, R.drawable.bmw), carname.getText().toString(), distance.getText().toString(), point.getText().toString());
                else if (distance.getText().toString().equals("AUDI"))
                    adapter.addItem(ContextCompat.getDrawable(StartActivity.this, R.drawable.audi), carname.getText().toString(), distance.getText().toString(), point.getText().toString());
                else if (distance.getText().toString().equals("BENZ"))
                    adapter.addItem(ContextCompat.getDrawable(StartActivity.this, R.drawable.benz), carname.getText().toString(), distance.getText().toString(), point.getText().toString());
                else if (distance.getText().toString().equals("FERRARI"))
                    adapter.addItem(ContextCompat.getDrawable(StartActivity.this, R.drawable.ferrari), carname.getText().toString(), distance.getText().toString(), point.getText().toString());
                else if (distance.getText().toString().equals("JENESIS"))
                    adapter.addItem(ContextCompat.getDrawable(StartActivity.this, R.drawable.jenesis), carname.getText().toString(), distance.getText().toString(), point.getText().toString());
                else
                    adapter.addItem(ContextCompat.getDrawable(StartActivity.this, R.drawable.car2), carname.getText().toString(), distance.getText().toString(), point.getText().toString());
                Query applesQuery1 = mDatebase.child("cinform").orderByChild("name").equalTo(carname.getText().toString());
                final FirebaseCar car = new FirebaseCar(carname.getText().toString(), 0, distance.getText().toString(), Integer.parseInt(point.getText().toString()));
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
                distance.setText(adapter.listViewItemList.get(index).getPlace());
                point.setText(adapter.listViewItemList.get(index).getPrice());
                insertitem(adapter.listViewItemList.get(index).getPlace());
                Query applesQuery2 = mDatebase.child("cinform").orderByChild("name").equalTo(carname.getText().toString());
                final FirebaseCar car1 = new FirebaseCar(tem, 1, adapter.listViewItemList.get(index).getPlace(), Integer.parseInt(adapter.listViewItemList.get(index).getPrice()));
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
                Query groupQuery = mDatebase.child("group");
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
                            String str = appleSnapshot.getRef().getKey();
                            mDatebase.child("group").child(str).child("hashMap").child(tem).removeValue();
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
}

class FirebaseCar {
    String name;
    int check = 0;
    String cate;
    int score = 0;

    public FirebaseCar() {
    }

    public FirebaseCar(String name, int check, String cate, int score) {
        this.name = name;
        this.check = check;
        this.cate = cate;
        this.score = score;
    }

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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
