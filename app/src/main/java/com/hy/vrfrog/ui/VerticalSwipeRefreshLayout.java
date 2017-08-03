package com.hy.vrfrog.ui;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * @author Jack Tony
 * @brief 只在竖直方向才能下拉刷新的控件
 * @date 2015/4/5
 */
public class VerticalSwipeRefreshLayout extends SwipeRefreshLayout {

    private int mTouchSlop;
    // 上一次触摸时的X坐标
    private float mPrevX;

    private int width = 0;

    public void setWidth(int width) {
        this.width = width;
    }

    public VerticalSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        setWidth(dm.widthPixels);

        // 触发移动事件的最短距离，如果小于这个距离就不触发移动控件
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPrevX = ev.getX();
                if (mPrevX > width * 9 / 10) {
                    return false;
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }


}