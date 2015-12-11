package com.wisteria.projectwalk.views.slider;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
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
public class SliderLayout extends CustomScrollView {

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

        setupSnapping();


    }


    private void setupRulerView() {
        rulerLayout = new RulerLayout(context);
        addView(rulerLayout);

    }


    private void setupSnapping() {
        setOnScrollEndListener(new OnScrollEndListener() {
            @Override
            public void onScrollDidEnd(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.i("SliderLayout", "" + scrollX + ", " + scrollY + ", " + oldScrollX + ", " + oldScrollY);


                DashLayout[] dashLayouts = rulerLayout.getDashLayouts();

                DashLayout closestDashLayout = dashLayouts[0];
                int closestDiff = 100000;

                for (int i = 0; i < dashLayouts.length; i++) {

                    int diff = Math.abs(scrollX - (int) (dashLayouts[i].getX() + dashLayouts[i].getWidth()/2) + rulerLayout.getPaddingLeft());

                    if (diff < closestDiff) {
                        closestDiff = diff;
                        closestDashLayout = dashLayouts[i];
                    }
                }

                Log.i("Year", "Year is "+closestDashLayout.getYear());

            }
        });
    }

}
