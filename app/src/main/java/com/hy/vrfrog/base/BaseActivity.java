package com.hy.vrfrog.base;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.hy.vrfrog.R;

/**
 * Created by qwe on 2017/8/7.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    private Dialog mProgressDialog;
    protected TextView mTitleTv;
    protected TextView mTitleRightTv;
    protected ImageView mBackImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 无标�?
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 竖屏显示
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);
        mBackImg = (ImageView)findViewById(R.id.nav_back);
        mTitleTv = (TextView)findViewById(R.id.nav_title);

    }




    @Override
    public void onClick(View view) {

    }

    public void showLoadingView(boolean flag) {
        if (mProgressDialog == null) {
            mProgressDialog = new Dialog(BaseActivity.this,
                    R.style.theme_dialog_alert);
            mProgressDialog.setContentView(R.layout.dialog_window_layout);
        }
        mProgressDialog.setCancelable(flag);
        mProgressDialog.show();
    }

    public void hideLoadingView() {
        mProgressDialog.dismiss();
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
