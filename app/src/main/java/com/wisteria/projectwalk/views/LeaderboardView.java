package com.wisteria.projectwalk.views;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Created by martinkubat on 03/12/15.
 */
public class LeaderboardView extends LinearLayout {

    public LeaderboardView(Context context) {
        super(context);
        setBackgroundColor(Color.BLUE);

        setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        setOrientation(LinearLayout.VERTICAL);

        addView(new TextView(context));
    }




}
