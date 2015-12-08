package com.wisteria.projectwalk.views;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wisteria.projectwalk.models.DummyData;
import com.wisteria.projectwalk.models.Entry;
import com.wisteria.projectwalk.models.LeaderboardDataSource;
import com.wisteria.projectwalk.models.Manager;

import java.util.ArrayList;


/**
 * Created by martinkubat on 03/12/15.
 */
public class LeaderboardLayout extends LinearLayout {

    private Context context;
    private LeaderboardRowLayout headerRowLayout;
    private ArrayList<LeaderboardRowLayout> rowLayouts = new ArrayList<>();
    private LeaderboardDataSource dataSource = Manager.getInstance();

    public LeaderboardLayout(Context context) {
        super(context);
        this.context = context;

        setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        setOrientation(LinearLayout.VERTICAL);

        setupHeader();
        setupRows();
    }

    private void setupHeader() {
        headerRowLayout = new LeaderboardRowLayout(
                context,
                "Ranking",
                "Country",
                ""
        );

        addView(headerRowLayout);
    }

    private void setupRows() {
        for (int i = 1; i < 10; i++) {
            Entry entry = dataSource.entryForRanking(i);

            LeaderboardRowLayout rowLayout = new LeaderboardRowLayout(
                    context,
                    i,
                    entry.getCountry(),
                    entry.getPercentage(),
                    entry.getDiffPercentage()
            );

            addView(rowLayout);
            rowLayouts.add(rowLayout);
        }
    }
}
