package com.mytv365.view.activity.user;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.fhrj.library.base.SystemBarTintManager;
import com.mytv365.R;
import com.mytv365.view.fragment.user.PhoneBindedFragment;
import com.mytv365.view.fragment.user.PhoneBindingFragment;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/6
 * Description:
 */
public class PhoneBindActivity extends FragmentActivity {
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private PhoneBindingFragment bindingFragment;
    private PhoneBindedFragment bindedFragment;
    private LinearLayout ll_left_btns;
    private boolean isBinded = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_bind);
        ll_left_btns = (LinearLayout) findViewById(R.id.ll_left_btns);
        isBinded = getIntent().getBooleanExtra("isBinded", true);
        if (savedInstanceState == null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (isBinded) {
                bindedFragment = PhoneBindedFragment.newInstance();
                ft.replace(R.id.fl_fragment, bindedFragment).commit();
            } else {
                bindingFragment = PhoneBindingFragment.newInstance();
                ft.replace(R.id.fl_fragment, bindingFragment).commit();
            }
        }
        initTitle("手机绑定");
    }

    /***
     * 初始化标题
     */
    private void initTitle(String title) {
        setTintManager(R.color.touming);
        ll_left_btns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void goToBind() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
//        ft.setCustomAnimations(R.anim.fragment_slide_in, R.anim.fragment_slide_out);
        if (bindingFragment == null) {
            bindingFragment = PhoneBindingFragment.newInstance();
        }
        ft.replace(R.id.fl_fragment, bindingFragment).commit();
    }

    public void setIsBinded(boolean isBinded) {
        this.isBinded = isBinded;
    }

    /***
     * 修改状态栏颜色
     *
     * @param color
     */
    @SuppressLint("InlinedApi")
    public void setTintManager(int color) {

        SystemBarTintManager tintManager;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            setTranslucentStatus(true);
            tintManager = new SystemBarTintManager(this);
            WindowManager.LayoutParams attrs = getWindow().getAttributes();

            if ((attrs.flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager.LayoutParams.FLAG_FULLSCREEN) {
                tintManager.setStatusBarTintEnabled(false);
            } else {
                tintManager.setStatusBarTintEnabled(true);
            }
            tintManager.setStatusBarTintResource(color);//通知栏所需颜色

        }
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
