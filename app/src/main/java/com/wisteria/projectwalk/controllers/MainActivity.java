package com.wisteria.projectwalk.controllers;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.wisteria.projectwalk.R;
import com.wisteria.projectwalk.models.Category;
import com.wisteria.projectwalk.models.DataHandler;
import com.wisteria.projectwalk.models.Manager;
import com.wisteria.projectwalk.models.ManagerCallback;
import com.wisteria.projectwalk.views.LeaderboardLayout;
import com.wisteria.projectwalk.views.YearSlider;

/**
 * To be removed, this basic Activity is just to show the backend is working...
 */
public class MainActivity extends Activity implements ManagerCallback {

    private LinearLayout leaderboardContainerLayout;
    private LeaderboardLayout leaderboardLayout;
    private Manager manager = Manager.getInstance();
    private YearSlider yearSlider;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager.setManagerCallback(this);

    }

    @Override
    public void dataIsReady(Category category, int year) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (leaderboardLayout == null && yearSlider == null) {
                    leaderboardContainerLayout = (LinearLayout) findViewById(R.id.leaderboard_view);
                    leaderboardLayout = new LeaderboardLayout(context);
                    leaderboardContainerLayout.addView(leaderboardLayout);

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
