package com.mytv365.view.activity.user;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.fhrj.library.base.impl.BaseActivity;
import com.fhrj.library.third.asynchttp.TextHttpResponseHandler;
import com.fhrj.library.tools.Tool;
import com.fhrj.library.tools.ToolAlert;
import com.fhrj.library.tools.ToolResource;
import com.fhrj.library.view.pulltorefresh.PullToRefreshBase;
import com.fhrj.library.view.pulltorefresh.PullToRefreshScrollView;
import com.mytv365.R;
import com.mytv365.common.MyOnFocusChange;
import com.mytv365.http.UserServer;

import org.apache.http.Header;
import org.json.JSONObject;

/***
 * 忘了密码
 *
 * @author ZhangGuoHao
 * @date 2016年6月13日 上午9:25:16
 */
public class ForgetPasswordActivity extends BaseActivity {
    private Context mContext;
    /*滚动条*/
    private ScrollView scrollView;
    /*下拉刷新*/
    private PullToRefreshScrollView mPullScrollView;
    /*内容界面*/
    private LinearLayout parent;

    private String forget_password = "forget_password";
    /*手机号 */
    private EditText username;
    /*密码 */
    private EditText password;
    /*验证码 */
    private EditText code;
    /*发送验证码 */
    private Button send;
    /*找回密码 */
    private Button forget;

    /*手机号*/
    private String phs;
    /* 密码*/
    private String passwords;
    /* 验证码*/
    private String codes;
    private boolean isCode = false;
    private AlertDialog dialog;


    @Override
    public int bindLayout() {
        return R.layout.activity_forget_password;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView(View view) {
        setTintManager(R.color.touming);
        initTitle(getResources().getString(ToolResource.getStringId(forget_password)));



    }

    @Override
    public void doBusiness(final Context mContext) {
        this.mContext = mContext;


        parent = (LinearLayout) LayoutInflater.from(mContext).inflate(
                R.layout.activity_forget_password_content, null);
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

        username = (EditText) parent.findViewById(R.id.username);
        password = (EditText) parent.findViewById(R.id.passwprd);
        code = (EditText) parent.findViewById(R.id.code);
        send = (Button) parent.findViewById(R.id.send);
        forget = (Button) parent.findViewById(R.id.forget);

        scrollView = mPullScrollView.getRefreshableView();
        scrollView.setVerticalScrollBarEnabled(false);
        scrollView.addView(parent);

        focusChange();
        // 点击事件
        onClick();
    }


    /***
     * 初始化标题
     */
    private void initTitle(String title) {
        initBackTitleBar(title, Gravity.CENTER);
        showTitleBar();
    }

    /***
     * 验证信息
     *
     * @return
     */
    private boolean verification() {
        if (isCode == false) {
            ToolAlert.toastShort("未发送手机验证码");
            return false;
        } else if (phs.equals("")) {
            ToolAlert.toastShort("手机号不能为空");
            return false;
        } else if (phs.length() != 11) {
            ToolAlert.toastShort("手机号格式不正确");
            return false;
        } else if (codes.equals("")) {
            ToolAlert.toastShort("验证码不能为空");
            return false;
        } else if (passwords.equals("")) {
            ToolAlert.toastShort("密码不能为空");
            return false;
        } else if (passwords.length() < 6) {
            ToolAlert.toastShort("密码不能少于6位");
            return false;
        } else {
            return true;
        }

    }

    /***
     * 焦点事件
     */
    private void focusChange() {
        username.setOnFocusChangeListener(new MyOnFocusChange(username));
        password.setOnFocusChangeListener(new MyOnFocusChange(password));
        code.setOnFocusChangeListener(new MyOnFocusChange(code));
    }

    /***
     * 点击事件
     */
    private void onClick() {
        send.setOnClickListener(new MyOnClick(2));
        forget.setOnClickListener(new MyOnClick(1));
    }


    /***
     * 发送验证码
     */
    private void sendCode() {
        phs = username.getText().toString().trim();
        if (phs.length() == 11) {
            UserServer.forgetPasswordCode(mContext, new TextHttpResponseHandler() {
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
                    dialog.hide();
                    try {
                        JSONObject jsonObjet = new JSONObject(responseString);
                        if (jsonObjet.getString("type").equals("1")) {
                            Tool.sendCode(mContext, send, 90);
                            isCode = true;
                        } else {
                            ToolAlert.toastShort(jsonObjet.getString("msg"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, phs);
        } else {
            ToolAlert.toastShort("手机号格式不正确！");
        }
    }

    /****
     * 点击事件
     *
     * @author: 张国浩
     * @date: 2016年6月30日 下午6:51:18
     */
    public class MyOnClick implements OnClickListener {
        private int item = 0;

        public MyOnClick(int item) {
            this.item = item;
        }

        @Override
        public void onClick(View v) {
            switch (item) {
                case 1:// 找回密码
                    sendForgetPassword();
                    break;
                case 2:// 发送验证码
                    sendCode();
                    break;
                default:
                    break;
            }
        }
    }


    /***
     * 登录
     */
    private void sendForgetPassword() {
        codes = code.getText().toString().trim();
        passwords = password.getText().toString().trim();
        if (verification()) {
            UserServer.forgetPassword(mContext, new TextHttpResponseHandler() {
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
                    dialog.hide();
                    try {
                        JSONObject jsonObject = new JSONObject(responseString);
                        if (jsonObject.getString("type").equals("1")) {
                            ToolAlert.toastShort("找回密码成功！");
                            finish();
                        } else {
                            ToolAlert.toastShort(jsonObject.getString("msg"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            },  codes, passwords,phs);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(dialog!=null){
            dialog.dismiss();
        }
    }

}
