package com.wisteria.projectwalk.views.slider;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wisteria.projectwalk.models.Manager;
import com.wisteria.projectwalk.models.YearSliderDataSource;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by martinkubat on 07/12/15.
 */
public class RulerLayout extends LinearLayout {
    final private String TAG = "RulerLayout";
    private YearSliderDataSource dataSources = Manager.getInstance();

    private Context context;

    private DashLayout[] dashLayouts;

    public RulerLayout(Context context) {
        super(context);
        this.context = context;

        LayoutParams layoutParams = new LayoutParams(
                5000,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );


        setLayoutParams(layoutParams);



        setupDashLayouts();

        final Context tempContext = context;
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                WindowManager wm = (WindowManager) tempContext.getSystemService(Context.WINDOW_SERVICE);
                Display display = wm.getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int width = size.x;

                int padding = width / 2 - dashLayouts[0].getWidth() / 2;
                setPadding(padding , 0, padding, 0);
            }
        });



        Log.i(TAG, "This is executing");
    }

    private void setupDashLayouts() {
        ArrayList<Integer> availableYears = dataSources.getAvailableYears();
        dashLayouts = new DashLayout[availableYears.size()];

        int i = 0;
        for (Integer year: availableYears) {
            Log.i(TAG, "Year is "+year);

            DashLayout dashLayout = new DashLayout(context, year, availableYears.size() == i + 1);
            addView(dashLayout);

            dashLayouts[i] = dashLayout;
            i++;

        }

        Log.i(TAG, "dash layout "+ dashLayouts);
    }

    public DashLayout[] getDashLayouts() {
        return dashLayouts;
    }

}
