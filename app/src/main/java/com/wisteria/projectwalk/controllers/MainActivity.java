package com.wisteria.projectwalk.controllers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.wisteria.projectwalk.R;
import com.wisteria.projectwalk.models.Category;
import com.wisteria.projectwalk.models.Manager;
import com.wisteria.projectwalk.models.ManagerCallback;
import com.wisteria.projectwalk.views.CountryBar;
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
    private CountryBar countryBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        manager.setManagerCallback(this);

        manager.setContext(this);
        manager.initManager();


        LinearLayout leaderboardContainerLayout = (LinearLayout) findViewById(R.id.leaderboard_view);
        leaderboardContainerLayout.removeAllViews();
        leaderboardChart = new LeaderboardChart(context);
        leaderboardContainerLayout.addView(leaderboardChart);


        final Spinner spin = (Spinner)findViewById(R.id.spinner);

        final LinearLayout CountryBarLayout = (LinearLayout) findViewById(R.id.CountryBar_view);
        countryBar = new CountryBar(context);
        CountryBarLayout.addView(countryBar);


        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    countryBar = new CountryBar(context);
                    CountryBarLayout.removeAllViews();
                    CountryBarLayout.addView(countryBar);
                }else {
                    try {
                        String cname = spin.getSelectedItem().toString();

                        if (cname != null) {
                            countryBar.refresh(cname);
                            countryBar.invalidate();
                        }

                    } catch (NullPointerException e) {

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button button = (Button) findViewById(R.id.button_cloud);
        Button button2 = (Button) findViewById(R.id.button_fuel);
        Button button3 = (Button) findViewById(R.id.button_tree);

        Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");

        //Set the typeface
        button.setTypeface(font);
        button2.setTypeface(font);
        button3.setTypeface(font);

        View.OnClickListener listner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button_cloud:
                        manager.setCategory(Category.C02Emissions);
                        break;
                    case R.id.button_fuel:
                        manager.setCategory(Category.ForestArea);
                        break;
                    case R.id.button_tree:
                        manager.setCategory(Category.FossilFuel);
                        break;
                }
                LinearLayout leaderboardContainerLayout = (LinearLayout) findViewById(R.id.leaderboard_view);
                leaderboardContainerLayout.removeAllViews();
                leaderboardChart = new LeaderboardChart(context);
                leaderboardContainerLayout.addView(leaderboardChart);
            }
        };
    }

    @Override
    public void dataIsReady(Category category, int year) {
        Log.i("MainActivity", "data is ready for category "+category+", year "+year);

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