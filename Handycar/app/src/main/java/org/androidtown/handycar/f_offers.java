package org.androidtown.handycar;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Iterator;

/**
 * Created by GE62 on 2017-08-01.
 */

public class f_offers extends AppCompatActivity implements OnMapReadyCallback {
    TextView t1;
    Intent intent;
    String arr="";
    String name[]= new String[10];
    Double pointX[]= new Double[10];
    Double pointY[]= new Double[10];
    int cnt=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f_offers);
        t1 = (TextView)findViewById(R.id.textView9);

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Iterator<String> iterator = f_main.location.keySet().iterator();
        while (iterator.hasNext()) {
            String s = (String) iterator.next();
            name[cnt]=s;
            String key = f_main.location.get(s);
            String str[] = key.split(" ");

            GeoPoint katec_pt = new GeoPoint(Integer.parseInt(str[0]),Integer.parseInt(str[1]));
            GeoPoint out_pt = GeoTrans.convert(GeoTrans.KATEC, GeoTrans.GEO, katec_pt);
            Log.d("QWE",s+" "+str[0]+" "+str[1]);
            Log.d("ASD3","geo out : xGeo=" + out_pt.getY() + ", yGeo=" + out_pt.getX());
            pointX[cnt]=out_pt.getY();
            pointY[cnt]=out_pt.getX();
            cnt++;
            arr+=s + "\n";
        }
        t1.setText(arr);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng place = new LatLng(37.400741,127.112140);
        for (int idx = 0; idx < cnt; idx++) {
            // 1. 마커 옵션 설정 (만드는 과정)
            MarkerOptions makerOptions = new MarkerOptions();
            makerOptions // LatLng에 대한 어레이를 만들어서 이용할 수도 있다.
                    .position(new LatLng(pointX[idx], pointY[idx]))
                    .title(name[idx]); // 타이틀.
            // 2. 마커 생성 (마커를 나타냄)
            googleMap.addMarker(makerOptions);
        }
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(place));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }
}