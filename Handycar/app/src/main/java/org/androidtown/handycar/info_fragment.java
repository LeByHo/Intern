package org.androidtown.handycar;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by LEE on 2017-07-25.
 */

public class info_fragment extends Fragment  implements Button.OnClickListener {
    Button seven, Month, all;
    ArrayList<ListViewItem> list = new ArrayList<>();
    String setCurDate;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.info_fragment, container, false);
        Month = (Button)rootView.findViewById(R.id.seven_day);
        seven = (Button)rootView.findViewById(R.id.three_day);
        all = (Button)rootView.findViewById(R.id.all_day);
        all.setBackgroundColor(Color.rgb(25, 147, 168));
        all.setOnClickListener(this);
        seven.setOnClickListener(this);
        Month.setOnClickListener(this);
        list.clear();
        for (int i = 0; i < Mainactivity.itemList.size(); i ++){
            list.add(Mainactivity.itemList.get(i));
        }
        if(Mainactivity.itemList.size()==0)
            Mainactivity.fadapter.addItem(null,"기록이 없습니다",null,null,0);
        else
            Mainactivity.fadapter.change(list);
        Mainactivity.fadapter.notifyDataSetChanged();
        return rootView;
    }

    @Override
    public void onClick(View view) {
        String Temp = null;
        String[] temp;
        int month=0,day=0;
        switch (view.getId()) {
            case R.id.seven_day :

                setDate();
                list.clear();
                all.setBackgroundColor(Color.rgb(255, 255, 255));
                seven.setBackgroundColor(Color.rgb(255, 255, 255));
                Month.setBackgroundColor(Color.rgb(30, 154, 207));
                all.setBackgroundResource(R.drawable.btn_shape);
                seven.setBackgroundResource(R.drawable.btn_shape);
                temp = new String[3];
                temp[0] = setCurDate.substring(0,4);
                temp[1] =  setCurDate.substring(5,7);
                temp[2] = setCurDate.substring(8);

                month = Integer.parseInt(temp[1]);
                month = month -1;

                if(month<10)
                    Temp = temp[0]+".0"+month+"."+temp[2];
                else if(month>=10)
                    Temp = temp[0]+"."+month+"."+temp[2];
                for (int i = 0; i < Mainactivity.itemList.size(); i ++){
                    if(Temp.compareTo(Mainactivity.itemList.get(i).getText())<1)
                        list.add(Mainactivity.itemList.get(i));
                    else
                        break;
                }
                if(list.size()== 0){
                    Mainactivity.fadapter.addItem(null,"기록이 없습니다.",null,null,0);
                }
                else
                    Mainactivity.fadapter.change(list);
                Mainactivity.fadapter.notifyDataSetChanged();
                break ;
            case R.id.three_day :
                setDate();
                list.clear();
                Month.setBackgroundColor(Color.rgb(255, 255, 255));
                all.setBackgroundColor(Color.rgb(255, 255, 255));
                all.setBackgroundResource(R.drawable.btn_shape);
                Month.setBackgroundResource(R.drawable.btn_shape);
                seven.setBackgroundColor(Color.rgb(30, 154, 207));
                temp = new String[3];
                temp[0] = setCurDate.substring(0,4);
                temp[1] =  setCurDate.substring(5,7);
                temp[2] = setCurDate.substring(8);
                month = Integer.parseInt(temp[1]);
                day = Integer.parseInt(temp[2])-7;

                if(day<=0){
                    day = 31+day;
                    month = month -1;
                }

                if(month<10 && day>=10)
                    Temp = temp[0]+".0"+month+"."+day;
                else if(month>=10 && day>=10)
                    Temp = temp[0]+"."+month+"."+day;
                else if(month<10 && day<10)
                    Temp = temp[0]+".0"+month+".0"+day;
                else if(month>=10 && day<10)
                    Temp = temp[0]+"."+month+".0"+day;
                for (int i = 0; i < Mainactivity.itemList.size(); i ++){
                    if(Temp.compareTo(Mainactivity.itemList.get(i).getText())<1)
                        list.add(Mainactivity.itemList.get(i));

                    else
                        break;
                }
                if(list.size()== 0){
                    Mainactivity.fadapter.addItem(null,"기록이 없습니다.",null,null,0);
                }
                else
                    Mainactivity.fadapter.change(list);
                Mainactivity.fadapter.notifyDataSetChanged();
                break ;
            case R.id.all_day :
                list.clear();
                Month.setBackgroundColor(Color.rgb(255, 255, 255));
                seven.setBackgroundColor(Color.rgb(255, 255, 255));
                Month.setBackgroundResource(R.drawable.btn_shape);
                seven.setBackgroundResource(R.drawable.btn_shape);
                all.setBackgroundColor(Color.rgb(30, 154, 207));
                for (int i = 0; i < Mainactivity.itemList.size(); i ++){
                    list.add(Mainactivity.itemList.get(i));
                }
                if(list.size()== 0){
                    Mainactivity.fadapter.addItem(null,"기록이 없습니다.",null,null,0);
                }
                else
                Mainactivity.fadapter.change(list);
                Mainactivity.fadapter.notifyDataSetChanged();
                break ;
        }
    }
    private void setDate() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat SettingFormat = new SimpleDateFormat("yyyy.MM.dd");
        setCurDate = SettingFormat.format(date);
    }
}