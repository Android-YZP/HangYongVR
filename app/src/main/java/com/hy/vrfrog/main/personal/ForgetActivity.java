package com.hy.vrfrog.main.personal;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.vr.sdk.widgets.pano.VrPanoramaEventListener;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;
import com.hy.vrfrog.R;
import com.hy.vrfrog.application.User;
import com.hy.vrfrog.application.VrApplication;
import com.hy.vrfrog.http.HttpURL;
import com.hy.vrfrog.http.JsonCallBack;
import com.hy.vrfrog.http.responsebean.ForgetYzmBean;
import com.hy.vrfrog.http.responsebean.ResetPasswordBean;
import com.hy.vrfrog.utils.NetUtil;
import com.hy.vrfrog.utils.SPUtil;
import com.hy.vrfrog.utils.StringUtils;
import com.hy.vrfrog.utils.UIUtils;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Timer;
import java.util.TimerTask;

public class ForgetActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int HTTP_SUCCESS = 0;
    private VrPanoramaView panoWidgetView;
    public boolean loadImageSuccessful;
    private VrPanoramaView.Options panoOptions = new VrPanoramaView.Options();
    private EditText mEtForgetPhone;
    private EditText mEtForgetYZM;
    private EditText mEtForgetPassword;
    private EditText mEtForgetNewPassword;
    private TextView mTvForgetYZM;
    private Button mTvForgetCommit;
    private boolean isForgetYZM = true;
    private boolean isSendSms = false;
    private Timer mTimer;
    private TimerTask task;
    private int recLen;
    private ImageView mTvForgetChacha;
    private ProgressDialog mProgressDialog;
    private ImageView mForgetClose;
    private ImageView mForgetOpen;
    private ImageView mForgetClose1;
    private ImageView mForgetOpen1;
    private ImageView mForgetCha;
    private FrameLayout mFlRootChacha;

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
        mFlRootChacha.setOnClickListener(this);

        //手机号监测
        mEtForgetPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {//获得焦点

                } else {//失去焦点
                    if (!StringUtils.isPhone(mEtForgetPhone.getText().toString())) {
                        UIUtils.showTip("请输入正确的手机号");
                    }

                }
            }
        });

        mForgetClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HideReturnsTransformationMethod method = HideReturnsTransformationMethod.getInstance();
                mEtForgetPassword.setTransformationMethod(method);
                mForgetClose.setVisibility(View.GONE);
                mForgetOpen.setVisibility(View.VISIBLE);
            }
        });

        mForgetOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransformationMethod method1 = PasswordTransformationMethod.getInstance();
                mEtForgetPassword.setTransformationMethod(method1);
                mForgetClose.setVisibility(View.VISIBLE);
                mForgetOpen.setVisibility(View.GONE);
            }
        });

        mForgetClose1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HideReturnsTransformationMethod method = HideReturnsTransformationMethod.getInstance();
                mEtForgetNewPassword.setTransformationMethod(method);
                mForgetClose1.setVisibility(View.GONE);
                mForgetOpen1.setVisibility(View.VISIBLE);
            }
        });

        mForgetOpen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransformationMethod method1 = PasswordTransformationMethod.getInstance();
                mEtForgetNewPassword.setTransformationMethod(method1);
                mForgetClose1.setVisibility(View.VISIBLE);
                mForgetOpen1.setVisibility(View.GONE);
            }
        });

        mForgetCha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtForgetPhone.setText("");
            }
        });

    }

    private void initView() {
        mEtForgetPhone = (EditText) findViewById(R.id.et_forget_phone);
        mEtForgetYZM = (EditText) findViewById(R.id.et_forget_yzm);
        mEtForgetPassword = (EditText) findViewById(R.id.et_forget_password);
        mEtForgetNewPassword = (EditText) findViewById(R.id.et_forget_new_password);
        mTvForgetYZM = (TextView) findViewById(R.id.tv_forger_yzm);
        mTvForgetCommit = (Button) findViewById(R.id.btn_activity_forget_commit);
        mTvForgetChacha = (ImageView) findViewById(R.id.img_forget_return);
        mFlRootChacha = (FrameLayout) findViewById(R.id.fl_root_back);

        mForgetClose = (ImageView) findViewById(R.id.img_forget_close);
        mForgetClose1 = (ImageView) findViewById(R.id.img_forget_close1);
        mForgetOpen = (ImageView) findViewById(R.id.img_forget_open);
        mForgetOpen1 = (ImageView) findViewById(R.id.img_forget_open1);
        mForgetCha = (ImageView) findViewById(R.id.img_forget_delete_cha);

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
        //        //本地资源转换成bitmap
        Drawable drawable = getResources().getDrawable(R.mipmap.login_bg);
        BitmapDrawable bd = (BitmapDrawable) drawable;
        final Bitmap bmm = bd.getBitmap();
        panoWidgetView.loadImageFromBitmap(bmm, panoOptions);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /**
             * 重置密码
             */
            case R.id.tv_forger_yzm:
                if (!NetUtil.isOpenNetwork()) {
                    UIUtils.showTip("请打开网络");
                    return;
                }

                if (isSendSms) {
                    return;
                }
                String smsPhone = mEtForgetPhone.getText().toString();
                if (StringUtils.isPhone(smsPhone)) {//判断手机号是不是手机号
                    if (!isForgetYZM) {
                        return;
                    }
                    mTvForgetYZM.setTextColor(Color.parseColor("#2fffffff"));
                    isForgetYZM = false;
                    HttpResetPassWordYZM(smsPhone);

                } else {
                    UIUtils.showTip("请填入正确的手机号码");
                }

                break;
            /**
             * 退出
             */
            case R.id.fl_root_back:
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
                if (!NetUtil.isOpenNetwork()) {
                    UIUtils.showTip("请打开网络");
                    return;
                }


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
                    UIUtils.showTip("两次密码输入不一致");
                    return;
                }
                if (forgetPassword.length() < 6) {
                    UIUtils.showTip("最少需要输入6位密码");
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
        if (!NetUtil.isOpenNetwork()) {
            UIUtils.showTip("请打开网络");
            return;
        }

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
                if (resetPasswordBean.getCode() == HTTP_SUCCESS){

                    HttpLogin(phone, psw);
                } else {
                    UIUtils.showTip(resetPasswordBean.getMsg());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                UIUtils.showTip("服务端连接失败");
            }
        });


    }

    /**
     * 向网络发起登录
     */
    private void HttpLogin(String phone, String password) {
        if (!NetUtil.isOpenNetwork()) {
            UIUtils.showTip("请打开网络");
            return;
        }
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
                LogUtil.i("LOGIN=" + result);
                User userBean = new Gson().fromJson(result, User.class);
                if (userBean.getCode() == HTTP_SUCCESS) {
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
                UIUtils.showTip("服务端连接失败");
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
                LogUtil.i("YZM=" + result);
                ForgetYzmBean forgetYzmBean = new Gson().fromJson(result, ForgetYzmBean.class);
                if (forgetYzmBean.getMsg().equals("用户不存在")) {
                    UIUtils.showTip("用户不存在");
                } else if (forgetYzmBean.getMsg().equals("验证失败!")) {
                    UIUtils.showTip("验证码有误");
                } else if (forgetYzmBean.getCode() == HTTP_SUCCESS) {
                    timekeeping();
                    UIUtils.showTip("发送成功");
                } else {
                    UIUtils.showTip(forgetYzmBean.getMsg());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                UIUtils.showTip("服务端连接失败");
            }

            @Override
            public void onFinished() {
                mTvForgetYZM.setTextColor(Color.parseColor("#217081"));
                isForgetYZM = true;
            }
        });
    }


    /**
     * 计时重新发送验证码
     */
    private void timekeeping() {
        recLen = 60;
        mTimer = new Timer();
        // UI thread
        task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread( new Runnable() {      // UI thread
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
