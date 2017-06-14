package com.jt.base.login;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.google.vr.sdk.widgets.pano.VrPanoramaEventListener;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;
import com.jt.base.R;
import com.jt.base.http.HttpURL;
import com.jt.base.http.JsonCallBack;
import com.jt.base.utils.UIUtils;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class ForgetActivity extends AppCompatActivity {
    private VrPanoramaView panoWidgetView;
    public boolean loadImageSuccessful;
    private VrPanoramaView.Options panoOptions = new VrPanoramaView.Options();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        initPanorama();
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
        Glide.with(this)
                .load("https://ws1.sinaimg.cn/large/610dc034ly1ffv3gxs37oj20u011i0vk.jpg")
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        BitmapDrawable bd = (BitmapDrawable) resource;
                        panoWidgetView.loadImageFromBitmap(bd.getBitmap(), panoOptions);
                    }
                });
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
