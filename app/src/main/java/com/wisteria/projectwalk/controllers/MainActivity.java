package com.wisteria.projectwalk.controllers;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.wisteria.projectwalk.R;
import com.wisteria.projectwalk.models.Category;
import com.wisteria.projectwalk.models.DataHandler;
import com.wisteria.projectwalk.models.Manager;
import com.wisteria.projectwalk.models.ManagerCallback;
import com.wisteria.projectwalk.views.LeaderboardLayout;

/**
 * To be removed, this basic Activity is just to show the backend is working...
 */
public class MainActivity extends Activity implements ManagerCallback {

    private LinearLayout leaderboardContainerLayout;
    private LeaderboardLayout leaderboardLayout;
    private Manager manager = Manager.getInstance();

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
        leaderboardContainerLayout = (LinearLayout) findViewById(R.id.leaderboard_view);
        leaderboardLayout = new LeaderboardLayout(this);
        leaderboardContainerLayout.addView(leaderboardLayout);
    }
}
