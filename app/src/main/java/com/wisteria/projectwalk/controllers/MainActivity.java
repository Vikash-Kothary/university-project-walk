package com.wisteria.projectwalk.controllers;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.wisteria.projectwalk.R;
import com.wisteria.projectwalk.models.DataHandler;
import com.wisteria.projectwalk.views.LeaderboardLayout;

/**
 * To be removed, this basic Activity is just to show the backend is working...
 */
public class MainActivity extends Activity {

    private LinearLayout leaderboardContainerLayout;
    private LeaderboardLayout leaderboardLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        leaderboardContainerLayout = (LinearLayout) findViewById(R.id.leaderboard_view);
        leaderboardLayout = new LeaderboardLayout(this);
        leaderboardContainerLayout.addView(leaderboardLayout);


    }
}
