package org.androidtown.handycar;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by LEE on 2017-07-27.
 */

public class f_main extends AppCompatActivity {
    FragmentManager fm;
    Button btn1, btn2, btn4;
    TextView text1, text2;
    f_price_fragment Frag;
    Intent intent;
    String str1, str2, str3, str4;
    public static HashMap<String, String> location = new HashMap<String, String>();
    ListView listview;
    Double latitude = 0.0;
    Double longitude = 0.0;
    public static double lati =37.400622, logi = 127.112162;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f_main);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFFFF));
        setup();
        text1.setBackgroundColor(Color.rgb(30, 154, 207));
        text2.setBackgroundColor(Color.rgb(30, 154, 207));

        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(Mainactivity.f2adapter);
        ArrayList<ListViewItem> list = new ArrayList<>();

        if(f_offers.sum>0)
            f_offers.sum=0.0;
        for (int i = 0; i < Mainactivity.itemList.size(); i++) {
            if (i > 1)
                break;
            list.add(Mainactivity.itemList.get(i));
        }

        if (Mainactivity.itemList.size() == 0)
            Mainactivity.f2adapter.addItem(null, "기록이 없습니다", null, null, 0);
        else
            Mainactivity.f2adapter.change(list);
        Mainactivity.f2adapter.notifyDataSetChanged();

        if (Mainactivity.chk == 0) {
            startLocationService();
            if (latitude != 0.0 && longitude != 0.0) {
                logi = longitude;
                lati = latitude;
            }
            GeoPoint in_pt = new GeoPoint(logi, lati);
            GeoPoint tm_pt = GeoTrans.convert(GeoTrans.GEO, GeoTrans.TM, in_pt);
            GeoPoint katec_pt = GeoTrans.convert(GeoTrans.TM, GeoTrans.KATEC, tm_pt);

            str1 = "http://www.opinet.co.kr/api/avgAllPrice.do?out=json&code=F191170721";
            str2 = "http://www.opinet.co.kr/api/avgSidoPrice.do?out=json&prodcd=B027&sido=01&code=F191170721";
            str3 = "http://www.opinet.co.kr/api/avgRecentPrice.do?out=json&prodcd=B027&code=F191170721";
            str4 = "http://www.opinet.co.kr/api/aroundAll.do?code=F191170721&x=" + katec_pt.getX() + "&y=" + katec_pt.getY() + "&radius=1000&sort=1&prodcd=B027&out=json";
            showDialog(1);
            new Thread() {
                @Override
                public void run() {
                    try {
                        Message message = handler.obtainMessage();
                        InputStream in = new BufferedInputStream(getConnectionurl(str1).getInputStream());
                        JSONObject json = new JSONObject(getStringFromInputStream(in));
                        parseJSON(json, 1);

                        InputStream in1 = new BufferedInputStream(getConnectionurl(str2).getInputStream());
                        JSONObject json1 = new JSONObject(getStringFromInputStream(in1));
                        parseJSON(json1, 2);

                        InputStream in2 = new BufferedInputStream(getConnectionurl(str3).getInputStream());
                        JSONObject json2 = new JSONObject(getStringFromInputStream(in2));
                        parseJSON(json2, 3);

                        InputStream in3 = new BufferedInputStream(getConnectionurl(str4).getInputStream());
                        JSONObject json3 = new JSONObject(getStringFromInputStream(in3));
                        parseJSON(json3, 4);
                        handler.sendMessage(message);
                        removeDialog(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        } else {
            Message message = handler.obtainMessage();
            handler.sendMessage(message);

        }
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), f_offers.class);
                startActivity(intent);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), totalmain.class);
                intent.setFlags(1);
                startActivity(intent);
            }
        });
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            fm = getFragmentManager();
            FragmentTransaction tr = fm.beginTransaction();
            Bundle bundle = new Bundle();
            bundle.putString("Allavg", f_offers.Allavg);
            bundle.putString("Silavg", f_offers.Siavg);
            String tem = String.format("%.2f", f_offers.sum / 7);
            bundle.putString("sum", tem);
            String ctem = String.format("%.2f", f_offers.csum / f_offers.cnut);
            bundle.putString("csum", ctem);
            Frag.setArguments(bundle);
            tr.add(R.id.Linear, Frag, "repair");
            tr.commit();
        }
    };

    public void setup() {
        Frag = new f_price_fragment();
        btn1 = (Button) findViewById(R.id.button1);
        btn1.setBackgroundColor(Color.rgb(30, 154, 207));
        btn2 = (Button) findViewById(R.id.button2);
        btn4 = (Button) findViewById(R.id.button4);
        text1 = (TextView) findViewById(R.id.text);
        text2 = (TextView) findViewById(R.id.text2);
    }

    public HttpURLConnection getConnectionurl(String str) {
        try {
            URL url = new URL(str);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            return con;
        } catch (Exception e) {
            return null;
        }
    }

    private void parseJSON(JSONObject json, int num) throws JSONException {
        arrayToobject(json.getJSONObject("RESULT").getJSONArray("OIL"), num);
    }

    private void arrayToobject(JSONArray jsonArray, int num) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject order = jsonArray.getJSONObject(i);
            if (num == 4) {
                location.put(order.getString("OS_NM"), order.getDouble("GIS_X_COOR")+" "+order.getDouble("GIS_Y_COOR")+" "+order.getDouble("DISTANCE")+" "+order.getInt("PRICE"));
                f_offers.csum += order.getInt("PRICE");
                f_offers.cnut++;
            } else {
                if (order.getString("PRODCD").equals("B027")) {
                    if (num == 1)
                        f_offers.Allavg = order.getString("PRICE");
                    if (num == 2)
                        f_offers.Siavg = order.getString("PRICE");
                    if (num == 3)
                        f_offers.sum += Double.parseDouble(order.getString("PRICE"));
                }
            }
        }
    }

    private static String getStringFromInputStream(InputStream is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("위치 정보를 가져오는 중...");
        return dialog;
    }

    private void startLocationService() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        GPSListener gpsListener = new GPSListener();
        long minTime = 10000;
        float minDistance = 0;
        try {
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime,
                    minDistance,
                    gpsListener);
            Location lastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastLocation != null) {
                latitude = lastLocation.getLatitude();
                longitude = lastLocation.getLongitude();
            }
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }
    }

    private class GPSListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}