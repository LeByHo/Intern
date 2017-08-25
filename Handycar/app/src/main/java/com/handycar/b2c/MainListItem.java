package com.handycar.b2c;

import android.graphics.drawable.Drawable;

/**
 * Created by LEE on 2017-07-28.
 */

public class MainListItem {
    private Drawable icon ;
    private String text ;

    public void setIcon(Drawable icon) {

        this.icon = icon ;
    }
    public void setText(String text) {
        this.text = text ;
    }
    public Drawable getIcon() {
        return this.icon ;
    }
    public String getText() {
        return this.text ;
    }
}