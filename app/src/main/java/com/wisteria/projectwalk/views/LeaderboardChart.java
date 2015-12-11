package com.wisteria.projectwalk.views;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
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
    private String filterString = "";

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
//        setVisibleXRangeMinimum(2);

        zoom(1, 10, 0, 0);
        setDrawGridBackground(false);

        getXAxis().setDrawGridLines(false);
        getAxisLeft().setDrawGridLines(false);

        getAxisLeft().setEnabled(false);
        getAxisRight().setEnabled(false);
        getXAxis().setTextSize(12f);


    }

    public void refresh() {
        ArrayList<Entry> dataEntries = dataSource.getEntries();

        if (dataEntries == null)
            return;

        ArrayList<String> labels = new ArrayList<>();
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<BarEntry> pastEntries = new ArrayList<>();



        for (int i = 0; i < dataEntries.size(); i++){

                entries.add(new BarEntry((int)dataEntries.get(i).getPercentage(), i));
                labels.add(dataEntries.get(i).getCountry().getCountryName());


        }


        // TODO: change tilte of data set
        BarDataSet currentDataSet = new BarDataSet(entries, "Percentage Change of data");

        currentDataSet.setBarSpacePercent(0f);
        currentDataSet.setValueTextSize(20f);
        currentDataSet.setColor(dataSource.colorForBar());

        BarData data = new BarData(labels, currentDataSet);
        setData(data);


        invalidate();
    }
}
