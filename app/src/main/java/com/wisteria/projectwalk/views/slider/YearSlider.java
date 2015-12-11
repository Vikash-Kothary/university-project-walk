package com.wisteria.projectwalk.views.slider;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;

import com.wisteria.projectwalk.R;
import com.wisteria.projectwalk.models.Manager;
import com.wisteria.projectwalk.models.YearSliderDataSource;
import com.wisteria.projectwalk.models.YearSliderDelegate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

/**
 * Created by martinkubat on 08/12/15.
 */
public class YearSlider extends FrameLayout {
    private YearSliderDelegate delegate = Manager.getInstance();
    private YearSliderDataSource dataSource = Manager.getInstance();
    private int minYear;
    private ArrayList<Integer> availableYears;
    private SliderLayout sliderLayout;
    private ImageView pointerView;
    private Context context;
    private int height = 112;

    private void updateProgress(){
        delegate.setCurrentYear(2001);
    }

    public YearSlider(Context context) {
        super(context);
        this.context = context;

        availableYears = dataSource.getAvailableYears();
        minYear = Collections.min(availableYears);

        setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                142
        ));

//        setBackgroundColor(Color.CYAN);

        setupSliderLayout();
        setupPointerView();

    }

    public void setDelegate(YearSliderDelegate delegate) {
        this.delegate = delegate;
    }

    private void setupSliderLayout() {
        sliderLayout = new SliderLayout(context);
        addView(sliderLayout);
    }

    private void setupPointerView() {
        pointerView = new ImageView(context);
        pointerView.setImageResource(R.drawable.pointer);
        addView(pointerView);
        pointerView.setLayoutParams(new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                height / 2
        ));
    }
}
