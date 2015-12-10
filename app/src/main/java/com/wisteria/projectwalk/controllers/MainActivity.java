package com.wisteria.projectwalk.controllers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wisteria.projectwalk.R;
import com.wisteria.projectwalk.models.Category;
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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager.setManagerCallback(this);
        //Spinner spinner = (Spinner) findViewById(R.id.spinner);

        manager.setContext(this);
        manager.initManager();


        //for the Buttons
        Button button = (Button)findViewById(R.id.button_cloud);
        Button button2 = (Button)findViewById(R.id.button_fuel);
        Button button3 = (Button)findViewById(R.id.button_tree);

        //for the Fonts
        TextView FontTextview = (TextView) findViewById(R.id.textView3);
        TextView FontTrees = (TextView) findViewById(R.id.textView2);
        TextView FontEnviorment = (TextView) findViewById(R.id.textView);


        Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        Typeface fontText = Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Light.ttf");
        Typeface fontTree = Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Bold.ttf");
        Typeface fontEnvir = Typeface.createFromAsset(getAssets(),"fonts/OpenSans-Regular.ttf");

        //Set the typeface for Buttons
        button.setTypeface(font);
        button2.setTypeface(font);
        button3.setTypeface(font);

        //Set the typeface for fonts
        FontTextview.setTypeface(fontText);
        FontTrees.setTypeface(fontTree);
        FontEnviorment.setTypeface(fontEnvir);

        View.OnClickListener listner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
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
                //manager.setCategory(Category.);
            }
        };
        //button.setOnClickListener(listner);


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

    public void onClick(View view) {
        Toast.makeText(this,"C02Emissions",Toast.LENGTH_SHORT).show();
    }

    public void onClick2(View view) {
        Toast.makeText(this,"ForestArea",Toast.LENGTH_SHORT).show();
    }
    public void onClick3(View view) {
        Toast.makeText(this,"FossilFuel",Toast.LENGTH_SHORT).show();
    }

    }


