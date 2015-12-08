package com.wisteria.projectwalk.controllers;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.wisteria.projectwalk.R;
import com.wisteria.projectwalk.models.Category;
import com.wisteria.projectwalk.models.DataHandler;
import com.wisteria.projectwalk.models.Manager;
import com.wisteria.projectwalk.models.ManagerCallback;
import com.wisteria.projectwalk.views.LeaderboardChart;
import com.wisteria.projectwalk.views.LeaderboardLayout;
import com.wisteria.projectwalk.views.YearSlider;

import java.util.ArrayList;

/**
 * To be removed, this basic Activity is just to show the backend is working...
 */
public class MainActivity extends Activity implements ManagerCallback {

    private Manager manager = Manager.getInstance();
    private YearSlider yearSlider;
    private Context context = this;
    private LeaderboardChart leaderboardChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        manager.setManagerCallback(this);


        LinearLayout leaderboardContainerLayout = (LinearLayout) findViewById(R.id.leaderboard_view);
        leaderboardChart = new LeaderboardChart(context);
        leaderboardContainerLayout.addView(leaderboardChart);




    }

    @Override
    public void dataIsReady(Category category, int year) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (yearSlider == null) {
                    LinearLayout yearSliderContainer = (LinearLayout) findViewById(R.id.year_slider_container);
                    yearSlider = new YearSlider(context);
                    yearSliderContainer.addView(yearSlider);
                } else {
                    // Refresh data
                }

            }
        });

    }
}
