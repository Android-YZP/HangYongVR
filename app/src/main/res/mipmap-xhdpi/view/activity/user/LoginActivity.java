package com.mytv365.view.activity.user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.fhrj.library.base.impl.BaseActivity;
import com.fhrj.library.third.asynchttp.TextHttpResponseHandler;
import com.fhrj.library.tools.Tool;
import com.fhrj.library.tools.ToolAlert;
import com.fhrj.library.tools.ToolResource;
import com.fhrj.library.view.pulltorefresh.PullToRefreshBase;
import com.fhrj.library.view.pulltorefresh.PullToRefreshScrollView;
import com.mytv365.R;
import com.mytv365.common.Constant;
import com.mytv365.common.MyOnFocusChange;
import com.mytv365.entity.Userinfo;
import com.mytv365.http.UserServer;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 登陆
 * Created by zhangguohao on 16/8/1.
 */
public class LoginActivity extends BaseActivity {
    private Context mContext;
    /*滚动条*/
    private ScrollView scrollView;
    /*下拉刷新*/
    private PullToRefreshScrollView mPullScrollView;
    /*内容界面*/
    private LinearLayout parent;


    private String login = "login";
    /**
     * 用户名
     */
    private EditText username;
    /**
     * 密码
     */
    private EditText password;
    /**
     * 立即注册
     */
    private TextView register;
    /**
     * 忘了密码
     */
    private TextView back;
    /**
     * 登录
     */
    private Button loginBut;
    /**
     * 用户登录输入name
     */
    private String user_username;
    /**
     * 用户登录输入password
     */
    private String user_password;
    /**
     * SharePreferences记录用户登录信息
     */
    private SharedPreferences mSettings = null;
    /**
     * 数据库名
     */
    private String user_key = "USERID_KEY";

    private static final String TAG = "LoginActivity";

    /*加载提示*/
    private AlertDialog dialog;


    @Override
    public int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initParms(Bundle parms) {
    }

    @Override
    public void initView(View view) {
        setTintManager(R.color.touming);
        initTitle(getResources().getString(ToolResource.getStringId(login)));


    }

