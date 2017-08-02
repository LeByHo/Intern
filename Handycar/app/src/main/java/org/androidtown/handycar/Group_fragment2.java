package org.androidtown.handycar;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
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

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(4f, 0));
        entries.add(new Entry(8f, 1));
        entries.add(new Entry(6f, 2));
        entries.add(new Entry(2f, 3));
        entries.add(new Entry(18f, 4));
        entries.add(new Entry(9f, 5));


        //BarDataSet dataset = new BarDataSet(entries, "# of Calls");*/

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");
        labels.add("May");
        labels.add("June");
        labels.add("May");
        labels.add("June");
        LineDataSet lineDataSet = new LineDataSet(entries, "# of Ex-Rates");
        lineDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);

        lineDataSet.setDrawCubic(true);

        lineDataSet.setDrawFilled(true); //선아래로 색상표시

        lineDataSet.setDrawValues(false);
        LineData lineData = new LineData(labels, lineDataSet);

        lineChart.setData(lineData); // set the data and list of lables into chart

        return rootView;
    }
}
