package com.wisteria.projectwalk.controllers;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.wisteria.projectwalk.R;
import com.wisteria.projectwalk.models.Category;
import com.wisteria.projectwalk.models.Manager;
import com.wisteria.projectwalk.models.ManagerCallback;
import com.wisteria.projectwalk.views.LeaderboardChart;
import com.wisteria.projectwalk.views.YearSlider;

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
                leaderboardChart.refresh();

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
