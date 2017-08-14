package org.androidtown.handycar;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by LEE on 2017-07-25.
 */

public class info_fragment3 extends Fragment implements Button.OnClickListener{
    ArrayList<ListViewItem> list = new ArrayList<>();
    Button three, six, all;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.info_fragment3, container, false);
        six = (Button)rootView.findViewById(R.id.six_month);
        three = (Button)rootView.findViewById(R.id.three_month);
        all = (Button)rootView.findViewById(R.id.all_day);
        all.setBackgroundColor(Color.rgb(25, 147, 168));
        all.setOnClickListener(this);
        six.setOnClickListener(this);
        three.setOnClickListener(this);
        list.clear();
        for (int i = 0; i < Mainactivity.itemList2.size(); i ++){
            list.add(Mainactivity.itemList2.get(i));
        }
        Mainactivity.tadapter.change(list);
        Mainactivity.tadapter.notifyDataSetChanged();

        return rootView;
    }
    @Override
    public void onClick(View view) {
        String Temp = null;
        String[] temp;
        int year = 0 ,month=0,day=0;
        switch (view.getId()) {
            case R.id.three_month :
                list.clear();
                all.setBackgroundColor(Color.rgb(255, 255, 255));
                six.setBackgroundColor(Color.rgb(255, 255, 255));
                three.setBackgroundColor(Color.rgb(25, 147, 168));
                temp = new String[3];
                temp[0] = Mainactivity.itemList2.get(0).getText().substring(0,4);
                temp[1] = Mainactivity.itemList2.get(0).getText().substring(5,7);
                temp[2] = Mainactivity.itemList2.get(0).getText().substring(8);

                year = Integer.parseInt(temp[0]);
                month = Integer.parseInt(temp[1]);
                month = month -3;
                if(month<=0) {
                    month = 12 + month;
                    year = year-1;
                }

                if(month<10)
                    Temp = year+".0"+month+"."+temp[2];
                else if(month>=10)
                    Temp = year+"."+month+"."+temp[2];
                for (int i = 0; i < Mainactivity.itemList2.size(); i ++){
                    if(Temp.compareTo(Mainactivity.itemList2.get(i).getText())<1)
                        list.add(Mainactivity.itemList2.get(i));
                    else
                        break;
                }
                Mainactivity.tadapter.change(list);
                Mainactivity.tadapter.notifyDataSetChanged();
                break ;
            case R.id.six_month :
                list.clear();
                three.setBackgroundColor(Color.rgb(255, 255, 255));
                all.setBackgroundColor(Color.rgb(255, 255, 255));
                six.setBackgroundColor(Color.rgb(25, 147, 168));
                temp = new String[3];
                temp[0] = Mainactivity.itemList2.get(0).getText().substring(0,4);
                temp[1] = Mainactivity.itemList2.get(0).getText().substring(5,7);
                temp[2] = Mainactivity.itemList2.get(0).getText().substring(8);
                year = Integer.parseInt(temp[0]);
                month = Integer.parseInt(temp[1]);
                month = month -6;
                if(month<=0) {
                    month = 12 + month;
                    year = year-1;
                }

                if(month<10)
                    Temp = year+".0"+month+"."+temp[2];
                else if(month>=10)
                    Temp = year+"."+month+"."+temp[2];
                for (int i = 0; i < Mainactivity.itemList2.size(); i ++){
                    if(Temp.compareTo(Mainactivity.itemList2.get(i).getText())<1)
                        list.add(Mainactivity.itemList2.get(i));
                    else
                        break;
                }
                Mainactivity.tadapter.change(list);
                Mainactivity.tadapter.notifyDataSetChanged();
                break ;
            case R.id.all_day :
                list.clear();
                three.setBackgroundColor(Color.rgb(255, 255, 255));
                six.setBackgroundColor(Color.rgb(255, 255, 255));
                all.setBackgroundColor(Color.rgb(25, 147, 168));
                for (int i = 0; i < Mainactivity.itemList2.size(); i ++){
                    list.add(Mainactivity.itemList2.get(i));
                }
                Mainactivity.tadapter.change(list);
                Mainactivity.tadapter.notifyDataSetChanged();
                break ;
        }
    }
}
