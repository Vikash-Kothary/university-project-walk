package com.wisteria.projectwalk.controllers;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.wisteria.projectwalk.R;
import com.wisteria.projectwalk.models.Category;
import com.wisteria.projectwalk.models.Manager;
import com.wisteria.projectwalk.views.LeaderboardLayout;

/**
 * To be removed, this basic Activity is just to show the backend is working...
 */
public class MainActivity extends Activity {

    private LinearLayout leaderboardContainerLayout;
    private LeaderboardLayout leaderboardLayout;
    private Manager manager = Manager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        leaderboardContainerLayout = (LinearLayout) findViewById(R.id.leaderboard_view);
        leaderboardLayout = new LeaderboardLayout(this);
        leaderboardContainerLayout.addView(leaderboardLayout);

        View.OnClickListener listner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.button:
                        manager.setCategory(Category.C02Emissions);
                        break;

                    case R.id.button2:
                        manager.setCategory(Category.ForestArea);
                        break;

                    case R.id.button3:
                        manager.setCategory(Category.FossilFuel);
                        break;
                }
               //manager.setCategory(Category.);
            }
        };
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(listner);
    }
}
