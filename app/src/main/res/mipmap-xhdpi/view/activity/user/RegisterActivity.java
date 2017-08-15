package com.mytv365.view.activity.user;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

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
import com.mytv365.view.activity.WebPageActivity;

import org.apache.http.Header;
import org.json.JSONObject;


/***
 * 注册
 *
 * @author: 张国浩
 * @date: 2016年6月30日 下午7:12:53
 */
public class RegisterActivity extends BaseActivity {
    private String register = "register";

    /*滚动条*/
    private ScrollView scrollView;
    /*下拉刷新*/
    private PullToRefreshScrollView mPullScrollView;
    /*内容界面*/
    private LinearLayout parent;

    private Context mContext;
    /* 手机号 */
    private EditText ph;
    /* 密码 */
    private EditText password;
    /* 密码 */
    private EditText password1;
    /* 验证码 */
    private EditText code;
    /* 发送验证码 */
    private Button send;
    /* 注册密码 */
    private Button registerBut;
    /* 登录 */
    private TextView login;
    /*协议*/
    private CheckBox agreement;
    /*协议内容*/
    private TextView agreementText;

    /*手机号*/
    private String phs;
    /* 密码*/
    private String passwords;
    /* 密码 */
    private String passwords1;
    /* 验证码*/
    private String codes;

    private boolean isCode = true;


    private AlertDialog dialog;

    @Override
    public int bindLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView(View view) {
        setTintManager(R.color.touming);
        initTitle(getResources().getString(ToolResource.getStringId(register)));
    }

    @Override
    public void doBusiness(final Context mContext) {
        this.mContext = mContext;

        parent = (LinearLayout) LayoutInflater.from(mContext).inflate(
                R.layout.activity_register_content, null);
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


        ph = (EditText) parent.findViewById(R.id.ph);
        code = (EditText) parent.findViewById(R.id.code);
        password = (EditText) parent.findViewById(R.id.passwprd);
        password1 = (EditText) parent.findViewById(R.id.passwprd1);

        registerBut = (Button) parent.findViewById(R.id.register);
        send = (Button) parent.findViewById(R.id.send);
        login = (TextView) parent.findViewById(R.id.login);

        agreement = (CheckBox) parent.findViewById(R.id.agreement);
        agreementText = (TextView) parent.findViewById(R.id.agreement_text);


        SpannableStringBuilder builder = Tool.textViewColor(login.getText()
                .toString(), 5, 9, ContextCompat
                .getColor(mContext, R.color.theme_select));
        login.setText(builder);

        focusChange();
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
     * 焦点事件
     */
    private void focusChange() {
        ph.setOnFocusChangeListener(new MyOnFocusChange(ph));
        password.setOnFocusChangeListener(new MyOnFocusChange(password));
        password1.setOnFocusChangeListener(new MyOnFocusChange(password1));
        code.setOnFocusChangeListener(new MyOnFocusChange(code));
    }

    /***
     * 点击事件
     */
    private void onClick() {
        registerBut.setOnClickListener(new MyOnClick(1));
        send.setOnClickListener(new MyOnClick(2));
        login.setOnClickListener(new MyOnClick(3));
        agreementText.setOnClickListener(new MyOnClick(4));

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
                case 1:// 注册
                    sendRegister();
                    break;
                case 2:// 发送验证码
                    sendCode();
                    break;
                case 3:// 登录
                    finish();
                    break;
                case 4:// 协议内容
                    getOperation().addParameter("url", "http://member.mytv365.com/member/xieyi");
                    getOperation().addParameter("isShowShare", false);
                    getOperation().forward(WebPageActivity.class);
                    break;
                default:
                    break;
            }
        }
    }


    /***
     * 验证注册信息
     *
     * @return
     */
    private boolean verification() {
        phs = ph.getText().toString().trim();
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
        } else if (!passwords.equals(passwords1)) {
            ToolAlert.toastShort("两次密码不一致");
            return false;
        } else if (agreement.isChecked() == true) {
            ToolAlert.toastShort("您未同意炎黄用户协议");
            return false;
        } else {
            return true;
        }

    }

    /***
     * 发送验证码
     */
    private void sendCode() {
        phs = ph.getText().toString().trim();
        if (phs.length() == 11) {
            UserServer.registerCode(mContext, new TextHttpResponseHandler() {
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

    /***
     * 登录
     */
    private void sendRegister() {
        codes = code.getText().toString().trim();
        passwords = password.getText().toString().trim();
        passwords1 = password1.getText().toString().trim();
        if (verification()) {
            UserServer.registre(mContext, new TextHttpResponseHandler() {
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
                            ToolAlert.toastShort("注册成功！");
                            finish();
                        } else {
                            ToolAlert.toastShort(jsonObject.getString("msg"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, phs, codes, passwords);
        }
    }


}
