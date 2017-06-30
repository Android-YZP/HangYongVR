package com.jt.base.videos.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.lsjwzh.widget.recyclerviewpager.LoopRecyclerViewPager;

/**
 * Created by Smith on 2017/6/29.
 */

public class MyLoopRecyclerViewPager extends LoopRecyclerViewPager {
    public MyLoopRecyclerViewPager(Context context) {
        super(context);
    }

    public MyLoopRecyclerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLoopRecyclerViewPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }






    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        super.onInterceptTouchEvent(ev);
        if (ev.getAction() == MotionEvent.ACTION_MOVE){
            getParent().requestDisallowInterceptTouchEvent(false);
        }else if (ev.getAction() == MotionEvent.ACTION_UP){
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return false;

    }



}
