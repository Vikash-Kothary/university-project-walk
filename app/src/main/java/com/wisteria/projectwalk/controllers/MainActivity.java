package com.wisteria.projectwalk.controllers;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.wisteria.projectwalk.models.DataHandler;
import com.wisteria.projectwalk.R;
import com.wisteria.projectwalk.views.LeaderboardView;

/**
 * To be removed, this basic Activity is just to show the backend is working...
 */
public class MainActivity extends Activity {

    private LinearLayout leaderboardContainerView;
    private LeaderboardView leaderboardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); 

        leaderboardContainerView = (LinearLayout) findViewById(R.id.leaderboard_view);
        leaderboardView = new LeaderboardView(this);
        leaderboardContainerView.addView(leaderboardView);


    }
}
