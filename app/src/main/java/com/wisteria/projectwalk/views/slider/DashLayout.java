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
        dashView = new ImageView(context);
        dashView.setImageResource(R.drawable.dash);
        addView(dashView);
        dashView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
    }
}
