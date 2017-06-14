package com.jt.base.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.jt.base.R;
import com.jt.base.http.HttpURL;
import com.jt.base.http.JsonCallBack;
import com.jt.base.utils.UIUtils;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class ForgetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

    }



    /**
     * 重置密码
     */
    private void HttpResetPassWord(String phone, String yzm, String psw, String psw1) {
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


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                UIUtils.showTip(ex.getMessage());
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


}
