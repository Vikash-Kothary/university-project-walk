package com.wisteria.projectwalk.views.slider;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wisteria.projectwalk.models.Manager;
import com.wisteria.projectwalk.models.YearSliderDataSource;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by martinkubat on 07/12/15.
 */
public class RulerLayout extends LinearLayout {
    final private String TAG = "RulerLayout";
    private YearSliderDataSource dataSources = Manager.getInstance();

    private Context context;
    private DashLayout[] dashLayouts;

    public RulerLayout(Context context) {
        super(context);
        this.context = context;

        setLayoutParams(new LayoutParams(
                5000,
                5000
        ));

        setupDashLayouts();


        Log.i(TAG, "This is executing");
    }

    private void setupDashLayouts() {
        ArrayList<Integer> availableYears = dataSources.getAvailableYears();

        for (Integer year: availableYears) {
            Log.i(TAG, "Year is "+year);

            DashLayout dashLayout = new DashLayout(context, year);
            addView(dashLayout);

        }
    }
}