    @Override
    public void doBusiness(final Context mContext) {
        parent = (LinearLayout) LayoutInflater.from(mContext).inflate(
                R.layout.activity_login_content, null);
        mPullScrollView = (PullToRefreshScrollView) findViewById(R.id.scrollView);
        mPullScrollView
                .setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
                    @Override
                    public void onRefresh(
                            PullToRefreshBase<ScrollView> refreshView) {
                        String label = DateUtils.formatDateTime(mContext,
                                System.currentTimeMillis(),
                                DateUtils.FORMAT_SHOW_TIME
                                        | DateUtils.FORMAT_SHOW_DATE
                                        | DateUtils.FORMAT_ABBREV_ALL);
                        // 更新最后一次刷新时间
                        refreshView.getLoadingLayoutProxy()
                                .setLastUpdatedLabel(label);
                        // 更新最后一次刷新时间
                        refreshView.getLoadingLayoutProxy()
                                .setLastUpdatedLabel(label);
                        // foundPageIndex = 1;
                        mPullScrollView.onRefreshComplete();
                    }


                });
        /***
         * 状态发生改变时候调用
         */
        mPullScrollView.setOnPullEventListener(new PullToRefreshBase.OnPullEventListener<ScrollView>() {
            @Override
            public void onPullEvent(PullToRefreshBase<ScrollView> refreshView, PullToRefreshBase.State state, PullToRefreshBase.Mode direction) {
            }
        });

        scrollView = mPullScrollView.getRefreshableView();
        scrollView.setVerticalScrollBarEnabled(false);
        scrollView.addView(parent);

        username = (EditText) parent.findViewById(R.id.username);
        password = (EditText) parent.findViewById(R.id.passwprd);
        register = (TextView) parent.findViewById(R.id.register);
        back = (TextView) parent.findViewById(R.id.back);
        loginBut = (Button) parent.findViewById(R.id.login);


        SpannableStringBuilder builder = Tool.textViewColor(register.getText()
                .toString(), 5, 9, ContextCompat
                .getColor(mContext, R.color.theme_select));
        register.setText(builder);
        this.mContext = mContext;
        focusChange();
        mSettings = getSharedPreferences(user_key, Context.MODE_PRIVATE);

        String usernameContent = mSettings.getString("username", "");
        String passwordContent = mSettings.getString("userpassword", "");
        if (usernameContent != null && !"".equals(usernameContent)) {
            username.setText(usernameContent);
        }
        if (passwordContent != null && !"".equals(passwordContent)) {
            password.setText(passwordContent);
        }
        onclick();
    }

    /***
     * 初始化标题
     *
     * @param title
     */
    private void initTitle(String title) {
        initBackTitleBar(title, Gravity.CENTER);
        showTitleBar();
    }

    /***
     * 焦点事件
     */
    private void focusChange() {
        username.setOnFocusChangeListener(new MyOnFocusChange(username));
        password.setOnFocusChangeListener(new MyOnFocusChange(password));
    }

    /**
     * 点击事件
     */
    private void onclick() {
        register.setOnClickListener(new MyOnclick(1));
        back.setOnClickListener(new MyOnclick(2));
        loginBut.setOnClickListener(new MyOnclick(3));
    }

    /**
     * 点击事件
     */
    private class MyOnclick implements View.OnClickListener {
        public int item = 0;

        public MyOnclick(int item) {
            this.item = item;
        }

        @Override
        public void onClick(View v) {
            switch (item) {
                case 1://注册
                    getOperation().forward(com.mytv365.view.activity.user.RegisterActivity.class);
                    break;
                case 2://忘了密码
                    getOperation().forward(com.mytv365.view.activity.user.ForgetPasswordActivity.class);
                    break;
                case 3://登录
                    user_username = username.getText().toString().trim();
                    user_password = password.getText().toString().trim();
                    if (TextUtils.isEmpty(user_password) || TextUtils.isEmpty(user_username)) {
                        Toast.makeText(com.mytv365.view.activity.user.LoginActivity.this, "帐号或密码为空", Toast.LENGTH_SHORT).show();
                    } else {
                        sendLogin(user_username, user_password);
                    }
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    /***
     * 发送登录
     */
    private void sendLogin(String name, String password1) {
        UserServer.login(mContext, new TextHttpResponseHandler() {
            @Override
            public void onStart() {
                dialog = ToolAlert.dialog(mContext, R.layout.public_dialog_load);
                dialog.show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                dialog.hide();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    dialog.hide();
                    Log.i("=====", responseString);
                    JSONObject jsonobj = new JSONObject(responseString);
                    if (jsonobj.getString("type").equals("1")) {
                        JSONObject userobj = jsonobj.getJSONObject("obj");
                        Userinfo info = new Userinfo();
                        info.setAutograph(userobj.getString("autograph"));
                        info.setBirthday(userobj.getString("birthday"));
                        info.setGender(userobj.getString("gender"));
                        info.setAge(userobj.getInt("age") + "");
                        info.setQq(userobj.getString("qq"));//
                        info.setEducation(userobj.getString("xueli"));//
                        info.setJob(userobj.getString("zhiye"));//
                        info.setAddress(userobj.getString("address"));//
                        info.setHeadimgurl(userobj.has("headimgurl") ? userobj.getString("headimgurl") : "");
                        info.setNickname(userobj.getString("nickname"));
                        info.setUserid(userobj.getString("userid"));
                        info.setUsername(userobj.getString("username"));
                        info.setToken(jsonobj.getString("token"));
                        Constant.userinfo = info;
//                        Toast.makeText(LoginActivity.this, "用户信息是："+info.toString()+"token 是："+Constant.token, Toast.LENGTH_SHORT).show();
                        // 是否保存用户名
                        SharedPreferences.Editor editor = mSettings.edit();
                        editor.clear();
                        editor.putString("username", username.getText().toString().trim());
                        editor.putString("userpassword", password.getText().toString().trim());
                        editor.commit();
                        finish();
                    } else {
                        ToolAlert.toastShort(jsonobj.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, name, password1);
    }

}
