package com.jt.base.ui;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.jt.base.R;

/**
 * Created by Smith on 2017/6/30.
 */

public class BottomBar extends FrameLayout implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup mBottomGroup;
    private ViewPager viewPager;

    public BottomBar(Context context) {
        super(context);
        initView(context);
        initListener();
    }

    public BottomBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initListener();
    }

    public BottomBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initListener();
    }

    /**
     * 必须先传入viewpager
     *
     * @param viewPager
     */
    public void init(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    private void initListener() {
        mBottomGroup.setOnCheckedChangeListener(this);
    }

    private void initView(Context context) {
        View view = inflate(context, R.layout.bottom_bar_layout, null);
        mBottomGroup = (RadioGroup) view.findViewById(R.id.radio_group_bottom);
        addView(view);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rb_search:
                if (viewPager != null)
                    viewPager.setCurrentItem(0,false);
                break;
            case R.id.rb_home:
                if (viewPager != null)
                    viewPager.setCurrentItem(1,false);
                break;
            case R.id.rb_me:
                if (viewPager != null)
                    viewPager.setCurrentItem(2,false);
                break;
        }
    }
}
