package com.wisteria.projectwalk;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * To be removed, this basic Activity is just to show the backend is working...
 */
public class MainActivity extends Activity {

    private View leaderboardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DataHandler handle = new DataHandler(MainActivity.this);

        leaderboardView = (View) findViewById(R.id.leaderboard_view);


    }
}
