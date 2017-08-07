package org.androidtown.handycar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by GE62 on 2017-08-02.
 */

public class SpendAdapter extends BaseAdapter {
    //주유 가격
    public static Map<String, Integer> hashMap =  new HashMap<String, Integer>();
    public static TreeMap tm;
    //정비 가격
    public static Map<String, Integer> hashMap2 =  new HashMap<String, Integer>();
    public static TreeMap tm2;
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    public ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>() ;

    // ListViewAdapter의 생성자
    public SpendAdapter() {
        setup();
    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.recent_fuel, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.image) ;
        TextView textText = (TextView) convertView.findViewById(R.id.text) ;
        TextView textTextView = (TextView) convertView.findViewById(R.id.tx1) ;
        TextView textTextView1 = (TextView) convertView.findViewById(R.id.tx2) ;
        TextView textTextView2 = (TextView) convertView.findViewById(R.id.tx3) ;
        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        iconImageView.setImageDrawable(listViewItem.getIcon());
        textTextView.setText(listViewItem.getText());
        textTextView1.setText(listViewItem.getPlace());
        textTextView2.setText(listViewItem.getPrice());


        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(Drawable icon, String date, String place, String price) {
        ListViewItem item = new ListViewItem();
        item.setIcon(icon);
        item.setText(date);
        item.setPlace(place);
        item.setPrice(price);
        listViewItemList.add(item);
    }
    public void addf(String date, String price){
        String temp;
        temp = date.substring(0,7);
        int i = hashMap.get(temp);
        i= i + Integer.parseInt(price);
        hashMap.put(temp,i);
        tm= new TreeMap<String, Integer>(hashMap);;
    }
    public void addr(String date, String price){
        String temp;
        temp = date.substring(0,7);
        int i = hashMap2.get(temp);
        i= i + Integer.parseInt(price);
        hashMap2.put(temp,i);
       tm2= new TreeMap<String, Integer>(hashMap2);
    }
    public void setup(){
        //디비에서 여기서 불러오면 될듯
        hashMap.put("2017.01",44440);
        hashMap.put("2017.02",55550);
        hashMap.put("2017.03",32320);
        hashMap.put("2017.04",123240);
        hashMap.put("2017.05",5550);
        hashMap.put("2017.06",66660);
        hashMap.put("2017.07",77070);
        hashMap.put("2017.08",7470);
        hashMap.put("2017.09",10000);
        hashMap.put("2017.10",100100);
        tm= new TreeMap<String, Integer>(hashMap);;
        hashMap2.put("2017.01",50000);
        hashMap2.put("2017.02",100000);
        hashMap2.put("2017.03",30000);
        hashMap2.put("2017.04",150040);
        hashMap2.put("2017.05",30000);
        hashMap2.put("2017.06",60000);
        hashMap2.put("2017.07",77000);
        hashMap2.put("2017.08",74700);
        hashMap2.put("2017.09",10000);
        hashMap2.put("2017.10",10000);
        tm2= new TreeMap<String, Integer>(hashMap2);
    }
}