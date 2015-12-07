package com.wisteria.projectwalk.views.slider;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by martinkubat on 07/12/15.
 */
public class DashLayout extends LinearLayout {

    private TextView yearView;
    private View dashView;
    private Context context;

    public DashLayout(Context context, int year) {
        super(context);
        this.context = context;

        setOrientation(VERTICAL);

        setupDashView();
        setupYearView(year);
    }

    public void setupYearView(int year) {
        yearView = new TextView(context);
        yearView.setText("" + year);
        addView(yearView);
    }

    public void setupDashView() {
        dashView = new View(context);
        dashView.setBackgroundColor(Color.GRAY);
    }
}
