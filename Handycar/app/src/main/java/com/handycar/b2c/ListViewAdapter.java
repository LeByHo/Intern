package com.handycar.b2c;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by GE62 on 2017-07-27.
 */

public class ListViewAdapter extends BaseAdapter {
    public ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>() ;

    public ListViewAdapter(ArrayList<ListViewItem> itemList) {
        if (itemList == null) {
            listViewItemList = new ArrayList<ListViewItem>();
        } else {
            listViewItemList = itemList;
        }
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
            convertView = inflater.inflate(R.layout.grouplist_item, parent, false);
        }

        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView1) ;
        TextView titleTextView = (TextView) convertView.findViewById(R.id.textView1) ;

        ListViewItem listViewItem = listViewItemList.get(position);

        iconImageView.setImageDrawable(listViewItem.getIcon());
        titleTextView.setText(listViewItem.getText());

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

    public void addItem(Drawable icon, String title) {
        ListViewItem item = new ListViewItem();
        item.setIcon(icon);
        item.setText(title);
        listViewItemList.add(item);
    }
}

