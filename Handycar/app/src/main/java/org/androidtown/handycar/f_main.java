package org.androidtown.handycar;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by LEE on 2017-07-27.
 */

public class f_main extends AppCompatActivity {
    FragmentManager fm;
    Button btn1, btn2, btn3, btn4;
    TextView text1, text2;
    f_price_fragment Frag;
    f_Record_fragment Frag2;
    Intent intent;
    Server server = new Server();
    String str1, str2, str3, str4;
    String Allavg="", Siavg="";
    double sum=0.0;
    public static HashMap<String, String> location = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f_main);
        setup();
        text1.setBackgroundColor(Color.rgb(25, 147, 168));
        text2.setBackgroundColor(Color.rgb(25, 147, 168));

        GeoPoint in_pt = new GeoPoint(127.112140,37.400741);
        GeoPoint tm_pt = GeoTrans.convert(GeoTrans.GEO, GeoTrans.TM, in_pt);
        GeoPoint katec_pt = GeoTrans.convert(GeoTrans.TM, GeoTrans.KATEC, tm_pt);

        str1 = "http://www.opinet.co.kr/api/avgAllPrice.do?out=json&code=F191170721";
        str2 = "http://www.opinet.co.kr/api/avgSidoPrice.do?out=json&prodcd=B027&sido=01&code=F191170721";
        str3 = "http://www.opinet.co.kr/api/avgRecentPrice.do?out=json&prodcd=B027&code=F191170721";
        str4 = "http://www.opinet.co.kr/api/aroundAll.do?code=F191170721&x="+katec_pt.getX()+"&y="+katec_pt.getY()+"&radius=500&sort=1&prodcd=B027&out=json";

        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                fm = getFragmentManager();
                FragmentTransaction tr = fm.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("Allavg", Allavg);
                bundle.putString("Silavg", Siavg);
                String tem = String.format("%.2f", sum / 7);
                bundle.putString("sum", tem);
                Frag.setArguments(bundle);
                tr.add(R.id.Linear, Frag, "repair");
                for (int i = 0; i < 2; i++) {
                    Frag2 = new f_Record_fragment();
                    tr.add(R.id.Linear2, Frag2, "RECENT");
                }
                tr.commit();
            }
        };
        new Thread() {
            @Override
            public void run() {
                try {
                    InputStream in = new BufferedInputStream(server.getConnectionurl(str1).getInputStream());
                    JSONObject json = new JSONObject(getStringFromInputStream(in));
                    Log.d("ASD", json + "");
                    parseJSON(json, 1);

                    InputStream in1 = new BufferedInputStream(server.getConnectionurl(str2).getInputStream());
                    JSONObject json1 = new JSONObject(getStringFromInputStream(in1));
                    Log.d("ASD2", json1 + "");
                    parseJSON(json1, 2);

                    InputStream in2 = new BufferedInputStream(server.getConnectionurl(str3).getInputStream());
                    JSONObject json2 = new JSONObject(getStringFromInputStream(in2));
                    Log.d("ASD2", json2 + "");
                    parseJSON(json2, 3);

                    InputStream in3 = new BufferedInputStream(server.getConnectionurl(str4).getInputStream());
                    JSONObject json3 = new JSONObject(getStringFromInputStream(in3));
                    Log.d("ASD3", json3 + "");
                    parseJSON(json3, 4);

                    Message message = handler.obtainMessage();
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
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

    public void setup() {
        Frag = new f_price_fragment();
        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        btn4 = (Button) findViewById(R.id.button4);
        text1 = (TextView) findViewById(R.id.text);
        text2 = (TextView) findViewById(R.id.text2);
    }

    private void parseJSON(JSONObject json, int num) throws JSONException {
        arrayToobject(json.getJSONObject("RESULT").getJSONArray("OIL"), num);
    }

    private void arrayToobject(JSONArray jsonArray, int num) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject order = jsonArray.getJSONObject(i);
            if(num==4)
                location.put(order.getString("OS_NM"),order.getInt("GIS_X_COOR")+" "+order.getInt("GIS_Y_COOR"));
            else {
                if (order.getString("PRODCD").equals("B027")) {
                    if (num == 1)
                        Allavg = order.getString("PRICE");
                    if (num == 2)
                        Siavg = order.getString("PRICE");
                    if (num == 3)
                        sum += Double.parseDouble(order.getString("PRICE"));
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
}
