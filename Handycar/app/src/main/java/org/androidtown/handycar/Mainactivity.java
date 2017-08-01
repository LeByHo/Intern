package org.androidtown.handycar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.yalantis.phoenix.PullToRefreshView;

/**
 * Created by LEE on 2017-07-27.
 */

public class Mainactivity extends AppCompatActivity {
    Button btn1, btn2, btn3, btn4;
    Intent intent;
    PullToRefreshView mPullToRefreshView;

   MainViewAdapter adapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listview = (ListView) findViewById(R.id.list_view) ;
        adapter = new MainViewAdapter() ;
        listview.setAdapter(adapter);
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.m_car),"내차관리");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.break_oil),"주유");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.settings),"정비");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.graph),"통계");
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                    intent = new Intent(getApplicationContext(),StartActivity.class);
                else if(position == 1)
                intent = new Intent(getApplicationContext(),f_main.class);
                else if(position == 2)
                    intent = new Intent(getApplicationContext(),m_main.class);
                else if(position == 3) {
                    intent = new Intent(getApplicationContext(), totalmain.class);
                    intent.setFlags(1);
                }
                startActivity(intent);
            }
        });
        mPullToRefreshView = (PullToRefreshView) findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            public static final long REFRESH_DELAY = 100;
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                    }
                }, REFRESH_DELAY);
            }
        });
    }
}