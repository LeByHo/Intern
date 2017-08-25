package com.handycar.b2c;

import android.graphics.drawable.Drawable;

/**
 * Created by GE62 on 2017-07-26.
 */

public class ListViewItem {
    private Drawable icon;
    private String text;
    private String place;
    private String price;
    private int setting;

    public int getSetting() {
        return setting;
    }

    public void setSetting(int setting) {
        this.setting = setting;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon ;
    }
    public Drawable getIcon() {
        return this.icon ;
    }
    public void setText(String text) {
        this.text = text ;
    }
    public String getText() {
        return this.text ;
    }
    public void setPlace(String text) {
        this.place = text ;
    }
    public String getPlace() {
        return this.place ;
    }
    public void setPrice(String text) {
        this.price = text ;
    }
    public String getPrice() {
        return this.price ;
    }
}
