package com.wisteria.projectwalk.views;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by martinkubat on 03/12/15.
 */
public class LeaderboardLayout extends LinearLayout {

    private Context context;
    private LeaderboardRowLayout headerRowLayout;
    private ArrayList<LeaderboardRowLayout> rowLayouts;

    public LeaderboardLayout(Context context) {
        super(context);
        this.context = context;

        setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        setOrientation(LinearLayout.VERTICAL);

        setupHeader(); 
    }

    private void setupHeader() {
//        headerRowLayout = new LeaderboardRowLayout(this, "")
//        addView(headerRowLayout);
    }
}
