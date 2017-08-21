package com.hy.vrfrog.main.personal;


import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;

import com.google.gson.Gson;
import com.hy.vrfrog.http.HttpURL;
import com.hy.vrfrog.http.JsonCallBack;
import com.hy.vrfrog.http.responsebean.CreateHouseBean;
import com.hy.vrfrog.http.responsebean.CreateLiveRoom;
import com.hy.vrfrog.ui.photoChoiceDialog;
import com.hy.vrfrog.utils.LongLogUtil;
import com.hy.vrfrog.utils.NetUtil;
import com.hy.vrfrog.utils.SPUtil;
import com.hy.vrfrog.utils.UIUtils;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by qwe on 2017/8/16.
 */

public class PersonalPresenter implements PersonalContract.Presenter {


    private PersonalContract.View mView;

    PersonalPresenter(PersonalContract.View view) {

        this.mView = view;

        mView.setPresenter(this);

    }

    @Override
    public void getHttpEditRoom(String uid, String isTranscribe, String channelName, String isCharge, String price, String introduce, final ProgressDialog mProgressDialog) {
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

        LogUtil.i("开始直播 isTranscribe = " + isTranscribe);
        LogUtil.i("开始直播 isCharge = " + isCharge);


        //获取数据
        x.http().post(requestParams, new JsonCallBack() {

            @Override
            public void onSuccess(String result) {
                LongLogUtil.e("开始直播-----------", result);
                CreateLiveRoom createLiveRoom = new Gson().fromJson(result, CreateLiveRoom.class);
                int id = createLiveRoom.getResult().getId();
                mView.showId(id, createLiveRoom);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onFinished() {
                super.onFinished();
                if (mProgressDialog != null) mProgressDialog.dismiss();
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
    public void createHouseData() {

        if (SPUtil.getUser() != null) {
            RequestParams requestParams = new RequestParams(HttpURL.UpdatePersonRoom);
            requestParams.addHeader("token", HttpURL.Token);

            requestParams.addBodyParameter("uid", SPUtil.getUser().getResult().getUser().getUid() + "");//用户名
            //获取数据
            // 有上传文件时使用multipart表单, 否则上传原始文件流.
            requestParams.setMultipart(true);
            x.http().post(requestParams, new JsonCallBack() {

                @Override
                public void onSuccess(String result) {
                    LogUtil.i("创建房间 =" + result);
                    CreateHouseBean createHouseBean = new Gson().fromJson(result, CreateHouseBean.class);
                    mView.showCreateHouseData(createHouseBean);

                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

                }
            });
        } else {
            mView.goLogin();
        }

    }


    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
