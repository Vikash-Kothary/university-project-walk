package com.wisteria.projectwalk.views;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.wisteria.projectwalk.R;
import com.wisteria.projectwalk.models.Entry;
import com.wisteria.projectwalk.models.LeaderboardDataSource;
import com.wisteria.projectwalk.models.Manager;

import java.util.ArrayList;

/**
 * Created by Shubham on 08/12/2015.
 */
public class LeaderboardChart extends HorizontalBarChart {
    private LeaderboardDataSource dataSource = Manager.getInstance();
    private String filterString="";



    public LeaderboardChart(Context context) {
        super(context);

        setLayoutParams(new LinearLayout.LayoutParams(
                RadioGroup.LayoutParams.MATCH_PARENT,
                RadioGroup.LayoutParams.FILL_PARENT
        ));

        setTouchEnabled(true);
        setDragEnabled(true);
        setPinchZoom(false);
        setDoubleTapToZoomEnabled(false);
        setVisibleXRangeMinimum(2);




        zoom(30, 30, 1, 1);
        setDrawGridBackground(false);

        getXAxis().setDrawGridLines(false);
        getAxisLeft().setDrawGridLines(false);

        getAxisLeft().setEnabled(false);
        getAxisRight().setEnabled(false);
        getXAxis().setTextSize(12f);


    }

    public void refresh() {
        ArrayList<Entry> dataEntries = dataSource.getEntries();

        ArrayList<String> labels = new ArrayList<>();
        ArrayList<BarEntry> entries = new ArrayList<>();

        for (int i = 0; i < dataEntries.size(); i++){

                entries.add(new BarEntry((int)dataEntries.get(i).getPercentage(), i));
                labels.add(dataEntries.get(i).getCountry().getCountryName());

        }


        BarDataSet dataset = new BarDataSet(entries, "Percentage Change of data");

        dataset.setBarSpacePercent(0f);
        dataset.setValueTextSize(20f);
        dataset.setColor(Color.GREEN);

        BarData data = new BarData(labels, dataset);
        setData(data);


        invalidate();
    }




    public void changeColor(Color color) {



    }



    public void filterData(String s) {
        System.out.println(s);
        filterString = s;
        refresh();
    }
}
