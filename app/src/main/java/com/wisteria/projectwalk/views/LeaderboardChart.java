package com.wisteria.projectwalk.views;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.wisteria.projectwalk.models.Entry;
import com.wisteria.projectwalk.models.LeaderboardDataSource;
import com.wisteria.projectwalk.models.Manager;

import java.util.ArrayList;

/**
 * Created by Shubham on 08/12/2015.
 */
public class LeaderboardChart extends HorizontalBarChart {
    private LeaderboardDataSource dataSource = Manager.getInstance();


    public LeaderboardChart(Context context) {
        super(context);

        setLayoutParams(new LinearLayout.LayoutParams(
                RadioGroup.LayoutParams.MATCH_PARENT,
                RadioGroup.LayoutParams.FILL_PARENT
        ));

        setTouchEnabled(true);
        setDragEnabled(true);
        setPinchZoom(true);

    }

    public void refresh() {
        ArrayList<Entry> dataEntries = dataSource.getEntries();

        ArrayList<String> labels = new ArrayList<>();
        ArrayList<BarEntry> entries = new ArrayList<>();

        for (int i = 0; i < dataEntries.size(); i++){

            entries.add(new BarEntry((int)dataEntries.get(i).getPercentage(), i));

            labels.add(dataEntries.get(i).getCountry().getCountryName());

        }


        BarDataSet dataset = new BarDataSet(entries, "Ranking");




        BarData data = new BarData(labels, dataset);
        setData(data);

    }


    public void changeColor(Color color) {

    }
}
