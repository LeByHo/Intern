package com.handycar.b2c;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyBroadcastReceiver extends BroadcastReceiver {
    String s[] = {"6505551212", "01071645362", "15881600","01020819665"};
    String tem;
    Pattern p = Pattern.compile("[a-zA-Z0-9가-힣 ]{0,15}주유소");
    Pattern pp = Pattern.compile("\\d{0,3},\\d{3}원");
    Pattern ppp = Pattern.compile("[a-zA-Z0-9가-힣 ]{0,15}정비소");

    StringBuilder sms;
    String setCurDate;
    String car;
    FirebaseOptions options = new FirebaseOptions.Builder()
            .setApplicationId("1:51453844849:android:51579fa183019e0b") // Required for Analytics.
            .setApiKey("AIzaSyBVMO8soca7w7qQ9vsQhhHTD-L6JQphA2E") // Required for Auth.
            .setDatabaseUrl("https://handycar-c8cf9.firebaseio.com") // Required for RTDB.
            .build();
    DatabaseReference mDatebase;
    @Override
    public void onReceive(Context context, Intent intent) {
        Random random = new Random();
        int x = random.nextInt(1000);
        FirebaseApp.initializeApp(context /* Context */, options, "SMS"+x);
        FirebaseApp secondary = FirebaseApp.getInstance("SMS"+x);
        FirebaseDatabase secondaryDatabase = FirebaseDatabase.getInstance(secondary);
        mDatebase = secondaryDatabase.getReference();
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            sms = new StringBuilder();
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdusObj = (Object[]) bundle.get("pdus");
                SmsMessage[] messages = new SmsMessage[pdusObj.length];
                for (int i = 0; i < pdusObj.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                }
                tem = messages[0].getOriginatingAddress();
                for (SmsMessage smsMessage : messages)
                    sms.append(smsMessage.getMessageBody());
                sms.toString();
            }
        }
        mDatebase.child("cinform").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                FirebaseCar car1 = dataSnapshot.getValue(FirebaseCar.class);
                if (car1.getCheck() == 1) {
                    car = car1.getName();
                    Log.d("car",car);
                    insert(car);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void insert(String car){
        Matcher m = p.matcher(sms);
        Matcher mm = pp.matcher(sms);
        Matcher mmm = ppp.matcher(sms);
        for (int a = 0; a < s.length; a++) {
            if (tem.equals(s[a])) {
                Log.d("car1",car);
                if (m.find() && mm.find()) {
                    Log.d("car2",car);
                    setDate();
                    String tem = mm.group(0);
                    tem = tem.replaceAll("[^0-9]", "");
                    Firebaseinfo fire = new Firebaseinfo(car, "fuel", m.group(0), setCurDate, tem);
                    mDatebase.child("inform").push().setValue(fire);

                }
                if(mmm.find() && mm.find()){
                    setDate();
                    String tem = mm.group(0);
                    tem = tem.replaceAll("[^0-9]", "");
                    Firebaseinfo fire = new Firebaseinfo(car, "fix", mmm.group(0), setCurDate, tem);
                    mDatebase.child("inform").push().setValue(fire);
                }
            }
        }
    }
    private void setDate() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat SettingFormat = new SimpleDateFormat("yyyy.MM.dd");
        setCurDate = SettingFormat.format(date);
    }
}