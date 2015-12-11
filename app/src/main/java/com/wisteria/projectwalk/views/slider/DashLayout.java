package com.wisteria.projectwalk.views.slider;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wisteria.projectwalk.R;

/**
 * Created by martinkubat on 07/12/15.
 */
public class DashLayout extends LinearLayout {

    private TextView yearView;
    private ImageView dashView;
    private Context context;

    private int year;

    public DashLayout(Context context, int year, boolean isLast) {
        super(context);
        this.context = context;
        this.year = year;

        setOrientation(VERTICAL);

        LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        if (!isLast) {
            layoutParams.setMargins(0, 0, 200, 0);
        }

        setLayoutParams(layoutParams);

        setupDashView();
        setupYearView(year);

    }

    public void setupYearView(int year) {
        yearView = new TextView(context);
        yearView.setText("" + year);
        yearView.setTextSize(20);
        addView(yearView);
    }

    public void setupDashView() {
        dashView = new ImageView(context);
        dashView.setImageResource(R.drawable.dash);
        addView(dashView);

        LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        dashView.setLayoutParams(layoutParams);
    }


    public int getYear() {
        return year;
    }

}
