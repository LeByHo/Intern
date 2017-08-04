package org.androidtown.handycar;

/**
 * Created by GE62 on 2017-08-03.
 */

public class Firebaseinfo {
    String name;
    String cate;
    String place;
    String date;
    String price;
    public Firebaseinfo() { }
    public Firebaseinfo(String name, String cate, String place, String date, String price) {
        this.name = name;
        this.cate = cate;
        this.place = place;
        this.date = date;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getCate() {
        return cate;
    }

    public String getPlace() {
        return place;
    }

    public String getDate() {
        return date;
    }

    public String getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
