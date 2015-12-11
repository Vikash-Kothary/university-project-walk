package com.wisteria.projectwalk.views;

import android.content.Context;
import android.graphics.Color;
import android.text.style.SuperscriptSpan;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.wisteria.projectwalk.models.Country;
import com.wisteria.projectwalk.models.Entry;
import com.wisteria.projectwalk.models.LeaderboardDataSource;
import com.wisteria.projectwalk.models.Manager;

import java.util.ArrayList;

/**
 * Created by Shubham on 10/12/2015.
 */
public class CountryBar extends HorizontalBarChart {

    private LeaderboardDataSource dataSource = Manager.getInstance();

    public CountryBar(Context context){
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

        setDrawGridBackground(false);

        getXAxis().setDrawGridLines(false);
        getAxisLeft().setDrawGridLines(false);

        getAxisLeft().setEnabled(false);
        getAxisRight().setEnabled(false);

        getXAxis().setTextSize(30f);




    }

    public void refresh(String s) {
        ArrayList<Entry> dataEntries = dataSource.getEntries();

        ArrayList<String> labels = new ArrayList<>();
        ArrayList<BarEntry> entries = new ArrayList<>();

        for (int i = 0; i < dataEntries.size(); i++) {

            if (s.equals(dataEntries.get(i).getCountry().getCountryName())){
                entries.add(new BarEntry((int) dataEntries.get(i).getPercentage(), 0));
                labels.add(dataEntries.get(i).getCountry().getCountryName());
            }



        }




        BarDataSet dataset = new BarDataSet(entries, "Percentage Change of data");

        dataset.setBarSpacePercent(0f);
        dataset.setValueTextSize(20f);
//        dataset.setColor(Color.parseColor("50E399"));

        BarData data = new BarData(labels, dataset);
        setData(data);

    }
}
