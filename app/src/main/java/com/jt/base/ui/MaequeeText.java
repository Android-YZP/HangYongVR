package com.jt.base.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Smith on 2017/6/29.
 */

public class MaequeeText extends TextView {

    public MaequeeText(Context context) {
        super(context);
    }

    //    重写所有的构造函数    Source==>Generate Constructors from Superclass
    public MaequeeText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MaequeeText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean isFocused() {
        return true;
//        自定义设置让focusable为true
//        这个方法相当于在layout中
//        android:focusable="true"
//        android:focusableInTouchMode="true"
    }
}