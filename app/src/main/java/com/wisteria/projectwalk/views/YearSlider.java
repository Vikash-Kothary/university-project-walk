package com.wisteria.projectwalk.views;

import android.app.ActionBar;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.wisteria.projectwalk.models.Manager;
import com.wisteria.projectwalk.models.YearSliderDelegate;

/**
 * Created by martinkubat on 08/12/15.
 */
public class YearSlider extends SeekBar {
    private YearSliderDelegate delegate = Manager.getInstance();
    private int minYear;
    private int maxYear;
    private OnSeekBarChangeListener onSeekBarChangeListener = new OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            delegate.setCurrentYear(progress + minYear);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    public YearSlider(Context context, int minYear, int maxYear) {
        super(context);

        this.minYear = minYear;
        this.maxYear = maxYear;

        setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        setMax(maxYear - minYear);

        setOnSeekBarChangeListener(onSeekBarChangeListener);
    }

    public void setDelegate(YearSliderDelegate delegate) {
        this.delegate = delegate;
    }

}
