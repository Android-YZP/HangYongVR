package com.mytv365.view.fragment.user;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fhrj.library.base.impl.BaseFragment;
import com.fhrj.library.third.asynchttp.TextHttpResponseHandler;
import com.fhrj.library.tools.Tool;
import com.fhrj.library.tools.ToolAlert;
import com.fhrj.library.tools.ToolToast;
import com.mytv365.R;
import com.mytv365.common.Constant;
import com.mytv365.http.UserServer;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/6
 * Description:
 */
public class PhoneBindingFragment extends BaseFragment {
    private EditText et_phone;
    private EditText et_code;
    private Button btn_send;
    private Button btn_ok;
    private AlertDialog dialog;

    public static com.mytv365.view.fragment.user.PhoneBindingFragment newInstance() {

        Bundle args = new Bundle();

        com.mytv365.view.fragment.user.PhoneBindingFragment fragment = new com.mytv365.view.fragment.user.PhoneBindingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_phone_binding;
    }

    @Override
    public void initParams(Bundle params) {

    }

    @Override
    public void initView(View view) {
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_code = (EditText) findViewById(R.id.et_code);
        btn_send = (Button) findViewById(R.id.btn_send);
        btn_ok = (Button) findViewById(R.id.btn_ok);
    }

    @Override
    public void doBusiness(final Context mContext) {

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = et_phone.getText().toString();
                if (TextUtils.isEmpty(phone) || phone.length() != 11) {
                    ToolToast.showShort(mContext, "手机号格式不正确");
                    return;
                } else {
                    UserServer.bindPhoneGetCode(mContext, new TextHttpResponseHandler() {
                        @Override
                        public void onStart() {
                            dialog = ToolAlert.dialog(getActivity(), R.layout.public_dialog_load);
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
                                    Tool.sendCode(getActivity(), btn_send, 90);
                                } else {
                                    ToolAlert.toastShort(jsonObjet.getString("msg"));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, phone, Constant.userinfo.getUserid(), Constant.userinfo.getToken());
                }
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = et_phone.getText().toString();
                String code = et_code.getText().toString();
                if (TextUtils.isEmpty(phone) || phone.length() != 11) {
                    ToolToast.showShort(mContext, "手机号格式不正确");
                } else if (TextUtils.isEmpty(code)) {
                    ToolToast.showShort(mContext, "请输入验证码");
                } else {
                    UserServer.bindPhone(mContext, new TextHttpResponseHandler() {
                        @Override
                        public void onStart() {
                            dialog = ToolAlert.dialog(getActivity(), R.layout.public_dialog_load);
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
                                    ToolToast.showLong(mContext, "绑定成功");
                                    btn_send.setEnabled(false);
                                    btn_ok.setEnabled(false);
                                } else {
                                    ToolAlert.toastShort(jsonObjet.getString("msg"));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, phone, code, Constant.userinfo.getUserid(), Constant.userinfo.getToken());
                }
            }
        });
    }
}
