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
import java.util.Iterator;

/**
 * Created by LEE on 2017-08-01.
 */

public class Group_fragment extends Fragment{
    BarChart barChart;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.group_fragment,container,false);
        /*barChart = (BarChart)rootView.findViewById(R.id.chart);
        */

         HorizontalBarChart barChart= (HorizontalBarChart)rootView.findViewById(R.id.chart);
        barChart.zoom(3,1,50,50);
        barChart.setDescription("");
        barChart.getAxisLeft(). setLabelCount(10,false);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisRight().setDrawLabels(false);
        barChart.getAxisRight().setDrawAxisLine(false);

        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<String>();

        int i =0 ;
        for ( final String key : Group_total.totalMap.keySet() ) {
            labels.add(key);
            entries.add(new BarEntry(Group_total.totalMap.get(key),i));
            i++;
        }
        BarDataSet dataset = new BarDataSet(entries, "Score");

/*
        ArrayList<BarEntry> group1 = new ArrayList<>();
        group1.add(new BarEntry(4f, 0));
        group1.add(new BarEntry(8f, 1));
        group1.add(new BarEntry(6f, 2));
        group1.add(new BarEntry(12f, 3));
        group1.add(new BarEntry(7f, 4));
        group1.add(new BarEntry(9f, 5));
        ArrayList<BarEntry> group2 = new ArrayList<>();
        group2.add(new BarEntry(6f, 0));
        group2.add(new BarEntry(7f, 1));
        group2.add(new BarEntry(8f, 2));
        group2.add(new BarEntry(12f, 3));
        group2.add(new BarEntry(15f, 4));
        group2.add(new BarEntry(10f, 5));

        *//*ArrayList<BarEntry> group3 = new ArrayList<>();
        group3.add(new BarEntry(1f, 0));
        group3.add(new BarEntry(2f, 1));
        group3.add(new BarEntry(3f, 2));
        group3.add(new BarEntry(4f, 3));
        group3.add(new BarEntry(2f, 4));
        group3.add(new BarEntry(3f, 5));*//*

        final BarDataSet barDataSet1 = new BarDataSet(group1, "소나타");
        barDataSet1.setColor(Color.rgb(032, 178, 170));

        barDataSet1.setDrawValues(false);
        //barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

        BarDataSet barDataSet2 = new BarDataSet(group2, "그랜져");
        barDataSet2.setColor(Color.rgb(0, 51, 102));
        barDataSet2.setDrawValues(false);
        //barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

    *//*    BarDataSet barDataSet3 = new BarDataSet(group3, "유지");
        barDataSet3.setDrawValues(false);
        //barDataSet3.setColors(ColorTemplate.COLORFUL_COLORS);
*//*
        ArrayList<BarDataSet> dataset = new ArrayList<>();
        dataset.add(barDataSet1);
        dataset.add(barDataSet2);
        // dataset.add(barDataSet3);*//*
*/

        BarData data = new BarData(labels, dataset);
        // dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        barChart.setData(data);


        return rootView;
    }
}
