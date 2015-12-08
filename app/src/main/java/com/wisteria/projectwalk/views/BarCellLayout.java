package com.wisteria.projectwalk.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by martinkubat on 03/12/15.
 */
public class BarCellLayout extends LinearLayout {

    private double percentage;
    private double diffPercent;
    private TextView textView;
    private int color;

    public BarCellLayout(Context context, double percentage, double diffPercent) {
        super(context);

        this.percentage = percentage;
        this.diffPercent = percentage;

        textView = new TextView(context);
        textView.setText(""+percentage);
        addView(textView);
    }

}
