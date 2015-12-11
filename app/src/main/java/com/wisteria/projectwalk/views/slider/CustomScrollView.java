package com.wisteria.projectwalk.views.slider;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;


interface OnScrollEndListener {
    public void onScrollDidEnd(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY);
}

/**
 * Created by martinkubat on 10/12/15.
 */
public class CustomScrollView extends HorizontalScrollView {
    private CustomScrollView self;
    private Handler handler = new Handler();
    private OnScrollEndListener onScrollEndListener;
    private Runnable currentRunnable;

    public CustomScrollView(Context context) {
        super(context);

        setOnScrollChangeListener(new OnScrollChangeListener() {
            @Override
            public void onScrollChange(final View v, final int scrollX, final int scrollY, final int oldScrollX, final int oldScrollY) {
                handler.removeCallbacks(currentRunnable);

                currentRunnable = new Runnable() {
                    @Override
                    public void run() {
                        if (onScrollEndListener != null)
                            onScrollEndListener.onScrollDidEnd(v, scrollX, scrollY, oldScrollX, oldScrollY);
                    }
                };

                handler.postDelayed(currentRunnable, 40);

            }
        });
    }

    public void setOnScrollEndListener(OnScrollEndListener onScrollEndListener) {
        this.onScrollEndListener = onScrollEndListener;
    }





}
