package com.hy.vrfrog.personal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.hy.vrfrog.http.responsebean.RegisterBean;
import com.hy.vrfrog.utils.NetUtil;
import com.hy.vrfrog.utils.SPUtil;
import com.hy.vrfrog.utils.StringUtils;
import com.hy.vrfrog.utils.UIUtils;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int HTTP_SUCCESS = 0;
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
    private Boolean isLogin = true;//是否是登录界面
    private boolean isRegisterYZM = true;
    private TimerTask task;
    private int recLen;
    private Timer mTimer;
    private CheckBox mRegisterCheckBox;
    private InputMethodManager mImm;
    private TextView mTvforget;
    private ImageView mTvLoginChacha;
    private Boolean isLoginChecked = true;
    private Boolean isLoginEye = true;
    private Boolean isRegisterEye1 = true;
    private Boolean isRegisterEye2 = true;
    private ImageView mEtLoginCha;
    private ImageView mTvLoginEyeClose;
    private ImageView mTvLoginEyeOpen;
    private ImageView mRegisterCha;
    private ImageView mRegisterClose;
    private ImageView mRegisterOpen;
    private ImageView mRegisterClose1;
    private ImageView mRegisterOpen1;
    private FrameLayout mflRootChacha;

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
        initData();
        initListener();
        VrApplication.contexts.add(LoginActivity.this);

    }

    private void initData() {
        String phone = (String) SPUtil.get(LoginActivity.this, "phone", "");
        String password = (String) SPUtil.get(LoginActivity.this, "password", "");
        mImm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mEtPhone.setText(phone);
        mEtPassWord.setText(password);
        Boolean rememberPassword = (Boolean) SPUtil.get(LoginActivity.this, "rememberPassword", true);
        mCheckBox.setChecked(rememberPassword);
    }

    private void initListener() {
        mLoginButton.setOnClickListener(this);
        mTvRegister.setOnClickListener(this);
        mTvLogin.setOnClickListener(this);
        mRegister.setOnClickListener(this);
        mRegisterSms.setOnClickListener(this);
        mTvforget.setOnClickListener(this);
        mflRootChacha.setOnClickListener(this);
        mRegisterCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCheckedAgree = isChecked;
            }
        });

        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isLoginChecked = isChecked;
                SPUtil.put(LoginActivity.this, "rememberPassword", isChecked);
            }
        });


        mTvLoginEyeClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HideReturnsTransformationMethod method = HideReturnsTransformationMethod.getInstance();
                mEtPassWord.setTransformationMethod(method);
                mTvLoginEyeClose.setVisibility(View.GONE);
                mTvLoginEyeOpen.setVisibility(View.VISIBLE);
            }
        });
        mTvLoginEyeOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransformationMethod method1 = PasswordTransformationMethod.getInstance();
                mEtPassWord.setTransformationMethod(method1);
                mTvLoginEyeClose.setVisibility(View.VISIBLE);
                mTvLoginEyeOpen.setVisibility(View.GONE);
            }
        });


        mRegisterClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HideReturnsTransformationMethod method = HideReturnsTransformationMethod.getInstance();
                mRegisterPassword.setTransformationMethod(method);
                mRegisterClose.setVisibility(View.GONE);
                mRegisterOpen.setVisibility(View.VISIBLE);
            }
        });
        mRegisterOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransformationMethod method1 = PasswordTransformationMethod.getInstance();
                mRegisterPassword.setTransformationMethod(method1);
                mRegisterClose.setVisibility(View.VISIBLE);
                mRegisterOpen.setVisibility(View.GONE);
            }
        });


        mRegisterClose1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HideReturnsTransformationMethod method = HideReturnsTransformationMethod.getInstance();
                mRegisterPassword2.setTransformationMethod(method);
                mRegisterClose1.setVisibility(View.GONE);
                mRegisterOpen1.setVisibility(View.VISIBLE);
            }
        });

        mRegisterOpen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransformationMethod method1 = PasswordTransformationMethod.getInstance();
                mRegisterPassword2.setTransformationMethod(method1);
                mRegisterClose1.setVisibility(View.VISIBLE);
                mRegisterOpen1.setVisibility(View.GONE);
            }
        });


        //登录手机号叉叉,清空数据
        mEtLoginCha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtPhone.setText("");
            }
        });

        //注册手机号叉叉,清空数据
        mRegisterCha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRegisterPhone.setText("");
            }
        });


        //手机好监测
        mEtPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (isLogin) {
                    if (hasFocus) {//获得焦点

                    } else {//失去焦点
                        if (!StringUtils.isPhone(mEtPhone.getText().toString())) {
                            UIUtils.showTip("请输入正确的手机号");
                        }

                    }
                }


            }
        });

        //手机好监测
        mRegisterPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!isLogin) {
                    if (hasFocus) {//获得焦点

                    } else {//失去焦点
                        if (!StringUtils.isPhone(mRegisterPhone.getText().toString())) {
                            UIUtils.showTip("请输入正确的手机号");
                        }

                    }
                }
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
        mRegisterCha = (ImageView) mRootRegisterView.findViewById(R.id.img_register_delete_cha);

        mRegisterClose = (ImageView) mRootRegisterView.findViewById(R.id.img_register_close);
        mRegisterOpen = (ImageView) mRootRegisterView.findViewById(R.id.img_register_open);
        mRegisterClose1 = (ImageView) mRootRegisterView.findViewById(R.id.img_register_open1);
        mRegisterOpen1 = (ImageView) mRootRegisterView.findViewById(R.id.img_register_close1);

        mRootLoginView = (LinearLayout) findViewById(R.id.root_login_login);
        mTvRegister = (TextView) findViewById(R.id.tv_login_register);
        mTvLogin = (TextView) findViewById(R.id.tv_login_login);
        mEtPhone = (EditText) mRootLoginView.findViewById(R.id.et_phone_item_login);
        mEtLoginCha = (ImageView) mRootLoginView.findViewById(R.id.img_login_delete_cha);
        mTvforget = (TextView) mRootLoginView.findViewById(R.id.tv_item_forget);
        mEtPassWord = (EditText) mRootLoginView.findViewById(R.id.et_password_item_login);
        mTvRemPassWord = (TextView) mRootLoginView.findViewById(R.id.tv_login_rem_password);
        mCheckBox = (CheckBox) mRootLoginView.findViewById(R.id.cb_login_checkBox);
        mLoginButton = (Button) mRootLoginView.findViewById(R.id.item_login_btn);
        mTvLoginChacha = (ImageView) findViewById(R.id.login_return_img);
        mflRootChacha = (FrameLayout) findViewById(R.id.fl_root_back);
        mTvLoginEyeClose = (ImageView) findViewById(R.id.img_login_close);
        mTvLoginEyeOpen = (ImageView) findViewById(R.id.img_login_open);

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
                if (!NetUtil.isOpenNetwork()) {
                    UIUtils.showTip("请打开网络");
                    return;
                }

                if (StringUtils.isPhone(phone)) {//判断手机号是不是手机号
                    if (!TextUtils.isEmpty(password)) {
                        HttpLogin(phone, password, false);
                        if (isLoginChecked) {//勾选记住密码
                            SPUtil.put(LoginActivity.this, "phone", mEtPhone.getText().toString());
                            SPUtil.put(LoginActivity.this, "password", mEtPassWord.getText().toString());
                        } else {
                            SPUtil.put(LoginActivity.this, "password", "");
                        }

                    } else {
                        UIUtils.showTip("密码不能为空");
                    }
                } else {
                    UIUtils.showTip("请输入正确的手机号");
                }
                break;
            case R.id.tv_login_register:
                //隐藏键盘
                if (isLogin) {
                    mImm.hideSoftInputFromWindow(mEtPhone.getWindowToken(), 0);
                }
                isLogin = false;
                mRootRegisterView.setVisibility(View.VISIBLE);
                mRootLoginView.setVisibility(View.GONE);
                mTvLogin.setTextColor(Color.parseColor("#2fffffff"));
                mTvRegister.setTextColor(Color.parseColor("#ffffffff"));


                break;
            case R.id.tv_login_login:
                //隐藏键盘
                if (!isLogin) {
                    mImm.hideSoftInputFromWindow(mRegisterPhone.getWindowToken(), 0);
                }
                isLogin = true;
                mRootRegisterView.setVisibility(View.GONE);
                mRootLoginView.setVisibility(View.VISIBLE);
                mTvLogin.setTextColor(Color.parseColor("#ffffffff"));
                mTvRegister.setTextColor(Color.parseColor("#2fffffff"));
                break;
            case R.id.btn_register:
                String registerPhone = mRegisterPhone.getText().toString();
                String registerYZM = mRegisterYZM.getText().toString();
                String registerPassword = mRegisterPassword.getText().toString();
                String registerPassword2 = mRegisterPassword2.getText().toString();
                if (!NetUtil.isOpenNetwork()) {
                    UIUtils.showTip("请打开网络");
                    return;
                }
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
                    UIUtils.showTip("两次密码输入不一致");
                    return;
                }

                if (registerPassword.length() < 6) {
                    UIUtils.showTip("最少需要输入6位密码");
                    return;
                }
                if (!isCheckedAgree) {
                    UIUtils.showTip("请先阅读用户协议");
                    return;
                }
                HttpRegister(registerPhone, registerYZM, registerPassword, registerPassword2);

                break;
            case R.id.tv_item_register_sms:////////////////////////////////////////////////////////////////////////////////////////
                if (!NetUtil.isOpenNetwork()) {
                    UIUtils.showTip("请打开网络");
                    return;
                }
                if (isSendSms) {
                    // mRegisterSms.setTextColor(Color.parseColor("#"));
                    return;
                }
                String smsPhone = mRegisterPhone.getText().toString();
                if (StringUtils.isPhone(smsPhone)) {//判断手机号是不是手机号

                    if (!isRegisterYZM) {
                        return;
                    }
                    mRegisterSms.setTextColor(Color.parseColor("#2fffffff"));
                    isRegisterYZM = false;
                    HttpYzm(smsPhone);

                } else {
                    UIUtils.showTip("请填入正确的手机号");
                }
                break;
            case R.id.tv_item_forget:
                startActivity(new Intent(LoginActivity.this, ForgetActivity.class));
                break;
            case R.id.fl_root_back:
                finish();
                break;
        }
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
//        //本地资源转换成bitmap
        Drawable drawable = getResources().getDrawable(R.mipmap.login_bg);
        BitmapDrawable bd = (BitmapDrawable) drawable;
        final Bitmap bmm = bd.getBitmap();
        panoWidgetView.loadImageFromBitmap(bmm, panoOptions);

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
        if (!NetUtil.isOpenNetwork()) {
            UIUtils.showTip("请打开网络");
            return;
        }
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
                ForgetYzmBean forgetYzmBean = new Gson().fromJson(result, ForgetYzmBean.class);
                if (forgetYzmBean.getMsg().equals("用户已存在")) {
                    UIUtils.showTip("用户已存在");
                } else if (forgetYzmBean.getMsg().equals("验证失败!")) {
                    UIUtils.showTip("验证码有误");
                } else if (forgetYzmBean.getCode() == HTTP_SUCCESS) {
                    UIUtils.showTip("发送成功 ");
                    timekeeping();
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
                mRegisterSms.setTextColor(Color.parseColor("#217081"));
                isRegisterYZM = true;
            }

        });
    }

    /**
     * 注册
     */
    private void HttpRegister(final String phone, String yzm, final String psw, String psw1) {
        if (!NetUtil.isOpenNetwork()) {
            UIUtils.showTip("请打开网络");
            return;
        }
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
                if (registerBean.getCode() == HTTP_SUCCESS) {
                    //注册成功之后直接登录
                    HttpLogin(phone, psw, true);

                } else {
                    UIUtils.showTip(registerBean.getMsg());
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
    private void HttpLogin(String phone, String password, final boolean isRegist) {
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
                User userBean = new Gson().fromJson(result, User.class);
                LogUtil.i(userBean.getMsg() + "");
                LogUtil.i(result);
                if (userBean.getCode() == HTTP_SUCCESS) {
                    SPUtil.putUser(userBean);
                    SPUtil.put(UIUtils.getContext(),"password",mEtPassWord.getText().toString());
                    SPUtil.put(UIUtils.getContext(),"phone",mEtPhone.getText().toString());
                    SPUtil.put(LoginActivity.this, "isLogin", true);
                    if (isRegist) {//判断用户是注册还是登陆
                        UIUtils.showTip("注册成功");
                    } else {
                        UIUtils.showTip("登录成功");
                    }

                    finish();
                } else {
                    UIUtils.showTip(userBean.getMsg());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
//                UIUtils.showTip("服务端连接失败");
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
