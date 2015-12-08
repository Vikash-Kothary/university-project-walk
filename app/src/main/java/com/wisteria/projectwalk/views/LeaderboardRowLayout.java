package com.wisteria.projectwalk.views;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wisteria.projectwalk.models.Country;

import org.w3c.dom.Text;

/**
 * Created by martinkubat on 03/12/15.
 */
public class LeaderboardRowLayout extends LinearLayout {
    private TextView rankingCell;
    private TextView countryCell;
    private BarCellLayout barCell;

    public LeaderboardRowLayout(Context context, int ranking, Country country, double percentage, double diffPercentage) {
        super(context);

        setupBefore();

        rankingCell = new TextView(context);
        rankingCell.setText(""+ranking);
        addView(rankingCell);

        countryCell = new TextView(context);
        countryCell.setText(country.getCountryName());
        addView(countryCell);

        barCell = new BarCellLayout(context, percentage, diffPercentage);
        addView(barCell);

        setupAfter();
    }
    public LeaderboardRowLayout(Context context, String rankingHeader, String countryHeader, String barHeader) {
        super(context);

        setupBefore();

        rankingCell = new TextView(context);
        rankingCell.setText(rankingHeader);
        addView(rankingCell);

        countryCell = new TextView(context);
        countryCell.setText(countryHeader);
        addView(countryCell);


        setupAfter();
    }

    private void setupBefore() {
        setOrientation(LinearLayout.HORIZONTAL);

        setBackgroundColor(Color.CYAN);

        setWeightSum(1.0f);
    }

    private void setupAfter() {
        rankingCell.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                0.1f
        ));

        countryCell.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                0.3f
        ));

        if (barCell == null) return;

        barCell.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                0.6f
        ));
    }

}
