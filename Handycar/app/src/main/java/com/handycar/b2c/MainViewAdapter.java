package com.handycar.b2c;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;


/**
 * Created by LEE on 2017-07-28.
 */

public class MainViewAdapter extends BaseAdapter {
    public ArrayList<MainListItem> listViewItemList = new ArrayList<MainListItem>() ;

    public MainViewAdapter() {

    }

    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.main_listview, parent, false);
        }

        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView) ;

        final MainListItem listViewItem = listViewItemList.get(position);

        iconImageView.setImageDrawable(listViewItem.getIcon());

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

    public void addItem(Drawable icon) {
        MainListItem item = new MainListItem();
        item.setIcon(icon);
        listViewItemList.add(item);
    }
}

