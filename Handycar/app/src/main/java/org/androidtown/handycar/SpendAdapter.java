package org.androidtown.handycar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by GE62 on 2017-08-02.
 */

public class SpendAdapter extends BaseAdapter {
    public ArrayList<ListViewItem> listViewItemList;

    public SpendAdapter(ArrayList<ListViewItem> itemList) {
        if (itemList == null) {
            listViewItemList = new ArrayList<ListViewItem>() ;
        } else {
            listViewItemList = itemList ;
        }
    }
    public void change(ArrayList<ListViewItem> itemList){
        this.listViewItemList= itemList;
    }

    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.recent_fuel, parent, false);
        }

        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.image) ;
        TextView textText = (TextView) convertView.findViewById(R.id.text) ;
        TextView textTextView = (TextView) convertView.findViewById(R.id.tx1) ;
        TextView textTextView1 = (TextView) convertView.findViewById(R.id.tx2) ;
        TextView textTextView2 = (TextView) convertView.findViewById(R.id.tx3) ;
        ListViewItem listViewItem = listViewItemList.get(position);

        if(listViewItem.getSetting()==1)
            textText.setBackgroundColor(Color.BLUE);
        iconImageView.setImageDrawable(listViewItem.getIcon());
        textTextView.setText(listViewItem.getText());
        textTextView1.setText(listViewItem.getPlace());
        textTextView2.setText(listViewItem.getPrice());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    public void addItem(Drawable icon, String date, String place, String price,int num) {
        ListViewItem item = new ListViewItem();
        item.setIcon(icon);
        item.setText(date);
        item.setPlace(place);
        item.setPrice(price);
        item.setSetting(num);
        listViewItemList.add(item);
    }
}