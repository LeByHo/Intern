package org.androidtown.handycar;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyBroadcastReceiver extends BroadcastReceiver {
    String s[] = {"6505551212", "01071645362", "15881600"};
    String tem;
    Pattern p = Pattern.compile("[a-zA-Z0-9가-힣 ]{0,15}주유소");
    Pattern pp = Pattern.compile("\\d{0,3},\\d{3}원");

    StringBuilder sms;
    String setCurDate;
    Server ser = new Server();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("ZXC","QWE");
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
        Matcher m = p.matcher(sms);
        Matcher mm = pp.matcher(sms);
        for (int a = 0; a < s.length; a++) {
            if (tem.equals(s[a])) {
                if (m.find() && mm.find()) {
                    setDate();
                    String tem = mm.group(0);
                    tem = tem.replaceAll("[^0-9]", "");
                    ser.Insertfuel("0866224021558365","fuel",m.group(0), setCurDate, tem);
                    // if (isApplicationInBackground(context)) {
                    Intent i = new Intent(context, f_main.class);
                    Log.d("ZXC","QWE");
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                    //}
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

    public static boolean isApplicationInBackground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }
}