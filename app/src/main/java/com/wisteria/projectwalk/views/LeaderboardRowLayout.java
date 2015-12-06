package com.wisteria.projectwalk.views;

import android.content.Context;
import android.widget.LinearLayout;

import com.wisteria.projectwalk.models.Country;

/**
 * Created by martinkubat on 03/12/15.
 */
public class LeaderboardRowLayout extends LinearLayout {
    private BarCellLayout rankingCell;
    private BarCellLayout countryCell;
    private BarCellLayout barCell;

    public LeaderboardRowLayout(Context context, int ranking, Country country, double percentage, double diffPercentage) {
        super(context);

    }
    public LeaderboardRowLayout(Context context, String rankingHeader, String countryHeader, String barHeader) {
        super(context);

    }

}
