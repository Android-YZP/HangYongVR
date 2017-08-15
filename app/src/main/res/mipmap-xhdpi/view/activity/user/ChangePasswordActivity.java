package com.mytv365.view.activity.user;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fhrj.library.base.impl.BaseActivity;
import com.fhrj.library.third.asynchttp.TextHttpResponseHandler;
import com.fhrj.library.tools.Tool;
import com.fhrj.library.tools.ToolAlert;
import com.mytv365.R;
import com.mytv365.common.Constant;
import com.mytv365.http.UserServer;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/5
 * Description:
 */
public class ChangePasswordActivity extends BaseActivity {
    private EditText et_ph;
    private EditText et_code;
    private EditText et_passwprd;
    private EditText et_passwprd1;
    private Button send;
    private Button ok;
    /*手机号*/
    private String phs;
    /* 密码*/
    private String passwords;
    /* 密码 */
    private String passwords1;
    /* 验证码*/
    private String codes;
    private boolean isCode;
    private AlertDialog dialog;


    @Override
    public int bindLayout() {
        return R.layout.activity_change_password;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView(View view) {
        iniTitle("修改密码");
        et_ph = (EditText) findViewById(R.id.ph);
        et_code = (EditText) findViewById(R.id.code);
        et_passwprd = (EditText) findViewById(R.id.passwprd);
        et_passwprd1 = (EditText) findViewById(R.id.passwprd1);
        send = (Button) findViewById(R.id.send);
        ok = (Button) findViewById(R.id.ok);
    }

    @Override
    public void doBusiness(Context mContext) {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendCode();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendTask();
            }
        });
    }

    private void iniTitle(String title) {
        setTintManager(R.color.touming);
        initBackTitleBar(title, Gravity.CENTER);
    }

    /**
     * 修改密码
     */
    private void sendTask() {
        phs = et_ph.getText().toString().trim();
        codes = et_code.getText().toString().trim();
        passwords = et_passwprd.getText().toString().trim();
        passwords1 = et_passwprd1.getText().toString().trim();
        if (verification()) {
            UserServer.userChangePassword(this, new TextHttpResponseHandler() {
                @Override
                public void onStart() {
                    dialog = ToolAlert.dialog(com.mytv365.view.activity.user.ChangePasswordActivity.this, R.layout.public_dialog_load);
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
                            ToolAlert.toastShort("修改成功");
                            finish();
                        } else {
                            ToolAlert.toastShort(jsonObjet.getString("msg"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, codes, passwords, passwords1, Constant.userinfo.getUserid(), Constant.userinfo.getToken());
        }
    }

    /***
     * 发送验证码
     */
    private void sendCode() {
        phs = et_ph.getText().toString().trim();
        if (TextUtils.isEmpty(phs)) {
            ToolAlert.toastShort("手机号不能为空！");
            return;
        }
        if (phs.length() == 11) {
            UserServer.userGetCode(this, new TextHttpResponseHandler() {
                @Override
                public void onStart() {
                    dialog = ToolAlert.dialog(com.mytv365.view.activity.user.ChangePasswordActivity.this, R.layout.public_dialog_load);
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
                            Tool.sendCode(com.mytv365.view.activity.user.ChangePasswordActivity.this, send, 90);
                            isCode = true;
                        } else {
                            ToolAlert.toastShort(jsonObjet.getString("msg"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, phs, Constant.userinfo.getUserid(), Constant.userinfo.getToken());
        } else {
            ToolAlert.toastShort("手机号格式不正确！");
        }
    }

    /***
     * 验证注册信息
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
        } else if (!passwords.equals(passwords1)) {
            ToolAlert.toastShort("两次密码不一致");
            return false;
        } else {
            return true;
        }

    }
}
