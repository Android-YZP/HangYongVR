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
import com.fhrj.library.tools.ToolAlert;
import com.fhrj.library.tools.ToolLog;
import com.fhrj.library.tools.ToolToast;
import com.fhrj.library.tools.ToolUtils;
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
public class UserNameAuthActivity extends BaseActivity {
    private Button htn_ok;
    private EditText et_name;
    private EditText et_idCard;
    private AlertDialog dialog;

    @Override
    public int bindLayout() {
        return R.layout.activity_username_auth;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView(View view) {
        iniTitle("实名认证");
        htn_ok = (Button) findViewById(R.id.btn_ok);
        et_name = (EditText) findViewById(R.id.et_name);
        et_idCard = (EditText) findViewById(R.id.et_user_id);
        htn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(et_name.getText()) || TextUtils.isEmpty(et_idCard.getText())) {
                    ToolToast.showShort(com.mytv365.view.activity.user.UserNameAuthActivity.this, "请填写姓名和身份证号码");
                    return;
                }
                if (checkID()) {
                    UserServer.usernameAuth(com.mytv365.view.activity.user.UserNameAuthActivity.this, new TextHttpResponseHandler() {
                        @Override
                        public void onStart() {
                            dialog = ToolAlert.dialog(com.mytv365.view.activity.user.UserNameAuthActivity.this, R.layout.public_dialog_load);
                            dialog.show();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            ToolLog.e("auth error=", responseString);
                            dialog.hide();
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            dialog.hide();
                            try {
                                JSONObject jsonObject = new JSONObject(responseString);
                                if (jsonObject.getString("type").equals("1")) {
                                    ToolAlert.toastShort("绑定成功！");
                                    setResult(RESULT_OK);
                                    finish();
                                } else {
                                    ToolAlert.toastShort(jsonObject.getString("msg"));
                                    ToolAlert.toastShort(jsonObject.getJSONObject("ogj").getString("result"));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, et_idCard.getText().toString(), et_name.getText().toString(), Constant.userinfo.getUserid(), Constant.userinfo.getToken());
                } else {
                    ToolToast.showShort(com.mytv365.view.activity.user.UserNameAuthActivity.this, "身份证号格式不正确");
                }
            }
        });
    }

    private boolean checkID() {
        return ToolUtils.isIdNO(et_idCard.getText().toString(), this) >= 0;
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    private void iniTitle(String title) {
        setTintManager(R.color.touming);
        initBackTitleBar(title, Gravity.CENTER);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }
}
