package com.jt.base.login;

import android.app.ProgressDialog;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.google.vr.sdk.widgets.pano.VrPanoramaEventListener;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;
import com.jt.base.R;
import com.jt.base.application.User;
import com.jt.base.http.HttpURL;
import com.jt.base.http.JsonCallBack;
import com.jt.base.utils.SPUtil;
import com.jt.base.utils.StringUtils;
import com.jt.base.utils.UIUtils;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private VrPanoramaView panoWidgetView;
    public boolean loadImageSuccessful;
    private VrPanoramaView.Options panoOptions = new VrPanoramaView.Options();
    private EditText mEtPhone;
    private EditText mEtPassWord;
    private TextView mTvRemPassWord;
    private CheckBox mCheckBox;
    private Button mLoginButton;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initPanorama();
        initView();

        mLoginButton.setOnClickListener(this);
    }

    private void initView() {
        LinearLayout rootView = (LinearLayout) findViewById(R.id.root_login_register);
        mEtPhone = (EditText) rootView.findViewById(R.id.et_phone_item_login);
        mEtPassWord = (EditText) rootView.findViewById(R.id.et_password_item_login);
        mTvRemPassWord = (TextView) rootView.findViewById(R.id.tv_login_rem_password);
        mCheckBox = (CheckBox) rootView.findViewById(R.id.cb_login_checkBox);
        mLoginButton = (Button) rootView.findViewById(R.id.item_login_btn);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /**
             * 登录操作
             */
            case R.id.item_login_btn:
                String phone = mEtPhone.getText().toString();
                String password = mEtPassWord.getText().toString();
                if (StringUtils.isPhone(phone)) {//判断手机号是不是手机号
                    if (!TextUtils.isEmpty(password)) {
                        HttpLogin(phone, password);
                    } else {
                        UIUtils.showTip("密码不能为空");
                    }
                } else {
                    UIUtils.showTip("请输入正确的手机号");
                }
        }
    }

    /**
     * 初始化全景图播放器
     */
    private void initPanorama() {
        loadImageSuccessful = false;//初始化图片状态
        panoWidgetView = (VrPanoramaView) findViewById(R.id.pano_view_login);
        panoWidgetView.setEventListener(new ActivityEventListener());
        //影藏三個界面的按鈕
        panoWidgetView.setFullscreenButtonEnabled(false);
        panoWidgetView.setInfoButtonEnabled(false);
        panoWidgetView.setStereoModeButtonEnabled(false);
        panoWidgetView.setOnTouchListener(null);//禁用手势滑动
        panoOptions.inputType = VrPanoramaView.Options.TYPE_MONO;
        //加载背景图片
        Glide.with(this)
                .load("https://ws1.sinaimg.cn/large/610dc034ly1ffv3gxs37oj20u011i0vk.jpg")
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        BitmapDrawable bd = (BitmapDrawable) resource;
                        panoWidgetView.loadImageFromBitmap(bd.getBitmap(), panoOptions);
                    }
                });
    }

    @Override
    protected void onPause() {
        panoWidgetView.pauseRendering();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        panoWidgetView.resumeRendering();
    }

    @Override
    protected void onDestroy() {
        // Destroy the widget and free memory.
        panoWidgetView.shutdown();
        super.onDestroy();
    }

    /**
     * 向网络发起登录
     */
    private void HttpLogin(String phone, String password) {
        mProgressDialog = ProgressDialog.show(LoginActivity.this, null, "正在登录...", true, true);
        //使用xutils3访问网络并获取返回值
        RequestParams requestParams = new RequestParams(HttpURL.Login);
        requestParams.addHeader("token", HttpURL.Token);
        //包装请求参数
        requestParams.addBodyParameter("phone", phone);//用户名
        requestParams.addBodyParameter("password", password);//用户名
        //获取数据
        x.http().post(requestParams, new JsonCallBack() {

            @Override
            public void onSuccess(String result) {
                LogUtil.i(result);
                User userBean = new Gson().fromJson(result, User.class);
                LogUtil.i(userBean.getMsg() + "");
                if (userBean.getMsg().equals("success")) {
                    SPUtil.putUser(userBean);
                } else {
                    UIUtils.showTip(userBean.getMsg());
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                UIUtils.showTip(ex.getMessage());
            }

            @Override
            public void onFinished() {
                super.onFinished();
                if (mProgressDialog != null) {
                    mProgressDialog.dismiss();
                }
            }
        });


    }

    /**
     * Listen to the important events from widget.
     */
    private class ActivityEventListener extends VrPanoramaEventListener {
        /**
         * Called by pano widget on the UI thread when it's done loading the image.
         */
        @Override
        public void onLoadSuccess() {
            loadImageSuccessful = true;
            Log.e("dflefseofjsdopfj", "Could not decode default bitmap: 2");
            panoWidgetView.setVisibility(View.VISIBLE);
        }

        /**
         * Called by pano widget on the UI thread on any asynchronous error.
         */
        @Override
        public void onLoadError(String errorMessage) {
            Log.e("dflefseofjsdopfj", "Could not decode default bitmap: 3");
            loadImageSuccessful = false;
        }
    }

}
