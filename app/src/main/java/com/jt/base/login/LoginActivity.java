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
import android.widget.CompoundButton;
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
import com.jt.base.http.responsebean.RegisterBean;
import com.jt.base.utils.SPUtil;
import com.jt.base.utils.StringUtils;
import com.jt.base.utils.UIUtils;
import com.tencent.cos.task.Task;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Timer;
import java.util.TimerTask;

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
    private TextView mTvRegister;
    private TextView mTvLogin;
    private LinearLayout mRootRegisterView;
    private LinearLayout mRootLoginView;
    private EditText mRegisterPhone;
    private EditText mRegisterPassword;
    private EditText mRegisterPassword2;
    private EditText mRegisterYZM;
    private TextView mRegisterSms;
    private Button mRegister;
    private Boolean isSendSms = false;
    private Boolean isCheckedAgree = true;//默认勾选用户协议

    private TimerTask task;
    private int recLen = 120;
    private Timer mTimer;
    private CheckBox mRegisterCheckBox;

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
        initListener();

    }

    private void initListener() {
        mLoginButton.setOnClickListener(this);
        mTvRegister.setOnClickListener(this);
        mTvLogin.setOnClickListener(this);
        mRegister.setOnClickListener(this);
        mRegisterSms.setOnClickListener(this);
        mRegisterCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCheckedAgree = isChecked;
            }
        });
    }

    private void initView() {
        mRootRegisterView = (LinearLayout) findViewById(R.id.root_login_register);
        mRegisterPhone = (EditText) mRootRegisterView.findViewById(R.id.et_item_register_phone);
        mRegisterYZM = (EditText) mRootRegisterView.findViewById(R.id.et_item_register_yzm);
        mRegisterSms = (TextView) mRootRegisterView.findViewById(R.id.tv_item_register_sms);
        mRegisterPassword = (EditText) mRootRegisterView.findViewById(R.id.et_item_register_password);
        mRegisterPassword2 = (EditText) mRootRegisterView.findViewById(R.id.et_item_register_password2);
        mRegister = (Button) mRootRegisterView.findViewById(R.id.btn_register);
        mRegisterCheckBox = (CheckBox) mRootRegisterView.findViewById(R.id.cb_register_checkBox);

        mRootLoginView = (LinearLayout) findViewById(R.id.root_login_login);
        mTvRegister = (TextView) findViewById(R.id.tv_login_register);
        mTvLogin = (TextView) findViewById(R.id.tv_login_login);
        mEtPhone = (EditText) mRootLoginView.findViewById(R.id.et_phone_item_login);
        mEtPassWord = (EditText) mRootLoginView.findViewById(R.id.et_password_item_login);
        mTvRemPassWord = (TextView) mRootLoginView.findViewById(R.id.tv_login_rem_password);
        mCheckBox = (CheckBox) mRootLoginView.findViewById(R.id.cb_login_checkBox);
        mLoginButton = (Button) mRootLoginView.findViewById(R.id.item_login_btn);
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
                break;
            case R.id.tv_login_register:
                mRootRegisterView.setVisibility(View.VISIBLE);
                mRootLoginView.setVisibility(View.GONE);
                break;
            case R.id.tv_login_login:
                mRootRegisterView.setVisibility(View.GONE);
                mRootLoginView.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_register:
                String registerPhone = mRegisterPhone.getText().toString();
                String registerYZM = mRegisterYZM.getText().toString();
                String registerPassword = mRegisterPassword.getText().toString();
                String registerPassword2 = mRegisterPassword2.getText().toString();
                if (!StringUtils.isPhone(registerPhone)) {
                    UIUtils.showTip("请输入正确的手机号码");
                    return;
                }
                if (TextUtils.isEmpty(registerYZM)) {
                    UIUtils.showTip("请输入验证码");
                    return;
                }
                if (TextUtils.isEmpty(registerPassword) || TextUtils.isEmpty(registerPassword2)) {
                    UIUtils.showTip("请输入密码");
                    return;
                }
                if (!registerPassword.equals(registerPassword2)) {
                    UIUtils.showTip("二次密码输入不一致");
                    return;
                }
                if (!isCheckedAgree) {
                    UIUtils.showTip("您未同意协议");
                    return;
                }
                HttpRegister(registerPhone, registerYZM, registerPassword, registerPassword2);

                break;
            case R.id.tv_item_register_sms:
                if (isSendSms) {
                    return;
                }
                String smsPhone = mRegisterPhone.getText().toString();
                if (StringUtils.isPhone(smsPhone)) {//判断手机号是不是手机号
                    HttpYzm(smsPhone);
                    timekeeping();
                } else {
                    UIUtils.showTip("请填入正确的手机号");
                }
                break;
        }
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
                        mRegisterSms.setText("已发送  " + recLen);
                        if (recLen < 0) {
                            mTimer.cancel();
                            isSendSms = false;//时间到了就可以再次发送了
                            mRegisterSms.setText("短信验证码");
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
     * 验证码
     */
    private void HttpYzm(String phone) {
        //使用xutils3访问网络并获取返回值
        RequestParams requestParams = new RequestParams(HttpURL.SendYzm);
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
     * 注册
     */
    private void HttpRegister(final String phone, String yzm, final String psw, String psw1) {
        //使用xutils3访问网络并获取返回值
        RequestParams requestParams = new RequestParams(HttpURL.Register);
        requestParams.addHeader("token", HttpURL.Token);
        //包装请求参数
        requestParams.addBodyParameter("phone", phone);//手机号
        requestParams.addBodyParameter("yzm", yzm);//验证码
        requestParams.addBodyParameter("psw", psw);//密码
        requestParams.addBodyParameter("psw1", psw1);//密码2
        //获取数据
        x.http().post(requestParams, new JsonCallBack() {

            @Override
            public void onSuccess(String result) {
                LogUtil.i(result);
                RegisterBean registerBean = new Gson().fromJson(result, RegisterBean.class);
                if (registerBean.getMsg().equals("success")) {
                    //注册成功之后直接登录
                    HttpLogin(phone,psw);

                } else {
                    UIUtils.showTip(registerBean.getMsg());
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
                    finish();
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
