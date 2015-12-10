package com.wisteria.projectwalk.views.slider;

import android.content.Context;
import android.graphics.Color;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;

import com.wisteria.projectwalk.R;

/**
 * Created by martinkubat on 07/12/15.
 */
public class SliderLayout extends HorizontalScrollView {

    private RulerLayout rulerLayout;
    private Context context;

    public SliderLayout(Context context) {
        super(context);
        this.context = context;

        setLayoutParams(
                new ScrollView.LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT
                )
        );

        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);

        setupRulerView();
    }


    private void setupRulerView() {
        rulerLayout = new RulerLayout(context);
        addView(rulerLayout);
    }

}
