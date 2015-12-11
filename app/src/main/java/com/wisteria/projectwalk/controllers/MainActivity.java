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
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.wisteria.projectwalk.R;
import com.wisteria.projectwalk.models.Category;
import com.wisteria.projectwalk.models.Country;
import com.wisteria.projectwalk.models.Manager;
import com.wisteria.projectwalk.models.ManagerCallback;
import com.wisteria.projectwalk.views.CountryBar;
import com.wisteria.projectwalk.views.LeaderboardChart;
import com.wisteria.projectwalk.views.slider.YearSlider;

/**
 * To be removed, this basic Activity is just to show the backend is working...
 */
public class MainActivity extends Activity implements ManagerCallback {
    private static final String TAG = "MainActivity";
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


        final Spinner countrySpinner = (Spinner)findViewById(R.id.spinner);

//        LinearLayout countryBarLayout = (LinearLayout) findViewById(R.id.CountryBar_view);
//        countryBar = new CountryBar(context);
//        countryBarLayout.addView(countryBar);

        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String cname = countrySpinner.getSelectedItem().toString();
                    manager.setUsersCountry(new Country(cname));
                } catch (NullPointerException e) {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button button = (Button) findViewById(R.id.button_cloud);
        Button button2 = (Button) findViewById(R.id.button_fuel);
        Button button3 = (Button) findViewById(R.id.button_tree);
        Button button4 = (Button) findViewById(R.id.button_average);

        //for the Fonts
        TextView FontTextview = (TextView) findViewById(R.id.textView3);
        final TextView FontTrees = (TextView) findViewById(R.id.textView2);
        TextView FontEnviorment = (TextView) findViewById(R.id.textView);

        Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        Typeface fontText = Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Light.ttf");
        final Typeface fontTree = Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Bold.ttf");
        Typeface fontEnvir = Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Regular.ttf");

        //Set the typeface for Buttons
        button.setTypeface(font);
        button2.setTypeface(font);
        button3.setTypeface(font);
        button4.setTypeface(font);

        //Set the typeface for fonts
        FontTextview.setTypeface(fontText);
        FontTrees.setTypeface(fontTree);
        FontEnviorment.setTypeface(fontEnvir);




        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button_cloud:
                        manager.setCategory(Category.C02Emissions);
                        FontTrees.setText("CO2 Emissions");
                        break;
                    case R.id.button_fuel:
                        manager.setCategory(Category.FossilFuel);
                        FontTrees.setText("Fossil Fuel");
                        break;
                    case R.id.button_tree:
                        manager.setCategory(Category.ForestArea);
                        FontTrees.setText("Forest Area");
                        break;
                    case R.id.button_average:
                        manager.setCategory(Category.Average);
                        FontTrees.setText("Average");
                        break;
                }
            }
        };

        button.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
    }

    @Override
    public void dataIsReady(Category category, int year) {
        Log.i("MainActivity", "data is ready for category " + category + ", year " + year);

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                leaderboardChart.refresh();

                Log.i("", "Status check");
                Log.i("", "" + manager.getHashmap().keySet());

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
