package org.androidtown.handycar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Created by GE62 on 2017-07-24.
 */

public class Group extends AppCompatActivity {
    ListViewAdapter adapter;
    ListView listview;
    Server server = new Server();
    ImageButton i1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        setup();
        adapter = new ListViewAdapter();
        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(listener);
        registerForContextMenu(listview);

        new Thread() {
            @Override
            public void run() {
                HttpURLConnection con = server.getConnection("GET", "/grp");
                System.out.println("Connection done");
                try {
                    con.getResponseCode();
                    arrayToobject(server.readJson(con));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

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
                        if(!(adapter.listViewItemList.contains(str))&& str.length() > 0){
                            adapter.addItem(ContextCompat.getDrawable(Group.this, R.drawable.car), str);
                            server.insertcol(str);
                        }
                        adapter.notifyDataSetChanged();

                    }
                });
                alert.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                alert.show();
            }
        });
    }

    public void setup() {
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
                server.deletecol(tem);
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
            startActivity(intent);
        }
    };

    private void arrayToobject(JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject order = jsonArray.getJSONObject(i);
            adapter.addItem(ContextCompat.getDrawable(this, R.drawable.car), order.getString("name"));
        }
    }
}
