package com.jt.base.utils;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.jt.base.application.User;
import com.jt.base.http.HttpURL;
import com.jt.base.http.JsonCallBack;
import com.jt.base.personal.LoginActivity;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by qwe on 2017/7/28.
 */

public class UserInfoUtil {

    private static UserInfoUtil instance;
    public static UserInfoUtil getInstance(){
        if (instance == null){
            instance = new UserInfoUtil();
        }
        return  instance;
    }
    public void HttpLogin(final Context context, String phone, String password) {

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
                if (userBean.getCode() == 0) {
                    SPUtil.putUser(userBean);
                } else {
                    context.startActivity(new Intent(context, LoginActivity.class));
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                UIUtils.showTip("服务端连接失败");
            }

            @Override
            public void onFinished() {
                super.onFinished();

            }
        });
    }
}
