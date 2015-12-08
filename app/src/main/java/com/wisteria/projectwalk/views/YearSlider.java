package com.wisteria.projectwalk.views;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.wisteria.projectwalk.models.Manager;
import com.wisteria.projectwalk.models.YearSliderDataSource;
import com.wisteria.projectwalk.models.YearSliderDelegate;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by martinkubat on 08/12/15.
 */
public class YearSlider extends SeekBar {
    private YearSliderDelegate delegate = Manager.getInstance();
    private YearSliderDataSource dataSource = Manager.getInstance();
    private int minYear;
    private ArrayList<Integer> availableYears;

    private OnSeekBarChangeListener onSeekBarChangeListener = new OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            delegate.setCurrentYear(availableYears.get(progress));

            System.out.println("Year: " + availableYears.get(progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    public YearSlider(Context context) {
        super(context);

        availableYears = dataSource.getAvailableYears();
        minYear = Collections.min(availableYears);
        setMax(availableYears.size() - 1);

        setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        setOnSeekBarChangeListener(onSeekBarChangeListener);
    }

    public void setDelegate(YearSliderDelegate delegate) {
        this.delegate = delegate;
    }

}
