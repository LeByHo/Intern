package org.androidtown.handycar;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeMap;

/**
 * Created by LEE on 2017-08-01.
 */

public class Group_fragment extends Fragment{
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.group_fragment,container,false);

        BarChart barChart= (BarChart)rootView.findViewById(R.id.chart);
        barChart.zoom(1,1,40,40);
        barChart.getAxisLeft(). setLabelCount(10,false);
        barChart.setDescription("");
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisRight().setDrawLabels(false);
        barChart.getAxisRight().setDrawAxisLine(false);

        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<String>();

        TreeMap<String, Integer> treeMap = new TreeMap<String, Integer>( Group_total.totalMap);
        Iterator<String> treeMapIter = treeMap.keySet().iterator();
        int i =0 ;
        while( treeMapIter.hasNext()) {
            String key = treeMapIter.next();
            labels.add(key);
            entries.add(new BarEntry(treeMap.get( key )-1,i));
            i++;
        }
        BarDataSet dataset = new BarDataSet(entries, "지출");

        BarData data = new BarData(labels, dataset);
        barChart.setData(data);

        return rootView;
    }
}