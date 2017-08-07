package org.androidtown.handycar;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by LEE on 2017-07-25.
 */

public class total_Fragment extends Fragment { BarChart barChart;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.total_fragment,container,false);
        barChart = (BarChart)rootView.findViewById(R.id.chart);
        barChart.setDescription("");
        barChart.zoom(2,1,50,50);
        barChart.getAxisLeft(). setLabelCount(10,false);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisRight().setDrawLabels(false);
        barChart.getAxisRight().setDrawAxisLine(false);


        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Jan");
        labels.add("Feb");
        labels.add("Mar");
        labels.add("Apr");
        labels.add("May");
        labels.add("Jun");
        labels.add("Jul");
        labels.add("Aug");
        labels.add("Sep");
        labels.add("Oct");

        ArrayList<BarEntry> group1 = new ArrayList<>();
        Iterator<String> iterator = SpendAdapter.tm.keySet().iterator();
        int i =0 ;
        while (iterator.hasNext()) {
            String s = (String) iterator.next();
            group1.add(new BarEntry((Integer) SpendAdapter.tm.get(s),i));
            i++;
        }
        ArrayList<BarEntry> group2 = new ArrayList<>();
        Iterator<String> iterator2 = SpendAdapter.tm2.keySet().iterator();
        i =0 ;
        while (iterator2.hasNext()) {
            String s = (String) iterator2.next();
            group2.add(new BarEntry((Integer) SpendAdapter.tm2.get(s),i));
            i++;
        }

        final BarDataSet barDataSet1 = new BarDataSet(group1, "주유");
        barDataSet1.setColor(Color.rgb(032, 178, 170));
        barDataSet1.setDrawValues(true);
        //barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

        BarDataSet barDataSet2 = new BarDataSet(group2, "정비");
        barDataSet2.setColor(Color.rgb(0, 51, 102));
        barDataSet2.setDrawValues(true);

        ArrayList<BarDataSet> dataset = new ArrayList<>();
        dataset.add(barDataSet1);
        dataset.add(barDataSet2);

        BarData data = new BarData(labels, dataset);
        barChart.setData(data);
        barChart.animateY(1000);

        return rootView;
    }
}
