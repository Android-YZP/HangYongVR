package com.hy.vrfrog.main.home.activitys;

import android.content.Context;

import com.google.gson.Gson;
import com.hy.vrfrog.http.HttpURL;
import com.hy.vrfrog.http.JsonCallBack;
import com.hy.vrfrog.http.responsebean.GiveRewardBean;
import com.hy.vrfrog.http.responsebean.VodbyTopicBean;
import com.hy.vrfrog.utils.NetUtil;
import com.hy.vrfrog.utils.SPUtil;
import com.hy.vrfrog.utils.ToolToast;
import com.hy.vrfrog.utils.UIUtils;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by qwe on 2017/8/20.
 */

public class VideoPresenter implements VideoContract.Presenter {

    private VideoContract.VideoView mVideoView;


    public VideoPresenter(VideoContract.VideoView videoView){

        this.mVideoView = videoView;
        mVideoView.setPresenter(this);

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void getRewardData(int count, int position, List<VodbyTopicBean.ResultBean> mData) {

        if (!NetUtil.isOpenNetwork()) {
            UIUtils.showTip("请打开网络");
            return;
        }

        if (SPUtil.getUser() != null){
            RequestParams requestParams = new RequestParams(HttpURL.Pay);
            requestParams.addHeader("token", SPUtil.getUser().getResult().getUser().getToken());
            requestParams.addBodyParameter("uid",SPUtil.getUser().getResult().getUser().getUid()+"");
            requestParams.addBodyParameter("type",2+"");
            requestParams.addBodyParameter("vid",mData.get(position).getId()+ "");
            requestParams.addBodyParameter("money",count+"");
            requestParams.addBodyParameter("yid",mData.get(position).getUid()+"");

            LogUtil.i("打赏token = " + SPUtil.getUser().getResult().getUser().getToken());
            LogUtil.i("打赏uid = " + SPUtil.getUser().getResult().getUser().getUid());
            LogUtil.i("打赏vid = " + mData.get(position).getId());
            LogUtil.i("打赏yid = " + mData.get(position).getUid());
            LogUtil.i("打赏money = " + count);

            LogUtil.i("打赏type = " + 2);

            //获取数据
            x.http().post(requestParams, new JsonCallBack() {
                @Override
                public void onSuccess(String result) {

                    GiveRewardBean giveBean = new Gson().fromJson(result,GiveRewardBean.class);
                    mVideoView.showGetRewardResult(giveBean);
                    LogUtil.i("打赏 = " +  result);


                }
                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

                    mVideoView.showGetRewardResultError(ex);

                }

                @Override
                public void onFinished() {

                }
            });

        }else {
            UIUtils.showTip("请登陆");
        }
    }

    @Override
    public void goPayMoney(int count, final int position, List<VodbyTopicBean.ResultBean> mData) {
        if (!NetUtil.isOpenNetwork()) {
            UIUtils.showTip("请打开网络");
            return;
        }

        if (SPUtil.getUser() != null){
            RequestParams requestParams = new RequestParams(HttpURL.Pay);
            requestParams.addHeader("token", SPUtil.getUser().getResult().getUser().getToken());
            requestParams.addBodyParameter("uid",SPUtil.getUser().getResult().getUser().getUid()+"");
            requestParams.addBodyParameter("type",1+"");
            requestParams.addBodyParameter("vid",mData.get(position).getId()+ "");
            requestParams.addBodyParameter("money",count+"");
            requestParams.addBodyParameter("yid",mData.get(position).getUid()+"");

            LogUtil.i("支付token = " + SPUtil.getUser().getResult().getUser().getToken());
            LogUtil.i("支付uid = " + SPUtil.getUser().getResult().getUser().getUid());
            LogUtil.i("支付vid = " + mData.get(position).getId());
            LogUtil.i("支付yid = " + mData.get(position).getUid());
            LogUtil.i("支付money = " + count);

            LogUtil.i("支付type = " + 1);

            //获取数据
            x.http().post(requestParams, new JsonCallBack() {
                @Override
                public void onSuccess(String result) {

                    LogUtil.i("支付 = " +  result);

                    GiveRewardBean giveBean = new Gson().fromJson(result,GiveRewardBean.class);
                    mVideoView.showPayMoneyResult(position,giveBean);

                }
                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

                    mVideoView.showPayMoneyResultError(ex);

                }

                @Override
                public void onFinished() {

                }
            });

        }else {
            UIUtils.showTip("请登陆");
        }
    }
}
