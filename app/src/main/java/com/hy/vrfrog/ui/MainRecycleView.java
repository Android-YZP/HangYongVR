package com.hy.vrfrog.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by Smith on 2017/7/25.
 */

public class MainRecycleView extends RecyclerView {
    private int mTouchSlop;
    // 上一次触摸时的X坐标
    private float mPrevX;


    public MainRecycleView(Context context) {
        super(context);
    }

    public MainRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MainRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


}
