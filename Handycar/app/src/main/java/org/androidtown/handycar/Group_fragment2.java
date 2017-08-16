package org.androidtown.handycar;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by LEE on 2017-08-01.
 */

public class Group_fragment2 extends Fragment {
    LineChart lineChart;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
      ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.group_fragment2,container,false);
      lineChart = (LineChart) rootView.findViewById(R.id.chart);
      // HorizontalBarChart barChart= (HorizontalBarChart) findViewById(R.id.chart);
      lineChart.setDescription("");
      ArrayList<Entry> entries = new ArrayList<>();
      ArrayList<String> labels = new ArrayList<String>();
      int i =0 ;
      for ( final String key : Group_total.scoreMap.keySet() ) {
        labels.add(key);
        entries.add(new BarEntry(Group_total.scoreMap.get(key),i));
        i++;
      }

        LineDataSet lineDataSet = new LineDataSet(entries, "Driving Score");
        lineDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        lineDataSet.setDrawCubic(false);

        lineDataSet.setDrawFilled(true); //선아래로 색상표시

        lineDataSet.setDrawValues(true);
        LineData lineData = new LineData(labels, lineDataSet);

        lineChart.setData(lineData); // set the data and list of lables into chart

        return rootView;
    }
}
