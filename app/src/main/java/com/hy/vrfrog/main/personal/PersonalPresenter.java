package com.hy.vrfrog.main.personal;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.google.gson.Gson;
import com.hy.vrfrog.http.HttpURL;
import com.hy.vrfrog.http.JsonCallBack;
import com.hy.vrfrog.http.responsebean.CreateLiveRoom;
import com.hy.vrfrog.ui.photoChoiceDialog;
import com.hy.vrfrog.utils.LongLogUtil;
import com.hy.vrfrog.utils.NetUtil;
import com.hy.vrfrog.utils.SPUtil;
import com.hy.vrfrog.utils.UIUtils;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by qwe on 2017/8/16.
 */

public class PersonalPresenter implements PersonalContract.Presenter {


    private PersonalContract.View mView;

    PersonalPresenter( PersonalContract.View view) {

        this.mView = view;

        mView.setPresenter(this);

    }

    @Override
    public void getHttpEditRoom(String uid, String isTranscribe, String channelName, String isCharge, String price, String introduce) {
        if (!NetUtil.isOpenNetwork()) {
            UIUtils.showTip("请打开网络");
            return;
        }
        //使用xutils3访问网络并获取返回值
        RequestParams requestParams = new RequestParams(HttpURL.editRoom);
        requestParams.addHeader("token", HttpURL.Token);
        requestParams.addBodyParameter("uid", uid);
        requestParams.addBodyParameter("isTranscribe", isTranscribe);
        requestParams.addBodyParameter("channelName", channelName);
        requestParams.addBodyParameter("isCharge", isCharge);
        requestParams.addBodyParameter("price", price);
        requestParams.addBodyParameter("introduce", introduce);

        //获取数据
        x.http().post(requestParams, new JsonCallBack() {

            @Override
            public void onSuccess(String result) {
                LongLogUtil.e("-----------", result);
                CreateLiveRoom createLiveRoom = new Gson().fromJson(result, CreateLiveRoom.class);
                int id = createLiveRoom.getResult().getId();
                mView.showId(id,createLiveRoom);


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onFinished() {
                super.onFinished();
            }
        });
    }


    @Override
    public void showPhotoDialog(Context context) {

        new photoChoiceDialog(context).builder()
                .setCanceledOnTouchOutside(true)
                .setAlbumListener("", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mView.showTakePhotoForAlbum();
                    }
                })
                .setCameraListener("", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mView.showWakePhotoForCamera();
                    }
                })
                .show();

    }

    @Override
    public void openCamera() {

        mView.openCamera();

    }

    @Override
    public void openAlbum() {

        mView.openAlbum();

    }


    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
