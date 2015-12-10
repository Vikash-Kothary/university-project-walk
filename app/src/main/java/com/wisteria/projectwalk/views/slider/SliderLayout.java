package com.wisteria.projectwalk.views.slider;

import android.content.Context;
import android.graphics.Color;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;

import com.wisteria.projectwalk.R;

/**
 * Created by martinkubat on 07/12/15.
 */
public class SliderLayout extends ScrollView {

    private RulerLayout rulerLayout;
    private ImageView pointerView;
    private Context context;

    public SliderLayout(Context context) {
        super(context);
        this.context = context;

        setBackgroundColor(Color.BLUE);

    }

    private void setupPointerView() {
        pointerView = new ImageView(context);
        pointerView.setImageDrawable(getResources().getDrawable(R.drawable.pointer));
    }
}
