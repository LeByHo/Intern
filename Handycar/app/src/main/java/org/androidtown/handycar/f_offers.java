package org.androidtown.handycar;

import android.app.FragmentManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Iterator;

/**
 * Created by GE62 on 2017-08-01.
 */

public class f_offers extends AppCompatActivity implements OnMapReadyCallback {
    Button b1, b2;
    String arr = "";
    String name[] = new String[10];
    Double pointX[] = new Double[10];
    Double pointY[] = new Double[10];
    int cnt = 0;
    public static int cnut = 0;
    public static String Allavg = "", Siavg = "";
    public static Double dis[] = new Double[10];
    public static int pri[] = new int[10];
    public static double sum = 0.0, csum = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f_offers);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFFFF));
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        b2.setBackgroundColor(Color.rgb(25, 147, 168));
        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Iterator<String> iterator = f_main.location.keySet().iterator();
        while (iterator.hasNext()) {
            String s = (String) iterator.next();
            name[cnt] = s;
            String key = f_main.location.get(s);
            String str[] = key.split(" ");

            GeoPoint katec_pt = new GeoPoint(Integer.parseInt(str[0]), Integer.parseInt(str[1]));
            GeoPoint out_pt = GeoTrans.convert(GeoTrans.KATEC, GeoTrans.GEO, katec_pt);
            pointX[cnt] = out_pt.getY();
            pointY[cnt] = out_pt.getX();
            cnt++;
            arr += s + "\n";
        }
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MarkerOptions makerOptions = new MarkerOptions();
        LatLng place = new LatLng(37.400741, 127.112140);
        makerOptions.position(new LatLng(37.400741, 127.112140));
        googleMap.addMarker(makerOptions);
        for (int idx = 0; idx < cnt; idx++) {
            // 1. 마커 옵션 설정 (만드는 과정)
            makerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            makerOptions // LatLng에 대한 어레이를 만들어서 이용할 수도 있다.
                    .position(new LatLng(pointX[idx], pointY[idx]))
                    .title(name[idx]) // 타이틀.
                    .snippet(dis[cnt - idx - 1] + "m" + " / " + pri[cnt - idx - 1] + "원");
            // 2. 마커 생성 (마커를 나타냄)
            googleMap.addMarker(makerOptions);
        }
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(place));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }
}