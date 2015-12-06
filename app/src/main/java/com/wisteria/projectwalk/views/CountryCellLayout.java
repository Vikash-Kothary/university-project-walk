package com.wisteria.projectwalk.views;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wisteria.projectwalk.models.Country;


/**
 * Created by martinkubat on 06/12/15.
 */
public class CountryCellLayout extends LinearLayout {

    private Context context;

    private Country country;

    private TextView nameView;
    private ImageView flagView;

    public CountryCellLayout(Context context, Country country) {
        super(context);
        this.context = context;

        setOrientation(LinearLayout.HORIZONTAL);

        this.country = country;

        setupFlag();
        setupName();

    }

    private void setupFlag() {
        // TODO: Setup flag
    }

    private void setupName() {
        nameView = new TextView(context);
        nameView.setText(country.getCountryName());
        addView(nameView);
    }

}