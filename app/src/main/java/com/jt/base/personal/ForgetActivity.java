package com.jt.base.personal;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.google.vr.sdk.widgets.pano.VrPanoramaEventListener;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;
import com.jt.base.R;
import com.jt.base.application.User;
import com.jt.base.application.VrApplication;
import com.jt.base.http.HttpURL;
import com.jt.base.http.JsonCallBack;
import com.jt.base.http.responsebean.ResetPasswordBean;
import com.jt.base.utils.SPUtil;
import com.jt.base.utils.StringUtils;
import com.jt.base.utils.UIUtils;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Timer;
import java.util.TimerTask;

public class ForgetActivity extends AppCompatActivity implements View.OnClickListener {
    private VrPanoramaView panoWidgetView;
    public boolean loadImageSuccessful;
    private VrPanoramaView.Options panoOptions = new VrPanoramaView.Options();
    private EditText mEtForgetPhone;
    private EditText mEtForgetYZM;
    private EditText mEtForgetPassword;
    private EditText mEtForgetNewPassword;
    private TextView mTvForgetYZM;
    private Button mTvForgetCommit;
    private boolean isSendSms = false;
    private Timer mTimer;
    private TimerTask task;
    private int recLen;
    private ImageView mTvForgetChacha;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        initPanorama();
        initView();
        initListener();
        VrApplication.contexts.add(this);
    }

    private void initListener() {
        mTvForgetYZM.setOnClickListener(this);
        mTvForgetCommit.setOnClickListener(this);
        mTvForgetChacha.setOnClickListener(this);
    }

    private void initView() {
        mEtForgetPhone = (EditText) findViewById(R.id.et_forget_phone);
        mEtForgetYZM = (EditText) findViewById(R.id.et_forget_yzm);
        mEtForgetPassword = (EditText) findViewById(R.id.et_forget_password);
        mEtForgetNewPassword = (EditText) findViewById(R.id.et_forget_new_password);
        mTvForgetYZM = (TextView) findViewById(R.id.tv_forger_yzm);
        mTvForgetCommit = (Button) findViewById(R.id.btn_activity_forget_commit);
        mTvForgetChacha = (ImageView) findViewById(R.id.img_forget_return);

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
    public void onClick(View v) {
        switch (v.getId()) {
            /**
             * 重置密码
             */
            case R.id.tv_forger_yzm:

                if (isSendSms) {
                    return;
                }
                String smsPhone = mEtForgetPhone.getText().toString();
                if (StringUtils.isPhone(smsPhone)) {//判断手机号是不是手机号
                    HttpResetPassWordYZM(smsPhone);
                    timekeeping();
                } else {
                    UIUtils.showTip("请填入正确的手机号");
                }

                break;
            /**
             * 退出
             */
            case R.id.img_forget_return:
                finish();
                break;
            /**
             * 提交请求
             */
            case R.id.btn_activity_forget_commit:
                String forgetPhone = mEtForgetPhone.getText().toString();
                String forgetYZM = mEtForgetYZM.getText().toString();
                String forgetPassword = mEtForgetPassword.getText().toString();
                String forgetPassword2 = mEtForgetNewPassword.getText().toString();
                if (!StringUtils.isPhone(forgetPhone)) {
                    UIUtils.showTip("请输入正确的手机号码");
                    return;
                }
                if (TextUtils.isEmpty(forgetYZM)) {
                    UIUtils.showTip("请输入验证码");
                    return;
                }
                if (TextUtils.isEmpty(forgetPassword) || TextUtils.isEmpty(forgetPassword2)) {
                    UIUtils.showTip("请输入密码");
                    return;
                }
                if (!forgetPassword.equals(forgetPassword2)) {
                    UIUtils.showTip("二次密码输入不一致");
                    return;
                }
                HttpResetPassWord(forgetPhone, forgetYZM, forgetPassword, forgetPassword2);
                break;
        }
    }

    /**
     * 重置密码
     */
    private void HttpResetPassWord(final String phone, String yzm, final String psw, String psw1) {
        //使用xutils3访问网络并获取返回值
        RequestParams requestParams = new RequestParams(HttpURL.UpdatePassWord);
        requestParams.addHeader("token", HttpURL.Token);
        //包装请求参数
        requestParams.addBodyParameter("phone", phone);//用户名
        requestParams.addBodyParameter("yzm", yzm);//验证码
        requestParams.addBodyParameter("psw", psw);//密码
        requestParams.addBodyParameter("psw1", psw1);//密码2
        //获取数据
        x.http().post(requestParams, new JsonCallBack() {

            @Override
            public void onSuccess(String result) {
                LogUtil.i(result);
                ResetPasswordBean resetPasswordBean = new Gson().fromJson(result, ResetPasswordBean.class);
                if (resetPasswordBean.getMsg().equals("success")) {

                    HttpLogin(phone, psw);
                } else {
                    UIUtils.showTip(resetPasswordBean.getMsg());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                UIUtils.showTip(ex.getMessage());
            }
        });


    }

    /**
     * 向网络发起登录
     */
    private void HttpLogin(String phone, String password) {
        mProgressDialog = ProgressDialog.show(ForgetActivity.this, null, "请稍后...", true, true);
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
                    SPUtil.put(ForgetActivity.this, "isLogin", true);
                    UIUtils.showTip("密码重置成功");
                    //连续退出二个界面
                    for (int i = 0; i < VrApplication.contexts.size(); i++) {
                        VrApplication.contexts.get(i).finish();
                    }
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
     * 重置密码的验证码
     */
    private void HttpResetPassWordYZM(String phone) {
        //使用xutils3访问网络并获取返回值
        RequestParams requestParams = new RequestParams(HttpURL.RestPassWordYZM);
        requestParams.addHeader("token", HttpURL.Token);
        //包装请求参数
        requestParams.addBodyParameter("phone", phone);//用户名
        //获取数据
        x.http().post(requestParams, new JsonCallBack() {

            @Override
            public void onSuccess(String result) {
                LogUtil.i(result);


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                UIUtils.showTip(ex.getMessage());
            }
        });
    }


    /**
     * 计时重新发送验证码
     */
    private void timekeeping() {
        recLen = 120;
        mTimer = new Timer();
        // UI thread
        task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {      // UI thread
                    @Override
                    public void run() {
                        recLen--;
                        mTvForgetYZM.setText("已发送  " + recLen);
                        if (recLen < 0) {
                            mTimer.cancel();
                            isSendSms = false;//时间到了就可以再次发送了
                            mTvForgetYZM.setText("短信验证码");
                        }
                    }
                });
            }
        };
        //从现在起过10毫秒以后，每隔1000毫秒执行一次。
        mTimer.schedule(task, 10, 1000);    // timeTask
        isSendSms = true;//不能再发验证码了
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
